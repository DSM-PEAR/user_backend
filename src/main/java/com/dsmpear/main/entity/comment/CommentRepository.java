package com.dsmpear.main.entity.comment;

import java.util.List;

public interface CommentRepository {
    List<Comment> findAllByReportIdOrderByIdAsc(Integer boardId);
}
