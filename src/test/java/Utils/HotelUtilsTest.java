package Utils;

import java.time.LocalDate;

public class HotelUtilsTest {

    private static void assertEquals(String actual, String expected) {
        if (!actual.equals(expected)) {
            System.out.println("TEST FAILED: " + actual + " se esperaba " + expected);
        } else {
            System.out.println("TEST PASSED: " + actual);
        }
    }

    public static void main(String[] args) {
        System.out.println("\n=== VALIDACION: HABITACION ===");

        // Habitacion valida
        assertEquals(
                HotelUtils.registrar("101", "Carlos", LocalDate.now().plusDays(1)),
                "El registro ha sido exitoso"
        );

        // Habitacion invalida
        assertEquals(
                HotelUtils.registrar("401", "Carlos", LocalDate.now().plusDays(1)),
                "Ingrese una habitación valida"
        );
    }
}