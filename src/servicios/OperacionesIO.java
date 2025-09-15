package servicios;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OperacionesIO {

    public static void main(String[] args) {
        visualizarContenido("C:\\Users\\josec\\Desktop\\Discord.lnk");
    }

    static void visualizarContenido (String ruta) {
        File file = new File(ruta);

        System.out.println("---LISTANDO EL DIRECTORIO "+ruta+"---");

        if(!file.exists()){
            System.out.println("La ruta especificada no existe");
            return;
        }
        if(!file.isDirectory()){
            System.out.println("La ruta no es un directorio");
            return;
        }

        File[] files = file.listFiles();

        for(File f : files){
            String filename, fileLastUpdate;

            filename = f.getName();
            if(f.isDirectory())
                filename = filename+" <DIR> ";
            else if(f.isFile()){
                filename = filename+" <FICHERO> "+  String.format("%.3f", f.length()/1024.0) +"_KB ";
            }
            fileLastUpdate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(f.lastModified());
            System.out.println("-|"+filename + fileLastUpdate);
        }
    }

    static void recorrerRecursivo (String ruta) {
        File file = new File(ruta);

        System.out.println("---LISTANDO EL DIRECTORIO "+ruta+"---");

        if(!file.exists()){
            System.out.println("La ruta especificada no existe");
            return;
        }
        if(!file.isDirectory()){
            System.out.println("La ruta no es un directorio");
            return;
        }

        File[] files = file.listFiles();
        String encabezado = "---|";

        for(File f : files){
            String filename, fileLastUpdate;

            filename = f.getName();
            if(f.isDirectory())
                filename = filename+" <DIR> ";
            else if(f.isFile()){
                filename = filename+" <FICHERO> "+  String.format("%.3f", f.length()/1024.0) +"_KB ";
            }
            fileLastUpdate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(f.lastModified());
            System.out.println("-|"+filename + fileLastUpdate);
        }
    }
}
