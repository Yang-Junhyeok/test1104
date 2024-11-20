package com.example.test1104.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemDto {
    @NotNull(message = "상품명은 필수입니다")
    private Long itemId;

    @Min(value= 1, message = "1개이상 담아주세요")
    private int count;
}
