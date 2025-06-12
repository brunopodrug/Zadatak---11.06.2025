package org.example.springbootvjezba2.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Type {
    CPU(1, "CPU", "Central processing unit"),
    GPU(2, "GPU", "Graphics processing unit"),
    MBO(3, "MBO", "Motherboard"),
    RAM(4, "RAM", "Random Access Memory"),
    STORAGE(5, "STORAGE", "Storage for data"),
    OTHER(6, "OTHER", "Other");

    private final Integer id;
    private final String name;
    private final String description;
}
