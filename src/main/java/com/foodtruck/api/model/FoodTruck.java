package com.foodtruck.api.model;

import java.util.Set;
import lombok.Builder;

@Builder
public record FoodTruck(
    String locationId,
    String applicant,
    String facilityType,
    String locationDescription,
    String address,
    String status,
    Set<String> foodItems,
    Double latitude,
    Double longitude,
    String schedule,
    String daysHours) {}
