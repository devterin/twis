package com.devterin.twis.service;

import com.devterin.twis.model.User;
import com.devterin.twis.repository.UserRepository;
import com.devterin.twis.response.ApiResponse;
import com.devterin.twis.response.AuthResponse;
import com.devterin.twis.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;


    public User findUserById(Integer id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        }
        throw new RuntimeException("user not found");
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findUserByJwtToken(String token) {
        String email = JwtUtils.getEmailFromJwtToken(token);
        return userRepository.findByEmail(email);
    }

    public User followUser(Integer reqUserId, Integer userId2) {
        User reqUser = findUserById(reqUserId);
        User user2 = findUserById(userId2);
        user2.getFollowers().add(reqUser.getId());
        reqUser.getFollowings().add(userId2);
        userRepository.save(reqUser);
        userRepository.save(user2);
        return reqUser;
    }

    public User updateUser(User user, Integer userId) {
        Optional<User> currentUserOpt = userRepository.findById(userId);
        if (currentUserOpt.isEmpty()) {
            throw new RuntimeException("user not found");
        }
        User currentUser = currentUserOpt.get();
        if (user.getFirstName() != null) {
            currentUser.setFirstName(user.getFirstName());
        }
        if (user.getLastName() != null) {
            currentUser.setLastName(user.getLastName());
        }
        if (user.getEmail() != null) {
            currentUser.setEmail(user.getEmail());
        }
        if (user.getPassword() != null) {
            currentUser.setPassword(user.getPassword());
        }
        if (user.getGender() != null) {
            currentUser.setGender(user.getGender());
        }
        return userRepository.save(currentUser);
    }

    public List<User> searchUser(String keyword) {
        return userRepository.searchUser(keyword);
    }

}
