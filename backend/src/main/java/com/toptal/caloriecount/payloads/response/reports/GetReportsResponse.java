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
    private List<AverageCaloriePerUserResponse> averageCaloriesList;
}
