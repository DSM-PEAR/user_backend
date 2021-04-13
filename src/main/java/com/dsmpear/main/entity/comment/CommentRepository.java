package com.dsmpear.main.entity.comment;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Integer> {
    List<Comment> findAllByReportIdOrderByCreatedAtDesc(Integer reportId);
    void deleteAllByReportId(Integer reportId);
    List<Comment> findAllBy();
}
