package org.example.springbootvjezba2.mapper;

import org.example.springbootvjezba2.domain.Hardware;
import org.example.springbootvjezba2.dto.HardwareDTO;

public class HardwareMapper {

    public static HardwareDTO maptoHardwareDto(Hardware hardware) {
        return new HardwareDTO(
                hardware.getId(),
                hardware.getName(),
                hardware.getCode(),
                hardware.getStock(),
                hardware.getPrice(),
                hardware.getHardwareType()
                );
    }

    public static Hardware maptoHardware(HardwareDTO hardwareDTO) {
        return new Hardware(
                hardwareDTO.getId(),
                hardwareDTO.getName(),
                hardwareDTO.getCode(),
                hardwareDTO.getStock(),
                hardwareDTO.getPrice(),
                hardwareDTO.getHardwareType()
                );
    }
}
