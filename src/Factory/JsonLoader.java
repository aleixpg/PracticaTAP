package Factory;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//Esta clase indica a la factoria como leer un archivo de este tipo
public class JsonLoader implements AssetLoader{

    @Override
    public Object load(String name) throws Exception {
        List<List<String[]>> list = new ArrayList<>();
        try{
            FileReader archivo = new FileReader (name);
            list = readJSONFile(archivo, 1, ",");
            //System.out.println(list.get(0).get(0)[0]);
        } catch (Exception e) {
            System.out.println("No se ha podido encontrar el fichero.");
            e.printStackTrace();
        }
        return list;
    }

    /**
     *
     * Metodo que se encarga de leer el contenido del JSON
     *
     * @param file csv objeto de archivo
     * @param characters de caracteres
     * @param startRow start row
     * @return
     * @throws IOException
     * Descripción de la excepción @exception
     * @see
     */
    private List<List<String[]>> readJSONFile(FileReader file, int startRow, String characters) throws IOException {
        //Listas auxiliares que nos ayudan en la construccion
        List<String[]> jsonFileFirstRowArrayList = new ArrayList<String[]>();
        List<String[]> strArrayList = new ArrayList<String[]>();

        JSONParser parser = new JSONParser();
        try {
            //Parseamos el archivo en JSON
            org.json.simple.JSONArray obj = (JSONArray) parser.parse(file);

            //Lo convertimos en una array
            // A JSON array. JSONObject supports java.util.List interface.
            //org.json.simple.JSONObject jsonObject= (org.json.simple.JSONObject) obj;
            org.json.simple.JSONArray jsonArray = (org.json.simple.JSONArray) obj;

            //Nos quedamos con el primer objeto para quedarnos con la keyValue(La cabecera de las columnas)
            JSONObject jsonObject = (JSONObject) jsonArray.get(0);

            //Calculamos como de grande sera la array iterando sobre los elementos
            int count = 0;
            for (Object key : jsonObject.keySet()) {
                count++;
            }
            String[] keys = new String[count];

            //Empezamos montando la array
            int i = 0;
            for (Object key : jsonObject.keySet()) {
                //System.out.println(key.toString());
                //Guardamos en la tabla la key value
                keys[i] = key.toString();
                i++;
            }

            //Lo añadimos a la primera lista
            jsonFileFirstRowArrayList.add(keys);

            //convertimos la array creada antes en iterator
            Iterator<JSONObject> iterator = jsonArray.iterator();

            //Hacemos hasta el final de esta array
            for (int j = 0; iterator.hasNext(); j++) {
                //Leemos los elementos del array
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(j);
                //System.out.println(jsonObject1.toString());

                //lista auxiliar para guardar el valor de cada columna
                String[] values = new String[count];

                //Separamos las columnas del elemento
                for (int k = 0; k < count; k++) {
                    //guardamos esos valores en la lista auxiliar
                    values[k] = jsonObject1.get(keys[k]).toString();
                    //System.out.println(values[k]);
                }

                //Añadimos a la segunda lista
                strArrayList.add(values);
                //Leemos el siguiente elemento
                iterator.next();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        //Devolvemos la fusion de las dos listas auxiliares creadas al inicio
        List<List<String[]>> strArrayList2 = new ArrayList<>();
        strArrayList2.add(jsonFileFirstRowArrayList);
        strArrayList2.add(strArrayList);
        return strArrayList2;
    }
}
