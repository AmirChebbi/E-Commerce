package com.example.ECommerce.DAOs.Product;

import com.example.ECommerce.DAOs.File.FileData;
import com.example.ECommerce.DAOs.Option.Option;
import com.example.ECommerce.DAOs.SubCategory.SubCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "products")
public class Product {

    @SequenceGenerator(
            name = "product_sequence",
            sequenceName = "product_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "product_sequence")
    @Column(name = "id", unique = true ,nullable = false)
    private long id;


    @Column(name = "title" , nullable = false)
    private String title;

    @Column(name = "price", nullable = false)
    private float price;

    @Column(name = "layout_description", nullable = false)
    private String layoutDescription;

    @Column(name = "reference" , nullable = false)
    private String reference;

    @OneToMany
    private List<Option> options;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "sub_category_id")
    private SubCategory subCategory;

    @OneToMany
    private List<FileData> files;

}
