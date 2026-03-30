package Utils;

import java.time.LocalDate;

public class HotelUtils {

    public static String registrar(String habitacion, String nombre, LocalDate fechaReserva) {

        // campos vacios
        if (habitacion == null || habitacion.isEmpty() ||
                nombre == null || nombre.isEmpty() ||
                fechaReserva == null)
        {
            return "Debe ingresar los datos requeridos";
        }

        // validacion habitacion
        if (!habitacion.matches("[1-3][0-9]{2}"))
        {
            return "Ingrese una habitación valida";
        }

        // validacion nombre
        if (!nombre.matches("[a-zA-Z]{4,}"))
        {
            return "Recuerde que el nombre del cliente debe contener al menos cuatro caracteres";
        }

        // validacion fecha
        if (!fechaReserva.isAfter(LocalDate.now()))
        {
            return "Debe ingresar una fecha valida";
        }

        return "El registro ha sido exitoso";
    }
}