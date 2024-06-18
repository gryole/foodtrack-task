package com.foodtruck.api.service;

import com.foodtruck.api.model.FoodTrack;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
public class LoadDataService {
  private static final String COMMA_DELIMITER = ",";
  public static final int LOCATION_ID_INDEX = 0;
  public static final int APPLICANT_INDEX = 1;
  public static final int FACILITY_TYPE_INDEX = 2;
  public static final int LOCATION_DESCRIPTION_INDEX = 4;
  public static final int ADDRESS_INDEX = 5;
  public static final int STATUS_INDEX = 10;
  public static final int FOOD_ITEMS_INDEX = 11;
  public static final int LATITUDE_INDEX = 14;
  public static final int LONGITUDE_INDEX = 15;
  public static final int SCHEDULE_INDEX = 16;
  public static final int DAYS_HOURS_INDEX = 17;

  @Value("${foodtrack.dataFile}")
  private String dataFile;

  @SneakyThrows
  public List<FoodTrack> parseData() {
    List<FoodTrack> foodTracks = new ArrayList<>();
    InputStream resource = new ClassPathResource(dataFile).getInputStream();
    try (BufferedReader br = new BufferedReader(new InputStreamReader(resource))) {
      // Skip first line with header
      br.readLine();
      String line;
      while ((line = br.readLine()) != null) {
        String[] values = line.split(COMMA_DELIMITER);
        FoodTrack foodTrack =
            FoodTrack.builder()
                .locationId(values[LOCATION_ID_INDEX])
                .applicant(values[APPLICANT_INDEX])
                .facilityType(values[FACILITY_TYPE_INDEX])
                .locationDescription(values[LOCATION_DESCRIPTION_INDEX])
                .address(values[ADDRESS_INDEX])
                .status(values[STATUS_INDEX])
                .foodItems(values[FOOD_ITEMS_INDEX])
                .latitude(values[LATITUDE_INDEX])
                .longitude(values[LONGITUDE_INDEX])
                .schedule(values[SCHEDULE_INDEX])
                .daysHours(values[DAYS_HOURS_INDEX])
                .build();
        foodTracks.add(foodTrack);
      }
    }
    return foodTracks;
  }
}