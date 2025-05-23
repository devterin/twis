package com.devterin.twis.repository;

import com.devterin.twis.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);
    @Query("select u from User u where u.firstName like %:keyword% or u.lastName like %:keyword% or u.email like %:keyword%")
    List<User> searchUser(@Param("keyword") String keyword);
}
