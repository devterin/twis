package com.devterin.twis.controller;

import com.devterin.twis.model.Post;
import com.devterin.twis.response.ApiResponse;
import com.devterin.twis.service.PostService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PostController {
    @Autowired
    PostService postService;

    @PostMapping("/posts/{userId}")
    public ResponseEntity<Post> createPost(Post post, @PathVariable Integer userId) {
        Post createPost = postService.createPost(post, userId);

        return new ResponseEntity<>(createPost, HttpStatus.CREATED);
    }

    @DeleteMapping("/posts/{postId}/{userId}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId,
                                           @PathVariable Integer userId) {
        String message = postService.deletePost(postId, userId);
        ApiResponse response = new ApiResponse(message, true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/posts/{postId}/{userId}")
    public ResponseEntity<Post> savedPost(@PathVariable Integer postId,
                                                  @PathVariable Integer userId) {
        Post post = postService.savedPost(postId, userId);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @PutMapping("/posts/like/{postId}/{userId}")
    public ResponseEntity<Post> likePost(@PathVariable Integer postId,
                                          @PathVariable Integer userId) {
        Post post = postService.likePost(postId, userId);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<Post> getPostById(@PathVariable Integer postId) {
        Post post = postService.findPostById(postId);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @GetMapping("/post/user/{userId}")
    public ResponseEntity<List<Post>> getPostsByUserId(@PathVariable Integer userId) {
        List<Post> postByUser = postService.findPostByUser(userId);
        return new ResponseEntity<>(postByUser, HttpStatus.OK);
    }


    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> posts = postService.findAllPosts();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

}
