package com.toptal.caloriecount.payloads.response.reports;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class EntriesPerDayResponse {
    private String date;
    private String day;
    private Integer totalEntries;
}
