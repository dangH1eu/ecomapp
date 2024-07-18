package com.project.ecomapp.serviceimpl;

import com.project.ecomapp.dto.UserDTO;
import com.project.ecomapp.exception.DataNotFoundException;
import com.project.ecomapp.model.Role;
import com.project.ecomapp.model.User;
import com.project.ecomapp.repository.RoleRepository;
import com.project.ecomapp.repository.UserRepository;
import com.project.ecomapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }
    @Override
    public User createUser(UserDTO userDTO) throws DataNotFoundException {
        String phoneNumber = userDTO.getPhoneNumber();
        // check if phone number already exists
        if(userRepository.existsByPhoneNumber(phoneNumber)){
            throw new DataIntegrityViolationException("phone number already exists");
        }

        // convert from userDTO to user
        User newUser = User.builder()
                .fullName(userDTO.getFullName())
                .phoneNumber(userDTO.getPhoneNumber())
                .password(userDTO.getPassword())
                .address(userDTO.getAddress())
                .dateOfBirth(userDTO.getDateOfBirth())
                .facebookAccountId(userDTO.getFacebookAccountId())
                .googleAccountId(userDTO.getGoogleAccountId())
                .build();
        Role role = roleRepository.findById(userDTO.getRoleId())
                .orElseThrow(() -> new DataNotFoundException("role not found"));

        newUser.setRole(role);
        // if user has accountId, password is not required
        if(userDTO.getFacebookAccountId() == 0 && userDTO.getGoogleAccountId() == 0) {
            String password = userDTO.getPassword();


        }
        return userRepository.save(newUser);
    }

    @Override
    public String login(String phoneNumber, String password) {
        //

        return null;
    }
}
