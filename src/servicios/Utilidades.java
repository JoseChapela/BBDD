package servicios;

import excepciones.DirectorioNoExisteException;
import excepciones.NoEsDirectorioException;

import java.io.File;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Utilidades {

    public static void esDirectorio (File file)
            throws DirectorioNoExisteException, NoEsDirectorioException {
        if(!file.exists())
            throw new DirectorioNoExisteException("El directorio no existe");
        else if (!file.isDirectory())
            throw new NoEsDirectorioException("El archivo no es un directorio");
    }

    public static String milisecToDate(Long milisec, String formato){
        LocalDateTime date = Instant.ofEpochMilli(milisec).atZone(ZoneId.systemDefault()).toLocalDateTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formato);
        return date.format(formatter);
    }
}
