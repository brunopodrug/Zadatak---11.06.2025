package org.example.springbootvjezba2.repository;

import org.example.springbootvjezba2.domain.Hardware;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface SpringDataHardwareRepository extends JpaRepository<Hardware, Long>, JpaSpecificationExecutor<Hardware> {
    Optional<Hardware> findByCode(String code);
}
