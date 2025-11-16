package com.example.foodsy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaginationResponseDTO {
    private int currentPage;
    private int limit;
    private int totalPages;
    private Long totalItems;
    private boolean hasNext;
    private boolean hasPrev;
}