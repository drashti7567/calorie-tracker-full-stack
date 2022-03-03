package com.toptal.caloriecount.services.impl;

import com.toptal.caloriecount.dao.impl.CalorieLimitRepository;
import com.toptal.caloriecount.dao.impl.UsersRepository;
import com.toptal.caloriecount.dao.models.CalorieLimit;
import com.toptal.caloriecount.dao.models.Users;
import com.toptal.caloriecount.exceptions.UserIdNotFoundException;
import com.toptal.caloriecount.payloads.request.calorieLimit.UpdateCalorieLimitRequest;
import com.toptal.caloriecount.payloads.response.calorieLimit.GetCalorieLimitResponse;
import com.toptal.caloriecount.payloads.response.misc.MessageResponse;
import com.toptal.caloriecount.services.interfaces.CalorieLimitService;
import com.toptal.caloriecount.shared.constants.CalorieIntakeConstants;
import com.toptal.caloriecount.shared.constants.MessageConstants;
import com.toptal.caloriecount.shared.constants.ReturnCodeConstants;
import com.toptal.caloriecount.shared.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;

@Service
public class CalorieLimitServiceImpl implements CalorieLimitService {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    CalorieLimitRepository calorieLimitRepository;

    @Override
    public MessageResponse updateCalorieLimit(String userId, UpdateCalorieLimitRequest request)
            throws UserIdNotFoundException {
        /**
         * Main Service function to update the calorie limit of a user.
         */

        Users user = CommonUtils.getUserFromUserId(userId, this.usersRepository);
        Optional<CalorieLimit> optionalCalorieLimit = this.calorieLimitRepository.findById(userId);
        Float basicLimit = request.getCarbs() + request.getFat() + request.getProtein();
        if(optionalCalorieLimit.isPresent()) {
            this.calorieLimitRepository.updateCalorieLimit(userId, basicLimit, request.getCarbs(),
                    request.getProtein(), request.getFat(), new Timestamp((new Date()).getTime()));
        }
        else {
            CalorieLimit calorieLimit = new CalorieLimit(userId, basicLimit, request.getCarbs(),
                    request.getProtein(), request.getFat(), null, null,
                    new Timestamp((new Date()).getTime()));
            this.calorieLimitRepository.save(calorieLimit);
        }
        return new MessageResponse(MessageConstants.UPDATE_CALORIE_LIMIT_SUCCESSFUL + userId,
                true, ReturnCodeConstants.SUCESS);
    }

    @Override
    public GetCalorieLimitResponse getCalorieLimit(String userId) throws UserIdNotFoundException {
        /**
         * Main Service function to get the calorie limit of an user.
         */
        Users user = CommonUtils.getUserFromUserId(userId, this.usersRepository);
        Optional<CalorieLimit> optionalCalorieLimit = this.calorieLimitRepository.findById(userId);
        Float calorieLimit = optionalCalorieLimit.isPresent() ?
                optionalCalorieLimit.get().getBasicLimit() : CalorieIntakeConstants.BASIC_LIMIT;
        GetCalorieLimitResponse response = new GetCalorieLimitResponse(calorieLimit);
        response.setMessageResponseVariables(MessageConstants.GET_CALORIE_LIMIT_SUCCESSFUL + userId,
                true, ReturnCodeConstants.SUCESS);
        return response;
    }
}
