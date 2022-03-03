package com.toptal.caloriecount.services;

import com.toptal.caloriecount.dao.impl.CalorieLimitRepository;
import com.toptal.caloriecount.dao.impl.FoodEntryRepository;
import com.toptal.caloriecount.dao.impl.UsersRepository;
import com.toptal.caloriecount.dao.models.CalorieLimit;
import com.toptal.caloriecount.dao.models.FoodEntry;
import com.toptal.caloriecount.dao.models.Users;
import com.toptal.caloriecount.exceptions.FoodEntryNotFoundException;
import com.toptal.caloriecount.exceptions.UserIdNotFoundException;
import com.toptal.caloriecount.exceptions.UserNotAuthorizedException;
import com.toptal.caloriecount.payloads.request.calorieLimit.UpdateCalorieLimitRequest;
import com.toptal.caloriecount.payloads.request.foodEntry.FoodEntryRequest;
import com.toptal.caloriecount.payloads.response.misc.MessageResponse;
import com.toptal.caloriecount.services.impl.CalorieLimitServiceImpl;
import com.toptal.caloriecount.services.impl.FoodEntryServiceImpl;
import com.toptal.caloriecount.services.interfaces.CalorieLimitService;
import com.toptal.caloriecount.services.interfaces.FoodEntryService;
import com.toptal.caloriecount.shared.constants.MessageConstants;
import com.toptal.caloriecount.shared.constants.ReturnCodeConstants;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.util.Locale;
import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class FoodEntryServiceImplTest {
    @TestConfiguration
    static class FoodEntryServiceImplTestContextConfiguration {

        @Bean
        public FoodEntryService getFoodEntryService() {
            return new FoodEntryServiceImpl();
        }
    }

    @Autowired
    FoodEntryService foodEntryService;

    @MockBean
    FoodEntryRepository foodEntryRepository;

    @MockBean
    UsersRepository usersRepository;

    @MockBean
    CalorieLimitRepository calorieLimitRepository;

    private String mockUserId = "AASSDDFF";
    private String entryId = "KKJJHHGG";

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

    private FoodEntryRequest getMockAddEntryRequest() {
        FoodEntryRequest request = new FoodEntryRequest(this.mockUserId, "Banana", "2022-12-12 12:12:12", 200.12F);
        return request;
    }

    private FoodEntry getMockFoodEntry() {
        return new FoodEntry(this.entryId, Timestamp.valueOf("2022-12-12 12:12:12"), "Banana",
                222F, null, null, this.getMockUser());
    }

    @Test(expected = UserIdNotFoundException.class)
    public void addFoodEntry_throwsUserIdNotFoundException_ifUserIsnOtPresentInSystem() throws UserIdNotFoundException {
        foodEntryService.addEntry(this.getMockAddEntryRequest());
    }

    @Test()
    public void addFoodEntry_callsSaveMethodOdFoodRepository() throws UserIdNotFoundException {
        Mockito.when(usersRepository.findById(this.mockUserId)).thenReturn(Optional.of(this.getMockUser()));
        foodEntryService.addEntry(this.getMockAddEntryRequest());
        verify(foodEntryRepository, times(1)).save(any());
    }

    @Test(expected = UserIdNotFoundException.class)
    public void updateFoodEntry_throwsUserIdNotFoundException_ifUserIsnOtPresentInSystem()
            throws UserIdNotFoundException, UserNotAuthorizedException, FoodEntryNotFoundException {
        Mockito.when(foodEntryRepository.findById(this.entryId.toLowerCase())).thenReturn(Optional.of(this.getMockFoodEntry()));
        foodEntryService.updateEntry(this.entryId, this.getMockAddEntryRequest());
    }

    @Test(expected = FoodEntryNotFoundException.class)
    public void updateFoodEntry_throwsFoodEntryNotFoundException_ifEntryIsnOtPresentInSystem()
            throws UserIdNotFoundException, UserNotAuthorizedException, FoodEntryNotFoundException {
        foodEntryService.updateEntry(this.entryId, this.getMockAddEntryRequest());
    }

    @Test(expected = UserNotAuthorizedException.class)
    public void updateFoodEntry_throwsUserNotAuthorizedException_ifEntryIsNotOfUserAndUserIsNotAdmin()
            throws UserIdNotFoundException, UserNotAuthorizedException, FoodEntryNotFoundException {
        Users user = this.getMockUser();
        user.setUserId("RandomUserId");
        user.setType("REGULAR");
        FoodEntryRequest request = this.getMockAddEntryRequest();
        request.setUserId("RandomUserId");
        Mockito.when(usersRepository.findById("RandomUserId")).thenReturn(Optional.of(user));
        Mockito.when(foodEntryRepository.findById(this.entryId.toLowerCase())).thenReturn(Optional.of(this.getMockFoodEntry()));
        foodEntryService.updateEntry(this.entryId, request);
    }

    @Test()
    public void updateFoodEntry_successfulExecution()
            throws UserIdNotFoundException, UserNotAuthorizedException, FoodEntryNotFoundException {
        Mockito.when(usersRepository.findById(this.mockUserId)).thenReturn(Optional.of(this.getMockUser()));
        Mockito.when(foodEntryRepository.findById(this.entryId.toLowerCase())).thenReturn(Optional.of(this.getMockFoodEntry()));
        FoodEntryRequest request = this.getMockAddEntryRequest();
        MessageResponse response = foodEntryService.updateEntry(this.entryId, request);
        verify(foodEntryRepository, times(1)).updateFoodEntry(eq(this.entryId.toLowerCase()),
                eq(request.getFoodName()), eq(request.getCalories()), any(), any());

        assert (response).equals(new MessageResponse(
                this.entryId.toLowerCase() + ": " + MessageConstants.UPDATE_ENTRY_SUCCESSFUL,
                true, ReturnCodeConstants.SUCESS));
    }


    @Test(expected = FoodEntryNotFoundException.class)
    public void deleteFoodEntry_throwsFoodEntryNotFoundException_ifEntryIsnOtPresentInSystem()
            throws UserIdNotFoundException, UserNotAuthorizedException, FoodEntryNotFoundException {
        foodEntryService.deleteEntry(this.mockUserId, this.entryId);
    }

    @Test(expected = UserIdNotFoundException.class)
    public void deleteFoodEntry_throwsUserIdNotFoundException_ifUserIsnOtPresentInSystem()
            throws UserIdNotFoundException, UserNotAuthorizedException, FoodEntryNotFoundException {
        Mockito.when(foodEntryRepository.findById(this.entryId.toLowerCase())).thenReturn(Optional.of(this.getMockFoodEntry()));
        foodEntryService.deleteEntry(this.mockUserId, this.entryId);
    }

    @Test()
    public void deleteFoodEntry_successfulExecution()
            throws UserIdNotFoundException, UserNotAuthorizedException, FoodEntryNotFoundException {
        Mockito.when(usersRepository.findById(this.mockUserId)).thenReturn(Optional.of(this.getMockUser()));
        Mockito.when(foodEntryRepository.findById(this.entryId.toLowerCase())).thenReturn(Optional.of(this.getMockFoodEntry()));
        FoodEntryRequest request = this.getMockAddEntryRequest();
        MessageResponse response = foodEntryService.deleteEntry(this.mockUserId, this.entryId);
        verify(foodEntryRepository, times(1)).deleteById(this.entryId.toLowerCase());
        assert (response).equals(new MessageResponse(
                this.entryId.toLowerCase() + ": " + MessageConstants.DELETE_ENTRY_SUCCESSFUL,
                true, ReturnCodeConstants.SUCESS));
    }

//    @Test()
//    public void updateCalorieLimit_updatesTheValueOfExistingRecordIfAlreadyPresent() throws UserIdNotFoundException {
//
//        CalorieLimit calorieLimit = this.getMockCalorieLimit();
//
//        Mockito.when(usersRepository.findById(mockUserId)).thenReturn(Optional.of(this.getMockUser()));
//
//        Mockito.when(calorieLimitRepository.findById(mockUserId)).thenReturn(Optional.of(calorieLimit));
//
//        calorieLimitService.updateCalorieLimit(mockUserId, this.getMockRequest());
//
//        verify(calorieLimitRepository, times(1))
//                .updateCalorieLimit(eq(mockUserId), eq(1500F), eq(500F), eq(500F), eq(500F), any());
//        verify(calorieLimitRepository, times(0)).save(calorieLimit);
//    }
//
//    @Test()
//    public void updateCalorieLimit_addsNewRecordIfNotAlreadyPresent() throws UserIdNotFoundException {
//
//        CalorieLimit calorieLimit = this.getMockCalorieLimit();
//
//        Mockito.when(usersRepository.findById(mockUserId)).thenReturn(Optional.of(this.getMockUser()));
//
//        Mockito.when(calorieLimitRepository.findById(mockUserId)).thenReturn(Optional.empty());
//
//        calorieLimitService.updateCalorieLimit(mockUserId, this.getMockRequest());
//
//        verify(calorieLimitRepository, times(0))
//                .updateCalorieLimit(eq(mockUserId), eq(1500F), eq(500F), eq(500F), eq(500F), any());
//        verify(calorieLimitRepository, times(1)).save(any());
//    }
//
//    @Test()
//    public void updateCalorieLimit_successfullExecution() throws UserIdNotFoundException {
//
//        CalorieLimit calorieLimit = this.getMockCalorieLimit();
//
//        Mockito.when(usersRepository.findById(mockUserId)).thenReturn(Optional.of(this.getMockUser()));
//
//        Mockito.when(calorieLimitRepository.findById(mockUserId)).thenReturn(Optional.empty());
//
//        MessageResponse response = calorieLimitService.updateCalorieLimit(mockUserId, this.getMockRequest());
//
//        assert (response).equals(new MessageResponse(MessageConstants.UPDATE_CALORIE_LIMIT_SUCCESSFUL+mockUserId,
//                true, ReturnCodeConstants.SUCESS));
//
//    }


}
