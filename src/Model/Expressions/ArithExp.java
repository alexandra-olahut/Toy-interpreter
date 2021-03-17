package Model.Expressions;

import Exceptions.ExpException;
import Exceptions.HeapException;
import Exceptions.TypeException;
import Model.ADT.IDict;
import Model.ADT.IHeap;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.IntValue;
import Model.Values.Value;

public class ArithExp implements Exp {

    private final Exp e1;
    private final Exp e2;
    private final char op; // op can be + - * /

    public ArithExp(char op, Exp e1, Exp e2) {this.e1=e1; this.e2=e2; this.op=op;}

    @Override
    public Value evaluate(IDict<String, Value> symTable, IHeap hp) throws ExpException, HeapException {
        Value v1 = e1.evaluate(symTable, hp);
        if (!v1.getType().equals(new IntType()))
            throw new ExpException("Operand 1 is not of type int\n");
        Value v2 = e2.evaluate(symTable, hp);
        if (!v2.getType().equals(new IntType()))
            throw new ExpException("Operand 2 is not of type int\n");

        IntValue result = new IntValue(0);
        switch (op) {
            case '+' -> result = new IntValue(((IntValue) v1).getValue() + ((IntValue) v2).getValue());
            case '-' -> result = new IntValue(((IntValue) v1).getValue() - ((IntValue) v2).getValue());
            case '*' -> result = new IntValue(((IntValue) v1).getValue() * ((IntValue) v2).getValue());
            case '/' -> {
                if (((IntValue) v2).getValue() == 0)
                    throw new ExpException("Division by 0\n");
                result = new IntValue(((IntValue) v1).getValue() / ((IntValue) v2).getValue());
            }
        }
        return result;
    }

    @Override
    public Type typecheck(IDict<String, Type> typeEnv) throws RuntimeException {
        Type type1 = e1.typecheck(typeEnv);
        Type type2 = e2.typecheck(typeEnv);

        if (!type1.equals(new IntType()))
            throw new TypeException("First operand is not an integer \n");
        if (!type2.equals(new IntType()))
            throw new TypeException("Second operand is not an integer \n");

        return new IntType();
    }

    @Override
    public String toString(){ return "("+e1+" "+op+" "+e2+")"; }
}
