package org.example.springbootvjezba2.service;

import org.example.springbootvjezba2.domain.Hardware;
import org.example.springbootvjezba2.domain.Type;
import org.example.springbootvjezba2.dto.HardwareDTO;
import org.example.springbootvjezba2.repository.HardwareRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class HardwareServiceImpl implements HardwareService{

    private final HardwareRepository hardwareRepository;

    public HardwareServiceImpl(HardwareRepository hardwareRepository) {
        this.hardwareRepository = hardwareRepository;
    }

    @Override
    public List<HardwareDTO> findAll() {
        return hardwareRepository.findAll().stream()
                .map(this::convertHardwareToHardwareDTO)
                .toList();
    }

    @Override
    public HardwareDTO findByCode(String code) {
        return hardwareRepository.findAll().stream()
                .filter(hardware -> hardware.getCode().equals(code))
                .findFirst()
                .map(this::convertHardwareToHardwareDTO)
                .orElseThrow();
    }

    @Override
    public HardwareDTO saveNewHardware(HardwareDTO hardware) {
        return convertHardwareToHardwareDTO(hardwareRepository.saveNewHardware(convertHardwareDtoToHardware(hardware)));
    }

    @Override
    public boolean hardwareByIdExists(Integer id) {
        return hardwareRepository.hardwareByIdExists(id);
    }

    @Override
    public Optional<HardwareDTO> updateHardware(HardwareDTO hardwareDTO, Integer id) {
        Optional<Hardware> updatedHardwareOptional =
                hardwareRepository.updateHardware(convertHardwareDtoToHardware(hardwareDTO), id);

        if(updatedHardwareOptional.isPresent()) {
            return Optional.of(convertHardwareToHardwareDTO(updatedHardwareOptional.get()));
        }

        return Optional.empty();
    }

    @Override
    public boolean deleteHardwareById(Integer id) {
        return hardwareRepository.deleteHardwareById(id);
    }

    private HardwareDTO convertHardwareToHardwareDTO(Hardware hardware) {
        return new HardwareDTO(hardware.getName(), hardware.getPrice());
    }

    private Hardware convertHardwareDtoToHardware(HardwareDTO hardwareDTO) {
        Integer latestId =
                hardwareRepository.findAll().stream()
                        .max(Comparator.comparing(Hardware::getId))
                        .get().getId();

        return new Hardware(latestId + 1, hardwareDTO.getName(),
                Type.OTHER, "unknown", 1, BigDecimal.valueOf(100));
    }
}
