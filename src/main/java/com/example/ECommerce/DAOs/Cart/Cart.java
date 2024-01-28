package com.example.ECommerce.DAOs.Cart;

import com.example.ECommerce.DAOs.Order.Order;
import com.example.ECommerce.DAOs.User.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table
public class Cart {
    @SequenceGenerator(
            name = "cart_sequel",
            sequenceName = "cart_sequel",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE ,
            generator = "cart_sequel"
    )
    private long id;

    @ManyToOne
    private UserEntity userEntity;

    @OneToMany
    private List<Order> orders;

    @Column(nullable = false)
    private float totalPrice =0f;

    @Column(nullable = false)
    private boolean isConfirmed,isSubmitted;

    public Cart(UserEntity userEntity, List<Order> orders) {
        this.userEntity = userEntity;
        this.orders = orders;
    }
}
