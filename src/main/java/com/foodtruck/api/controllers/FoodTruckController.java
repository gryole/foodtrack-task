package com.foodtruck.api.controllers;

import com.foodtruck.api.model.FoodTruck;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FoodTruckController {

  @GetMapping("/foodtrucks")
  public ResponseEntity<FoodTruck> getAllFoodTrucks() {
    // TODO
    return ResponseEntity.ok().build();
  }

  @GetMapping("/foodtrucks/facilityTypes")
  public ResponseEntity<String> getAllFacilityTypes() {
    // TODO
    return ResponseEntity.ok().build();
  }

  @GetMapping("/foodtrucks/foodItems")
  public ResponseEntity<String> getFoodItems() {
    // TODO
    return ResponseEntity.ok().build();
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
