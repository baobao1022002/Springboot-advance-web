package com.nqbao.project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nqbao.project.model.*;
import com.nqbao.project.service.OrderService;
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

public class OrderControllerTest {
    OrderDTO expected;
    OrderDTO updatedOrder;
    private List<OrderDTO> listOrder;


    @Autowired
    private MockMvc mockMvc;

    @Mock
    OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    @BeforeEach
    public void init() {
        UserDTO user = new UserDTO(1, "Test", Gender.MALE, "Dak Nong", "test@gmail.com", "test", "123456", UserRole.ROLE_EMPLOYEE);

        expected = new OrderDTO(1, user.getId(), null);
        List<OrderItemDTO> orderItemList1 = Arrays.asList(
                new OrderItemDTO(1, 2, 500, expected.getId()),
                new OrderItemDTO(2, 1, 450, expected.getId())
        );
        expected.setItems(orderItemList1);

        updatedOrder = new OrderDTO(1, user.getId(), null);
        List<OrderItemDTO> updatedOrderItems = Arrays.asList(
                new OrderItemDTO(1, 2, 500, expected.getId()),
                new OrderItemDTO(2, 1, 450, expected.getId()),
                new OrderItemDTO(3, 2, 350, expected.getId())
        );
        updatedOrder.setItems(updatedOrderItems);

        OrderDTO order2 = new OrderDTO(2, user.getId(), Arrays.asList(
                new OrderItemDTO(1, 1, 400, expected.getId()),
                new OrderItemDTO(2, 2, 300, expected.getId())
        ));
        listOrder = Arrays.asList(expected, order2);
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(orderController)
                .build();
    }

    // =========================================== Get All Users ==========================================
    @Test
    void testGetAllSuccess() throws Exception {
        when(orderService.findAll()).thenReturn(listOrder);
        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)));
        verify(orderService, times(1)).findAll();
        verifyNoMoreInteractions(orderService);
    }


    // =========================================== Get User By ID =========================================
    @Test
    void testGetByIdSuccess() throws Exception {
        when(orderService.findById(expected.getId())).thenReturn(Optional.ofNullable(expected));
        mockMvc.perform(get("/orders/" + expected.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(expected.getId()));

        verify(orderService, times(1)).findById(1);
        verifyNoMoreInteractions(orderService);
    }

    @Test
    void testGetByIdNotFound() throws Exception {
        when(orderService.findById(expected.getId())).thenReturn(Optional.empty());
        mockMvc.perform(get("/orders/" + expected.getId()))
                .andExpect(status().isNotFound());
        verify(orderService, times(1)).findById(expected.getId());
        verifyNoMoreInteractions(orderService);
    }

    // =========================================== Create New User ========================================
    @Test
    void testCreateSuccess() throws Exception {
        when(orderService.create(expected)).thenReturn(expected);
        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(expected)))
                .andExpect(status().isCreated());
        verify(orderService, times(1)).create(expected);
        verifyNoMoreInteractions(orderService);
    }


    // =========================================== Update  User ===================================
    @Test
    void updateUserSuccess() throws Exception {
        when(orderService.updateById(expected.getId(), updatedOrder)).thenReturn(updatedOrder);
        mockMvc.perform(put("/orders/" + expected.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedOrder))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(updatedOrder.getUserId()));
        verify(orderService, times(1)).updateById(expected.getId(), updatedOrder);
        verifyNoMoreInteractions(orderService);
    }


    // =========================================== Delete User ============================================
    @Test
    void deleteUserSuccess() throws Exception {
        doNothing().when(orderService).deleteById(expected.getId());
        mockMvc.perform(delete("/orders/" + expected.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(orderService, times(1)).deleteById(expected.getId());
        verifyNoMoreInteractions(orderService);
    }
}
