package org.example.springbootvjezba2.repository;

import org.example.springbootvjezba2.domain.Hardware;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SpringDataHardwareRepository extends JpaRepository<Hardware, Long>, JpaSpecificationExecutor<Hardware> {
}
