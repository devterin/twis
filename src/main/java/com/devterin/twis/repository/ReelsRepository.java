package com.devterin.twis.repository;

import com.devterin.twis.model.Reels;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReelsRepository extends JpaRepository<Reels, Integer> {
    List<Reels> findByUserId(Integer userId);
}
