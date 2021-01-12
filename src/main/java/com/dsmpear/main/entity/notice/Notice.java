package com.dsmpear.main.entity.notice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;


@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "notice_tbl")
@Entity
public class Notice {

    @Id @GeneratedValue
    private Integer id;

    @Column(length = 50, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(nullable = false, name = "created_at")
    private LocalDateTime createdAt;

    @Column(length = 50, name = "file_name")
    private String fileName;

}
