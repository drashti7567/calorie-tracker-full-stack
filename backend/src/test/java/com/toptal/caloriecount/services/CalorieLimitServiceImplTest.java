package com.toptal.caloriecount.services;

import com.toptal.caloriecount.dao.impl.CalorieLimitRepository;
import com.toptal.caloriecount.dao.impl.FoodEntryRepository;
import com.toptal.caloriecount.dao.impl.UsersRepository;
import com.toptal.caloriecount.dao.models.CalorieLimit;
import com.toptal.caloriecount.dao.models.FoodEntry;
import com.toptal.caloriecount.dao.models.Users;
import com.toptal.caloriecount.exceptions.UserIdNotFoundException;
import com.toptal.caloriecount.payloads.request.calorieLimit.UpdateCalorieLimitRequest;
import com.toptal.caloriecount.payloads.response.misc.MessageResponse;
import com.toptal.caloriecount.payloads.response.reports.AverageCaloriePerUserResponse;
import com.toptal.caloriecount.payloads.response.reports.GetReportsResponse;
import com.toptal.caloriecount.services.impl.CalorieLimitServiceImpl;
import com.toptal.caloriecount.services.impl.ReportsServiceImpl;
import com.toptal.caloriecount.services.interfaces.CalorieLimitService;
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

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class CalorieLimitServiceImplTest {
    @TestConfiguration
    static class CalorieLimitServiceImplTestContextConfiguration {

        @Bean
        public CalorieLimitService getCalorieLimitService() {
            return new CalorieLimitServiceImpl();
        }
    }

    @Autowired
    CalorieLimitService calorieLimitService;

    @MockBean
    CalorieLimitRepository calorieLimitRepository;

    @MockBean
    UsersRepository usersRepository;

    private String mockUserId = "AASSDDFF";

    private Users getMockUser() {
        Users user = new Users(mockUserId, "Drashti Dobariya", "ADMIN", "drashtidobariya11@gmail.com",
                "9999988888", null, null, null, null, null);
        return user;
    }

    private Users getMockUser2() {
        Users user = new Users("AASSDDFFHH", "Kirtan Dobariya", "CUSTOMER", "drashtidobariya11@gmail.com",
                "9999988888", null, null, null, null, null);
        return user;
    }

    private CalorieLimit getMockCalorieLimit() {
        return new CalorieLimit(mockUserId, 2100F, 800F, 800F, 500F,
                null, null, null);
    }

    private UpdateCalorieLimitRequest getMockRequest() {
        UpdateCalorieLimitRequest request = new UpdateCalorieLimitRequest(500F, 500F, 500F);
        return request;
    }

    @Test(expected = UserIdNotFoundException.class)
    public void updateCalorieLimit_throwsUserIdNotFoundException_ifUserIsNotPresentInSystem() throws UserIdNotFoundException {
        calorieLimitService.updateCalorieLimit(null, null);
    }

    @Test()
    public void updateCalorieLimit_updatesTheValueOfExistingRecordIfAlreadyPresent() throws UserIdNotFoundException {

        CalorieLimit calorieLimit = this.getMockCalorieLimit();

        Mockito.when(usersRepository.findById(mockUserId)).thenReturn(Optional.of(this.getMockUser()));

        Mockito.when(calorieLimitRepository.findById(mockUserId)).thenReturn(Optional.of(calorieLimit));

        calorieLimitService.updateCalorieLimit(mockUserId, this.getMockRequest());

        verify(calorieLimitRepository, times(1))
                .updateCalorieLimit(eq(mockUserId), eq(1500F), eq(500F), eq(500F), eq(500F), any());
        verify(calorieLimitRepository, times(0)).save(calorieLimit);
    }

    @Test()
    public void updateCalorieLimit_addsNewRecordIfNotAlreadyPresent() throws UserIdNotFoundException {

        CalorieLimit calorieLimit = this.getMockCalorieLimit();

        Mockito.when(usersRepository.findById(mockUserId)).thenReturn(Optional.of(this.getMockUser()));

        Mockito.when(calorieLimitRepository.findById(mockUserId)).thenReturn(Optional.empty());

        calorieLimitService.updateCalorieLimit(mockUserId, this.getMockRequest());

        verify(calorieLimitRepository, times(0))
                .updateCalorieLimit(eq(mockUserId), eq(1500F), eq(500F), eq(500F), eq(500F), any());
        verify(calorieLimitRepository, times(1)).save(any());
    }

    @Test()
    public void updateCalorieLimit_successfullExecution() throws UserIdNotFoundException {

        CalorieLimit calorieLimit = this.getMockCalorieLimit();

        Mockito.when(usersRepository.findById(mockUserId)).thenReturn(Optional.of(this.getMockUser()));

        Mockito.when(calorieLimitRepository.findById(mockUserId)).thenReturn(Optional.empty());

        MessageResponse response = calorieLimitService.updateCalorieLimit(mockUserId, this.getMockRequest());

        assert (response).equals(new MessageResponse(MessageConstants.UPDATE_CALORIE_LIMIT_SUCCESSFUL+mockUserId,
                true, ReturnCodeConstants.SUCESS));

    }


}
