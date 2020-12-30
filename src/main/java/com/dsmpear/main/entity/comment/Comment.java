package com.dsmpear.main.entity.comment;

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
@Table(name = "comment_tbl")
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, name="report_id")
    private Integer reportId;

    @Column(nullable = false, name="user_email")
    private String userEmail;

    @Column(nullable = false, name="created_at")
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private String content;

    public Comment updateContent(String content) {
        this.content = content;
        return this;
    }

}
