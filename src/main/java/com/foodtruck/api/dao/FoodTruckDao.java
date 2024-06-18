package com.foodtruck.api.dao;

import static java.util.function.Predicate.not;

import com.foodtruck.api.common.Utils;
import com.foodtruck.api.model.FoodTruck;
import com.foodtruck.api.service.LoadDataService;
import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FoodTruckDao {
  private final LoadDataService loadDataService;
  private List<FoodTruck> data = new ArrayList<>();

  @PostConstruct
  public void init() {
    data = loadDataService.parseData();
  }

  public List<FoodTruck> getAll() {
    return List.copyOf(data);
  }

  public List<String> getAllFoodItems() {
    return data.stream()
        .parallel()
        .map(FoodTruck::foodItems)
        .filter(Objects::nonNull)
        .flatMap(Collection::stream)
        .filter(not(String::isBlank))
        .distinct()
        .sorted()
        .toList();
  }

  public List<FoodTruck> search(
      String applicant, List<String> foodItems, Double latitude, Double longitude, double radius) {
    return data.stream()
        .parallel()
        .filter(foodTruck -> searchByApplicant(applicant, foodTruck))
        .filter(foodTruck -> searchByFoodItems(foodItems, foodTruck))
        .filter(foodTruck -> searchNearest(latitude, longitude, foodTruck, radius))
        .toList();
  }

  private static boolean searchNearest(
      Double latitude, Double longitude, FoodTruck foodTruck, double radius) {
    if (Objects.isNull(latitude) || Objects.isNull(longitude)) {
      return true;
    }
    return Objects.nonNull(foodTruck.latitude())
        && Objects.nonNull(foodTruck.longitude())
        && Utils.calculateDistance(foodTruck.latitude(), foodTruck.longitude(), latitude, longitude)
            <= radius;
  }

  private static boolean searchByFoodItems(List<String> foodItems, FoodTruck foodTruck) {
    if (Objects.isNull(foodItems)) {
      return true;
    }
    return Objects.nonNull(foodTruck.foodItems()) && foodTruck.foodItems().containsAll(foodItems);
  }

  private static boolean searchByApplicant(String applicant, FoodTruck foodTruck) {
    if (Objects.isNull(applicant) || applicant.isBlank()) {
      return true;
    }
    return Objects.nonNull(foodTruck.applicant())
        && foodTruck.applicant().toLowerCase().contains(applicant.toLowerCase());
  }
}
