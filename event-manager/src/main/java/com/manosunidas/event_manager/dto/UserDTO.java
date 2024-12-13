package com.manosunidas.event_manager.dto;

import com.manosunidas.event_manager.model.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private String username;
    private String password;
    private String name;
    private String email;
    private String phone;
    private int age;
    private Role role;
}
