package org.example.springbootvjezba2.repository;

import lombok.AllArgsConstructor;
import org.example.springbootvjezba2.domain.Hardware;
import org.example.springbootvjezba2.domain.Type;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
@Primary
@AllArgsConstructor
public class JdbcHardwareRepository implements HardwareRepository {
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Hardware> findAll() {
        return jdbcTemplate.query("SELECT * FROM HARDWARE", new HardwareMapper());
    }

    @Override
    public Optional<Hardware> findByCode(String code) {
        return Optional.of(jdbcTemplate.queryForObject("SELECT * FROM HARDWARE WHERE CODE = :?",
                new HardwareMapper(), code));
    }

    @Override
    public Hardware saveNewHardware(Hardware hardware) {
        final String SQL =
                "SELECT ID FROM FINAL TABLE (INSERT INTO HARDWARE (name, code, price, stock, typeID) VALUES (?, ?, ?, ?, ?)) HARDWARE";
        Integer generatedId = jdbcTemplate.queryForObject(SQL, Integer.class, hardware.getName(), hardware.getCode(),
                hardware.getPrice(), hardware.getStock(), hardware.getType().getId());
        hardware.setId(generatedId);
        return hardware;
    }

    @Override
    public boolean hardwareByIdExists(Integer id) {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT (*) FROM HARDWARE WHERE ID = ?", Integer.class, id);
        return count > 0;
    }

    @Override
    public Optional<Hardware> updateHardware(Hardware hardwareToUpdate, Integer id) {
        {
            if (hardwareByIdExists(id)) {
                final String SQL =
                        "UPDATE HARDWARE SET name = ?, code = ?, price = ?, stock = ?, typeId = ? WHERE ID = ?";
                jdbcTemplate.update(connection -> {
                    PreparedStatement ps = connection.prepareStatement(SQL);
                    ps.setString(1, hardwareToUpdate.getName());
                    ps.setString(2, hardwareToUpdate.getCode());
                    ps.setBigDecimal(3, hardwareToUpdate.getPrice());
                    ps.setLong(4, hardwareToUpdate.getStock());
                    ps.setInt(5, hardwareToUpdate.getType().getId());
                    ps.setInt(6, id);
                    return ps;
                });
                hardwareToUpdate.setId(id);
                return Optional.of(hardwareToUpdate);
            } else {
                return Optional.empty();
            }
        }
    }

    @Override
    public boolean deleteHardwareById(Integer id) {
        if (hardwareByIdExists(id)) {
            jdbcTemplate.update(
                    "DELETE FROM HARDWARE WHERE ID = ?", id);
            return true;
        } else {
            return false;
        }
    }

    private static class HardwareMapper implements RowMapper<Hardware> {

        public Hardware mapRow(ResultSet rs, int i) throws SQLException {

            Hardware newHardware = new Hardware();
            newHardware.setId(rs.getInt("ID"));
            newHardware.setName(rs.getString("NAME"));
            newHardware.setCode(rs.getString("CODE"));
            newHardware.setPrice(rs.getBigDecimal("PRICE"));
            newHardware.setStock(rs.getInt("STOCK"));

            Integer categoryId = rs.getInt("TYPEID");

            switch (categoryId) {
                case 1 -> newHardware.setType(Type.CPU);
                case 2 -> newHardware.setType(Type.GPU);
                case 3 -> newHardware.setType(Type.MBO);
                case 4 -> newHardware.setType(Type.RAM);
                case 5 -> newHardware.setType(Type.STORAGE);
                default -> newHardware.setType(Type.OTHER);
            }

            return newHardware;
        }
    }
}
