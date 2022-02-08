package com.toptal.caloriecount.payloads.response.foodEntry;

import com.toptal.caloriecount.payloads.response.misc.MessageResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetFoodEntriesResponse extends MessageResponse {
    private List<FoodEntryFields> foodEntryList;
    private Float calorieLimit;
}
