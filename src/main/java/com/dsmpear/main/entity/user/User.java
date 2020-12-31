package com.dsmpear.main.entity.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_tbl")
@Entity
public class User {

    @Id
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(name = "self_intro")
    private String selfIntro;

    @Column(name = "git_hub")
    private String gitHub;

    @Column(name = "auth_status", nullable = false)
    private Boolean authStatus;

    public void authenticatedSuccess() {
        this.authStatus = true;
    }

    public void setSelfIntro(String intro){
        this.selfIntro = intro;
    }

    public void setGitHub(String gitHub){
        this.gitHub = gitHub;
    }

}
