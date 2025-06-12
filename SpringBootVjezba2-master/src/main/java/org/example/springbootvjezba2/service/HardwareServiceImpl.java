package org.example.springbootvjezba2.service;

import lombok.AllArgsConstructor;
import org.example.springbootvjezba2.domain.Hardware;
import org.example.springbootvjezba2.domain.HardwareType;
import org.example.springbootvjezba2.dto.HardwareDTO;
import org.example.springbootvjezba2.repository.SpringDataHardwareRepository;
import org.example.springbootvjezba2.repository.SpringDataHardwareTypeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class HardwareServiceImpl implements HardwareService {

    SpringDataHardwareRepository springDataHardwareRepository;
    SpringDataHardwareTypeRepository hardwareTypeRepository;
    ModelMapper modelMapper;

    @Override
    public List<HardwareDTO> findAll() {
        return springDataHardwareRepository.findAll().stream().map(h -> modelMapper.map(h, HardwareDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public HardwareDTO findHardwareById(Long hardwareId) {
        Hardware hardware = springDataHardwareRepository.findById(hardwareId).orElseThrow();
        return modelMapper.map(hardware, HardwareDTO.class);
    }

    @Override
    public HardwareDTO saveNewHardware(HardwareDTO hardwareDTO) {
        Hardware hardware = modelMapper.map(hardwareDTO, Hardware.class);

        HardwareType hardwareType = hardwareTypeRepository.findById(hardwareDTO.getTypeId())
                .orElseThrow(() -> new RuntimeException("HardwareType with id " + hardwareDTO.getTypeId()
                        + " not found"));
        hardware.setHardwareType(hardwareType);

        Hardware savedHardware = springDataHardwareRepository.save(hardware);

        // Convert back to DTO and manually set hardwareId again
        HardwareDTO savedDto = modelMapper.map(savedHardware, HardwareDTO.class);
        savedDto.setTypeId(savedHardware.getHardwareType().getId());

        return savedDto;
    }

    @Override
    public HardwareDTO updateHardware(HardwareDTO hardwareDTO, Long hardwareId) {
        Hardware hardware = springDataHardwareRepository.findById(hardwareId)
                .orElseThrow();

        hardware.setName(hardwareDTO.getName());
        hardware.setCode(hardwareDTO.getCode());
        hardware.setStock(hardwareDTO.getStock());
        hardware.setPrice(hardware.getPrice());
        HardwareType hardwareType = hardwareTypeRepository.findById(hardwareDTO.getTypeId())
                .orElseThrow(() -> new RuntimeException("HardwareType with id " + hardwareDTO.getTypeId()
                        + " not found"));
        hardware.setHardwareType(hardwareType);
        Hardware savedHardware = springDataHardwareRepository.save(hardware);

        return modelMapper.map(savedHardware, HardwareDTO.class);
    }

    @Override
    public void deleteHardwareById(Long hardwareId) {
        Hardware hardware = springDataHardwareRepository.findById(hardwareId)
                .orElseThrow();

        springDataHardwareRepository.deleteById(hardwareId);
    }

    @Override
    public HardwareDTO findHardwareByCode(String hardwareCode) {
        Hardware hardware = springDataHardwareRepository.findByCode(hardwareCode).orElseThrow(
                () -> new RuntimeException("HardwareType with code " + hardwareCode
                        + " not found")
        );

        return modelMapper.map(hardware, HardwareDTO.class);
    }
}
