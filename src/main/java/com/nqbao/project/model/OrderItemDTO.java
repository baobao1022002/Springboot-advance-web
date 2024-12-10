package com.nqbao.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDTO {
    private int id;
    private int productId;
    private int quantity;
    private int orderId;

    public OrderItemDTO(OrderItem orderItem){
        this.id=orderItem.getId();
        this.productId=orderItem.getProduct().getId();
        this.quantity=orderItem.getQuantity();
        this.orderId=orderItem.getOrder().getId();
    }
}
