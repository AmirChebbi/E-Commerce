package com.example.ECommerce.DAOs.SubCategory;

import com.example.ECommerce.DAOs.Category.Category;
import com.example.ECommerce.DAOs.Product.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table (name = "sub_categories")
public class SubCategory {

    @SequenceGenerator(
            name =  "sub_categories_sequence",
            sequenceName = "sub_categories_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "sub_categories_sequence")
    private long id;


    @Column(name = "title",unique = true)
    private String title;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;


    @OneToMany(mappedBy = "subCategory" , cascade = CascadeType.ALL , fetch = FetchType.EAGER)
    private List<Product> products = new ArrayList<>();
}
