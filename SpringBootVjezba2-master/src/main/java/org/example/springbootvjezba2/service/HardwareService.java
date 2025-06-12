package org.example.springbootvjezba2.service;

import org.example.springbootvjezba2.domain.Hardware;
import org.example.springbootvjezba2.dto.HardwareDTO;

import java.util.List;
import java.util.Optional;

public interface HardwareService {

    List<HardwareDTO> findAll();

    HardwareDTO findByCode(String code);

    HardwareDTO saveNewHardware(HardwareDTO hardwareDTO);

    boolean hardwareByIdExists(Integer id);

    Optional<HardwareDTO> updateHardware(HardwareDTO hardwareDTO, Integer id);

    boolean deleteHardwareById(Integer id);

}
