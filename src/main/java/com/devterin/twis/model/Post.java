package com.devterin.twis.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String caption;
    private String image;
    private String video;
    private LocalDateTime createdAt;

    @OneToMany
    private List<User> liked;

    @ManyToOne
    private User user;


}
