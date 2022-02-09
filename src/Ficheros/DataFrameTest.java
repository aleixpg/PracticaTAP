package Ficheros;
import static org.junit.Assert.*;

import Factory.AssetManager;
import Factory.CSVLoader;
import Factory.JsonLoader;
import Visitor.OperationVisitor;
import Visitor.Visitor;
import org.junit.Test;

import java.util.List;
import java.util.Scanner;

public class DataFrameTest {
    static Scanner teclat = new Scanner(System.in);
    @Test
    public void probarDataframe(){
        AssetManager assets = new AssetManager();
        assets.addLoader(new JsonLoader(), "json");
        assets.addLoader(new CSVLoader(), "csv");
        assets.addLoader(new CSVLoader(), "txt");
        DataFrameComposite dataframe = new DataFrameComposite();
        DataFrameComposite list1 = new DataFrameComposite((List<List<String[]>>) assets.load("cities.csv"));
        System.out.println("AT");
        System.out.println("introduzca el numero de fila: ");
        int fila = teclat.nextInt();
        System.out.println("introduzca el nombre de la columna: ");
        String columna = teclat.nextLine();
        list1.at(fila,columna);
        System.out.println("IAT");
        System.out.println("introduzca el numero de fila: ");
        fila = teclat.nextInt();
        System.out.println("introduzca el numero de columna: ");
        int columna2 = teclat.nextInt();
        list1.iat(fila,columna2);
        System.out.println("COLUMNS");
        list1.columns();
        System.out.println("SIZE");
        list1.size();
        System.out.println("SORT");
        System.out.println("introduzca el tipo de ordenacion que desea (Letters, intAscending, intDescending): ");
        String orden = teclat.nextLine();
        System.out.println("introduzca el nombre de la columna que desea ordenar: ");
        columna = teclat.nextLine();
        list1.sort(columna, orden);
        System.out.println("QUERY");
        System.out.println("introduzca el nombre del header: ");
        String elem1 = teclat.nextLine();
        System.out.println("introduzca el condicional: ");
        String comp = teclat.nextLine();
        System.out.println("introduzca el numero a comparar: ");
        String elem2 = teclat.nextLine();
        list1.query(elem1,comp, elem2);

        OperationVisitor visitor = new Visitor();
        System.out.println("introduzca que desea saber del dataframe visitado: (maximum, minimum, average, sum)");
        String saber = teclat.nextLine();
        System.out.println("introduzca el nombre de la columna que desea: ");
        columna = teclat.nextLine();
        list1.accept(visitor, saber,columna);
    }
    @Test
    public void probarDataframeComposite(){
        AssetManager assets = new AssetManager();
        assets.addLoader(new JsonLoader(), "json");
        assets.addLoader(new CSVLoader(), "csv");
        assets.addLoader(new CSVLoader(), "txt");
        DataFrameComposite dataframe = new DataFrameComposite();
        DataFrameComposite list3 = new DataFrameComposite();
        DataFrameComposite list4 = new DataFrameComposite(list3);
        DataFrameComposite list5 = new DataFrameComposite(assets.load("cities.csv"),list3);
        DataFrameComposite list8 = new DataFrameComposite(assets.load("cities.json"),list3);
        DataFrameComposite list6 = new DataFrameComposite(assets.load("cities.csv"),list4);
        System.out.println("AT");
        System.out.println("introduzca el numero de fila: ");
        int fila = teclat.nextInt();
        System.out.println("introduzca el nombre de la columna: ");
        String columna = teclat.nextLine();
        list3.at(fila,columna);
        System.out.println("IAT");
        System.out.println("introduzca el numero de fila: ");
        fila = teclat.nextInt();
        System.out.println("introduzca el numero de columna: ");
        int columna2 = teclat.nextInt();
        list3.iat(fila,columna2);
        System.out.println("COLUMNS");
        list3.columns();
        System.out.println("SIZE");
        list3.size();
        System.out.println("SORT");
        System.out.println("introduzca el tipo de ordenacion que desea (Letters, intAscending, intDescending): ");
        String orden = teclat.nextLine();
        System.out.println("introduzca el nombre de la columna que desea ordenar: ");
        columna = teclat.nextLine();
        list3.sort(columna, orden);
        System.out.println("QUERY");
        System.out.println("introduzca el nombre del header: ");
        String elem1 = teclat.nextLine();
        System.out.println("introduzca el condicional: ");
        String comp = teclat.nextLine();
        System.out.println("introduzca el numero a comparar: ");
        String elem2 = teclat.nextLine();
        list3.query(elem1,comp, elem2);


        System.out.println("SIZES SEPARADOS:");
        System.out.println(list3.getName()+": ");
        list3.size();
        System.out.println(list4.getName()+": ");
        list4.size();
        System.out.println(list5.getName()+": ");
        list5.size();
        System.out.println(list6.getName()+": ");
        list6.size();
        System.out.println(list8.getName()+": ");
        list8.size();

        System.out.println("Visitor Dataframe "+list3.getName());
        OperationVisitor visitor = new Visitor();

        System.out.println("introduzca que desea saber del dataframe visitado: (maximum, minimum, average, sum)");
        String saber = teclat.nextLine();
        System.out.println("introduzca el nombre de la columna que desea: ");
        columna = teclat.nextLine();
        list3.accept(visitor, saber,columna);


        System.out.println("Visitor Dataframe "+list4.getName());
        System.out.println("introduzca que desea saber del dataframe visitado: (maximum, minimum, average, sum)");
        saber = teclat.nextLine();
        System.out.println("introduzca el nombre de la columna que desea: ");
        columna = teclat.nextLine();
        list4.accept(visitor, saber,columna);

    }
}
