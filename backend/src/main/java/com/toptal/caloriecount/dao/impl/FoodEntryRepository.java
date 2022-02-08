package com.toptal.caloriecount.dao.impl;

import com.toptal.caloriecount.dao.models.FoodEntry;
import com.toptal.caloriecount.dao.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface FoodEntryRepository extends JpaRepository<FoodEntry, String> {

    @Modifying
    @Transactional
    @Query("update FoodEntry set foodName = :foodName, calories = :calories, eatTime = :eatTime," +
            " lastUpdatedTimestamp = :lastUpdatedTimestamp where entryId = :entryId")
    int updateFoodEntry(@Param("entryId") String entryId, @Param("foodName") String foodName,
                        @Param("calories") Float calories, @Param("eatTime") Timestamp eatTime,
                        @Param("lastUpdatedTimestamp") Timestamp lastUpdatedTimestamp);


    @Query("select e from FoodEntry e where e.user = :user and e.eatTime >= :dateFrom and e.eatTime <= :dateTo order by e.eatTime")
    List<FoodEntry> getFoodEntries(@Param("dateFrom") Timestamp dateFrom, @Param("dateTo") Timestamp dateTo, @Param("user") Users user);


    @Query("select e from FoodEntry e where e.eatTime >= :dateFrom and e.eatTime <= :dateTo order by e.eatTime")
    List<FoodEntry> getAllFoodEntries(@Param("dateFrom") Timestamp dateFrom, @Param("dateTo") Timestamp dateTo);

    List<FoodEntry> findAllByUserOrderByEatTime(Users user);
}
