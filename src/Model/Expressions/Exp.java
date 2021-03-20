package Model.Expressions;

import Exceptions.ExpException;
import Exceptions.HeapException;
import Model.ADT.IDict;
import Model.ADT.IHeap;
import Model.Types.Type;
import Model.Values.Value;

public interface Exp {

    Value evaluate(IDict<String,Value> symTable, IHeap hp) throws ExpException, HeapException;
    Type typecheck(IDict<String,Type> typeEnv) throws RuntimeException;
}
