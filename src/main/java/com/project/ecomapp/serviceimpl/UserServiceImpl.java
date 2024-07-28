package com.project.ecomapp.serviceimpl;

import com.project.ecomapp.component.JwtTokenUtil;
import com.project.ecomapp.dto.UserDTO;
import com.project.ecomapp.exception.DataNotFoundException;
import com.project.ecomapp.exception.PermissionDenyException;
import com.project.ecomapp.model.Role;
import com.project.ecomapp.model.User;
import com.project.ecomapp.repository.RoleRepository;
import com.project.ecomapp.repository.UserRepository;
import com.project.ecomapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;


    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder, JwtTokenUtil jwtTokenUtil,
                           AuthenticationManager authenticationManager){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenUtil = jwtTokenUtil;
        this.authenticationManager = authenticationManager;
    }
    @Override
    public User createUser(UserDTO userDTO) throws Exception {
        String phoneNumber = userDTO.getPhoneNumber();
        // check if phone number already exists
        if(userRepository.existsByPhoneNumber(phoneNumber)){
            throw new DataIntegrityViolationException("phone number already exists");
        }
        Role role = roleRepository.findById(userDTO.getRoleId())
                .orElseThrow(() -> new DataNotFoundException("role not found"));
        if(role.getName().toUpperCase().equals(Role.ADMIN)){
            throw new PermissionDenyException("cannot register an admin account");

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

        newUser.setRole(role);
        // if user has accountId, password is not required
        if(userDTO.getFacebookAccountId() == 0 && userDTO.getGoogleAccountId() == 0) {
            String password = userDTO.getPassword();
            String encodedPassword = passwordEncoder.encode(password);
            newUser.setPassword(encodedPassword);
        }
        return userRepository.save(newUser);
    }

    @Override
    public String login(String phoneNumber, String password) throws Exception {
        Optional<User> optionalUser = userRepository.findByPhoneNumber(phoneNumber);
        if(optionalUser.isEmpty()){
            throw new DataNotFoundException("invalid phonenumber or password");
        }
//        return optionalUser.get();
        User existingUser = optionalUser.get();
        // check password
        if(existingUser.getFacebookAccountId() == 0 && existingUser.getGoogleAccountId() == 0){
            if(!passwordEncoder.matches(password, existingUser.getPassword())){
                throw new BadCredentialsException("wrong phone number or password");
            }
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                phoneNumber, password,
                existingUser.getAuthorities()
        );

        // authenticate with spring security
        authenticationManager.authenticate(authenticationToken);
        return jwtTokenUtil.generateToken(existingUser);
    }
}
