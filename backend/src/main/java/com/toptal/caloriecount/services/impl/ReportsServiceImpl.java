package com.toptal.caloriecount.services.impl;

import com.toptal.caloriecount.dao.impl.FoodEntryRepository;
import com.toptal.caloriecount.dao.models.FoodEntry;
import com.toptal.caloriecount.dao.models.Users;
import com.toptal.caloriecount.payloads.response.reports.AverageCaloriePerUserResponse;
import com.toptal.caloriecount.payloads.response.reports.EntriesPerDayResponse;
import com.toptal.caloriecount.payloads.response.reports.GetReportsResponse;
import com.toptal.caloriecount.services.interfaces.ReportsService;
import com.toptal.caloriecount.shared.constants.MessageConstants;
import com.toptal.caloriecount.shared.constants.ReturnCodeConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReportsServiceImpl implements ReportsService {

    @Autowired
    FoodEntryRepository foodEntryRepository;

    private GetReportsResponse generateReportsResponse(List<FoodEntry> currentWeekList, List<FoodEntry> pastWeekList,
                                                       Float averageCaloriesCurrentWeek, Float averageCaloriesPastWeek,
                                                       List<AverageCaloriePerUserResponse> averageCalorieList,
                                                       List<EntriesPerDayResponse> currentWeekEntriesResponseList,
                                                       List<EntriesPerDayResponse> pastWeekEntriesResponseList) {
        /**
         * FUnction to generate response for get reports service
         */
        GetReportsResponse response = new GetReportsResponse(currentWeekList.size(), pastWeekList.size(),
                 averageCaloriesCurrentWeek, averageCaloriesPastWeek,
                 currentWeekEntriesResponseList, pastWeekEntriesResponseList, averageCalorieList);
        response.setMessageResponseVariables(MessageConstants.GET_REPORTS_SUCCESSFULL,
                true, ReturnCodeConstants.SUCESS);
        return response;
    }

    private Float getAverageCaloriesPerUserPerDays(List<FoodEntry> foodEntryList) {
        /**
         * Function that calculates the average calories per user per number of days.
         */
        Float totalCalories = foodEntryList.stream()
                .map(entry -> entry.getCalories()).reduce(0F, (c1, c2) -> c1 + c2);
        Long totalNumberOfUsers = foodEntryList.stream().map(entry -> entry.getUser()).distinct().count();

        return Math.round(totalCalories / (totalNumberOfUsers * 7) * 100F) / 100F;
    }

    private List<EntriesPerDayResponse> getEntriesPerDayList(List<FoodEntry> foodEntryList) {
        /**
         * Function given a list of food Entries, calculates total number of entries in a particular day.
         */

        Map<String, Integer> map = new HashMap<>();
        foodEntryList.forEach(entry -> {
            String date = entry.getEatTime().toString().substring(0, 10);
            map.put(date, map.containsKey(date) ? map.get(date) + 1 : 1);
        });

        List<EntriesPerDayResponse> responseList = map.entrySet().stream().map(entry -> {
                    LocalDate date = LocalDate.of(Integer.parseInt(entry.getKey().substring(0, 4)),
                            Integer.parseInt(entry.getKey().substring(5, 7)),
                            Integer.parseInt(entry.getKey().substring(8, 10)));
                    DayOfWeek day = date.getDayOfWeek();

                    return new EntriesPerDayResponse(entry.getKey(),
                            day.getDisplayName(TextStyle.FULL, Locale.ENGLISH), entry.getValue());
                })
                .collect(Collectors.toList());
        return responseList;
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
        List<EntriesPerDayResponse> currentWeekEntriesResponseList = this.getEntriesPerDayList(currentWeekFoodEntryList);

        dateTo = dateFrom;
        dateFrom = Timestamp.valueOf(LocalDateTime.now().with(LocalTime.MIN).minusDays(13));

        List<FoodEntry> pastWeekFoodEntryList = this.foodEntryRepository.getAllFoodEntries(dateFrom, dateTo);
        List<EntriesPerDayResponse> pastWeekEntriesResponseList = this.getEntriesPerDayList(pastWeekFoodEntryList);

        List<AverageCaloriePerUserResponse> averageCaloriePerUserResponseList =
                this.getAverageCaloriesPerUserList(currentWeekFoodEntryList);

        Float averageCaloriesCurrentWeek = this.getAverageCaloriesPerUserPerDays(currentWeekFoodEntryList);
        Float averageCaloriesPastWeek = this.getAverageCaloriesPerUserPerDays(pastWeekFoodEntryList);

        return this.generateReportsResponse(currentWeekFoodEntryList, pastWeekFoodEntryList, averageCaloriesCurrentWeek,
                averageCaloriesPastWeek, averageCaloriePerUserResponseList,
                currentWeekEntriesResponseList, pastWeekEntriesResponseList);
    }
}
