package Ficheros;

import Factory.*;
import Visitor.*;

import java.lang.*;

public class Main {

    public static void main(String[] args) {
        AssetManager assets = new AssetManager();
        assets.addLoader(new JsonLoader(), "json");
        assets.addLoader(new CSVLoader(), "csv");
        assets.addLoader(new CSVLoader(), "txt");
        //List<List<String[]>> list = new ArrayList<>();
        //Iterator<MyObject> iter = assets.load("example.json");
        //System.out.println(list);
        DataFrameComposite list3 = new DataFrameComposite();
        DataFrameComposite list4 = new DataFrameComposite(list3);
        DataFrameComposite list5 = new DataFrameComposite(assets.load("cities.csv"),list3);
        DataFrameComposite list8 = new DataFrameComposite(assets.load("cities.json"),list3);

        DataFrameComposite list6 = new DataFrameComposite(assets.load("cities.csv"),list4);
        DataFrameComposite list7 = new DataFrameComposite(assets.load("cities.txt"),list4);

        list3.size();
        list4.size();
        list5.size();
        list6.size();
        list7.size();
        list8.size();

        OperationVisitor visitor = new Visitor();
        list4.accept(visitor, "maximum","LatM");
        list4.accept(visitor, "minimum","LatM");
        list4.accept(visitor, "average", "LatM");
        list4.accept(visitor, "sum", "LatM");
        //DataFrameComposite list9 = MyInterceptor.getProxy(new DataFrameComposite(), DataFrameInterface.class);
        //list9.size();

        //visitor.visit(list3,"average");
        //visitor.visit(list3,"sum");
        //list1.loadData();
        /*list1.at(3,"City");
        list1.iat(3,2);
        list1.columns();
        list1.size();
        list1.sort("LonS", "intAscending");
        list1.sort("City", "Letters");*/


        //DataFrameCSV list1 = new DataFrameCSV();
        //list1.loadData();
        /*list1.at(3, "City");
        list1.iat(3, 2);
        list1.columns();
        list1.size();
        System.out.println("");
        list1.sort("LonS", "intAscending");
        System.out.println("");
        list1.sort("City", "Letters");
        System.out.println("");
        list1.query("LatS", "<", "14");
        System.out.println("");
        list1.query("LatS", ">", "14");
        System.out.println("");
        list1.query("LatS", "=", "11");
        System.out.println("");
        list1.query("State", "=", "OH");
        System.out.println("");
        list1.query("State", "!=", "OH");
        System.out.println("");
        list1.query("LatS", "!=", "11");
        System.out.println(list1.iterator().hasNext());
        int cont =0;
        for (String[] i: list1) {
            list1.line(i);
            cont++;
            if (cont==128) break;
        }*/
    }
}