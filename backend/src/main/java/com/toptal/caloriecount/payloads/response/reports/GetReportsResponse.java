package com.toptal.caloriecount.payloads.response.reports;

import com.toptal.caloriecount.payloads.response.misc.MessageResponse;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class GetReportsResponse extends MessageResponse {
    private Integer currentWeekEntriesCount;
    private Integer pastWeekEntriesCount;
    private Float averageCaloriesCurrentWeek;
    private Float averageCaloriesPastWeek;
    private List<EntriesPerDayResponse> currentWeekEntriesList;
    private List<EntriesPerDayResponse> pastWeekEntriesList;
    private List<AverageCaloriePerUserResponse> averageCaloriesList;
}
