package com.dsmpear.main.entity.team;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.function.Consumer;

@Entity
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Team {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String userEmail;

    public void updateUser(String memberEamil){
        setIfNotNull(this::setUserEmail,memberEamil);
    }

    private <T> void setIfNotNull(final Consumer<T> setter, final T value) {
        if (value != null)
            setter.accept(value);
    }
}
