package com.dsmpear.main.entity.question;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Getter
@Entity
@Table(name = "question_tbl")
@NoArgsConstructor
@AllArgsConstructor
public class Question{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false,length = 30)
    private String email;

    @Column(nullable = false, length = 150)
    private String description;

}
