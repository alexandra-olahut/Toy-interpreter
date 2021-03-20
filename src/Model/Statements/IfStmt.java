package Model.Statements;

import Exceptions.ExpException;
import Exceptions.HeapException;
import Exceptions.StmtException;
import Exceptions.TypeException;
import Model.ADT.IDict;
import Model.ADT.IStack;
import Model.Expressions.Exp;
import Model.ProgramState.PrgState;
import Model.Types.BoolType;
import Model.Types.Type;
import Model.Values.BoolValue;
import Model.Values.Value;

public class IfStmt implements Stmt {

    private final Exp condition;
    private final Stmt ifS;
    private final Stmt elseS;
    public IfStmt(Exp c, Stmt i, Stmt e) {condition=c; ifS=i; elseS=e;}

    @Override
    public PrgState execute(PrgState state) throws StmtException, ExpException, HeapException {
        IStack<Stmt> exeStack = state.getExeStack();

        Value cond = condition.evaluate(state.getSymTable(), state.getHeap());
        if (!cond.getType().equals(new BoolType()))
            throw new StmtException("Condition is not of boolean type\n");

        if (((BoolValue) cond).getValue())
            exeStack.push(ifS);
        else
            exeStack.push(elseS);
        return null;
    }

    @Override
    public IDict<String, Type> typecheck(IDict<String, Type> typeEnv) throws RuntimeException {
        Type condType = condition.typecheck(typeEnv);
        if (!condType.equals(new BoolType()))
            throw new TypeException("IF condition must be of boolean type\n");

        ifS.typecheck((IDict<String, Type>) typeEnv.clone());
        elseS.typecheck((IDict<String, Type>) typeEnv.clone());

        return typeEnv;
    }

    @Override
    public String toString() {
        return "if (" + condition + ") then {" + ifS + "} else {" + elseS + "}";
    }
}
