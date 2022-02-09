package Visitor;

import Ficheros.*;

public interface OperationVisitor {
    public Object visit(DataFrameComposite DF1, String Operation, String Label);
}
