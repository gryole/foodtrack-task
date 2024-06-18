package com.foodtruck.api.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.foodtruck.api.model.FoodTruck;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class LoadDataServiceTest {
  private final LoadDataService loadDataService = new LoadDataService();

  @BeforeEach
  void setUp() {
    ReflectionTestUtils.setField(loadDataService, "dataFile", "test_data.csv");
  }

  @Test
  void shouldParseData() {
    List<FoodTruck> foodTrucks = loadDataService.parseData();

    assertThat(foodTrucks)
        .contains(
            FoodTruck.builder()
                .locationId("735318")
                .applicant("Ziaurehman Amini")
                .facilityType("Push Cart")
                .locationDescription("MARKET ST: DRUMM ST intersection")
                .address("5 THE EMBARCADERO")
                .status("REQUESTED")
                .foodItems(Set.of("South American/Peruvian food"))
                .latitude(37.7943310032468)
                .longitude(-122.395811053023)
                .schedule(
                    "http://bsm.sfdpw.org/PermitsTracker/reports/report.aspx?title=schedule&report=rptSchedule&params=permit=15MFF-0159&ExportPDF=1&Filename=15MFF-0159_schedule.pdf")
                .daysHours("Mo-We:7AM-7PM")
                .build());
  }
}
