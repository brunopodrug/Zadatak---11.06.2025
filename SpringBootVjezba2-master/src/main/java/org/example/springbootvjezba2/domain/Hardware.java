package org.example.springbootvjezba2.domain;

import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Hardware {
    private Integer id;
    private String name;
    private Type type;
    private String code;
    private long stock;
    private BigDecimal price;
}
