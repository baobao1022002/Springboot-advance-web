package com.nqbao.project.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonProperty("user")
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @JsonProperty("items")
    @JsonManagedReference("order-items")
    @JsonIgnore
    private List<OrderItem> items = new ArrayList<>();


    public Order(OrderDTO orderDTO) {
        this.id = orderDTO.getId();
        this.user=new User(orderDTO.getUserId());
        this.items=orderDTO.getItems().stream().map(OrderItem::new).collect(Collectors.toList());
    }

    public Order(int id) {
        this.id = id;
    }
}
