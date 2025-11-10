package com.example.foodsy.controller;

import com.example.foodsy.dto.ProductRequestDTO;
import com.example.foodsy.dto.ProductResponseDTO;
import com.example.foodsy.entity.ImageEntity;
import com.example.foodsy.entity.ProductEntity;
import com.example.foodsy.mapper.ProductMapper;
import com.example.foodsy.service.ImageService;
import com.example.foodsy.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    ProductService productService;

    @Autowired
    ImageService imageService;

    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(@Valid @RequestBody ProductRequestDTO productRequestDTO) {
        ProductResponseDTO product = productService.createProduct(productRequestDTO);

        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PostMapping("/{id}/upload")
    public ResponseEntity<String> uploadProductImages(@PathVariable Long id, List<MultipartFile> files) throws IOException {
        List<ImageEntity> images =  imageService.uploadProductImages(id, files);
        return new ResponseEntity<>("Successful", HttpStatus.OK);
    }
}
