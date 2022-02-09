package Factory;
import java.util.HashMap;

//CREDITS: https://stackoverflow.com/questions/35139016/which-design-pattern-to-use-to-process-different-files-in-java
public class AssetManager {
    private HashMap<String, AssetLoader> loaders = new HashMap<>();

    /**
     * Metodo que añade en un hashmap los diferentes tipos de Loader con su extension que podemos tener
     * @param loader Clase que indica como leer un fichero
     * @param extension string que indica para que extension hay que usar la clase loader definida
     */
    public void addLoader(AssetLoader loader, String extension)
    {
        loaders.put(extension, loader);
    }

    /**
     * Metodo que le pasamos el PATH del fichero, leemos su extension, y segun sea va al HashMap y busca la clase
     * necesaria para leer ese tipo de fichero
     * @param name PATH del fichero
     * @param <T>
     * @return el elemento que devuelve al hacer .load() del fichero
     */
    @SuppressWarnings("unchecked")
    public <T> T load(String name) {
        //Buscamos en que posicion esta el punto que indica la extension
        int i = name.lastIndexOf('.');
        //En caso de devolver -1 quiere decir que no se ha encontrado, por lo tanto salta error
        if (i == -1)
            throw new RuntimeException("\"" + name + "\" has no extension, and so has no associated asset loader");
        //Caso contrario, seguimos y nos quedamos con la continuacion del string a partir del punte, es decir
        //la extension del fichero
        String extension = name.substring(i + 1);
        //Buscamos en el HashMap el assetloader(Clase que indica como leer ese tipo de extension) que pertenece
        //a esta extension
        AssetLoader loader = loaders.get(extension);
        //En caso de no encontrarla, salta error avisando de que esa extension no esta registrada en el asset loader
        //para que este indique como leer la extension
        if (loader == null)
            throw new RuntimeException("No loader registered for \"." + extension + "\" files");
        try {
            //Finalmente retornamos lo que devuelve la lectura del fichero
            return (T) loader.load(name);
        } catch (ClassCastException e) {
            throw new RuntimeException("\"" + name + "\" could not be loaded as the expected type");
        } catch (Exception e) {
            throw new RuntimeException("Failed to load " + name, e);
        }
    }
}
