package com.foodtruck.api.dao;

import static java.util.function.Predicate.not;

import com.foodtruck.api.model.FoodTruck;
import com.foodtruck.api.service.LoadDataService;
import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
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

  public List<String> getAllFacilityTypes() {
    return data.stream()
        .parallel()
        .map(FoodTruck::facilityType)
        .distinct()
        .filter(not(String::isBlank))
        .toList();
  }

  public List<String> getAllFoodItems() {
    return data.stream()
        .parallel()
        .flatMap(foodTruck -> foodTruck.foodItems().stream())
        .distinct()
        .sorted()
        .toList();
  }
}
