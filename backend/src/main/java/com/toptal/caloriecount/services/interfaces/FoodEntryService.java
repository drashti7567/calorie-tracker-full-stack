package com.toptal.caloriecount.services.interfaces;

import com.toptal.caloriecount.exceptions.FoodEntryNotFoundException;
import com.toptal.caloriecount.exceptions.UserIdNotFoundException;
import com.toptal.caloriecount.exceptions.UserNotAuthorizedException;
import com.toptal.caloriecount.payloads.request.foodEntry.FoodEntryRequest;
import com.toptal.caloriecount.payloads.response.foodEntry.GetFoodEntriesResponse;
import com.toptal.caloriecount.payloads.response.misc.MessageResponse;

import java.util.Map;

public interface FoodEntryService {
    MessageResponse addEntry(FoodEntryRequest request) throws UserIdNotFoundException;

    MessageResponse updateEntry(String entryId, FoodEntryRequest request) throws FoodEntryNotFoundException, UserNotAuthorizedException, UserIdNotFoundException;

    MessageResponse deleteEntry(String userId, String entryId) throws FoodEntryNotFoundException, UserNotAuthorizedException, UserIdNotFoundException;

    GetFoodEntriesResponse getEntries(String userId, Map<String, String> params);
}
