package Ficheros;

import Visitor.OperationVisitor;

public interface DataFrameInterface<T> {
    //T loadData() throws IOException;
    T at(int row, String column);
    T iat(int row2, int column2);
    T columns();
    T size();
    T sort(String column, String typeofsort) throws IllegalStateException;
    T query (T elem, String condition, T elem2);
    public void accept(OperationVisitor visitor,String op, String Label);
}