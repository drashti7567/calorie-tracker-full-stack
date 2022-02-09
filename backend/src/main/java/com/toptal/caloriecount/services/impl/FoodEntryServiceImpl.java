package com.toptal.caloriecount.services.impl;

import com.toptal.caloriecount.dao.impl.CalorieLimitRepository;
import com.toptal.caloriecount.dao.impl.FoodEntryRepository;
import com.toptal.caloriecount.dao.impl.UsersRepository;
import com.toptal.caloriecount.dao.models.CalorieLimit;
import com.toptal.caloriecount.dao.models.FoodEntry;
import com.toptal.caloriecount.dao.models.Users;
import com.toptal.caloriecount.exceptions.FoodEntryNotFoundException;
import com.toptal.caloriecount.exceptions.UserIdNotFoundException;
import com.toptal.caloriecount.exceptions.UserNotAuthorizedException;
import com.toptal.caloriecount.mapper.FoodEntryMapper;
import com.toptal.caloriecount.payloads.request.foodEntry.FoodEntryRequest;
import com.toptal.caloriecount.payloads.response.foodEntry.FoodEntryFields;
import com.toptal.caloriecount.payloads.response.foodEntry.GetFoodEntriesResponse;
import com.toptal.caloriecount.payloads.response.misc.MessageResponse;
import com.toptal.caloriecount.services.interfaces.FoodEntryService;
import com.toptal.caloriecount.shared.constants.*;
import com.toptal.caloriecount.shared.utils.CommonUtils;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FoodEntryServiceImpl implements FoodEntryService {

    @Autowired
    FoodEntryRepository foodEntryRepository;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    CalorieLimitRepository calorieLimitRepository;

    @Override
    public MessageResponse addEntry(FoodEntryRequest request) throws UserIdNotFoundException {
        /**
         * Main Service function to add food entries.
         */

        Users user = CommonUtils.getUserFromUserId(request.getUserId(), this.usersRepository);

        FoodEntry foodEntry = FoodEntryMapper.convertAddEntryToEntity(request, user);
        this.foodEntryRepository.save(foodEntry);

        return new MessageResponse(foodEntry.getEntryId() + ": " + MessageConstants.ADD_ENTRY_SUCCESSFUL, true, ReturnCodeConstants.SUCESS);
    }

    private FoodEntry checkIfFoodEntryPresentInSystem(String entryId) throws FoodEntryNotFoundException {
        /**
         * Function to check if food entry is present in the system or not.
         * Throws food entry not found exception is id is not found
         */
        FoodEntry entry = this.foodEntryRepository.findById(entryId)
                .orElseThrow(() -> new FoodEntryNotFoundException(MessageConstants.FOOD_ENTRY_NOT_FOUND_ERROR + entryId));
        return entry;
    }

    private void checkIfUserCanUpdateOrDeleteEntry(String userId, FoodEntry foodEntry)
            throws UserIdNotFoundException, UserNotAuthorizedException {
        /**
         * Function to checkIf the current logged-in user can update or delete this entry.
         * Food entry can only be updated if it is created by that user or if the user is an admin
         */
        Users user = CommonUtils.getUserFromUserId(userId, this.usersRepository);
        if (!user.getType().equals(UserTypeConstants.ADMIN) && !foodEntry.getUser().getUserId().equals(userId))
            throw new UserNotAuthorizedException(MessageConstants.USER_NOT_AUTHORIZED_ERROR);
    }

    @Override
    public MessageResponse updateEntry(String entryId, FoodEntryRequest request)
            throws FoodEntryNotFoundException, UserNotAuthorizedException, UserIdNotFoundException {
        /**
         * Main service function to update a food entry
         */
        entryId = entryId.toLowerCase();
        FoodEntry entry = this.checkIfFoodEntryPresentInSystem(entryId);
        this.checkIfUserCanUpdateOrDeleteEntry(request.getUserId(), entry);

        this.foodEntryRepository.updateFoodEntry(entryId, request.getFoodName(), request.getCalories(),
                Timestamp.valueOf(request.getEatingTime()), new Timestamp((new Date()).getTime()));

        return new MessageResponse(entryId + ": " + MessageConstants.UPDATE_ENTRY_SUCCESSFUL,
                true, ReturnCodeConstants.SUCESS);
    }

    @Override
    public MessageResponse deleteEntry(String userId, String entryId)
            throws FoodEntryNotFoundException, UserNotAuthorizedException, UserIdNotFoundException {
        /**
         * Main service function to delete food entry
         */
        entryId = entryId.toLowerCase();
        FoodEntry entry = this.checkIfFoodEntryPresentInSystem(entryId);
        this.checkIfUserCanUpdateOrDeleteEntry(userId, entry);
        this.foodEntryRepository.deleteById(entryId);

        return new MessageResponse(entryId + ": " + MessageConstants.DELETE_ENTRY_SUCCESSFUL,
                true, ReturnCodeConstants.SUCESS);
    }

    private GetFoodEntriesResponse generateGetFoodEntriesResponse(List<FoodEntryFields> foodEntryFieldsList, CalorieLimit calorieLimit) {
        /**
         * Function to generate response object for getFoodEntries service
         */

        Float calorieBasicLimit = calorieLimit != null ? calorieLimit.getBasicLimit() : CalorieIntakeConstants.BASIC_LIMIT;
        GetFoodEntriesResponse response = new GetFoodEntriesResponse(foodEntryFieldsList, calorieBasicLimit);
        response.setMessageResponseVariables(MessageConstants.GET_ENTRY_SUCCESSFUL,
                true, ReturnCodeConstants.SUCESS);
        return response;
    }

    private List<FoodEntry> getListOfEntries(Users user, Map<String, String> params) {
        /**
         * Function to get list of food entries based on date to and date from date conditions
         */

        if(params == null) return this.foodEntryRepository.findAllByUserOrderByEatTimeDesc(user);

        Timestamp dateFrom, dateTo;

        if (params.containsKey(FoodEntryQueryParamConstants.DATE_TO) && params.containsKey(FoodEntryQueryParamConstants.DATE_FROM)) {
            dateFrom = Timestamp.valueOf(params.get(FoodEntryQueryParamConstants.DATE_FROM).substring(0, 10) + " 00:00:00");
            dateTo = Timestamp.valueOf(params.get(FoodEntryQueryParamConstants.DATE_TO).substring(0, 10) + " 23:59:59");
            return this.foodEntryRepository.getFoodEntries(dateFrom, dateTo, user);
        }
        if ((params.containsKey(FoodEntryQueryParamConstants.DATE_FROM) &&
                !params.containsKey(FoodEntryQueryParamConstants.DATE_TO)) ||
                (!params.containsKey(FoodEntryQueryParamConstants.DATE_FROM) &&
                        params.containsKey(FoodEntryQueryParamConstants.DATE_TO))) {

            String date = params.containsKey(FoodEntryQueryParamConstants.DATE_FROM) ?
                    params.get(FoodEntryQueryParamConstants.DATE_FROM) :
                    params.get(FoodEntryQueryParamConstants.DATE_TO);

            dateFrom = Timestamp.valueOf(date.substring(0, 10) + " 00:00:00");
            dateTo = Timestamp.valueOf(date.substring(0, 10) + " 23:59:59");
            return this.foodEntryRepository.getFoodEntries(dateFrom, dateTo, user);
        }

        return this.foodEntryRepository.findAllByUserOrderByEatTimeDesc(user);
    }

    @Override
    public GetFoodEntriesResponse getEntries(String userId, Map<String, String> params) throws UserIdNotFoundException {
        /**
         * Main function to get the list of food entries of user.
         * @param userId: User id of the user logged in
         * @param params: Query params - date from and date to for filtering
         */
        Users user = CommonUtils.getUserFromUserId(userId, this.usersRepository);
        List<FoodEntry> foodEntryList = this.getListOfEntries(user, params);

        List<FoodEntryFields> foodEntryFieldsList = foodEntryList.stream()
                .map(FoodEntryMapper::convertEntityToEntryFields).collect(Collectors.toList());

        Optional<CalorieLimit> optionalCalorieLimit = this.calorieLimitRepository.findById(user.getUserId());
        CalorieLimit calorieLimit  = optionalCalorieLimit.isPresent() ? optionalCalorieLimit.get() : null;

        GetFoodEntriesResponse response = this.generateGetFoodEntriesResponse(foodEntryFieldsList, calorieLimit);

        return response;
    }

    @Override
    public GetFoodEntriesResponse getAllEntries() {
        /**
         * Main service function to get all the food entries
         * Admin Only
         */

        List<FoodEntry> foodEntryList = this.foodEntryRepository.findAll();
        List<FoodEntryFields> foodEntryFieldsList = foodEntryList.stream()
                .map(FoodEntryMapper::convertEntityToEntryFields).collect(Collectors.toList());
        GetFoodEntriesResponse response =  this.generateGetFoodEntriesResponse(foodEntryFieldsList, null);
        return response;
    }
}
