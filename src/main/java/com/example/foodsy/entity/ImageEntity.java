package com.example.foodsy.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "images")
public class ImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "file_name")
    private String file_name;

    @Column(name = "file_type")
    private String file_type;

    @Lob
    @Column(name = "data", columnDefinition = "LONGBLOB")
    private byte[] data;

    @OneToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity categoryEntity;

    public ImageEntity(String originalFilename, String contentType, byte[] bytes,
                       CategoryEntity categoryEntity) {
        this.file_name = originalFilename;
        this.file_type = contentType;
        this.data = bytes;
        this.categoryEntity = categoryEntity;
    }
}
