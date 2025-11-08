package com.example.foodsy.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Generate getter, setter, toString, equals and hashCode
@NoArgsConstructor // Generate no args constructor
@AllArgsConstructor // Generate all args constructor
@Entity
@Table(name = "category")
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Category name is required")
    @Size(min = 2, max = 15, message = "Character must be between 2 and 15")
    @Column(name = "category_name", nullable = false)
    private String category_name;

    @NotBlank(message = "Description is required")
    @Column(name = "description", nullable = false)
    private String description;

    @OneToOne(mappedBy = "categoryEntity", cascade = CascadeType.ALL, orphanRemoval = true, optional = true)
    private ImageEntity imageEntity;

    public CategoryEntity(Long id, String categoryName, String description) {
        this.id = id;
        this.category_name = categoryName;
        this.description = description;
    }
}
