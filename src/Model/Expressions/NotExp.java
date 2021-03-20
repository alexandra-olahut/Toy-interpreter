package Model.Expressions;

import Exceptions.ExpException;
import Exceptions.HeapException;
import Exceptions.TypeException;
import Model.ADT.IDict;
import Model.ADT.IHeap;
import Model.Types.BoolType;
import Model.Types.Type;
import Model.Values.BoolValue;
import Model.Values.Value;

public class NotExp implements Exp {

    private final Exp exp;

    public NotExp(Exp e) {exp=e;}

    @Override
    public Value evaluate(IDict<String, Value> symTable, IHeap hp) throws ExpException, HeapException {

        if (!exp.evaluate(symTable, hp).getType().equals(new BoolType()))
            throw new ExpException("NOT operand must be boolean\n");

        BoolValue v = (BoolValue)exp.evaluate(symTable, hp);
        return new BoolValue(!v.getValue());
    }

    @Override
    public Type typecheck(IDict<String, Type> typeEnv) throws RuntimeException {
        if (!exp.typecheck(typeEnv).equals(new BoolType()))
            throw new TypeException("NOT operand must be boolean\n");

        return new BoolType();
    }

    @Override
    public String toString() {return "!"+exp;}
}
