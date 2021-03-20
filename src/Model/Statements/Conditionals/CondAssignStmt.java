package Model.Statements.Conditionals;

import Exceptions.*;
import Model.ADT.Interfaces.IDict;
import Model.Expressions.Exp;
import Model.ProgramState.PrgState;
import Model.Statements.AssignStmt;
import Model.Statements.Stmt;
import Model.Types.BoolType;
import Model.Types.Type;

public class CondAssignStmt implements Stmt {

    private final String v;
    private final Exp exp1;
    private final Exp exp2;
    private final Exp exp3;

    public CondAssignStmt(String v, Exp e1, Exp e2, Exp e3) {this.v=v; exp1=e1; exp2=e2; exp3=e3;}

    @Override
    public PrgState execute(PrgState state) throws StmtException, ExpException, FileException, HeapException {

        if(!exp1.evaluate(state.getSymTable(), state.getHeap()).getType().equals(new BoolType()))
            throw new StmtException("Condition must be boolean\n");
        if(!state.getSymTable().isDefined(v))
            throw new StmtException(v+" is not defined\n");

        Type tv = state.getSymTable().lookup(v).getType();
        Type texp2 = exp2.evaluate(state.getSymTable(), state.getHeap()).getType();
        Type texp3 = exp3.evaluate(state.getSymTable(), state.getHeap()).getType();
        if(!tv.equals(texp2) || !tv.equals(texp3))
            throw new TypeException("The variable and the 2 expressions must have the same type\n");

        Stmt ifStmt = new IfStmt(exp1, new AssignStmt(v, exp2), new AssignStmt(v, exp3));
        state.getExeStack().push(ifStmt);

        return null;
    }

    @Override
    public IDict<String, Type> typecheck(IDict<String, Type> typeEnv) throws RuntimeException {
        if (!exp1.typecheck(typeEnv).equals(new BoolType()))
            throw new TypeException("Condition must be a boolean expression\n");
        Type tv = typeEnv.lookup(v);
        Type texp2 = exp2.typecheck(typeEnv);
        Type texp3 = exp3.typecheck(typeEnv);

        if(!tv.equals(texp2) || !tv.equals(texp3))
            throw new TypeException("The variable and the 2 expressions must have the same type\n");

        return typeEnv;
    }

    @Override
    public String toString() {return v+"="+exp1+"?"+exp2+":"+exp3;}
}
