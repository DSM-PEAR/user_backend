package com.dsmpear.main.controller;

import com.dsmpear.main.payload.request.CommentRequest;
import com.dsmpear.main.service.comment.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public void writeComment(@RequestBody @Valid CommentRequest commentRequest) {
        commentService.createComment(commentRequest);
    }

    @DeleteMapping
    public void deleteComment(@PathVariable Integer commentId) {
        commentService.deleteComment(commentId);
    }
/*
    @PatchMapping
    public void updateComment(@RequestParam) {
        commentService.updateComment(com)
    }*/
}
