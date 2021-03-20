package Model.Statements;

import Model.ADT.IDict;
import Model.ADT.MyStack;
import Model.ProgramState.PrgState;
import Model.Types.Type;

public class ForkStmt implements Stmt {

    private final Stmt stmt;
    public ForkStmt(Stmt s) {stmt=s;}

    @Override
    public PrgState execute(PrgState state) {

        return new PrgState(new MyStack<>(),
                state.getSymTable().copy(),
                state.getOut(),
                state.getFileTable(),
                state.getHeap(),
                stmt);
    }

    @Override
    public IDict<String, Type> typecheck(IDict<String, Type> typeEnv) throws RuntimeException {
        stmt.typecheck((IDict<String,Type>) typeEnv.clone());
        return typeEnv;
    }

    @Override
    public String toString() {return "fork(" + stmt + ")";}
}
