package com.example.ECommerce.DAOs.Option;

import com.example.ECommerce.DAOs.Product.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table
public class Option {
    @SequenceGenerator(
            name = "option_sequence",
            sequenceName = "option_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE ,
            generator = "option_sequence")
    private long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description" , nullable = false)
    private String description;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "product_id")
    private Product product;

    public Option(String title, String description, Product product) {
        this.title = title;
        this.description = description;
        this.product = product;
    }
}
