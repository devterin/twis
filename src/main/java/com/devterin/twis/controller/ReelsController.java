package com.devterin.twis.controller;

import com.devterin.twis.model.Reels;
import com.devterin.twis.model.User;
import com.devterin.twis.service.ReelsService;
import com.devterin.twis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reels")
public class ReelsController {
    @Autowired
    private ReelsService reelsService;

    @Autowired
    private UserService userService;

    @PostMapping
    public Reels addReels(@RequestHeader("Authorization") String token,
                          @RequestBody Reels reels) {

        User user = userService.findUserByJwtToken(token);
        return reelsService.createReels(reels, user);
    }

    @GetMapping
    public List<Reels> getAllReels() {
        return reelsService.findAllReels();
    }

    @GetMapping("/{userId}")
    public List<Reels> findUsersReel(@PathVariable Integer userId) {
        return reelsService.findUsersReel(userId);
    }
}
