package com.project.ecomapp.service;

import com.project.ecomapp.dto.UserDTO;
import com.project.ecomapp.model.User;

public interface UserService {
    User createUser(UserDTO userDTO) throws Exception;
    String login(String phoneNumber, String password, Long roleId) throws Exception;

}
