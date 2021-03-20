package Model.Statements;

import Model.ADT.IDict;
import Model.ADT.IStack;
import Model.ProgramState.PrgState;
import Model.Types.Type;

public class CompStmt implements Stmt {

    private final Stmt first;
    private final Stmt second;
    public CompStmt(Stmt s1, Stmt s2) {first = s1; second = s2;}

    @Override
    public PrgState execute(PrgState state) {
        IStack<Stmt> exeStack = state.getExeStack();
        exeStack.push(second);
        exeStack.push(first);
        return null;
    }

    @Override
    public IDict<String, Type> typecheck(IDict<String, Type> typeEnv) throws RuntimeException {
        return second.typecheck(first.typecheck(typeEnv));
    }

    @Override
    public String toString() {
        return first + "; " + second;
    }
}
