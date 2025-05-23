package com.devterin.twis.controller;

import com.devterin.twis.model.User;
import com.devterin.twis.service.UserService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping
    public User addUser(@RequestBody User user) {
        return userService.registerUser(user);
    }

    @PutMapping("/{id}")
    public User updateUser(@RequestBody User user, @PathVariable int id) {
        return userService.updateUser(user, id);
    }

    @PutMapping("/{userId1}/{userId2}")
    public User followUserHandle(@PathVariable int userId1, @PathVariable int userId2) {
        return userService.followUser(userId1, userId2);
    }

    @GetMapping("/search")
    public List<User> searchUser(@Param("keyword") String keyword) {
        return userService.searchUser(keyword);
    }
}
