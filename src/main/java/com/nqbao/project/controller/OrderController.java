package com.nqbao.project.controller;

import com.nqbao.project.model.Order;
import com.nqbao.project.model.OrderDTO;
import com.nqbao.project.service.OrderService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController extends GenericController<Order, Integer, OrderDTO> {
    public OrderController(OrderService service) {
        super(service);
    }
}
