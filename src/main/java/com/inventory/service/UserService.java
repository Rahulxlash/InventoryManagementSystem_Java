package com.inventory.service;

import com.inventory.entity.User;
import com.inventory.entity.UserLog;
import com.inventory.repository.UserLogRepository;
import com.inventory.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserLogRepository userLogRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    public Optional<User> getUserById(Integer id) {
        return userRepository.findById(id);
    }
    
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    public User saveUser(User user) {
        if (userRepository.existsByFullNameAndLocationAndPhoneAndUserType(
                user.getFullName(), user.getLocation(), user.getPhone(), user.getUserType())) {
            throw new RuntimeException("User already exists");
        }
        
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
    
    public User updateUser(User user) {
        if (!userRepository.existsById(user.getId())) {
            throw new RuntimeException("User not found");
        }
        
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        
        return userRepository.save(user);
    }
    
    public void deleteUser(Integer id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(id);
    }
    
    public void deleteUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.delete(user);
    }
    
    public List<User> searchUsers(String searchText) {
        return userRepository.searchUsers(searchText);
    }
    
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
    
    public boolean checkLogin(String username, String password, String userType) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            User foundUser = user.get();
            return passwordEncoder.matches(password, foundUser.getPassword()) 
                   && foundUser.getUserType().equals(userType);
        }
        return false;
    }
    
    public void changePassword(String username, String newPassword) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
    
    public void addUserLogin(String username, String inTime, String outTime) {
        UserLog userLog = new UserLog(username, inTime, outTime);
        userLogRepository.save(userLog);
    }
    
    public List<UserLog> getAllUserLogs() {
        return userLogRepository.findAllWithUserDetails();
    }
    
    public List<UserLog> getUserLogsByUsername(String username) {
        return userLogRepository.findByUsername(username);
    }
}
