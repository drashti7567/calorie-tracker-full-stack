package com.toptal.caloriecount.shared.utils;

import com.toptal.caloriecount.dao.impl.UsersRepository;
import com.toptal.caloriecount.dao.models.Users;
import com.toptal.caloriecount.exceptions.UserIdNotFoundException;
import com.toptal.caloriecount.shared.constants.MessageConstants;

import java.util.Date;
import java.util.Optional;

public class CommonUtils {
    public static String generateUniqueId() {
        Long currentTime = (new Date()).getTime();
        return Long.toString(currentTime, 36);
    }

    public static Users getUserFromUserId(String userId, UsersRepository usersRepository) throws UserIdNotFoundException {
        /**
         * Function to get User object given user id.
         */
        Optional<Users> optionalUsers = usersRepository.findById(userId);
        if(!optionalUsers.isPresent())
            throw new UserIdNotFoundException(MessageConstants.USER_NOT_FOUND_ERROR);
        return optionalUsers.get();
    }
}
