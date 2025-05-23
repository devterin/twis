package com.devterin.twis.service;

import com.devterin.twis.model.User;
import com.devterin.twis.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public User registerUser(User user) {
        User newUser = User.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
        return userRepository.save(newUser);
    }

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

    public User followUser(Integer userId1, Integer userId2) {
        User user1 = findUserById(userId1);
        User user2 = findUserById(userId2);
        user2.getFollowers().add(userId1);
        user1.getFollowings().add(userId2);
        userRepository.save(user1);
        userRepository.save(user2);
        return user1;
    }

    public User updateUser(User user, Integer userId) {
        Optional<User> currentUser = userRepository.findById(userId);
        if (currentUser.isEmpty()) {
            throw new RuntimeException("user not found");
        }
        user.setFirstName(currentUser.get().getFirstName());
        user.setLastName(currentUser.get().getLastName());
        user.setEmail(currentUser.get().getEmail());
        user.setPassword(currentUser.get().getPassword());
        user.setGender(currentUser.get().getGender());
        return userRepository.save(user);
    }

    public List<User> searchUser(String keyword) {
        return userRepository.searchUser(keyword);
    }

}
