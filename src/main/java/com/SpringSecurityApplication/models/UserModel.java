package com.SpringSecurityApplication.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="UserDetails")
@Getter
@Setter
@ToString
@AllArgsConstructor
public class UserModel {
    @Id
    private String id;
    private String username;
    private String password;
    private String roles;
}
