package org.example.springbootvjezba2.repository;

import org.example.springbootvjezba2.domain.HardwareType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataTypeRepository extends JpaRepository<HardwareType, Long> {
}
