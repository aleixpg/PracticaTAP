package Ficheros;



import Visitor.OperationVisitor;

import java.util.*;
import java.util.function.Consumer;

/**
 * Esta seria la clase dataframe compuesta con la que podremos construir el arbol
 */
public class DataFrameComposite implements DataFrameInterface, Iterable<String[]> {
    private List<List<String[]>> list = new ArrayList<>();
    private final String name;
    private final DataFrameComposite[] nextnode = new DataFrameComposite[10000];
    private DataFrameComposite prevnode;
    static Scanner teclat = new Scanner(System.in);
    private int childs = 0;

    /**
     * Constructor para dataframe composite vacio
     */
    public DataFrameComposite(){
        //this.list = list;
        System.out.println("Introduzca el nombre de este Dataframe: ");
        name = teclat.nextLine();
    }

    /**
     * Constructor para dataframe simple
     * @param list lista que queremos colocar en el dataframe
     */
    public DataFrameComposite(List<List<String[]>> list) {
        this.list = list;
        System.out.println("Introduzca el nombre de este Dataframe: ");
        name = teclat.nextLine();
    }

    /**
     * Constructor para dataframe nodo
     * @param nodex nodo
     */
    public DataFrameComposite(DataFrameComposite nodex){
        //this.list = list;
        this.prevnode = nodex;
        this.prevnode.setNextnode(this);
        System.out.println("Introduzca el nombre de este dataframe que su padre es: " + prevnode.getName());
        String frase = teclat.nextLine();
        name = prevnode.getName() + "/" + frase;
    }

    /**
     * Constructor para dataframe compuesto por nodo y datos
     * @param list lista
     * @param nodex nodo
     */
    public DataFrameComposite(List<List<String[]>> list, DataFrameComposite nodex){
        this.list = list;
        this.prevnode = nodex;
        System.out.println("Introduzca el nombre de este dataframe que su padre es: " + prevnode.getName());
        String frase = teclat.nextLine();
        name = prevnode.getName() + "/" + frase;
        DataFrameComposite node = this;
        prevnode.setNextnode(this);
        List<List<String[]>> listaux;
        while(node.prevnode != null){
            if (node.prevnode.list.size() == 0){
                node.prevnode.list = this.getListCopy();
            }else {
                listaux = node.prevnode.getListCopy();
                int y = 0;
                for (String[] ignored : list.get(1)) {
                    listaux.get(1).add(node.list.get(1).get(y));
                    y++;
                }
                node.prevnode.list = listaux;
            }
            node = node.prevnode;
        }
    }

    /**
     * Esta seria la implementacion del iterador que nos pide
     * @return en cada metodo nos devuelve lo que pedimos, en next el siguiente elemento, en hasNext si tiene siguiente elemento...
     */
    @Override
    public Iterator<String[]> iterator() {

        final int[] counter = {-1};


        return new Iterator<>() {
            @Override
            public boolean hasNext() {
                return list.get(1).size() > counter[0];
            }
            @Override
            public String[] next() {
                counter[0] = counter[0]+1;
                return list.get(1).get(counter[0]);
            }
            @Override
            public void remove() {
                Iterator.super.remove();
            }
            @Override
            public void forEachRemaining(Consumer<? super String[]> action) {
                Iterator.super.forEachRemaining(action);
            }
        };
    }
    /**
     * Este at lo que haria seria sacarnos el valor que se encuentre en una fila y columna especificas con la columna con su nombre de header
     * @param row valor int de la fila
     * @param column valor int de la columna
     * @return valor guardado en esa posicion del dataframe
     *
     */
    @Override
    public String at(int row, String column) {
        int i = 0;
        int column2;
        String column3 = list.get(0).get(0)[i];
        while (!column.equalsIgnoreCase(column3)){
            i++;
            column3 = list.get(0).get(0)[i];
        }
        column2 = i;
        System.out.println("Value: "+ list.get(1).get(row)[column2]);
        return list.get(1).get(row)[column2];
    }
    /**
     * Este at2 nos haria exactamente lo mismo que el at pero sin el print para poderlo usar tanto en las funciones internas como en el visitor
     * sin que nos manche la pantalla
     * @param row valor int de la fila
     * @param column valor int de la columna
     * @return valor guardado en esa posicion del dataframe
     */
    public String at2(int row, String column) {
        int i = 0;
        int column2;
        String column3 = list.get(0).get(0)[i];
        while (!column.equalsIgnoreCase(column3)){
            i++;
            column3 = list.get(0).get(0)[i];
        }
        column2 = i;
        return list.get(1).get(row)[column2];
    }
    /**
     * Este iat lo que no haria seria sacarnos el valor que se encuentre en una fila y columna especificas siendo los 2 numeros
     * @param row valor int de la fila
     * @param column valor int de la columna
     * @return valor guardado en esa posicion del dataframe
     */
    @Override
    public String iat(int row, int column) {
        System.out.println("Value: "+ list.get(1).get(row)[column]);
        return list.get(1).get(row)[column];
    }
    /**
     * Este metodo nos dice la cantidad de columnas que contiene nuestro dataframe y nos lo muestra por pantalla
     * @return Devuelve el numero de columnas
     */
    @Override
    public Integer columns() {
        System.out.println("Columns: "+ list.get(0).get(0).length);
        return list.get(0).get(0).length;
    }
    /**
     * Este metodo nos dice la cantidad de filas que contiene nuestro dataframe y nos lo muestra por pantalla
     * @return Devuelve la cantidad de filas
     */
    @Override
    public Integer size() {
        System.out.println("Rows: "+ list.get(1).size());
        return list.get(1).size();
    }
    /**
     *  Este metodo esta creado manualmente para la ordenación del dataframe respecto a un typeofsort que nos pasan desde el main.
     *  Esta basado en una serie de tablas que nos serviran como auxiliares para comparar si el numero asignado para cada letra es mayor o menor.
     *  En caso de ser la misma letra, miraria las consiguientes letras para determinar cual va primero.
     *  Para el caso de los numeros, simplemente ordena ascendentemente o descendentemente recorriendo el dataframe y ordenando segun corresponda.
     * @param column nombre de columna
     * @param typeofsort nombre del tipo de ordenacion (Letters, intAscending, intDescending)
     * @return Devuelve una tabla
     * @throws IllegalStateException Excepcion
     */
    @Override
    public String[] sort(String column, String typeofsort) throws IllegalStateException {
        String[] nonsorted = new String[list.get(1).size()];
        int[] nonsortedi = new int[list.get(1).size()];
        int[] nonsortedy = new int[list.get(1).size()]; //order
        char[] nonsortedcharcapital = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
        char[] nonsortedcharnoncapital = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
        String[] sorted = new String[list.get(1).size()];
        switch (typeofsort){
            case "Letters":
                int i = 0;
                while(i < list.get(1).size()) {
                    nonsorted[i] = this.at2(i, column);
                    i++;
                }
                i = 0;
                char character;
                while(i<list.get(1).size()){
                    character = this.at2(i, column).charAt(0);
                    if(Character.isUpperCase(character)){
                        nonsortedi[i]=equalsto(nonsortedcharcapital, character);    //asignar valor unico a la letra con equalsto
                    }else{
                        nonsortedi[i]=equalsto(nonsortedcharnoncapital, character); //asignar valor unico a la letra con equalsto
                    }
                    i++;
                }

                i=0;
                int order = 0;
                int nonsortedbig;
                int nonsortedbigint;
                int y;
                while(i<list.get(1).size()){      //ordenar
                    y=0;
                    nonsortedbig = nonsortedi[i];
                    nonsortedbigint = i;
                    while(y<list.get(1).size()){
                        if ((nonsortedbig < nonsortedi[y])&&(nonsortedi[y]!=-1)){ // si es menor el bigger en este caso que el que estamos mirando,
                            nonsortedbigint = y;                                  // se cambiara por el actual. No aceptamos -1 porque quiere decir que esa
                            nonsortedbig = nonsortedi[y];                         // letra ya ha sido sacada de la tabla y por tanto que no necesitamos volverla a meter
                        }
                        if((nonsortedbig == nonsortedi[y])&&(nonsortedi[y]!=-1)){   // si es igual, tendremos que hacer un proceso donde miraremos cada letra
                            int[] word1 = new int[nonsorted[nonsortedbigint].length()]; // de la palabra por separado y extraeremos cual de las 2 tiene una letra mas grande que la otra
                            int[] word2 = new int[nonsorted[y].length()];               // si una vez llegado al final sigue siendo igual, miraremos si tiene 2da parte y sino
                            int length = Math.min(word1.length, word2.length);          // se colocaran de manera aleatoria sin importar el tamaño.
                            int z = 0;
                            while(z < length){
                                char character1 = this.at2(nonsortedbigint, column).charAt(z);
                                char character2 = this.at2(y, column).charAt(z);
                                if(Character.isUpperCase(character1)){
                                    word1[z]=equalsto(nonsortedcharcapital, character1);
                                }else{
                                    word1[z]=equalsto(nonsortedcharnoncapital, character1);
                                }
                                if(Character.isUpperCase(character2)){
                                    word2[z]=equalsto(nonsortedcharcapital, character2);
                                }else{
                                    word2[z]=equalsto(nonsortedcharnoncapital, character2);
                                }
                                z++;
                            }
                            z=0;
                            while (z < length){  //por si la segunda palabra es mas larga que la primera o a la inversa
                                if(word1[z]<word2[z]){
                                    nonsortedbigint = y;
                                    nonsortedbig = nonsortedi[y];
                                    break;
                                }else if(word1[z]>word2[z]){
                                    break;
                                }
                                z++;
                            }
                            if(z == length){            //por si las palabras son iguales
                                nonsortedbigint = y;
                                nonsortedbig = nonsortedi[y];
                            }

                        }
                        y++;
                    }
                    nonsortedy[nonsortedbigint]=order;
                    nonsortedi[nonsortedbigint]=-1;
                    order++;
                    i++;

                }
                i=0;
                int counter =0;
                while(i < list.get(1).size()) {
                    y=0;
                    while(y < list.get(1).size()){
                        if(nonsortedy[y]==counter){
                            sorted[list.get(1).size()-1-i]=nonsorted[y];        //Ordenacion final, en base a un contador que nos ira colocando el 0 en el 0, el
                            break;                                              //1 en el 1 y asi con todos
                        }
                        y++;
                    }
                    i++;
                    counter++;
                }
                i=0;
                while(i < list.get(1).size()) {
                    System.out.print(sorted[i]+"//");
                    i++;
                }
                return sorted;

            case "intDescending":
                int[] nonsortedints = new int[list.get(1).size()];
                int[] sortedints = new int[list.get(1).size()];
                int i2=0;
                while(i2 < list.get(1).size()) {
                    nonsortedints[i2]= Integer.parseInt(this.at2(i2,column).replace(" ",""));
                    i2++;
                }
                i2=0;
                int cont;
                int max;
                int y2;
                while(i2 < list.get(1).size()) {
                    y2=0;
                    max = nonsortedints[i2];
                    cont = i2;
                    while (y2 < list.get(1).size()){
                        if((max<nonsortedints[y2])&&(nonsortedints[y2]!=-1)){           //Aqui al ser numeros hariamos lo mismo que hemos hecho antes
                            max = nonsortedints[y2];                                    //pero ordenándolos sin necesidad de las 2 fases anteriores.
                            cont = y2;
                        }
                        y2++;
                    }
                    sortedints[i2]=nonsortedints[cont];
                    nonsortedints[cont]=-1;
                    i2++;
                }
                i2=0;
                while(i2 < list.get(1).size()) {
                    System.out.print(sortedints[i2]+"//");
                    sorted[i2]=sortedints[i2]+"";
                    i2++;
                }
                return sorted;

            case "intAscending":
                int[] nonsortedints2 = new int[list.get(1).size()];
                int[] sortedints2 = new int[list.get(1).size()];
                int i3=0;
                while(i3 < list.get(1).size()) {
                    nonsortedints2[i3]= Integer.parseInt(this.at2(i3,column).replace(" ",""));
                    i3++;
                }
                i3=0;
                int cont2;
                int min;
                int y3;
                while(i3 < list.get(1).size()) {
                    y3=0;
                    min = nonsortedints2[i3];
                    cont2 = i3;
                    while (y3 < list.get(1).size()){
                        if((min<nonsortedints2[y3])&&(nonsortedints2[y3]!=-1)){      // Y aqui seria exactamente lo mismo que arriba
                            min = nonsortedints2[y3];                                // pero cambiando el sentido de mayor a menor
                            cont2 = y3;
                        }
                        y3++;
                    }
                    sortedints2[list.get(1).size()-1-i3]=nonsortedints2[cont2];
                    nonsortedints2[cont2]=-1;
                    i3++;
                }
                i3=0;
                while(i3 < list.get(1).size()) {
                    System.out.print(sortedints2[i3]+"//");
                    sorted[i3]=sortedints2[i3]+"";
                    i3++;
                }
                return sorted;
            default:
                return null;
        }
    }
    /**
     * Esta query nos extrae los elementos del dataframe que cumplen con la condicion que nos pasan como parametro
     * @param elem Primer elemento a comparar
     * @param condition comparador
     * @param elem2 Segundo elemento a comparar
     * @return Nos devuelve una lista con todos los elementos que cumplen la condition.
     */
    @Override
    public Object query(Object elem, String condition, Object elem2) {
        List<String[]> querylist = new ArrayList<>();
        char[] capital = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
        char[] noncapital = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
        int i=0;
        int y;
        String[] header = new String[list.get(0).get(0).length];
        while(i < list.get(0).get(0).length){
            header[i] = list.get(0).get(0)[i];
            i++;
        }
        switch (condition){
            case "<=":
                i=0;
                int columnquery5;
                while(i<list.get(0).get(0).length){
                    if(elem.equals(list.get(0).get(0)[i])){
                        break;
                    }
                    i++;
                }
                columnquery5 = i;
                i=0;
                while(i < list.get(1).size()){
                    if(Integer.parseInt(list.get(1).get(i)[columnquery5].replace(" ","")) <= Integer.parseInt((String)elem2)){
                        querylist.add(list.get(1).get(i));
                    }
                    i++;
                }
                i=0;
                while (i < querylist.size()){
                    y=0;
                    while(y < header.length){
                        System.out.print(header[y]+": ["+ querylist.get(i)[y] +"], ");
                        y++;
                    }
                    System.out.println();
                    i++;
                }
                return querylist;

            case ">=":
                i=0;
                int columnquery6;
                while(i<list.get(0).get(0).length){
                    if(elem.equals(list.get(0).get(0)[i])){
                        break;
                    }
                    i++;
                }
                columnquery6 = i;
                i=0;
                while(i < list.get(1).size()){
                    if(Integer.parseInt(list.get(1).get(i)[columnquery6].replace(" ","")) <= Integer.parseInt((String)elem2)){
                        querylist.add(list.get(1).get(i));
                    }
                    i++;
                }
                i=0;
                while (i < querylist.size()){
                    y=0;
                    while(y < header.length){
                        System.out.print(header[y]+": ["+ querylist.get(i)[y] +"], ");
                        y++;
                    }
                    System.out.println();
                    i++;
                }
                return querylist;

            case ">":
                i=0;
                int columnquery2;
                while(i<list.get(0).get(0).length){
                    if(elem.equals(list.get(0).get(0)[i])){
                        break;
                    }
                    i++;
                }
                columnquery2 = i;
                i=0;
                while(i < list.get(1).size()){
                    if(Integer.parseInt(list.get(1).get(i)[columnquery2].replace(" ","")) > Integer.parseInt((String)elem2)){
                        querylist.add(list.get(1).get(i));
                    }
                    i++;
                }
                i=0;
                while (i < querylist.size()){
                    y=0;
                    while(y < header.length){
                        System.out.print(header[y]+": ["+ querylist.get(i)[y] +"], ");
                        y++;
                    }
                    System.out.println();
                    i++;
                }
                return querylist;
            case "<":
                i=0;
                int columnquery;
                while(i<list.get(0).get(0).length){
                    if(elem.equals(list.get(0).get(0)[i])){
                        break;
                    }
                    i++;
                }
                columnquery = i;
                i=0;
                while(i < list.get(1).size()){
                    if(Integer.parseInt(list.get(1).get(i)[columnquery].replace(" ","")) < Integer.parseInt((String)elem2)){
                        querylist.add(list.get(1).get(i));
                    }
                    i++;
                }
                i=0;
                while (i < querylist.size()){
                    y=0;
                    while(y < header.length){
                        System.out.print(header[y]+": ["+ querylist.get(i)[y] +"], ");
                        y++;
                    }
                    System.out.println();
                    i++;
                }
                return querylist;

            case "=":
                i=0;
                int columnquery3;
                while(i<list.get(0).get(0).length){
                    if(elem.equals(list.get(0).get(0)[i].replace(" ",""))){
                        break;
                    }
                    i++;
                }
                columnquery3 = i;
                i=0;
                int ok = 0; // 0 is f, 1 is t
                char character = (list.get(1).get(0)[columnquery3].replace(" ","")).charAt(0);
                while(i<capital.length){
                    if ((character == capital[i]) || (character == noncapital[i])) {
                        ok = 1;
                        break;
                    }
                    i++;
                }
                i=0;
                if (ok==1){
                    String word = ((String)elem2).replace(" ","");
                    String word2;
                    while(i < list.get(1).size()){
                        word2 = list.get(1).get(i)[columnquery3].replace(" ","");
                        if(word2.equalsIgnoreCase(word)){
                            querylist.add(list.get(1).get(i));
                        }
                        i++;
                    }
                }else{
                    while(i < list.get(1).size()){
                        if(Integer.parseInt(list.get(1).get(i)[columnquery3].replace(" ","")) == Integer.parseInt(((String)elem2).replace(" ",""))){
                            querylist.add(list.get(1).get(i));
                        }
                        i++;
                    }
                }
                i=0;
                while (i < querylist.size()){
                    y=0;
                    while(y < header.length){
                        System.out.print(header[y]+": ["+ querylist.get(i)[y] +"], ");
                        y++;
                    }
                    System.out.println();
                    i++;
                }
                return querylist;

            case "!=":
                i=0;
                int columnquery4;
                while(i<list.get(0).get(0).length){
                    if(elem.equals(list.get(0).get(0)[i].replace(" ",""))){
                        break;
                    }
                    i++;
                }
                columnquery4 = i;

                i=0;
                int ok2 = 0; // 0 is f, 1 is t
                char character2 = (list.get(1).get(0)[columnquery4].replace(" ","")).charAt(0);
                while(i<capital.length){
                    if ((character2 == capital[i]) || (character2 == noncapital[i])) {
                        ok2 = 1;
                        break;
                    }
                    i++;
                }
                i=0;
                if (ok2==1){
                    String word = ((String)elem2).replace(" ","");
                    String word2;
                    while(i < list.get(1).size()){
                        word2 = list.get(1).get(i)[columnquery4].replace(" ","");
                        if(!word2.equalsIgnoreCase(word)){
                            querylist.add(list.get(1).get(i));
                        }
                        i++;
                    }
                }else{
                    while(i < list.get(1).size()){
                        if(Integer.parseInt(list.get(1).get(i)[columnquery4].replace(" ","")) != Integer.parseInt(((String)elem2).replace(" ",""))){
                            querylist.add(list.get(1).get(i));
                        }
                        i++;
                    }
                }
                i=0;
                while (i < querylist.size()){
                    y=0;
                    while(y < header.length){
                        System.out.print(header[y]+": ["+ querylist.get(i)[y] +"], ");
                        y++;
                    }
                    System.out.println();
                    i++;
                }
                return querylist;

            default:
                return null;
        }

    }


    /**
     * Este metodo asigna un valor unico a cada letra de la tabla segun sea mayuscula o minuscula, representando a su posicion en la tabla.
     * @param table tabla de letras
     * @param character letra a buscar
     * @return numero de letra
     */
    private int equalsto (char[] table, char character) {
        int i=0;
        while (i < table.length){
            if (table[i] == character){
                break;
            }
            i++;
        }
        return i;
    }

    /**
     * getter nombre
     * @return devuelve el nombre
     */
    public String getName() {
        return name;
    }

    /**
     * Setter del siguiente nodo
     * @param node nodo siguiente
     */
    public void setNextnode(DataFrameComposite node) {
        this.nextnode[childs] = node;
        childs++;
    }

    /**
     * Getter de una fila concreta de todo el dataframe
     * @param index indice de fila que queremos extraer
     * @return nos devuelve un string [] con la fila en cuestion
     */
    public String[] getRow(int index){
        String[] aux = new String[this.list.get(0).get(0).length];

        for (int i = 0; this.list.get(0).get(0).length > i; i++){
            aux[i] = this.list.get(1).get(index)[i].replace(" ", "");
        }
        return aux;
    }
    /**
     * Getter de lista
     * @return devolvemos la lista entera
     */
    public List<List<String[]>> getList() {
        return list;
    }
    /**
     * Copia de la lista
     * @return devolvemos la lista copia
     */
    public List<List<String[]>> getListCopy() {
        List<List<String[]>> listAux = new ArrayList<>();
        listAux.add(this.list.get(0));
        listAux.add(new ArrayList<>());
        int y = 0;
        for(String[] ignored : this.list.get(1)){
            listAux.get(1).add(this.list.get(1).get(y));
            y ++;
        }
        return listAux;
    }

    /**
     *
     * @param visitor Visitante
     * @param op operacion a realizar
     * @param Label columna sobre la que quieres realizarla
     *
     */
    @Override
    public void accept(OperationVisitor visitor, String op, String Label)
    {
        visitor.visit(this, op, Label);
    }

}