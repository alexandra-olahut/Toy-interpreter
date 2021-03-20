package Model.Expressions;

import Model.ADT.Interfaces.IDict;
import Model.ADT.Interfaces.IHeap;
import Model.Types.Type;
import Model.Values.Value;

public class VarExp implements Exp {

    private final String identifier;

    public VarExp(String id) {identifier = id;}

    @Override
    public Value evaluate(IDict<String, Value> symTable, IHeap hp) {
        return symTable.lookup(identifier);
    }

    @Override
    public Type typecheck(IDict<String,Type> typeEnv) {
        return typeEnv.lookup(identifier);
    }

    @Override
    public String toString() {
        return identifier;
    }
}
