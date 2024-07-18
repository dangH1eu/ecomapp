package com.project.ecomapp.service;

import com.project.ecomapp.dto.UserDTO;
import com.project.ecomapp.exception.DataNotFoundException;
import com.project.ecomapp.model.User;
import org.springframework.stereotype.Service;

public interface UserService {
    User createUser(UserDTO userDTO) throws DataNotFoundException;
    String login(String phoneNumber, String password);

}
