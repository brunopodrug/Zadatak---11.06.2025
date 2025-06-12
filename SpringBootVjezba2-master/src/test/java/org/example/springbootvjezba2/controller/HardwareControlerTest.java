package org.example.springbootvjezba2.controller;

import java.math.BigDecimal;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.springbootvjezba2.domain.Hardware;
import org.example.springbootvjezba2.dto.HardwareDTO;
import org.example.springbootvjezba2.service.HardwareService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.*;

@ExtendWith(MockitoExtension.class)
public class HardwareControlerTest {
    @Mock
    private HardwareService hardwareService;

    @InjectMocks
    private HardwareController hardwareController;

    private MockMvc mockMvc;

    private List<HardwareDTO> hardwares;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(hardwareController).build();

        // Initialize mock data
        HardwareDTO hardware1 = new HardwareDTO
                (1L, "Hardware 1", "111111", 10, new BigDecimal("99.99"), 2L);
        HardwareDTO hardware2 = new HardwareDTO
                (2L, "Hardware 2", "22222", 20, new BigDecimal("499.99"), 1L);
        hardwares = Arrays.asList(hardware1, hardware2);
    }

    /*
    testGetAll – jUnit test koji poziva metodu za dohvaćanje svih zapisa o hardveru te provjerava je li
    broj objekata ispravan te je li jedanput pozvana servisna metoda
     */
    @Test
    void testGetAllArticles() throws Exception {
        when(hardwareService.findAll()).thenReturn(hardwares);

        // Perform GET request and verify the response
        mockMvc.perform(get("/hardware-list")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Hardware 1")))
                .andExpect(jsonPath("$[0].code", is("111111")))
                .andExpect(jsonPath("$[0].stock", is(10)))
                .andExpect(jsonPath("$[0].price", is(99.99)))
                .andExpect(jsonPath("$[0].typeId", is(2)))
                .andExpect(jsonPath("$[1].name", is("Hardware 2")));

        // Verify that the service method was called once
        verify(hardwareService, times(1)).findAll();
    }

    /*
    testGetByCode_Found – jUnit test koji poziva metodu za dohvaćanje zadanog hardvera
    po ispravnom kodu te je li jedanput pozvana servisna metoda
     */
    @Test
    void testGetByCode_Found() throws Exception {
        when(hardwareService.findHardwareByCode("111111")).thenReturn(hardwares.get(0));

        // Perform GET request and verify the response
        mockMvc.perform(get("/hardware-list/code/{code}", "111111")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is("111111")))
                .andExpect(jsonPath("$.name", is("Hardware 1")))
                .andExpect(jsonPath("$.stock", is(10)))
                .andExpect(jsonPath("$.price", is(99.99)))
                .andExpect(jsonPath("$.typeId", is(2)));

        // Verify that the service method was called once with the correct argument
        verify(hardwareService, times(1)).findHardwareByCode("111111");
    }

    /*
    testGetByCode_NotFound – jUnit test koji poziva metodu za dohvaćanje zadanog hardvera po neispravnom kodu,
    je li bačena odgovarajuća iznimka te je li jedanput pozvana servisna metoda
     */
    @Test
    void testGetByCode_NotFound() throws Exception {
        when(hardwareService.findHardwareByCode("33445566"))
                .thenReturn(null);

        mockMvc.perform(get("/hardware-list/code/{code}", "33445566")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(hardwareService, times(1)).findHardwareByCode("33445566");
    }

    /*
    testSave_Success – jUnit test koji poziva metodu za spremanje podataka te provjerava je li uspješno izvršena
    te je li jedan pozvana servisna metoda
    */
    @Test
    void testSave_Success() throws Exception {
        when(hardwareService.saveNewHardware(hardwares.get(0))).thenReturn(hardwares.get(0));

        mockMvc.perform(post("/hardware-list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(hardwares.get(0))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.code", is("111111")))
                .andExpect(jsonPath("$.name", is("Hardware 1")))
                .andExpect(jsonPath("$.stock", is(10)))
                .andExpect(jsonPath("$.price", is(99.99)))
                .andExpect(jsonPath("$.typeId", is(2)));

        verify(hardwareService, times(1)).saveNewHardware(hardwares.get(0));
    }

    /*
    testSave_Conflict – jUnit test koji poziva metodu za spremanje podataka te provjerava je
    li uspješno bačena iznimka i status „HttpStatus.CONFLICT” ako se želi spremiti hardver koji
    već postoji te je li jedan pozvana servisna metoda
    */
    @Test
    void testSave_Conflict() throws Exception {
        HardwareDTO duplicateHardware = new HardwareDTO(
                3L, "Hardware 3", "111111", 222, new BigDecimal("33.99"), 4L
        );

        // Simulate that hardware with the same code already exists
        when(hardwareService.findHardwareByCode("111111")).thenReturn(hardwares.get(0));

        mockMvc.perform(post("/hardware-list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(duplicateHardware)))
                .andExpect(status().isConflict());

        verify(hardwareService, times(1)).findHardwareByCode("111111");
        verify(hardwareService, times(0)).saveNewHardware(hardwares.get(0));
    }

    /*
    testUpdate_Success – jUnit test koji poziva metodu za ažuriranje podataka te provjerava
    je li uspješno izvršena te je li jedan pozvana servisna metoda
    */
    @Test
    void testUpdate_Success() throws Exception {
        when(hardwareService.updateHardware(hardwares.get(0), 1L))
                .thenReturn(hardwares.get(0));

        mockMvc.perform(put("/hardware-list/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(hardwares.get(0))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is("111111")))
                .andExpect(jsonPath("$.name", is("Hardware 1")))
                .andExpect(jsonPath("$.stock", is(10)))
                .andExpect(jsonPath("$.price", is(99.99)))
                .andExpect(jsonPath("$.typeId", is(2)));

        verify(hardwareService, times(1)).updateHardware(hardwares.get(0), 1L);
    }

    /*
    testUpdate_NotFound – jUnit test koji poziva metodu za ažuriranje zadanog hardvera po neispravnom kodu,
    je li bačena odgovarajuća iznimka ako se pokušava ažurirati hardver koji ne postoji te je li jedanput
    pozvana servisna metoda
    */
    @Test
    void testUpdate_NotFound() throws Exception {
        when(hardwareService.updateHardware(hardwares.get(0), 1L))
                .thenReturn(hardwares.get(0));

        mockMvc.perform(put("/hardware-list/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());

        verify(hardwareService, times(1)).updateHardware(hardwares.get(0), 1L);

    }

    //testDelete– jUnit test koji poziva metodu za brisanje zadanog hardvera te je li jedanput pozvana servisna metoda
    @Test
    void testDelete() throws Exception {
        doNothing().when(hardwareService).deleteHardwareById(1L);

        mockMvc.perform(delete("/hardware-list/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(hardwareService, times(1)).deleteHardwareById(1L);
    }
}
