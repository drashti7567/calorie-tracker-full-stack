package com.toptal.caloriecount.services.impl;

import com.toptal.caloriecount.dao.impl.FoodEntryRepository;
import com.toptal.caloriecount.dao.models.FoodEntry;
import com.toptal.caloriecount.dao.models.Users;
import com.toptal.caloriecount.payloads.response.reports.AverageCaloriePerUserResponse;
import com.toptal.caloriecount.payloads.response.reports.GetReportsResponse;
import com.toptal.caloriecount.services.interfaces.ReportsService;
import com.toptal.caloriecount.shared.constants.MessageConstants;
import com.toptal.caloriecount.shared.constants.ReturnCodeConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportsServiceImpl implements ReportsService {

    @Autowired
    FoodEntryRepository foodEntryRepository;

    private GetReportsResponse generateReportsResponse(List<FoodEntry> currentWeekList, List<FoodEntry> pastWeekList,
                                                       List<AverageCaloriePerUserResponse> averageCalorieList) {
        /**
         * FUnction to generate response for get reports service
         */
        GetReportsResponse response = new GetReportsResponse(currentWeekList.size(), pastWeekList.size(),
                averageCalorieList);
        response.setMessageResponseVariables(MessageConstants.GET_REPORTS_SUCCESSFULL,
                true, ReturnCodeConstants.SUCESS);
        return response;
    }

    private List<AverageCaloriePerUserResponse> getAverageCaloriesPerUserList(List<FoodEntry> foodEntryList) {
        /**
         * FUnction to calculate the average per user calories of past 7 days.
         */
        Map<Users, Float> map = new HashMap<>();
        foodEntryList.forEach(entry -> {
            map.put(entry.getUser(), map.containsKey(entry.getUser()) ?
                    map.get(entry.getUser()) + entry.getCalories() : entry.getCalories());
        });

        List<AverageCaloriePerUserResponse> averageCaloriePerUserResponseList =
                map.entrySet().stream().map(entry -> new AverageCaloriePerUserResponse(entry.getKey().getUserId(),
                        entry.getKey().getName(), Math.round(entry.getValue() / 7 * 100) / 100F))
                        .collect(Collectors.toList());

        return averageCaloriePerUserResponseList;
    }
    @Override
    public GetReportsResponse getReports() {
        /**
         * Main Service function to get the reports which include the total entries made in current and past week
         * And average calories per user of past 7 days.
         */

        Timestamp dateTo = Timestamp.valueOf(LocalDateTime.now().with(LocalTime.MAX));
        Timestamp dateFrom = Timestamp.valueOf(LocalDateTime.now().with(LocalTime.MIN).minusDays(6));

        List<FoodEntry> currentWeekFoodEntryList = this.foodEntryRepository.getAllFoodEntries(dateFrom, dateTo);

        dateTo = dateFrom;
        dateFrom =  Timestamp.valueOf(LocalDateTime.now().with(LocalTime.MIN).minusDays(13));

        List<FoodEntry> pastWeekFoodEntryList = this.foodEntryRepository.getAllFoodEntries(dateFrom, dateTo);

        List<AverageCaloriePerUserResponse> averageCaloriePerUserResponseList =
                this.getAverageCaloriesPerUserList(currentWeekFoodEntryList);

        return this.generateReportsResponse(currentWeekFoodEntryList, pastWeekFoodEntryList, averageCaloriePerUserResponseList);
    }
}
