package com.foodtruck.api.dao;

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
}