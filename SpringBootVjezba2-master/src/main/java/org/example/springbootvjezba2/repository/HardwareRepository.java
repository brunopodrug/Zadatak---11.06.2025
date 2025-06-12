package org.example.springbootvjezba2.repository;
import org.example.springbootvjezba2.domain.Hardware;
import org.example.springbootvjezba2.dto.HardwareDTO;

import java.util.List;
import java.util.Optional;

public interface HardwareRepository {

    List<Hardware> findAll();

    Optional<Hardware> findByCode(String code);

    Hardware saveNewHardware (Hardware hardware);

    boolean hardwareByIdExists(Integer id);

    Optional<Hardware> updateHardware(Hardware hardwareToUpdate, Integer id);

    boolean deleteHardwareById(Integer id);

}