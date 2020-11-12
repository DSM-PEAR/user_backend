package com.dsmpear.main.entity.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "user_tbl")
@Getter @Builder @NoArgsConstructor @AllArgsConstructor
public class User {
    @Id
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(name = "self_intro")
    private String selfIntro;

    @Column(name = "auth_status", nullable = false)
    private Boolean authStatus;
}
