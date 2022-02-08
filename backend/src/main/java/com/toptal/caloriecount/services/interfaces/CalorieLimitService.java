package com.toptal.caloriecount.services.interfaces;

import com.toptal.caloriecount.exceptions.UserIdNotFoundException;
import com.toptal.caloriecount.payloads.request.calorieLimit.UpdateCalorieLimitRequest;
import com.toptal.caloriecount.payloads.response.misc.MessageResponse;

public interface CalorieLimitService {
    MessageResponse updateCalorieLimit(String userId, UpdateCalorieLimitRequest request) throws UserIdNotFoundException;
}
