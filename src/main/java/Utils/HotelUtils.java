package Utils;

import java.time.LocalDate;

public class HotelUtils {

    public static String registrar(String habitacion, String nombre, LocalDate fechaReserva) {

        // validacion habitacion
        if (!habitacion.matches("[1-3][0-9]{2}"))
        {
            return "Ingrese una habitación valida";
        }

        return "El registro ha sido exitoso";
    }
}