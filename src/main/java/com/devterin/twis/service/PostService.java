package com.devterin.twis.service;

import com.devterin.twis.model.Post;
import com.devterin.twis.model.User;
import com.devterin.twis.repository.PostRepository;
import com.devterin.twis.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    @Autowired
    PostRepository postRepository;

    @Autowired
    UserService userService;
    @Autowired
    private UserRepository userRepository;

    public Post createPost(Post post, Integer userId) {
        User user = userService.findUserById(userId);

        Post newPost = new Post();
        newPost.setCaption(post.getCaption());
        newPost.setVideo(post.getVideo());
        newPost.setImage(post.getImage());
        newPost.setCreatedAt(LocalDateTime.now());
        newPost.setUser(user);

        return postRepository.save(newPost);
    }

    public Post findPostById(Integer postId) {
        Optional<Post> post = postRepository.findById(postId);
        if (post.isEmpty()) {
            throw new RuntimeException("Post not found");
        }
        return post.get();
    }

    public Post savedPost(Integer postId, Integer userId) {
        Post post = findPostById(postId);
        User user = userService.findUserById(userId);
        if (user.getPosts().contains(post)) {
            user.getPosts().remove(post);
        } else user.getPosts().add(post);
        return postRepository.save(post);
    }

    public String deletePost(Integer postId, Integer userId) {
        Post post = findPostById(postId);
        User user = userService.findUserById(userId);
        if (!post.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Post doesn't belong to user");
        }
        postRepository.delete(post);
        return "Post deleted";
    }

    public List<Post> findPostByUser(Integer userId) {
        return postRepository.findPostByUserId(userId);
    }

    public List<Post> findAllPosts() {
        return postRepository.findAll();
    }

    public Post likePost(Integer postId, Integer userId) {
        Post post = findPostById(postId);
        User user = userService.findUserById(userId);
        if (post.getLiked().contains(user)) {
            post.getLiked().remove(user);
        } else {
            post.getLiked().add(user);
        }
        return postRepository.save(post);
    }


}
