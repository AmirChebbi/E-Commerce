package com.example.ECommerce.DAOs.Category;

import com.example.ECommerce.DAOs.SubCategory.SubCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "categories")
public class Category {

    @SequenceGenerator(
            name = "categories_sequence",
            sequenceName = "categories_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "categories_sequence")
    @Column(name  = "id" , nullable = false , unique = true)
    private long id;

    @Column(name = "title", nullable = false , unique = true)
    private String title;


    @OneToMany(mappedBy = "category", cascade =  CascadeType.ALL , fetch = FetchType.EAGER)
    private List<SubCategory> subCategories;

    public Category(String title, List<SubCategory> subCategories) {
        this.title = title;
        this.subCategories = subCategories;
    }
}
