package com.segunda.preentrega.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductoDTO {
    private Long id;
    private String name;
    private String key;
    private Long userId;
}