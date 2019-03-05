package com.wari.eurekaServer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private Long createdAt;
    private Long lastModified;
    private Long id;

    public User(String username, String firstName, String lastName, String email) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
}
