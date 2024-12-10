package com.nqbao.project.service;
import com.nqbao.project.exception.DuplicateIdException;
import com.nqbao.project.exception.NotFoundException;
import com.nqbao.project.model.Gender;
import com.nqbao.project.model.User;
import com.nqbao.project.model.UserDTO;
import com.nqbao.project.model.UserRole;
import com.nqbao.project.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class UserServiceTest {
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserRepository userRepository;

    User expected;
    UserDTO expectedDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository, passwordEncoder);
        expected = new User(1, "Test", Gender.MALE, "Dak Nong", "test@gmail.com", "test", "123456", UserRole.ROLE_EMPLOYEE);
        expectedDTO=new UserDTO(expected);
    }

    // =========================================== Get All Users ==========================================
    @Test
    void testGetAllSuccess() {
        List<User> users = List.of(
                new User(1, "Bai", Gender.MALE, "Dak Nong", "bai@gmail.com", "bai", "123456", UserRole.ROLE_EMPLOYEE),
                new User(2, "Lien", Gender.FEMALE, "Dak Lak", "lien@gmail.com", "lien", "123456", UserRole.ROLE_EMPLOYEE)
        );

        when(userRepository.findAll()).thenReturn(users);
        List<UserDTO> actualUsers = userService.findAll();
        Assertions.assertEquals(2, actualUsers.size());
        Assertions.assertEquals("Bai", actualUsers.get(0).getName());
        Assertions.assertEquals("Lien", actualUsers.get(1).getName());
        verify(userRepository, times(1)).findAll();
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void testGetAllNoContent() {
        when(userRepository.findAll()).thenReturn(Collections.emptyList());
        List<UserDTO> actualUsers = userService.findAll();
        Assertions.assertEquals(0, actualUsers.size());
        verify(userRepository, times(1)).findAll();
        verifyNoMoreInteractions(userRepository);
    }

    // =========================================== Get User By ID =========================================
    @Test
    void testGetByIdSuccess() {
        when(userRepository.findById(expected.getId())).thenReturn(Optional.of(expected));
        UserDTO actual = userService.findById(expectedDTO.getId()).orElseThrow(() -> new NotFoundException("Can not found with id " + expectedDTO.getId()));
        Assertions.assertEquals(expected.getId(), actual.getId());
        Assertions.assertEquals(expected.getName(), actual.getName());
        Assertions.assertEquals(expected.getRole(), actual.getRole());
        Assertions.assertEquals(expected.getEmail(), actual.getEmail());
        Assertions.assertEquals(expected.getAddress(), actual.getAddress());
        Assertions.assertEquals(expected.getGender(), actual.getGender());
        verify(userRepository, times(1)).findById(expected.getId());
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void testGetByIdNotFound() {
        Mockito.when(userRepository.findById(expected.getId())).thenReturn(Optional.empty());
        Optional<UserDTO> actual = userService.findById(expectedDTO.getId());
        Assertions.assertTrue(actual.isEmpty(), "Expected empty Optional when user not found");
        verify(userRepository, times(1)).findById(expected.getId());
        verifyNoMoreInteractions(userRepository);
    }

    // =========================================== Create New User ========================================
    @Test
    void testCreateSuccess() throws Exception {
//        when(passwordEncoder.encode(expected.getPassword())).thenReturn("encodedPassword");
//        when(userRepository.existsById(expected.getId())).thenReturn(false);
//        when(userRepository.save(expected)).thenReturn(expected);
//        UserDTO actualUser = userService.create(expectedDTO);
//
//        Assertions.assertEquals(expected.getName(), actualUser.getName());
//        Assertions.assertEquals(expected.getEmail(), actualUser.getEmail());
//        Assertions.assertEquals(expected.getId(), actualUser.getId());
//        Assertions.assertEquals(expected.getRole(), actualUser.getRole());
//        Assertions.assertEquals(expected.getGender(), actualUser.getGender());
//
//        verify(userRepository, times(1)).findById(expected.getId());
//        verify(userRepository, times(1)).save(expected);
//        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void testCreateIsExist() {
        when(userRepository.findById(expected.getId())).thenReturn(Optional.of(expected));
        DuplicateIdException result = Assertions.assertThrows(DuplicateIdException.class, () -> userService.create(expectedDTO));
        Assertions.assertEquals("id " + expected.getId() + "already exists.", result.getMessage());
        verify(userRepository, times(1)).findById(expected.getId());
        verifyNoMoreInteractions(userRepository);
    }

    // =========================================== Delete User ============================================
    @Test
    void testDeleteByIdSuccess() {
        when(userRepository.findById(expected.getId())).thenReturn(Optional.of(expected));
        when(userRepository.existsById(expected.getId())).thenReturn(true);
        userService.deleteById(expected.getId());
        Mockito.verify(userRepository, Mockito.times(1)).deleteById(expected.getId());
        Mockito.verify(userRepository, Mockito.times(1)).existsById(expected.getId());
    }

    @Test
    void testDeleteByIdNotFound() {
        NotFoundException result = Assertions.assertThrows(NotFoundException.class, () -> userService.deleteById(999));
        Assertions.assertEquals("Can not find item with id = 999", result.getMessage());
    }

    // =========================================== Update  User ===================================
    @Test
    void testUpdateSuccess() {
//        when(userRepository.findById(expected.getId())).thenReturn(Optional.of(expected));
//        User updatedUser = new User(expected.getId(), "Long", expected.getGender(), expected.getAddress(), expected.getEmail(),expected.getUsername(),expected.getPassword(), expected.getRole());
//        UserDTO updatedUserDTO=expectedDTO;
//        updatedUserDTO.setName("Long");
//        userService.save(expectedDTO);
//        verify(userRepository).save(updatedUser);
//        when(userRepository.findById(expected.getId())).thenReturn(Optional.of(updatedUser));
//        UserDTO actual = userService.findById(updatedUser.getId()).orElseThrow(() -> new NotFoundException("Can not find user with id"));
//        Assertions.assertEquals(updatedUser.getName(), actual.getName());
    }

    @Test
    void testUpdateNotFound() {
        when(userRepository.findById(expected.getId())).thenReturn(Optional.empty());
        NotFoundException result = Assertions.assertThrows(NotFoundException.class, () -> userService.updateById(expected.getId(), expectedDTO));
        Assertions.assertEquals("Can not find item with id = " + expected.getId(), result.getMessage());
        verify(userRepository, times(1)).findById(expected.getId());
        verifyNoMoreInteractions(userRepository);
    }
}
