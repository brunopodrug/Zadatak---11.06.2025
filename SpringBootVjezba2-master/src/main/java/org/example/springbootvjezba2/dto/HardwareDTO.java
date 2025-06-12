package org.example.springbootvjezba2.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.springbootvjezba2.domain.Hardware;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class HardwareDTO {
    @NotBlank(message = "Hardware name cannot be blank")
    private String name;
    @DecimalMin(value = "0.0", message = "Hardware price must be positive")
    private BigDecimal price;
    @Override
    public String toString() {
        return "HardwareDTO{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
