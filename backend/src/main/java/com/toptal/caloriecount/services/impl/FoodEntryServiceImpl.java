package com.toptal.caloriecount.services.impl;

import com.toptal.caloriecount.dao.impl.FoodEntryRepository;
import com.toptal.caloriecount.dao.impl.UsersRepository;
import com.toptal.caloriecount.dao.models.FoodEntry;
import com.toptal.caloriecount.dao.models.Users;
import com.toptal.caloriecount.exceptions.FoodEntryNotFoundException;
import com.toptal.caloriecount.exceptions.UserIdNotFoundException;
import com.toptal.caloriecount.exceptions.UserNotAuthorizedException;
import com.toptal.caloriecount.mapper.FoodEntryMapper;
import com.toptal.caloriecount.payloads.request.foodEntry.FoodEntryRequest;
import com.toptal.caloriecount.payloads.response.foodEntry.GetFoodEntriesResponse;
import com.toptal.caloriecount.payloads.response.misc.MessageResponse;
import com.toptal.caloriecount.services.interfaces.FoodEntryService;
import com.toptal.caloriecount.shared.constants.MessageConstants;
import com.toptal.caloriecount.shared.constants.ReturnCodeConstants;
import com.toptal.caloriecount.shared.constants.UserTypeConstants;
import com.toptal.caloriecount.shared.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

@Service
public class FoodEntryServiceImpl implements FoodEntryService {

    @Autowired
    FoodEntryRepository foodEntryRepository;

    @Autowired
    UsersRepository usersRepository;

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
                request.getEatTime(), new Timestamp((new Date()).getTime()));

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

    @Override
    public GetFoodEntriesResponse getEntries(String userId, Map<String, String> params) {
        return null;
    }
}
