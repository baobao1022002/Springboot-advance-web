package com.nqbao.project.service;

import com.nqbao.project.exception.DuplicateIdException;
import com.nqbao.project.exception.NotFoundException;
import com.nqbao.project.model.*;
import com.nqbao.project.repository.OrderItemRepository;
import com.nqbao.project.repository.OrderRepository;
import com.nqbao.project.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.mockito.Mockito.*;

public class OrderServiceTest {
    private OrderService orderService;
    private List<Order> listOrder;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private OrderItemRepository orderItemRepository;

    User user;
    Order expected;
    OrderDTO expectedDTO;
    Order updatedOrder;
    OrderDTO updatedOrderDTO;
    Product product1;
    Product product2;
    Category category;
    OrderItem orderItem1;
    OrderItem orderItem2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        orderService = new OrderService(orderRepository,productRepository,orderItemRepository);
        user = new User(1,"Test", Gender.MALE, "Dak Nong", "test@gmail.com", UserRole.ROLE_EMPLOYEE);
        category = new Category(1, "Trái cây", null, null);
        product1 = new Product(1, "Cam", 5700, 50000, category);
        product2 = new Product(2, "Táo", 1020, 90000, category);
        List<Product> listProduct = Arrays.asList(product1, product2);
        category.setListProduct(listProduct);
        expected = new Order(1, user, null);
        orderItem1 = new OrderItem(product1.getId(), 0, expected.getId());
        orderItem2 = new OrderItem(product2.getId(), 0, expected.getId());
        List<OrderItem> orderItemList1 = Arrays.asList(orderItem1, orderItem2);
        expected.setItems(orderItemList1);
        expectedDTO=new OrderDTO(expected);

        updatedOrder = new Order(1, user, null);
        List<OrderItem> updatedOrderItems = Arrays.asList(
                new OrderItem(1, 40, 1),
                new OrderItem(2, 60, 1)

        );
        updatedOrder.setItems(updatedOrderItems);
        updatedOrderDTO= new OrderDTO(updatedOrder);

        Order order2 = new Order(2, user, Arrays.asList(
                new OrderItem(1, 400, 1),
                new OrderItem(2, 300, 1)
        ));
        listOrder = Arrays.asList(expected, order2);
    }

    // =========================================== Get All Users ==========================================
    @Test
    void testGetAllSuccess() {
        List<OrderItem> listEmpty = new ArrayList<>();
        Order rootOrder = new Order(1, null, listEmpty);
        when(orderRepository.findAll()).thenReturn(listOrder);
        List<OrderDTO> actualOrders = orderService.findAll();
        Assertions.assertEquals(2, actualOrders.size());
        Assertions.assertNotEquals(5, actualOrders.size());
        Assertions.assertFalse(actualOrders.isEmpty());
        Assertions.assertEquals(actualOrders.getFirst().getItems().getFirst().getId(), 0);
        Assertions.assertEquals(actualOrders.getFirst().getItems().getLast().getQuantity(), 400);
        Assertions.assertNotNull(actualOrders);
        verify(orderRepository, times(1)).findAll();
        verifyNoMoreInteractions(orderRepository);
    }

    @Test
    void testGetAllNoContent() {
        when(orderRepository.findAll()).thenReturn(Collections.emptyList());
        List<OrderDTO> actualOrders = orderService.findAll();
        Assertions.assertEquals(0, actualOrders.size());
        Assertions.assertNotEquals(5, actualOrders.size());
        verify(orderRepository, times(1)).findAll();
        verifyNoMoreInteractions(orderRepository);
    }

    // =========================================== Get User By ID =========================================
    @Test
    void testGetByIdSuccess() {
        List<OrderItem> listEmpty = new ArrayList<>();
        Order rootOrder = new Order(1, null, listEmpty);

        when(orderRepository.findById(expected.getId())).thenReturn(Optional.of(expected));
        OrderDTO actual = orderService.findById(expected.getId()).orElseThrow(() -> new NotFoundException("Can not found with id " + expected.getId()));
        Assertions.assertEquals(expected.getId(), actual.getId());

        verify(orderRepository, times(1)).findById(expected.getId());
        verifyNoMoreInteractions(orderRepository);
    }

    @Test
    void testGetByIdNotFound() {
        when(orderRepository.findById(expected.getId())).thenReturn(Optional.empty());
        Optional<OrderDTO> actual = orderService.findById(expected.getId());
        Assertions.assertTrue(actual.isEmpty(), "Expected empty Optional when user not found");

        verify(orderRepository, times(1)).findById(expected.getId());
        verifyNoMoreInteractions(orderRepository);
    }

    // =========================================== Create New User ========================================
//    @Test
//    void testCreateSuccess() throws Exception {
//        when(orderRepository.findById(expected.getId())).thenReturn(Optional.empty());
//        when(orderRepository.save(expected)).thenReturn(expected);
//        when(productRepository.findById(product1.getId())).thenReturn(Optional.of(product1));
//        when(productRepository.findById(product2.getId())).thenReturn(Optional.of(product2));
//        when(orderService.findById(expectedDTO.getId())).thenReturn(Optional.empty());
//        when(orderService.save(expectedDTO)).thenReturn(expectedDTO);
//        OrderDTO actualOrder = orderService.create(expectedDTO);
//
//        Assertions.assertEquals(expected.getItems(), actualOrder.getItems());
//        Assertions.assertEquals(expected.getId(), actualOrder.getId());
//        Assertions.assertEquals(expected.getUser(), user);
//
//        verify(orderRepository, times(1)).findById(expected.getId());
//        verify(orderRepository, times(1)).save(expected);
//        verifyNoMoreInteractions(orderRepository);
//    }

    @Test
    void testCreateIsExist() {
        when(orderRepository.findById(expected.getId())).thenReturn(Optional.of(expected));
        DuplicateIdException result = Assertions.assertThrows(DuplicateIdException.class, () -> orderService.create(expectedDTO));
        Assertions.assertEquals("id " + expected.getId() + "already exists.", result.getMessage());
        verify(orderRepository, times(1)).findById(expected.getId());
        verifyNoMoreInteractions(orderRepository);
    }

    // =========================================== Delete User ============================================
    @Test
    void testDeleteByIdSuccess() {
        doNothing().when(orderRepository).deleteById(expected.getId());
        when(orderRepository.existsById(expected.getId())).thenReturn(true);
        orderService.deleteById(expected.getId());
        verify(orderRepository, Mockito.times(1)).deleteById(expected.getId());
        verify(orderRepository, Mockito.times(1)).existsById(expected.getId());
        verifyNoMoreInteractions(orderRepository);
    }

    @Test
    void testDeleteByIdNotFound() {
        when(orderRepository.existsById(999)).thenReturn(false);
        NotFoundException result = Assertions.assertThrows(NotFoundException.class, () -> orderService.deleteById(999));
        Assertions.assertEquals("Can not find item with id = 999", result.getMessage());
        Mockito.verify(orderRepository, Mockito.never()).deleteById(999);
        Mockito.verify(orderRepository, Mockito.times(1)).existsById(999);
        verifyNoMoreInteractions(orderRepository);
    }

    // =========================================== Update  User ===================================
//    @Test
//    void testUpdateSuccess() throws NoSuchFieldException, IllegalAccessException {
//        when(orderRepository.findById(updatedOrder.getId())).thenReturn(Optional.of(expected));
//        when(orderRepository.save(updatedOrder)).thenReturn(updatedOrder);
//        when(productRepository.getReferenceById(orderItem1.getProduct().getId())).thenReturn(product1);
//        when(productRepository.getReferenceById(orderItem2.getProduct().getId())).thenReturn(product2);
//        OrderDTO actual = orderService.updateById(updatedOrder.getId(), updatedOrderDTO);
//        Assertions.assertEquals(updatedOrder.getItems(), actual.getItems());
//        verify(orderRepository).save(updatedOrder);
//    }

    @Test
    void testUpdateNotFound() {
        when(orderRepository.findById(expected.getId())).thenReturn(Optional.empty());
        NotFoundException result = Assertions.assertThrows(NotFoundException.class, () -> orderService.updateById(expected.getId(), expectedDTO));
        Assertions.assertEquals("Can not find item with id = " + expected.getId(), result.getMessage());
        verify(orderRepository, times(1)).findById(expected.getId());
        verifyNoMoreInteractions(orderRepository);
    }
}
