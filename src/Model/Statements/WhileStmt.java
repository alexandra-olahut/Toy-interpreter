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

public class WhileStmt implements Stmt {

    private final Exp condition;
    private final Stmt doStmt;
    public WhileStmt(Exp c, Stmt d) {condition=c; doStmt=d;}

    @Override
    public PrgState execute(PrgState state) throws StmtException, ExpException, HeapException {

        IStack<Stmt> exeStack = state.getExeStack();

        Value cond = condition.evaluate(state.getSymTable(), state.getHeap());
        if(!cond.getType().equals(new BoolType()))
            throw new StmtException("Condition is not of type bool\n");

        boolean isTrue = ((BoolValue)cond).getValue();

        if (isTrue){
            exeStack.push(this);
            exeStack.push(doStmt);
        }

        return null;
    }

    @Override
    public IDict<String, Type> typecheck(IDict<String, Type> typeEnv) throws RuntimeException {
        Type condType = condition.typecheck(typeEnv);
        if(!condType.equals(new BoolType()))
            throw new TypeException("While condition must be of type boolean\n");

        doStmt.typecheck((IDict<String, Type>) typeEnv.clone());
        return typeEnv;
    }

    @Override
    public String toString() {return "while (" + condition + ") {" + doStmt + "}";}
}
