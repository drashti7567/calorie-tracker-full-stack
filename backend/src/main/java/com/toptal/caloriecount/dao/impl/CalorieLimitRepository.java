package com.toptal.caloriecount.dao.impl;

import com.toptal.caloriecount.dao.models.CalorieLimit;
import com.toptal.caloriecount.dao.models.FoodEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.sql.Timestamp;

public interface CalorieLimitRepository extends JpaRepository<CalorieLimit, String> {

    @Modifying
    @Transactional
    @Query("update CalorieLimit c set c.basicLimit = :basicLimit, c.carbs = :carbs, c.protein = :protein," +
            " c.fat = :fat, c.lastUpdatedTimestamp = :lastUpdatedTimestamp where c.userId = :userId")
    int updateCalorieLimit(@Param("userId") String userId, @Param("basicLimit") Float basicLimit,
                        @Param("carbs") Float carbs, @Param("protein") Float protein,
                        @Param("fat") Float fat, @Param("lastUpdatedTimestamp") Timestamp lastUpdatedTimestamp);
}
