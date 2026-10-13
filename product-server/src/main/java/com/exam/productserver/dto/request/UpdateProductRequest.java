package com.exam.productserver.dto.request;

public record UpdateProductRequest(
        String name,
        String description,
        int stock
) {
}
