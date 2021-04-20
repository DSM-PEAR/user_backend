package com.dsmpear.main.entity.comment;

import com.dsmpear.main.entity.report.Report;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @ManyToOne
    @JoinColumn(name = "report_id")
    @JsonManagedReference
    private Report report;

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
