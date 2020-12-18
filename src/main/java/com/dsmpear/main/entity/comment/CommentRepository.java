package com.dsmpear.main.entity.comment;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comment, Integer> {
    List<Comment> findAllByReportIdOrderByCreatedAtAsc(Integer boardId);
}
