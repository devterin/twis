package com.devterin.twis.service;

import com.devterin.twis.model.Reels;
import com.devterin.twis.repository.ReelsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReelsService {
    @Autowired
    private ReelsRepository reelsRepository;

    public List<Reels> findByUserId(Integer userId) {

    }
}
