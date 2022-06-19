package com.lkdrestaurantserver.services.user;


import com.lkdrestaurantserver.dto.ChangePasswordDto;
import com.lkdrestaurantserver.dto.SignupRequest;
import com.lkdrestaurantserver.dto.UserDto;
import com.lkdrestaurantserver.responce.GeneralResponse;

public interface UserService {

     UserDto createUser(SignupRequest signupRequest) throws Exception;

     Boolean hasUserWithEmail(String email);

     void createAdminAccount();

     UserDto getUser(Long userId);

     GeneralResponse updateUser(UserDto userDto);

     GeneralResponse changePassword(ChangePasswordDto changePasswordDto);

}
