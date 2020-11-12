package com.dsmpear.main.entity.team;

import com.dsmpear.main.entity.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Builder
@Getter
@Table(name = "team")
@AllArgsConstructor
@NoArgsConstructor
public class Team {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private Integer reportId;

    @Email
    private String userEmail;

    //기본값은 유저 이름
    private String name;

    @OneToMany(cascade=CascadeType.ALL, mappedBy = "team")
    private List<Member> members;

    public void updateUser(String memberEmail){
        this.userEmail=memberEmail;
    }

    public void updateName(String name){
        this.name=name;
    }

}
