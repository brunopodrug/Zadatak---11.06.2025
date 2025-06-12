package org.example.springbootvjezba2.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class HardwareDTO {
    @NotBlank(message = "Hardware name cannot be blank")
    private Long id;
    private String name;
    private String code;
    private long stock;
    @DecimalMin(value = "0.0", message = "Hardware price must be positive")
    private BigDecimal price;
    private Long typeId;
}

