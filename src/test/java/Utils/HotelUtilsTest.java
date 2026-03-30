package Utils;

import java.time.LocalDate;

public class HotelUtilsTest {

    private static void assertEquals(String actual, String expected) {
        if (!actual.equals(expected)) {
            System.out.println("TEST FAILED: " + actual + " was expected " + expected);
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

        System.out.println("\n=== VALIDACION: NOMBRE ===");

        // Nombre valido
        assertEquals(
                HotelUtils.registrar("101", "Miguel", LocalDate.now().plusDays(1)),
                "El registro ha sido exitoso"
        );

        // Nombre invalido
        assertEquals(
                HotelUtils.registrar("101", "Ana", LocalDate.now().plusDays(1)),
                "Recuerde que el nombre del cliente debe contener al menos cuatro caracteres"
        );

        System.out.println("\n=== VALIDACION: FECHA ===");

        // Fecha valida
        assertEquals(
                HotelUtils.registrar("101", "Carlos", LocalDate.now().plusDays(2)),
                "El registro ha sido exitoso"
        );

        // Fecha invalida
        assertEquals(
                HotelUtils.registrar("101", "Carlos", LocalDate.now().minusDays(1)),
                "Debe ingresar una fecha valida"
        );

        System.out.println("\n=== TEST RESULTADO ===");

        //resultado correcto
        assertEquals(
                HotelUtils.registrar("256", "Martinez", LocalDate.now().plusDays(5)),
                "El registro ha sido exitoso"
        );

        // resultado vacio
        assertEquals(
                HotelUtils.registrar("", "", null),
                "Debe ingresar los datos requeridos"
        );
    }
}