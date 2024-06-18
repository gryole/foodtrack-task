package com.foodtruck.api.controllers;

import com.foodtruck.api.dao.FoodTruckDao;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

@ExtendWith(MockitoExtension.class)
class FoodTruckControllerTest {
  @Mock private FoodTruckDao foodTruckDao;

  @InjectMocks private FoodTruckController foodTruckController;

  @Test
  void shouldThrowExceptionWhenLatitudeIsNull() {
    Assertions.assertThatThrownBy(
            () -> foodTruckController.searchFoodTrucks("applicant", null, null, 1., null))
        .isInstanceOf(ResponseStatusException.class);
  }

  @Test
  void shouldThrowExceptionWhenLongitudeIsNull() {
    Assertions.assertThatThrownBy(
            () -> foodTruckController.searchFoodTrucks("applicant", null, 1., null, null))
        .isInstanceOf(ResponseStatusException.class);
  }

  @Test
  void shouldSearchWithAllNulls() {
    foodTruckController.searchFoodTrucks(null, null, null, null, null);

    Mockito.verify(foodTruckDao).search(null, null, null, null, 2.);
  }
}
