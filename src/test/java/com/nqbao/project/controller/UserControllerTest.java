package com.nqbao.project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nqbao.project.model.Gender;
import com.nqbao.project.model.UserDTO;
import com.nqbao.project.model.UserRole;
import com.nqbao.project.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class UserControllerTest {

    private UserDTO expected;

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void init() {
        expected = new UserDTO(1, "Test", Gender.MALE, "Dak Nong", "test@gmail.com", "test", "123456", UserRole.ROLE_EMPLOYEE);
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(userController)
                .build();
    }

    // =========================================== Get All Users ==========================================
    @Test
    void testGetAllSuccess() throws Exception {
        List<UserDTO> users = Arrays.asList(new UserDTO(1, "A", Gender.MALE, "Dak Nong", "a@gmail.com", "test", "123456", UserRole.ROLE_EMPLOYEE),
                new UserDTO(2, "B", Gender.MALE, "Dak Lak", "b@gmail.com", "test", "123456", UserRole.ROLE_EMPLOYEE));
        when(userService.findAll()).thenReturn(users);
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("A")))
                .andExpect(jsonPath("$[1].email", is("b@gmail.com")))
                .andExpect(jsonPath("$[1].gender", is("MALE")))
                .andExpect(jsonPath("$[1].role", is("ROLE_EMPLOYEE")));
        verify(userService, times(1)).findAll();
        verifyNoMoreInteractions(userService);
    }


    // =========================================== Get User By ID =========================================
    @Test
    void testGetByIdSuccess() throws Exception {
        when(userService.findById(expected.getId())).thenReturn(Optional.ofNullable(expected));
        mockMvc.perform(get("/users/" + expected.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(expected.getId()))
                .andExpect(jsonPath("$.name", is(expected.getName())))
                .andExpect(jsonPath("$.email", is(expected.getEmail())))
                .andExpect(jsonPath("$.gender", is("MALE")))
                .andExpect(jsonPath("$.address", is(expected.getAddress())));
        verify(userService, times(1)).findById(1);
        verifyNoMoreInteractions(userService);
    }

    @Test
    void testGetByIdNotFound() throws Exception {
        when(userService.findById(expected.getId())).thenReturn(Optional.empty());
        mockMvc.perform(get("/users/" + expected.getId()))
                .andExpect(status().isNotFound());
        verify(userService, times(1)).findById(expected.getId());
        verifyNoMoreInteractions(userService);
    }

    // =========================================== Create New User ========================================
    @Test
    void testCreateSuccess() throws Exception {

        when(userService.create(expected)).thenReturn(expected);
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(expected)))
                .andExpect(status().isCreated());
        verify(userService, times(1)).create(expected);
        verifyNoMoreInteractions(userService);
    }

    // =========================================== Update  User ===================================
    @Test
    void testUpdateSuccess() throws Exception {
        UserDTO updatedUser = new UserDTO(expected.getId(), "Bai Updated", Gender.MALE, "Dak Nong", "bai_updated@gmail.com", "test", "123456", UserRole.ROLE_EMPLOYEE);
        when(userService.updateById(expected.getId(), updatedUser)).thenReturn(updatedUser);
        mockMvc.perform(put("/users/" + expected.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedUser))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(updatedUser.getName()))
                .andExpect(jsonPath("$.email").value(updatedUser.getEmail()));
        verify(userService, times(1)).updateById(expected.getId(), updatedUser);
        verifyNoMoreInteractions(userService);
    }


    // =========================================== Delete User ============================================
    @Test
    void testDeleteSuccess() throws Exception {
        doNothing().when(userService).deleteById(expected.getId());
        mockMvc.perform(delete("/users/" + expected.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(userService, times(1)).deleteById(expected.getId());
        verifyNoMoreInteractions(userService);
    }
}
