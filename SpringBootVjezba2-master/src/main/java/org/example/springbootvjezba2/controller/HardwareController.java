package org.example.springbootvjezba2.controller;

import lombok.AllArgsConstructor;
import org.example.springbootvjezba2.dto.HardwareDTO;
import org.example.springbootvjezba2.service.HardwareService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hardware-list")
@AllArgsConstructor
public class HardwareController {

    private final HardwareService hardwareService;

    @PostMapping
    public ResponseEntity<HardwareDTO> saveNewHardware(@RequestBody HardwareDTO hardwareDTO) {
        HardwareDTO hardware = hardwareService.saveNewHardware(hardwareDTO);
        return new ResponseEntity<>(hardware, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HardwareDTO> findHardwareById(@PathVariable("id") Long hardwareId) {
        HardwareDTO hardware = hardwareService.findHardwareById(hardwareId);
        return ResponseEntity.ok(hardware);
    }

    @GetMapping
    public ResponseEntity<List<HardwareDTO>> getAllHardwares() {
        List<HardwareDTO> hardwares = hardwareService.findAll();
        return ResponseEntity.ok(hardwares);
    }

    @PutMapping("{id}")
    public ResponseEntity<HardwareDTO> updateHardware(@RequestBody HardwareDTO hardwareDto,
                                                        @PathVariable("id") Long hardwareId) {
        HardwareDTO hardware = hardwareService.updateHardware(hardwareDto, hardwareId);
        return ResponseEntity.ok(hardware);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteHardware(@PathVariable("id") Long hardwareId) {
        hardwareService.deleteHardwareById(hardwareId);
        return new ResponseEntity<>("Hardware successfully deleted", HttpStatus.OK);
    }
}
