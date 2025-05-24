package com.devterin.twis.service;

import com.devterin.twis.model.Reels;
import com.devterin.twis.model.User;
import com.devterin.twis.repository.ReelsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReelsService {
    @Autowired
    private ReelsRepository reelsRepository;
    @Autowired
    private UserService userService;

    public Reels createReels(Reels reels, User user) {
        Reels newReels = new Reels();
        newReels.setTitle(reels.getTitle());
        newReels.setVideo(reels.getVideo());
        newReels.setUser(user);
        return reelsRepository.save(newReels);
    }

    public List<Reels> findAllReels() {
        return reelsRepository.findAll();
    }

    public List<Reels> findUsersReel(Integer userId) {
        userService.findUserById(userId);
        return reelsRepository.findByUserId(userId);
    }


}
