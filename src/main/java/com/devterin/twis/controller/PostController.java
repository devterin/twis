package com.devterin.twis.controller;

import com.devterin.twis.model.Post;
import com.devterin.twis.model.User;
import com.devterin.twis.response.ApiResponse;
import com.devterin.twis.service.PostService;
import com.devterin.twis.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    @Autowired
    PostService postService;
    @Autowired
    UserService userService;

    @PostMapping("/create")
    public ResponseEntity<Post> createPost(@RequestBody Post post,
                                           @RequestHeader("Authorization") String token) {
        User reqUser = userService.findUserByJwtToken(token);

        Post createPost = postService.createPost(post, reqUser.getId());

        return new ResponseEntity<>(createPost, HttpStatus.CREATED);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiResponse> deletePost(@RequestHeader("Authorization") String token,
                                                  @PathVariable Integer postId) {

        User reqUser = userService.findUserByJwtToken(token);
        String message = postService.deletePost(postId, reqUser.getId());
        ApiResponse response = new ApiResponse(message, true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/saved/{postId}")
    public ResponseEntity<Post> savedPost(@PathVariable Integer postId,
                                          @RequestHeader("Authorization") String token) {
        User reqUser = userService.findUserByJwtToken(token);
        Post post = postService.savedPost(postId, reqUser.getId());
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @PutMapping("/like/{postId}")
    public ResponseEntity<Post> likePost(@RequestHeader("Authorization") String token,
                                         @PathVariable Integer postId) {
        User reqUser = userService.findUserByJwtToken(token);
        Post post = postService.likePost(postId, reqUser.getId());
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<Post> getPostById(@PathVariable Integer postId) {
        Post post = postService.findPostById(postId);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
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
