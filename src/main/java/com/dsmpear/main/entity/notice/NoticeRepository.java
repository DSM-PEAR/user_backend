package com.dsmpear.main.entity.notice;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface NoticeRepository extends CrudRepository<Notice,Integer> {
    Page<Notice> findAllByOrderByCreatedAtDesc(Pageable page);
}
