package com.foodtruck.api.model;

import lombok.Builder;

@Builder
public record FoodTruck(
    String locationId,
    String applicant,
    String facilityType,
    String locationDescription,
    String address,
    String status,
    String foodItems,
    String latitude,
    String longitude,
    String schedule,
    String daysHours) {}
