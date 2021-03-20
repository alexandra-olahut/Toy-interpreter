package Model.Expressions;

import Exceptions.ExpException;
import Exceptions.HeapException;
import Exceptions.TypeException;
import Model.ADT.Interfaces.IDict;
import Model.ADT.Interfaces.IHeap;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.Value;

public class MULExp implements Exp {

    private final Exp exp1;
    private final Exp exp2;

    public MULExp(Exp e1, Exp e2) {exp1=e1; exp2=e2;}


    @Override
    public Value evaluate(IDict<String, Value> symTable, IHeap hp) throws ExpException, HeapException {
        Type t1 = exp1.evaluate(symTable, hp).getType();
        Type t2 = exp2.evaluate(symTable, hp).getType();
        if(!t1.equals(new IntType()) || !t2.equals(new IntType()))
            throw new TypeException("Mul takes only integer operators\n");


        Exp first = new ArithExp('*', exp1, exp2);
        Exp second = new ArithExp('+', exp1, exp2);
        Exp fin = new ArithExp('-', first, second);

        return fin.evaluate(symTable, hp);
    }

    @Override
    public Type typecheck(IDict<String, Type> typeEnv) throws RuntimeException {
        Type t1 = exp1.typecheck(typeEnv);
        Type t2 = exp2.typecheck(typeEnv);

        if(!t1.equals(new IntType()) || !t2.equals(new IntType()))
            throw new TypeException("Mul takes integer operators\n");

        return new IntType();
    }

    @Override
    public String toString() {return "MUL("+exp1+","+exp2+")";}
}
