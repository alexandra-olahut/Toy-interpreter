package Model.Expressions;

import Exceptions.ExpException;
import Exceptions.HeapException;
import Exceptions.TypeException;
import Model.ADT.IDict;
import Model.ADT.IHeap;
import Model.Types.BoolType;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.BoolValue;
import Model.Values.Value;

public class LogicExp implements Exp {

    private final Exp e1;
    private final Exp e2;
    private final String op;   // op can be "and" or "or"

    public LogicExp(Exp e1, Exp e2, String op) {this.e1=e1; this.e2=e2; this.op=op;}

    @Override
    public Value evaluate(IDict<String, Value> symTable, IHeap hp) throws ExpException, HeapException {
        Value v1 = e1.evaluate(symTable, hp);
        if(! v1.getType().equals(new BoolType()))
            throw new ExpException("Operand 1 is not of type bool\n");
        Value v2 = e2.evaluate(symTable, hp);
        if(! v2.getType().equals(new BoolType()))
            throw new ExpException("Operand 2 is not of type bool\n");

        return switch (op) {
            case "and" -> new BoolValue(((BoolValue) v1).getValue() && ((BoolValue) v2).getValue());
            case "or" -> new BoolValue(((BoolValue) v1).getValue() || ((BoolValue) v2).getValue());
            default -> new BoolValue(true);
        };
    }

    @Override
    public Type typecheck(IDict<String, Type> typeEnv) throws RuntimeException {
        Type type1 = e1.typecheck(typeEnv);
        Type type2 = e2.typecheck(typeEnv);

        if (!type1.equals(new BoolType()))
            throw new TypeException("First operand is not a boolean \n");
        if (!type2.equals(new BoolType()))
            throw new TypeException("Second operand is not a boolean \n");

        return new BoolType();
    }

    @Override
    public String toString(){
        return e1+" "+op+" "+e2;
    }
}
