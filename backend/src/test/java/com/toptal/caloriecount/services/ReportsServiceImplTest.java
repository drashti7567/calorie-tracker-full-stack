package com.toptal.caloriecount.services;

import com.toptal.caloriecount.dao.impl.FoodEntryRepository;
import com.toptal.caloriecount.dao.models.FoodEntry;
import com.toptal.caloriecount.dao.models.Users;
import com.toptal.caloriecount.payloads.response.reports.AverageCaloriePerUserResponse;
import com.toptal.caloriecount.payloads.response.reports.GetReportsResponse;
import com.toptal.caloriecount.services.impl.ReportsServiceImpl;
import com.toptal.caloriecount.services.interfaces.ReportsService;
import com.toptal.caloriecount.shared.constants.MessageConstants;
import com.toptal.caloriecount.shared.constants.ReturnCodeConstants;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
public class ReportsServiceImplTest {
    @TestConfiguration
    static class ReportsServiceImplTestContextConfiguration {

        @Bean
        public ReportsService getReportService() {
            return new ReportsServiceImpl();
        }
    }

    @Autowired
    ReportsService reportsService;

    @MockBean
    FoodEntryRepository foodEntryRepository;

    private Users getMockUser() {
        Users user = new Users("AASSDDFF", "Drashti Dobariya", "ADMIN", "drashtidobariya11@gmail.com",
                "9999988888", null, null, null, null, null);
        return user;
    }

    private Users getMockUser2() {
        Users user = new Users("AASSDDFFHH", "Kirtan Dobariya", "CUSTOMER", "drashtidobariya11@gmail.com",
                "9999988888", null, null, null, null, null);
        return user;
    }

    private List<FoodEntry> getMockListFoodEntry() {
        List<FoodEntry> foodEntryList = new ArrayList<>();
        float calorie = 200F;
        for(int i=0; i<10; i++) {
            FoodEntry entry = new FoodEntry("kjyuthf", Timestamp.valueOf("2022-12-01 00:00:00"),
                    "banana", calorie, Timestamp.valueOf(LocalDateTime.now()),
                    Timestamp.valueOf(LocalDateTime.now()), getMockUser());
            foodEntryList.add(entry);
            calorie = calorie + 200;
        }

        for(int i=0; i<10; i++) {
            FoodEntry entry = new FoodEntry("kjyuthf", Timestamp.valueOf("2022-12-01 00:00:00"),
                    "banana", calorie, Timestamp.valueOf(LocalDateTime.now()),
                    Timestamp.valueOf(LocalDateTime.now()), getMockUser2());
            foodEntryList.add(entry);
            calorie = calorie + 200;
        }

        return foodEntryList;
    }

    private GetReportsResponse getSuccessfulGetReportsResponse() {
        List<AverageCaloriePerUserResponse> averageList = new ArrayList<>();
        AverageCaloriePerUserResponse response = new AverageCaloriePerUserResponse("AASSDDFF", "Drashti Dobariya", Math.round(11000F / 7 * 100F) / 100F);
        AverageCaloriePerUserResponse response2 = new AverageCaloriePerUserResponse("AASSDDFFHH", "Kirtan Dobariya", Math.round(31000F / 7 * 100F) / 100F);
        averageList.add(response2);
        averageList.add(response);

        GetReportsResponse finalResponse = new GetReportsResponse(20, 20, null, null, null, null,  averageList);
        finalResponse.setMessageResponseVariables(MessageConstants.GET_REPORTS_SUCCESSFULL, true, ReturnCodeConstants.SUCESS);

        return finalResponse;
    }

    @Test()
    public void successfulReportGeneration() {
        /**
         * Checks whether it returns calculates average calorie per user and also returns
         * the total entries made in last two weeks
         */
        Timestamp currentDateTo = Timestamp.valueOf(LocalDateTime.now().with(LocalTime.MAX));
        Timestamp currentDdateFrom = Timestamp.valueOf(LocalDateTime.now().with(LocalTime.MIN).minusDays(6));

        Mockito.when(foodEntryRepository.getAllFoodEntries(currentDdateFrom, currentDateTo))
                .thenReturn(this.getMockListFoodEntry());

        Timestamp pastDateTo = currentDdateFrom;
        Timestamp pastDateFrom =  Timestamp.valueOf(LocalDateTime.now().with(LocalTime.MIN).minusDays(13));

        Mockito.when(foodEntryRepository.getAllFoodEntries(pastDateFrom, pastDateTo))
                .thenReturn(this.getMockListFoodEntry());

        GetReportsResponse response = reportsService.getReports();
        assert (response).equals(this.getSuccessfulGetReportsResponse());

    }


}
