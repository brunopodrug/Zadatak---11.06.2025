package org.example.springbootvjezba2.service;

import org.example.springbootvjezba2.domain.Hardware;
import org.example.springbootvjezba2.dto.HardwareDTO;

import java.util.List;
import java.util.Optional;

public interface HardwareService {

    List<HardwareDTO> findAll();

    HardwareDTO findHardwareById(Long hardwareId);

    HardwareDTO saveNewHardware(HardwareDTO hardwareDTO);

    HardwareDTO updateHardware(HardwareDTO hardwareDTO, Long hardwareId);

    void deleteHardwareById(Long id);

}
