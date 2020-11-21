package com.dsmpear.main.service.comment;

import com.dsmpear.main.payload.request.CommentRequest;

public interface CommentService {
    void createComment(CommentRequest commentRequest);
    Integer updateComment(Integer commentId, String content);
    void deleteComment(Integer commentId);
}
