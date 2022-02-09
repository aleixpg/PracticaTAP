package Visitor;

import Ficheros.*;

public class Visitor implements OperationVisitor{
    public Object visit(DataFrameComposite D, String op, String Label){
        switch (op){
            case "maximum":
                System.out.println("Visitor Maximum");
                int absoluteMax = 0;
                int absoluteMaxI = 0;
                //Recorremos toda la lista de elems
                for (int i = 0; i < D.getList().get(1).size(); i++) {
                    try {
                        //Con el at2 cojemos el elem de fila i y la columna indicada en label
                        int aux = Integer.parseInt(D.at2(i, Label).replace(" ", ""));
                        //si es mayor lo guardamos
                        if (aux > absoluteMax) {
                            //el numero para tener la referencia
                            absoluteMax = aux;
                            //el indice para luego devolver toda la fila
                            absoluteMaxI = i;
                        }
                    } catch (Exception e) {
                        //sacamos error para el caso de indicarle una columna con letras
                        System.out.println("Valor de la columna introducido incorrecto.");
                    }
                }
                //Vamos a buscar la fila de ese indice(max), y la printamos
                for(String a: D.getRow(absoluteMaxI)){
                    System.out.print("[" + a + "]");
                }
                System.out.println();
                //Returnamos esa lista
                return D.getRow(absoluteMaxI);


            case "minimum":
                System.out.println("Visitor minimum");
                int absoluteMin = 999999999;
                int absoluteMinI = 0;
                //Recorremos toda la lista de elems
                for (int i = 0; i < D.getList().get(1).size(); i++) {
                    try {
                        //Con el at2 cojemos el elem de fila i y la columna indicada en label
                        int aux = Integer.parseInt(D.at2(i, Label).replace(" ", ""));
                        //si es menor lo guardamos
                        if (aux < absoluteMin) {
                            //el numero para tener la referencia
                            absoluteMin = aux;
                            //el indice para luego devolver toda la fila
                            absoluteMinI = i;
                        }
                    } catch (Exception e) {
                        //sacamos error para el caso de indicarle una columna con letras
                        System.out.println("Valor de la columna introducido incorrecto.");
                    }
                }
                //Vamos a buscar la fila de ese indice(min), y la printamos
                for(String b: D.getRow(absoluteMinI)){
                    System.out.print("[" + b + "]");
                }
                System.out.println();
                //Returnamos esa lista
                return D.getRow(absoluteMinI);


            case "average":
                System.out.println("Visitor average");
                double total = 0;
                int numElems = 0;
                //Recorremos toda la lista de elems
                for (int i = 0; i < D.getList().get(1).size(); i++) {
                    try {
                        //Con el at2 cojemos el elem de fila i y la columna indicada en label
                        int aux = Integer.parseInt(D.at2(i, Label).replace(" ", ""));
                        //sumamos ese valor en nuestra variable
                        total += aux;
                        //guardamos el numero de items
                        numElems++;
                    } catch (Exception e) {
                        //sacamos error para el caso de indicarle una columna con letras
                        System.out.println("Valor de la columna introducido incorrecto.");
                    }
                }
                //printamos el average = total/numElems
                System.out.println("Average: "+(total/numElems));
                //returnamos average
                return (total/numElems);


            case "sum":
                System.out.println("Visitor sum");
                int total1 = 0;
                //Recorremos toda la lista de elems
                for (int i = 0; i < D.getList().get(1).size(); i++) {
                    try {
                        //Con el at2 cojemos el elem de fila i y la columna indicada en label
                        int aux = Integer.parseInt(D.at2(i, Label).replace(" ", ""));
                        //sumamos ese valor en nuestra variable
                        total1 += aux;
                    } catch (Exception e) {
                        //sacamos error para el caso de indicarle una columna con letras
                        System.out.println("Valor de la columna introducido incorrecto.");
                    }
                }
                //printamos esa variable que contiene la suma
                System.out.println("Sum: "+total1);
                //returnamos la variable de la suma
                return total1;


            default:
                //Caso de pasar una operacion
                System.out.println("Visitor Operacion no existente");
                break;
        }
        return 0;
    }
}
