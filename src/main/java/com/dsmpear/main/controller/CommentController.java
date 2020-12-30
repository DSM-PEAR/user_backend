package com.dsmpear.main.controller;

import com.dsmpear.main.payload.request.CommentRequest;
import com.dsmpear.main.service.comment.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/comment")
@RestController
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public void writeComment(@RequestBody @Valid CommentRequest commentRequest) {
        commentService.createComment(commentRequest);
    }

    @DeleteMapping("/{commentId}")
    public void deleteComment(@PathVariable Integer commentId) {
        commentService.deleteComment(commentId);
    }

    @PatchMapping("/{commentId}")
    public void updateComment(@PathVariable Integer commentId, @RequestParam String content) {
        commentService.updateComment(commentId, content);
    }

}
