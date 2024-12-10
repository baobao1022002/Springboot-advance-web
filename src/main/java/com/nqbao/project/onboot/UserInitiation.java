package com.nqbao.project.onboot;

import com.nqbao.project.model.Gender;
import com.nqbao.project.model.UserDTO;
import com.nqbao.project.model.UserRole;
import com.nqbao.project.service.UserService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserInitiation {
    private final UserService userService;

    @PostConstruct
    public void init() throws NoSuchFieldException, IllegalAccessException {
        List<UserDTO> users = userService.findAll();
        if (users.isEmpty()) {
            UserDTO admin = new UserDTO();
            admin.setName("admin");
            admin.setGender(Gender.MALE);
            admin.setAddress("Viet Nam");
            admin.setEmail("admin@gmail.com");
            admin.setUsername("admin");
            admin.setPassword("123456");
            admin.setRole(UserRole.ROLE_ADMIN);
            userService.create(admin);
        }
    }
}
