package com.devterin.twis.service;

import com.devterin.twis.model.Comment;
import com.devterin.twis.model.Post;
import com.devterin.twis.model.User;
import com.devterin.twis.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    public Comment createComment(Comment comment, Integer postId, Integer userId) {
        User user = userService.findUserById(userId);
        Post post = postService.findPostById(postId);

        comment.setUser(user);
        comment.setContent(comment.getContent());
        comment.setCreatedAt(LocalDateTime.now());

        Comment saved = commentRepository.save(comment);

        post.getComments().add(saved);
        return saved;
    }

    public Comment findCommentById(Integer commentId) {
        Optional<Comment> comment = commentRepository.findById(commentId);

        if (comment.isEmpty()) {
            throw new RuntimeException("Comment not found");
        }
        return comment.get();
    }

    public Comment likeComment(Integer commentId, Integer userId) {
        Comment comment = findCommentById(commentId);
        User user = userService.findUserById(userId);
        if (!comment.getLiked().contains(user)) {
            comment.getLiked().add(user);
        } else {
            comment.getLiked().remove(user);
        }
        return commentRepository.save(comment);
    }
}
