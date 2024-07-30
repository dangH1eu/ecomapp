package com.project.ecomapp.controller;

import com.project.ecomapp.dto.UserDTO;
import com.project.ecomapp.dto.UserLoginDTO;
import com.project.ecomapp.model.User;
import com.project.ecomapp.response.LoginResponse;
import com.project.ecomapp.service.UserService;
import com.project.ecomapp.component.LocalizationUtil;
import com.project.ecomapp.util.MessageKey;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/users")
public class UserController {

    private final UserService userService;
    private final LocalizationUtil localizationUtils;
    @Autowired
    public UserController(UserService userService,
                          LocalizationUtil localizationUtils
                          ){
        this.userService = userService;
        this.localizationUtils = localizationUtils;
    }


    @PostMapping("/register")
    public ResponseEntity<?> createUser(
            @Valid @RequestBody UserDTO userDTO,
            BindingResult result
    ) {
        try {
            if (result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            if(!userDTO.getPassword().equals(userDTO.getRepeatPassword())){
                return ResponseEntity.badRequest().body(localizationUtils.getLocalizedMessage(MessageKey.PASSWORD_NOT_MATCH));
            }

            User user = userService.createUser(userDTO);
            return ResponseEntity.status(HttpStatus.OK).body(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody UserLoginDTO userLoginDTO
    ) {
        try {
            String token = userService.login(
                    userLoginDTO.getPhoneNumber(),
                    userLoginDTO.getPassword(),
                    userLoginDTO.getRoleId()
            );
            return ResponseEntity.ok(
                    LoginResponse.builder()
                            .message(localizationUtils.getLocalizedMessage(MessageKey.LOGIN_SUCCESSFULLY))
                            .token(token)
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
            LoginResponse.builder()
                    .message(localizationUtils.getLocalizedMessage(MessageKey.LOGIN_FAILED, e.getMessage()))
                    .build()
            );
        }



    }

}
