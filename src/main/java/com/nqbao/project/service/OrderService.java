package com.nqbao.project.service;

import com.nqbao.project.exception.NotFoundException;
import com.nqbao.project.exception.WrongParameterException;
import com.nqbao.project.model.*;
import com.nqbao.project.repository.OrderItemRepository;
import com.nqbao.project.repository.OrderRepository;
import com.nqbao.project.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class OrderService extends GenericService<Order, Integer, OrderDTO> {

    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository, OrderItemRepository orderItemRepository) {
        super(orderRepository, "order", OrderDTO::new, Order::new);
        this.productRepository = productRepository;
        this.orderItemRepository = orderItemRepository;
        this.orderRepository = orderRepository;
    }

    @Transactional
    @Override
    public OrderDTO save(OrderDTO orderDTO) {

        orderDTO.getItems().forEach(
                orderItem -> {
                    Product productInStock = productRepository.findById(orderItem.getProductId()).orElseThrow(
                            () -> new NotFoundException("Can not find product with id: " + orderItem.getOrderId()));
                    if (orderItem.getQuantity() > productInStock.getQuantity()) {
                        throw new WrongParameterException("Product quantity is not available, please choose smaller quantity for product: " + orderItem.getProductId());
                    }
                }
        );


        Order newOrder = new Order();
        newOrder.setUser(new User(orderDTO.getUserId()));
        orderRepository.save(newOrder);

        orderDTO.getItems().forEach(orderItemDTO -> {
            OrderItem orderItemEntity = new OrderItem(orderItemDTO);
            orderItemEntity.setOrder(newOrder);
            orderItemRepository.save(orderItemEntity);
            // Update product quantity in the product repository
            Product p = productRepository.findById(orderItemDTO.getProductId()).orElseThrow(() -> new NotFoundException("Can not find product with id: " + orderItemDTO.getOrderId()));
            int newQuantity = p.getQuantity() - orderItemDTO.getQuantity();
            p.setQuantity(newQuantity);
            productRepository.save(p);
        });
        return new OrderDTO(newOrder);
    }

}
