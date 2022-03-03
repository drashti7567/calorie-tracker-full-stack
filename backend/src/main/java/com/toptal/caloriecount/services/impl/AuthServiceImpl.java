package com.toptal.caloriecount.services.impl;

import com.toptal.caloriecount.dao.impl.UsersRepository;
import com.toptal.caloriecount.dao.models.Users;
import com.toptal.caloriecount.mapper.AuthMapper;
import com.toptal.caloriecount.payloads.request.auth.LoginRequest;
import com.toptal.caloriecount.payloads.response.auth.GetUsersResponse;
import com.toptal.caloriecount.payloads.response.auth.JwtResponse;
import com.toptal.caloriecount.payloads.response.auth.ResetTokenResponse;
import com.toptal.caloriecount.payloads.response.auth.UserFields;
import com.toptal.caloriecount.security.jwt.JwtUtils;
import com.toptal.caloriecount.security.services.UserDetailsImpl;
import com.toptal.caloriecount.shared.constants.MessageConstants;
import com.toptal.caloriecount.shared.constants.ReturnCodeConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.toptal.caloriecount.services.interfaces.AuthService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UsersRepository usersRepository;

    @Override
    public JwtResponse authenticateUser(LoginRequest loginRequest) {
        /**
         * Main service function to authenticate a user.
         * Returns User details along with a JWT authentication token to authorize further API requests
         */

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority()).collect(Collectors.toList());

        JwtResponse response = new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(),
                userDetails.getEmail(), userDetails.getName(), roles);
        response.setMessageResponseVariables("User " + response.getEmail() + " authenticated successfully",
                true, ReturnCodeConstants.SUCESS);

        return response;
    }

    @Override
    public ResetTokenResponse refreshToken(String bearerToken) throws Exception {
        /**
         * Main Service function to refresh the token before it expires.
         */
        String token = bearerToken.split(" ")[1];
        String newToken = jwtUtils.refreshJwtToken(token);

        ResetTokenResponse response = new ResetTokenResponse(newToken);
        response.setMessageResponseVariables(MessageConstants.RESET_TOKEN_SUCCESSFULL, true, ReturnCodeConstants.SUCESS);
        return response;
    }

    @Override
    public GetUsersResponse getUsers() {
        /**
         * Main Service function to return list of users.
         */
        List<Users> usersList = this.usersRepository.findAll();
        List<UserFields> userFieldsList = usersList.stream()
                .map(AuthMapper::convertEntityToResponse).collect(Collectors.toList());
        GetUsersResponse response = new GetUsersResponse(userFieldsList);
        response.setMessageResponseVariables(MessageConstants.GET_USERS_LIST_SUCCESSFUL,
                true, ReturnCodeConstants.SUCESS);
        return response;
    }
}
