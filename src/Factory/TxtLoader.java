package Factory;

import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TxtLoader implements AssetLoader{

    @Override
    public Object load(String name) throws Exception {
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

    /**
     *
     * Leer el contenido en csv
     * Clave de mapa: archivo csvFileFirstRow csv, título del encabezado;
     * clave: archivo csvFileContent csv, contenido (eliminar contenido del encabezado)
     *
     * @param file csv objeto de archivo
     * @param startRow start row
     * @return
     * @throws IOException
     * Descripción de la excepción @exception
     * @see
     */
    private List<List<String[]>> readCSVFile(File file, int startRow, String characters) throws IOException{
        List<String[]> csvFileFirstRowArrayList = new ArrayList<String[]>();
        List<String[]> strArrayList = new ArrayList<String[]>();

        CSVReader reader = null;
        // Con separador
        if (",".equalsIgnoreCase(characters)) reader = new CSVReader(new FileReader(file));
        if (!",".equalsIgnoreCase(characters))reader = new CSVReader(new FileReader(file), characters.toCharArray()[0]);

        String[] nextLine,nextLine2;
        int linias = 0;
        while ((nextLine = reader.readNext()) != null){
            linias++;
        }
        reader.close();
        CSVReader reader2 = null;
        // Con separador
        if ((",".equalsIgnoreCase(characters)) || ("/".equalsIgnoreCase(characters)) || (".".equalsIgnoreCase(characters)) || (":".equalsIgnoreCase(characters)) || (";".equalsIgnoreCase(characters))) reader2 = new CSVReader(new FileReader(file));
        if (!",".equalsIgnoreCase(characters))reader2 = new CSVReader(new FileReader(file), characters.toCharArray()[0]);
        int cont = 0;
        while (cont < linias-1) {
            nextLine2 = reader2.readNext();
            System.out.println("LatD: ["+ nextLine2[0] +"], LatM: ["+ nextLine2[1] +"], LatS: ["+ nextLine2[2] +"], " +
                    "NS: ["+ nextLine2[3] +"], LonD: ["+ nextLine2[4] +"], LonM: ["+ nextLine2[5] +"], LonS: ["+ nextLine2[6] +"]" +
                    ", EW: ["+ nextLine2[7] +"], City: ["+ nextLine2[8] +"], State: ["+ nextLine2[9] +"]");

            if (cont>=startRow)
                strArrayList.add(nextLine2);
            else
                csvFileFirstRowArrayList.add(nextLine2);

            cont ++;
        }


        reader.close();
        List<List<String[]>> strArrayList2 = new ArrayList<>();
        strArrayList2.add(csvFileFirstRowArrayList);
        strArrayList2.add(strArrayList);
        return strArrayList2;
    }
}
