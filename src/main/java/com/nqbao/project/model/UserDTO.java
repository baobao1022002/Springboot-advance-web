package com.nqbao.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private int id;

    private String name;

    private Gender gender;

    private String address;

    private String email;

    private String username;

    private String password;

    private UserRole role;

    public UserDTO(User user){
        this.id= user.getId();
        this.name=user.getName();
        this.gender=user.getGender();
        this.address=user.getAddress();
        this.email=user.getEmail();
        this.username=user.getUsername();
        this.password=user.getPassword();
        this.role=user.getRole();
    }
}
