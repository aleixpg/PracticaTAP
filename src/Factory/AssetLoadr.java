package Factory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Prueba en lugar de interface a abstract
 */
public abstract class AssetLoadr {
    public Object load(String name) throws Exception{
        List<List<String[]>> list = new ArrayList<>();
        try{
            File archivo = new File (name);
            list = readCSVFile(archivo, 1, ",");
            //System.out.println(list.get(0).get(0)[0]);
        } catch (Exception e) {
            System.out.println("No se ha podido encontrar el fichero.");
            e.printStackTrace();
        }
        return list;
    }

    abstract List<List<String[]>> readCSVFile(File file, int startRow, String characters) throws IOException;
}
