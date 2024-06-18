package com.foodtruck.api.controllers;

import com.foodtruck.api.dao.FoodTruckDao;
import com.foodtruck.api.model.FoodTruck;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FoodTruckController {

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
  public ResponseEntity<FoodTruck> searchFoodTrucks(
      @RequestParam String name,
      @RequestParam List<String> foodItems,
      @RequestParam String latitude,
      @RequestParam String longitude,
      @RequestParam String radius) {
    // TODO
    return ResponseEntity.ok().build();
  }
}
