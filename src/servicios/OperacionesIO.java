package servicios;

import excepciones.ArchivoNoExisteException;
import excepciones.DirectorioNoExisteException;
import excepciones.NoEsDirectorioException;

import java.io.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class OperacionesIO {

    public static void main(String[] args)
            throws DirectorioNoExisteException, NoEsDirectorioException {
        //recorrerRecursivo("C:\\Users\\josec\\Desktop\\prueba");
        //filtrarPorExtensionYOrdenar("C:\\Users\\josec\\Documents\\Wallpaper",".jpg",true);
        //List<File> lista = filtrarPorSubcadena("C:\\Users\\josec\\Documents\\Wallpaper","ca");
        borrar("C:\\Users\\josec\\Desktop\\prueba");
    }

    //---EJERCICIO 9---
    public static boolean borrar(String ruta)
            throws ArchivoNoExisteException {
        File file = new File(ruta);
        if(!file.exists())
            throw new ArchivoNoExisteException("No existe");
        if (file.isFile())
            return file.delete();
        if (file.isDirectory()){
            for (File f : file.listFiles()){
                borrar(f.getAbsolutePath());
            }
            file.delete();
        }
        return true;
    }

    //---EJERCICIO 8---
    public static boolean copiarDirectorio (String origen, String destino)
            throws DirectorioNoExisteException, NoEsDirectorioException, IOException {
        File fileOrigen = new File(origen);
        File fileDestino = new File(destino);
        Utilidades.esDirectorio(fileOrigen);
        Utilidades.esDirectorio(fileDestino);

        File[] files = fileOrigen.listFiles();

        for (File file: files){
            copiarArchivo(fileOrigen.getPath(),fileDestino.getPath());
        }
        return true;
    }

    //---EJERCICIO 7---
    public static boolean moverArchivo(String origen, String destino) throws DirectorioNoExisteException, NoEsDirectorioException, IOException {
        if (!copiarArchivo(origen, destino))
            return false;
        return new File(origen).delete();
    }

    //---EJERCICIO 6---
    public static boolean copiarArchivo(String origen, String destino)
            throws DirectorioNoExisteException, NoEsDirectorioException, IOException {
        File fileOrigen = new File(origen);
        File fileDestino = new File(destino);

        if(fileDestino.listRoots()==null || fileDestino.listRoots().length==0 || !fileOrigen.exists()){
            return false;
        }

        fileDestino.getParentFile().mkdirs();

        try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(fileOrigen));
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(fileDestino))) {
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }
        catch (IOException _) {
        }
        return true;
    }

    //---EJERCICIO 5---
    public static List<File> filtrarPorSubcadena (String ruta, String subcadena) throws DirectorioNoExisteException, NoEsDirectorioException {
        File file = new File(ruta);
        Utilidades.esDirectorio(file);
        File[] files = file.listFiles((dir, name) ->new File(dir, name).isDirectory() || name.toLowerCase().contains(subcadena.toLowerCase()));
        List<File> lista = new ArrayList<>();
        for (File f : files) {
            if (!f.isDirectory())
                lista.add(f);
            else
                lista.addAll(filtrarPorSubcadena(f.getPath(), subcadena));
        }
        return lista;
    }

    public static void filtrarPorSubcadenaAux (String ruta, String subcadena) throws DirectorioNoExisteException, NoEsDirectorioException {
        File file = new File(ruta);
        Utilidades.esDirectorio(file);
    }

    //---EJERCICIO 4---
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
        File[] files = file.listFiles((dir, name) -> new File(dir,name).isDirectory()||name.toLowerCase().endsWith(extension.toLowerCase()));
        List<File> lista = new ArrayList<>();
        for (File f : files) {
            if (!f.isDirectory())
                lista.add(f);
            else
                lista.addAll(filtrarPorExtensionYOrdenarAux(f, extension));
        }
        return lista;
    }

    //---EJERCICIO 3---
    public static void filtrarPorExtension(String ruta, String extension)
            throws DirectorioNoExisteException, NoEsDirectorioException {
        File file = new File(ruta);
        Utilidades.esDirectorio(file);
        System.out.println("---LISTANDO EL DIRECTORIO "+ruta+"---");
        File[] files = file.listFiles((dir, name) -> name.toLowerCase().endsWith(extension.toLowerCase()));
        if (files == null) {
            System.out.println("Sin resultados");
            return;
        }

        for(File f : files)
            mostrarInformacionFichero(f);
    }

    //---EJERCICIO 2---
    public static void recorrerRecursivo (String ruta)
            throws DirectorioNoExisteException, NoEsDirectorioException {
        final String sangria = "--|";
        File file = new File(ruta);
        Utilidades.esDirectorio(file);
        System.out.println("---LISTANDO EL DIRECTORIO "+ruta+"---");
        recorrerRecursivoAux(file, sangria);
    }

    private static void recorrerRecursivoAux(File file, String sangria) {

        File[] files = file.listFiles();
        for (File f : files) {
            System.out.print(sangria);
            mostrarInformacionFichero(f);
            if (f.isDirectory())
                recorrerRecursivoAux(f, sangria+sangria);
        }
    }

    //---EJERCICIO 1---
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
        final String FORMATO = "dd/MM/yyyy HH:mm:ss";
        String informacionFichero;
        if(file.isDirectory())
            informacionFichero = file.getName()+" <DIR> ";
        else
            informacionFichero = file.getName()+" <FICHERO> "+ NumberFormat.getInstance().format(file.length() / 1024.0) +" KB ";
        System.out.println(informacionFichero+Utilidades.milisecToDate(file.lastModified(),FORMATO));
    }

}
