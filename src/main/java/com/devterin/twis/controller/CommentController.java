package com.devterin.twis.controller;

import com.devterin.twis.model.Comment;
import com.devterin.twis.model.User;
import com.devterin.twis.service.CommentService;
import com.devterin.twis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;

    @PostMapping("/{postId}")
    public Comment createComment(@RequestHeader("Authorization") String token,
                                 @RequestBody Comment comment,
                                 @PathVariable Integer postId) {
        User user = userService.findUserByJwtToken(token);


        return commentService.createComment(comment, postId, user.getId());
    }

    @PutMapping("/like/{commentId}")
    public Comment likeComment(@RequestHeader("Authorization") String token,
                               @PathVariable Integer commentId) {
        User user = userService.findUserByJwtToken(token);


        return commentService.likeComment(commentId, user.getId());
    }
}
