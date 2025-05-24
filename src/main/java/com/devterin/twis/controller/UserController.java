package com.devterin.twis.controller;

import com.devterin.twis.model.User;
import com.devterin.twis.service.UserService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/profile")
    public User getUserProfile(@RequestHeader("Authorization") String token) {
        User user = userService.findUserByJwtToken(token);
        user.setPassword(null);
        return user;
    }

    @PutMapping
    public User updateUser(@RequestHeader("Authorization") String token,
                           @RequestBody User user) {
        User reqUser = userService.findUserByJwtToken(token);
        return userService.updateUser(user, reqUser.getId());
    }

    @PutMapping("/follow/{userId2}")
    public User followUserHandle(@RequestHeader("Authorization") String token,
                                 @PathVariable Integer userId2) {

        User reqUser = userService.findUserByJwtToken(token);
        return userService.followUser(reqUser.getId(), userId2);
    }

    @GetMapping("/search")
    public List<User> searchUser(@Param("keyword") String keyword) {
        return userService.searchUser(keyword);
    }
}
