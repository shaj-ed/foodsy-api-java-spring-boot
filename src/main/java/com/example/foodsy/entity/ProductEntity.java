package com.example.foodsy.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Product name is required")
    @Size(min = 2, max = 15, message = "Product name must be between 1 to 15 characters")
    @Column(name = "product_name", nullable = false)
    private String product_name;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be greater than zero")
    @Column(name = "price", nullable = false)
    private Integer price;

    @NotBlank(message = "A description is required")
    @Size(min = 10, max = 30, message = "Description must be between 10 to 30 characters")
    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "productEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ImageEntity> imageEntity = new ArrayList<>();

    @NotNull(message = "Category is required")
    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity categoryEntity;

    private LocalDateTime created_at = LocalDateTime.now();

//    public ProductEntity(Long id, String productName,
//                         Integer price, String description,
//                         CategoryEntity categoryEntity) {
//        this.id = id;
//        this.product_name = productName;
//        this.price = price;
//        this.description = description;
//        this.categoryEntity = categoryEntity;
//        this.created_at = LocalDateTime.now();
//    }
}
