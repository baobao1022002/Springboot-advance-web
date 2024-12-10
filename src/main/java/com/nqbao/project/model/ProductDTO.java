package com.nqbao.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private int id;
    private String name;
    private int quantity;
    private Integer price;
    private int categoryId;
    private List<OrderItemDTO> items=new ArrayList<>();

    public ProductDTO(Product product){
        this.id=product.getId();
        this.name=product.getName();
        this.quantity=product.getQuantity();
        this.price=product.getPrice();
        this.categoryId=product.getCategory().getId();
        this.items=product.getItems().stream().map(OrderItemDTO::new).collect(Collectors.toList());
    }
}
