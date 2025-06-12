package org.example.springbootvjezba2.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.springbootvjezba2.dto.AuthRequestDTO;
import org.example.springbootvjezba2.dto.HardwareDTO;
import org.example.springbootvjezba2.dto.JwtResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class HardwareControlerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthController authController;

    private String accessToken;

    @BeforeEach
    void setUp() {
        AuthRequestDTO authRequest = new AuthRequestDTO();
        authRequest.setUsername("admin");
        authRequest.setPassword("admin");

        if (Optional.ofNullable(accessToken).isEmpty()) {
            JwtResponseDTO jwtResponse = authController.authenticateAndGetToken(authRequest);
            accessToken = jwtResponse.getAccessToken();
        }
    }

    /*
    testGetAll – integration test koji koristi JWT token i poziva metodu za dohvaćanje svih zapisa o
    hardveru te provjerava je li vraćen JSON objekt zadani broj hardver zapisa te je li na prvom zapisu
    odgovarajući kod hardvera
    */
    @Test
    void testGetAll() throws Exception {
        mockMvc.perform(get("/hardware-list")
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$[0].name", is("Asus TUF RTX 3080")))
                .andExpect(jsonPath("$[1].name", is("EVGA XC3 RTX 3070 Ti")));
    }

    /*
    testGetByCode – integration test koji koristi JWT token i poziva metodu za dohvaćanje zadanog
    hardvera po ispravnom kodu te provjerava je li dohvaćen ispravan hardver
     */
    @Test
    void testGetByCode() throws Exception {
        mockMvc.perform(get("/hardware-list/code/{code}", "1234561")
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is("1234561")))
                .andExpect(jsonPath("$.name", is("Asus TUF RTX 3080")))
                .andExpect(jsonPath("$.stock", is(10)))
                .andExpect(jsonPath("$.price", is(1599.00)))
                .andExpect(jsonPath("$.typeId", is(2)));
    }

    /*
    testSaveHardware – integration test koji koristi JWT token i poziva metodu za spremanje
    podataka te provjerava je li uspješno izvršena te vraćen ispravan odgovor te vrijednosti
    spremljenog hardvera u JSON obliku
     */
    @Test
    void testSaveHardware() throws Exception {
        HardwareDTO hardware1 = new HardwareDTO(
                6L, "Hardware 1", "111111", 10, new BigDecimal("99.99"), 2L
        );

        mockMvc.perform(post("/hardware-list")
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(hardware1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.code", is("111111")))
                .andExpect(jsonPath("$.name", is("Hardware 1")))
                .andExpect(jsonPath("$.stock", is(10)))
                .andExpect(jsonPath("$.price", is(99.99)))
                .andExpect(jsonPath("$.typeId", is(2L)));
    }

    /*
    testUpdateHardware – integration test koji koristi JWT token i poziva metodu za ažuriranje
    podataka te provjerava je li uspješno ažuriran podatak o hardveru
     */
    @Test
    void testUpdateHardware() throws Exception {
        mockMvc.perform(get("/hardware-list")
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$[0].name", is("Asus TUF RTX 3080")))
                .andExpect(jsonPath("$[1].name", is("EVGA XC3 RTX 3070 Ti")));
    }
}
