package com.toptal.caloriecount.mapper;

import com.toptal.caloriecount.dao.models.FoodEntry;
import com.toptal.caloriecount.dao.models.Users;
import com.toptal.caloriecount.payloads.request.foodEntry.FoodEntryRequest;
import com.toptal.caloriecount.shared.utils.CommonUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.sql.Timestamp;
import java.util.Date;

public class FoodEntryMapper {
    private static ModelMapper mapper = new ModelMapper();

    FoodEntryMapper() {
        this.mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public static FoodEntry convertAddEntryToEntity(FoodEntryRequest request, Users user) {
        FoodEntry entry = mapper.map(request, FoodEntry.class);
        entry.setEntryId(CommonUtils.generateUniqueId());
        entry.setCreationTimestamp(new Timestamp((new Date()).getTime()));
        entry.setUser(user);
        return entry;
    }
}
