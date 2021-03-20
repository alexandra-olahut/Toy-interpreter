package Model.Expressions;

import Model.ADT.IDict;
import Model.ADT.IHeap;
import Model.Types.Type;
import Model.Values.Value;

public class ValueExp implements Exp {

    private final Value value;

    public ValueExp(Value v) {value = v;}

    @Override
    public Value evaluate(IDict<String, Value> symTable, IHeap hp) {
        return value;
    }

    @Override
    public Type typecheck(IDict<String,Type> typeEnv) {
        return value.getType();
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
