package org.example.springbootvjezba2.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MathServiceTest {

    private MathService mathService;

    @BeforeEach
    void setUp() {
        mathService = new MathService();
        System.out.println("Priprema za testiranje!");
    }

    @Test
    @DisplayName("Zbrojiti dva broja.")
    void testZbrojDvaBroja() {
        int rezult = mathService.add(6, 5);
        assertEquals(11, rezult, "6 + 5 treba iznositi 11");
    }

    @Test
    @DisplayName("Mnozenje dva broja.")
    void testMnozenjeDvaBroja() {
        int rezult = mathService.multiply(6, 5);
        assertEquals(11, rezult, "6 * 5 treba iznositi 30");
    }
}
