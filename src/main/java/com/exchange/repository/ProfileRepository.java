package com.exchange.repository;

import com.exchange.model.User;
import com.exchange.utils.JsonFileUtil;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

// ProfileRepository - handles profile.json operations
@Repository
public class ProfileRepository {
    
    private static final String PROFILE_FILE = "data/profile.json";
    
    // Get all users
    public List<User> getAllUsers() {
        return JsonFileUtil.readJsonArray(PROFILE_FILE, User.class);
    }
    
    // Find user by email
    public User findUserByEmail(String email) {
        List<User> users = getAllUsers();
        return users.stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }
    
    // Find user by ID
    public User findUserById(String userId) {
        List<User> users = getAllUsers();
        return users.stream()
                .filter(u -> u.getId().equals(userId))
                .findFirst()
                .orElse(null);
    }
    
    // Find user by address
    public User findUserByAddress(String address) {
        List<User> users = getAllUsers();
        return users.stream()
                .filter(u -> u.getAddress().equals(address))
                .findFirst()
                .orElse(null);
    }
    
    // Save a new user
    public void saveUser(User user) {
        List<User> users = getAllUsers();
        users.add(user);
        saveUserList(users);
    }
    
    // Update an existing user
    public void updateUser(User updatedUser) {
        List<User> users = getAllUsers();
        List<User> newUsers = users.stream()
                .map(u -> u.getId().equals(updatedUser.getId()) ? updatedUser : u)
                .collect(Collectors.toList());
        saveUserList(newUsers);
    }
    
    // Delete user by ID
    public boolean deleteUser(String userId) {
        List<User> users = getAllUsers();
        int sizeBefore = users.size();
        List<User> newUsers = users.stream()
                .filter(u -> !u.getId().equals(userId))
                .collect(Collectors.toList());
        
        if (newUsers.size() < sizeBefore) {
            saveUserList(newUsers);
            return true;
        }
        return false;
    }
    
    // Save user list to file
    public void saveUserList(List<User> users) {
        JsonFileUtil.writeJsonArray(PROFILE_FILE, users);
    }
    
    // Check if email exists
    public boolean emailExists(String email) {
        return findUserByEmail(email) != null;
    }
}
