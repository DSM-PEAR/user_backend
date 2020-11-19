package com.dsmpear.main.service.comment;

import com.dsmpear.main.payload.request.CommentRequest;

public interface CommentService {
    void createComment(Integer boardId, CommentRequest commentRequest);
    Integer updateComment(Integer commentId, CommentRequest commentRequest);
    void deleteComment(Integer commentId);
}
