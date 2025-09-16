package servicios;

import excepciones.DirectorioNoExisteException;
import excepciones.NoEsDirectorioException;

import java.io.File;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class OperacionesIO {

    public static void main(String[] args)
            throws DirectorioNoExisteException, NoEsDirectorioException {
        //recorrerRecursivo("C:\\Users\\josec\\Desktop\\prueba");
        filtrarPorExtensionYOrdenar("C:\\Users\\josec\\Documents\\Wallpaper",".jpg",true);
    }

    public static void filtrarPorExtensionYOrdenar(String ruta, String extension, boolean descendente)
            throws DirectorioNoExisteException, NoEsDirectorioException {
        File file = new File(ruta);
        Utilidades.esDirectorio(file);
        List<File> lista = filtrarPorExtensionYOrdenarAux(file, extension);
        lista.sort((f1, f2) -> f1.getName().compareTo(f2.getName()));
        if(!descendente)
            lista.reversed();
        for (File f : lista) {
            mostrarInformacionFichero(f);
        }
    }

    public static List<File> filtrarPorExtensionYOrdenarAux(File file, String extension)
            throws DirectorioNoExisteException, NoEsDirectorioException {
        File[] files = file.listFiles((dir, name) -> new File(dir,name).isDirectory()||name.endsWith(extension));
        List<File> lista = new ArrayList<>();
        for (File f : files) {
            if (!f.isDirectory())
                lista.add(f);
            else
                lista.addAll(filtrarPorExtensionYOrdenarAux(f, extension));
        }
        return lista;
    }

    public static void filtrarPorExtension(String ruta, String extension)
            throws DirectorioNoExisteException, NoEsDirectorioException {
        File file = new File(ruta);
        Utilidades.esDirectorio(file);
        System.out.println("---LISTANDO EL DIRECTORIO "+ruta+"---");
        File[] files = file.listFiles((dir, name) -> name.endsWith(extension));
        if (files == null) {
            System.out.println("Extension: "+extension);
            return;
        }

        for(File f : files)
            mostrarInformacionFichero(f);
    }

    public static void recorrerRecursivo (String ruta)
            throws DirectorioNoExisteException, NoEsDirectorioException {
        File file = new File(ruta);
        Utilidades.esDirectorio(file);
        System.out.println("---LISTANDO EL DIRECTORIO "+ruta+"---");
        recorrerRecursivoAux(file, "--|");
    }

    private static void recorrerRecursivoAux(File file, String sangria) {

        File[] files = file.listFiles();
        for (File f : files) {
            System.out.print(sangria);
            mostrarInformacionFichero(f);
            if (f.isDirectory())
                recorrerRecursivoAux(f, sangria+"--|");
        }
    }

    public static void visualizarContenido (String ruta)
            throws DirectorioNoExisteException, NoEsDirectorioException {
        File file = new File(ruta);
        Utilidades.esDirectorio(file);
        System.out.println("---LISTANDO EL DIRECTORIO "+ruta+"---");

        File[] files = file.listFiles();

        for(File f : files)
            mostrarInformacionFichero(f);
    }

    private static void mostrarInformacionFichero(File file){
        String informacionFichero;
        if(file.isDirectory())
            informacionFichero = file.getName()+" <DIR> ";
        else
            informacionFichero = file.getName()+" <FICHERO> "+ NumberFormat.getInstance().format(file.length() / 1024.0) +" KB ";
        System.out.println(informacionFichero+Utilidades.milisecToDate(file.lastModified(),"dd/MM/yyyy HH:mm:ss"));
    }

}
