package com.lkdrestaurantserver.services.user;

import com.lkdrestaurantserver.dto.ChangePasswordDto;
import com.lkdrestaurantserver.dto.SignupRequest;
import com.lkdrestaurantserver.dto.UserDto;
import com.lkdrestaurantserver.entity.User;
import com.lkdrestaurantserver.enums.UserRole;
import com.lkdrestaurantserver.repo.UserRepo;
import com.lkdrestaurantserver.responce.GeneralResponse;
import com.lkdrestaurantserver.services.cart.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CartService cartService;

    @Override
    public void createAdminAccount() {
        User adminAccount = userRepo.findByRole(UserRole.ADMIN);
        if(null == adminAccount){
            User user = new User();
            user.setEmail("admin@gmail.com");
            user.setName("Admin");
            user.setRole(UserRole.ADMIN);
            user.setPassword(new BCryptPasswordEncoder().encode("tayyab"));
            userRepo.save(user);
        }
    }

    @Transactional
    public UserDto createUser(SignupRequest signupRequest) throws Exception {
        User user = new User(signupRequest.getEmail(), new BCryptPasswordEncoder().encode(signupRequest.getPassword()), signupRequest.getName(), UserRole.USER);
        user = userRepo.save(user);
        if (user == null)
            return  null;

        cartService.createCart(user);
        return user.mapUsertoUserDto();
    }


    public Boolean hasUserWithEmail(String email) {
        return userRepo.findFirstByEmail(email) != null;
    }

    @Override
    public UserDto getUser(Long userId) {
        UserDto userDto = null;
        Optional<User> optionalUser = userRepo.findById(userId);
        if(optionalUser.isPresent()){
            userDto = optionalUser.get().mapUsertoUserDto();
            userDto.setReturnedImg(optionalUser.get().getImg());
        }
        return userDto;
    }

    @Override
    public GeneralResponse changePassword(ChangePasswordDto changePasswordDto) {
        GeneralResponse response = new GeneralResponse();
        User user = null;
        Optional<User> userOptional = userRepo.findById(changePasswordDto.getUserId());
        if (userOptional.isPresent()) {
            user = userOptional.get();
            if(new BCryptPasswordEncoder().matches(changePasswordDto.getOldPassword(),user.getPassword())) {

                user.setPassword(new BCryptPasswordEncoder().encode(changePasswordDto.getNewPassword()));

                userRepo.save(user);
                response.setMessage("Password Updated Successfully");
                response.setStatus(HttpStatus.CREATED);
            }
            else{
                response.setStatus(HttpStatus.NOT_ACCEPTABLE);
                response.setMessage("Password Doesn't Matched");
            }
        } else {
            response.setStatus(HttpStatus.NOT_ACCEPTABLE);
            response.setMessage("User Not Found");
        }
        return response;
    }

    public GeneralResponse updateUser(UserDto userDto)  {
        GeneralResponse response = new GeneralResponse();
        User user = null;
        try {
            Optional<User> userOptional = userRepo.findById(userDto.getId());
            if (userOptional.isPresent()) {
                user = userOptional.get();

                user.setName(userDto.getName());
                user.setImg(userDto.getImg().getBytes());


                userRepo.save(user);
                response.setMessage("User Updated Successfully");
                response.setStatus(HttpStatus.CREATED);
                return response;
            } else {
                response.setStatus(HttpStatus.NOT_ACCEPTABLE);
                response.setMessage("User Not Found");
                return response;
            }
        }catch (Exception e){
            response.setStatus(HttpStatus.NOT_ACCEPTABLE);
            response.setMessage("Unable to process Img");
            return response;
        }
    }

}
