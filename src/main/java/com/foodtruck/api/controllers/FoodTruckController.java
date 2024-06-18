package com.foodtruck.api.controllers;

import com.foodtruck.api.common.Utils;
import com.foodtruck.api.dao.FoodTruckDao;
import com.foodtruck.api.model.FoodTruck;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
public class FoodTruckController {

  private static final Double DEFAULT_RADIUS_KM = 2.;

  private final FoodTruckDao foodTruckDao;

  @GetMapping("/foodtrucks")
  public ResponseEntity<List<FoodTruck>> getAllFoodTrucks() {
    return ResponseEntity.ok(foodTruckDao.getAll());
  }

  @GetMapping("/foodtrucks/facilityTypes")
  public ResponseEntity<List<String>> getAllFacilityTypes() {
    return ResponseEntity.ok(foodTruckDao.getAllFacilityTypes());
  }

  @GetMapping("/foodtrucks/foodItems")
  public ResponseEntity<List<String>> getFoodItems() {
    return ResponseEntity.ok(foodTruckDao.getAllFoodItems());
  }

  @GetMapping("/foodtrucks/search")
  public ResponseEntity<List<FoodTruck>> searchFoodTrucks(
      @RequestParam(required = false) String applicant,
      @RequestParam(required = false) List<String> foodItems,
      @RequestParam(required = false) Double latitude,
      @RequestParam(required = false) Double longitude,
      @RequestParam(required = false) Double radiusKm) {
    double radius = radiusKm == null ? DEFAULT_RADIUS_KM : radiusKm;
    if (latitude != null && longitude == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "longitude should not be null");
    }
    if (longitude != null && latitude == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "latitude should not be null");
    }
    List<FoodTruck> results =
        foodTruckDao.getAll().stream()
            .parallel()
            .filter(foodTruck -> searchByApplicant(applicant, foodTruck))
            .filter(foodTruck -> searchByFoodItems(foodItems, foodTruck))
            .filter(foodTruck -> searchNearest(latitude, longitude, foodTruck, radius))
            .toList();
    return ResponseEntity.ok(results);
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
    return Objects.isNull(foodItems) || foodTruck.foodItems().containsAll(foodItems);
  }

  private static boolean searchByApplicant(String applicant, FoodTruck foodTruck) {
    return Objects.isNull(applicant)
        || applicant.isBlank()
        || foodTruck.applicant().toLowerCase().contains(applicant.toLowerCase());
  }
}
