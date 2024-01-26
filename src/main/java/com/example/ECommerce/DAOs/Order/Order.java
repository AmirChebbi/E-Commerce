package com.example.ECommerce.DAOs.Order;


import com.example.ECommerce.DAOs.Cart.Cart;
import com.example.ECommerce.DAOs.Option.Option;
import com.example.ECommerce.DAOs.Product.Product;
import com.example.ECommerce.DAOs.User.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class Order {

    @SequenceGenerator(
            name = "order_sequel",
            sequenceName = "order_sequel",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "order_sequel")
    @Column(name ="id" ,unique = true , nullable = false)
    private long id;


    @Column(name = "delivered" , nullable = false)
    private boolean delivered = false;

    @Column(name = "address" , nullable = false)
    private String address;

    @Column(name = "total_price", nullable = false)
    private  float price = 0f;

    @ManyToOne
    private Product product;

    @OneToMany
    private List<Option> options;

    @ManyToOne
    private Cart cart;

    @OneToOne
    private UserEntity user;

}
