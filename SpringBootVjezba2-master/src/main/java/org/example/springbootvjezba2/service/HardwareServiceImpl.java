package org.example.springbootvjezba2.service;

import lombok.AllArgsConstructor;
import org.example.springbootvjezba2.domain.Hardware;
import org.example.springbootvjezba2.dto.HardwareDTO;
import org.example.springbootvjezba2.mapper.HardwareMapper;
import org.example.springbootvjezba2.repository.SpringDataHardwareRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class HardwareServiceImpl implements HardwareService {

    SpringDataHardwareRepository springDataHardwareRepository;

    @Override
    public List<HardwareDTO> findAll() {
        List<Hardware> hardwares = springDataHardwareRepository.findAll();
        return hardwares.stream().map(HardwareMapper::maptoHardwareDto)
                .collect(Collectors.toList());
    }

    @Override
    public HardwareDTO findHardwareById(Long hardwareId) {
        Hardware hardware = springDataHardwareRepository.findById(hardwareId).orElseThrow();
        return HardwareMapper.maptoHardwareDto(hardware);
    }

    @Override
    public HardwareDTO saveNewHardware(HardwareDTO hardwareDTO) {
        Hardware hardware = HardwareMapper.maptoHardware(hardwareDTO);
        Hardware savedHardware = springDataHardwareRepository.save(hardware);

        return HardwareMapper.maptoHardwareDto(savedHardware);
    }

    @Override
    public HardwareDTO updateHardware(HardwareDTO hardwareDTO, Long hardwareId) {
        Hardware hardware = springDataHardwareRepository.findById(hardwareId)
                .orElseThrow();

        hardware.setName(hardwareDTO.getName());
        hardware.setHardwareType(hardwareDTO.getHardwareType());
        hardware.setCode(hardwareDTO.getCode());
        hardware.setStock(hardwareDTO.getStock());
        hardware.setPrice(hardwareDTO.getPrice());

        Hardware savedHardware = springDataHardwareRepository.save(hardware);

        return HardwareMapper.maptoHardwareDto(savedHardware);
    }

    @Override
    public void deleteHardwareById(Long hardwareId) {
        Hardware hardware = springDataHardwareRepository.findById(hardwareId)
                .orElseThrow();

        springDataHardwareRepository.deleteById(hardwareId);
    }
}
