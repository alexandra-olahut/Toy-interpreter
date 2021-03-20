package Model.Expressions;

import Exceptions.ExpException;
import Exceptions.HeapException;
import Exceptions.TypeException;
import Model.ADT.Interfaces.IDict;
import Model.ADT.Interfaces.IHeap;
import Model.Types.RefType;
import Model.Types.Type;
import Model.Values.RefValue;
import Model.Values.Value;

public class ReadHpExp implements Exp {

    private final Exp exp;
    public ReadHpExp(Exp e) {exp = e;}

    @Override
    public Value evaluate(IDict<String, Value> symTable, IHeap hp) throws ExpException, HeapException {

        Value val = exp.evaluate(symTable, hp);
        if (!(val instanceof RefValue))
            throw new HeapException("Heap reading can only be done from a RefValue\n");

        if (!hp.isAllocated(((RefValue) val).getAddress()))
            throw new HeapException("Referenced address was not found in heap memory\n");

        return hp.getValue(((RefValue) val).getAddress());
    }

    @Override
    public Type typecheck(IDict<String, Type> typeEnv) throws RuntimeException {
        Type type = exp.typecheck(typeEnv);
        if(!(type instanceof RefType))
            throw new TypeException("The argument must be Ref type\n");

        return ((RefType) type).getInner();
    }

    @Override
    public String toString() {return "*(" + exp + ")";}
}
