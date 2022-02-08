package com.toptal.caloriecount.mapper;

import com.toptal.caloriecount.dao.models.FoodEntry;
import com.toptal.caloriecount.dao.models.Users;
import com.toptal.caloriecount.payloads.request.foodEntry.FoodEntryRequest;
import com.toptal.caloriecount.payloads.response.foodEntry.FoodEntryFields;
import com.toptal.caloriecount.shared.utils.CommonUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.sql.Timestamp;
import java.util.Date;

public class FoodEntryMapper {
    private static ModelMapper mapper = new ModelMapper();

    public static FoodEntry convertAddEntryToEntity(FoodEntryRequest request, Users user) {
        /**
         * Mapper function to convert AddEntryRequest object to Food Entry Entity
         */
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        FoodEntry entry = mapper.map(request, FoodEntry.class);
        entry.setEatTime(Timestamp.valueOf(request.getEatingTime()));
        entry.setEntryId(CommonUtils.generateUniqueId());
        entry.setCreationTimestamp(new Timestamp((new Date()).getTime()));
        entry.setUser(user);
        return entry;
    }

    public static FoodEntryFields convertEntityToEntryFields(FoodEntry foodEntry) {
        /**
         * Mapper function to convert FoodEntry Entity to GetFoodEntriesResponse Fields
         */
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        FoodEntryFields entryFields = mapper.map(foodEntry, FoodEntryFields.class);
        entryFields.setUserId(foodEntry.getUser().getUserId());
        entryFields.setUserName(foodEntry.getUser().getName());
        entryFields.setEatingTime(foodEntry.getEatTime().toString());
        return entryFields;
    }
}
