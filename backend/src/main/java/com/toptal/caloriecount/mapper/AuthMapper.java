package com.toptal.caloriecount.mapper;

import com.toptal.caloriecount.dao.models.FoodEntry;
import com.toptal.caloriecount.dao.models.Users;
import com.toptal.caloriecount.payloads.request.foodEntry.FoodEntryRequest;
import com.toptal.caloriecount.payloads.response.auth.UserFields;
import com.toptal.caloriecount.shared.utils.CommonUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.sql.Timestamp;
import java.util.Date;

public class AuthMapper {
    private static ModelMapper mapper = new ModelMapper();

    public static UserFields convertEntityToResponse(Users user) {
        /**
         * Mapper function to convert AddEntryRequest object to Food Entry Entity
         */
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserFields userFields = mapper.map(user, UserFields.class);
        return userFields;
    }
}
