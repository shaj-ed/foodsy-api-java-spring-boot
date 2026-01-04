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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody ProductRequestDTO productRequestDTO) {
        ProductResponseDTO productResponseDTO = productService.updateProduct(id, productRequestDTO);
        return ResponseEntity.ok(Map.of(
        "message", "Successfully updated",
        "data", productResponseDTO
        ));
    }

    @PutMapping("/{id}/upload")
    public ResponseEntity<?> updateProductImages(@PathVariable Long id, List<MultipartFile> files) throws IOException {
        imageService.updateProductImages(id, files);
        return ResponseEntity.ok(Map.of("message", "Updated successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        productService.deleteProductById(id);
        return ResponseEntity.ok(Map.of(
                "message", "Delete successfully"
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        ProductEntity productEntity = productService.getProductById(id);
        return ResponseEntity.ok(Map.of(
                "data", ProductMapper.toResponse(productEntity)
        ));
    }

    @GetMapping("/{id}/images")
    public ResponseEntity<?> getSingleProductImages(@PathVariable Long id) {
        List<byte[]> images = productService.getSingleProductImages(id);
        return ResponseEntity.ok(Map.of("data", images));
    }

    @GetMapping
    public ResponseEntity<?> getProducts(@RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "10") int limit,
                                         @RequestParam(required = false) Long categoryId) {
        Pageable pageable = PageRequest.of(page, limit);
        Page<ProductEntity> productPage;

        if(categoryId != null) {
            productPage = productService.getProductsByCategory(categoryId, pageable);
        } else {
            productPage = productService.getProducts(pageable);
        }

        if(productPage.isEmpty()) {
            return ResponseEntity.ok(Map.of("message", "No product found",
                    "data", ""));
        } else {
            List<ProductResponseDTO> productResponseDTOS = new ArrayList<>();
            for(ProductEntity productEntity: productPage.getContent()) {
                productResponseDTOS.add(ProductMapper.toResponse(productEntity));
            }
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "data", productResponseDTOS,
                    "pagination",  Map.of(
                            "currentPage", productPage.getNumber(),
                            "limit", productPage.getSize(),
                            "totalPages", productPage.getTotalPages(),
                            "totalItems", productPage.getTotalElements(),
                            "hasNext", productPage.hasNext(),
                            "hasPrev", productPage.hasPrevious()
                    )
            ));
        }
    }
}
