package com.example.kpilesson.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class User {
    public User(UserEntity entity) {
        this.setId(entity.getId());
        this.setFirstName(entity.getFirstName());
        this.setLastName(entity.getLastName());
    }

    private Long id;
    private String firstName;
    private String lastName;

    public String fullName() {
        return this.lastName + " " + this.firstName;
    }
}
