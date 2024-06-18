package com.foodtruck.api.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.foodtruck.api.model.FoodTruck;
import com.foodtruck.api.service.LoadDataService;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FoodTruckDaoTest {

  @Mock private LoadDataService loadDataService;

  @InjectMocks private FoodTruckDao foodTruckDao;

  @Test
  void shouldReturnFacilityTypes() {
    when(loadDataService.parseData())
        .thenReturn(
            List.of(
                FoodTruck.builder().facilityType("facilityTypeA").build(),
                FoodTruck.builder().facilityType("facilityTypeB").build(),
                FoodTruck.builder().facilityType(null).build(),
                FoodTruck.builder().facilityType(" ").build()));
    foodTruckDao.init();

    List<String> allFacilityTypes = foodTruckDao.getAllFacilityTypes();

    assertThat(allFacilityTypes).containsExactly("facilityTypeA", "facilityTypeB");
  }

  @Test
  void shouldReturnAllFoodItems() {
    when(loadDataService.parseData())
        .thenReturn(
            List.of(
                FoodTruck.builder().foodItems(Set.of("itemA", "itemB")).build(),
                FoodTruck.builder().foodItems(Set.of("itemC", "itemA", " ")).build(),
                FoodTruck.builder().foodItems(null).build(),
                FoodTruck.builder().foodItems(Set.of()).build()));
    foodTruckDao.init();

    List<String> allFoodItems = foodTruckDao.getAllFoodItems();

    assertThat(allFoodItems).containsExactly("itemA", "itemB", "itemC");
  }

  @Test
  void shouldSearchByApplicant() {
    when(loadDataService.parseData())
        .thenReturn(
            List.of(
                FoodTruck.builder().applicant("applicantAAA").build(),
                FoodTruck.builder().applicant("applicantAAB").build(),
                FoodTruck.builder().applicant("applicantACC").build(),
                FoodTruck.builder().applicant(" ").build(),
                FoodTruck.builder().applicant(null).build()));
    foodTruckDao.init();

    List<FoodTruck> foodTrucks = foodTruckDao.search("aa", null, null, null, 1);
    assertThat(foodTrucks)
        .containsExactly(
            FoodTruck.builder().applicant("applicantAAA").build(),
            FoodTruck.builder().applicant("applicantAAB").build());
  }

  @Test
  void shouldSearchByFoodItems() {
    when(loadDataService.parseData())
        .thenReturn(
            List.of(
                FoodTruck.builder().foodItems(Set.of("itemA", "itemB")).build(),
                FoodTruck.builder().foodItems(Set.of("itemA", "itemC", " ")).build(),
                FoodTruck.builder().foodItems(Set.of("itemC", "itemD", " ")).build(),
                FoodTruck.builder().foodItems(null).build(),
                FoodTruck.builder().foodItems(Set.of()).build()));
    foodTruckDao.init();

    List<FoodTruck> foodTrucks = foodTruckDao.search(null, List.of("itemC"), null, null, 1);
    assertThat(foodTrucks)
        .containsExactly(
            FoodTruck.builder().foodItems(Set.of("itemA", "itemC", " ")).build(),
            FoodTruck.builder().foodItems(Set.of("itemC", "itemD", " ")).build());
  }

  @Test
  void shouldSearchByCoordinates() {
    when(loadDataService.parseData())
        .thenReturn(
            List.of(
                FoodTruck.builder().latitude(38.8976).longitude(-77.0366).build(),
                FoodTruck.builder().latitude(20.9496).longitude(-77.0366).build(),
                FoodTruck.builder().latitude(null).longitude(null).build()));
    foodTruckDao.init();

    List<FoodTruck> foodTrucks = foodTruckDao.search(null, null, 39.9496, -75.1503, 250);
    assertThat(foodTrucks)
        .containsExactly(FoodTruck.builder().latitude(38.8976).longitude(-77.0366).build());
  }
}
