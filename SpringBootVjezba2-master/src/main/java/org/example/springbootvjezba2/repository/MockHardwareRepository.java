package org.example.springbootvjezba2.repository;

import org.example.springbootvjezba2.domain.Hardware;
import org.example.springbootvjezba2.domain.Type;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class MockHardwareRepository implements HardwareRepository {

    private static List<Hardware> hardwareList;

    static {
        hardwareList = new ArrayList<>();

        Hardware h1 = new Hardware(1, "Asus TUF RTX 3080", Type.GPU, "1234561", 0, BigDecimal.valueOf(1599.00));
        Hardware h2 = new Hardware(2, "EVGA XC3 RTX 3070 Ti", Type.GPU, "1234562", 0, BigDecimal.valueOf(1299.00));
        Hardware h3 = new Hardware(3, "AMD Ryzen 5950X", Type.CPU, "1234563", 0, BigDecimal.valueOf(899.00));
        Hardware h4 = new Hardware(4, "Samsung 980 PRO SSD 1TB", Type.STORAGE, "1234564", 0, BigDecimal.valueOf(299.00));
        Hardware h5 = new Hardware(5, "Kingston FURY Beast DDR5 32GB", Type.RAM, "1234565", 0, BigDecimal.valueOf(699.00));

        hardwareList.add(h1);
        hardwareList.add(h2);
        hardwareList.add(h3);
        hardwareList.add(h4);
        hardwareList.add(h5);
    }


    @Override
    public List<Hardware> findAll() {
        return hardwareList;
    }

    @Override
    public Optional<Hardware> findByCode(String code) {
        return hardwareList.stream().filter(hardware -> Objects.equals(hardware.getCode(), code)).findAny();
    }

    @Override
    public Hardware saveNewHardware(Hardware hardware) {
        Integer generatedId = hardwareList.size() + 1;
        hardware.setId(generatedId);
        hardwareList.add(hardware);
        return hardware;
    }

    @Override
    public boolean hardwareByIdExists(Integer id) {
        return hardwareList.stream().anyMatch(h -> h.getId().equals(id));
    }

    @Override
    public Optional<Hardware> updateHardware(Hardware hardwareToUpdate, Integer id) {
        Optional<Hardware> storedHardwareOptional = hardwareList.stream().filter(h -> h.getId().equals(id)).findFirst();
        if (storedHardwareOptional.isPresent()) {
            Hardware storedHardware = storedHardwareOptional.get();
            storedHardware.setName(hardwareToUpdate.getName());
            storedHardware.setType(hardwareToUpdate.getType());
            storedHardware.setCode(hardwareToUpdate.getCode());
            storedHardware.setStock(hardwareToUpdate.getStock());
            storedHardware.setPrice(hardwareToUpdate.getPrice());

            return Optional.of(storedHardware);
        }

        return Optional.empty();
    }

    @Override
    public boolean deleteHardwareById(Integer id) {
        return hardwareList.removeIf(hardware -> hardware.getId().equals(id));
    }
}
