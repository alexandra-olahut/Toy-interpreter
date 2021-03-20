package Model.Statements.Semaphore;

import Exceptions.*;
import Model.ADT.Interfaces.IDict;
import Model.ProgramState.PrgState;
import Model.Statements.Stmt;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.IntValue;
import Model.Values.Value;

import java.util.List;

public class ReleaseStmt implements Stmt {

    private final String var;
    public ReleaseStmt(String v) {var=v;}

    @Override
    public PrgState execute(PrgState state) throws StmtException, ExpException, FileException, HeapException {
        IDict<String, Value> st = state.getSymTable();
        if(!st.isDefined(var))
            throw new StmtException(var+" is not defined\n");
        if(!st.lookup(var).getType().equals(new IntType()))
            throw new StmtException(var+" must be int\n");

        Integer foundIndex = ((IntValue)st.lookup(var)).getValue();
        if (!state.getSemaphoreTable().contains(foundIndex))
            throw new StmtException(var+" is not in semaphore table\n");

        synchronized (state.getSemaphoreTable()) {
            Integer N = state.getSemaphoreTable().get(foundIndex).getKey();
            List<Integer> L = state.getSemaphoreTable().get(foundIndex).getValue();

            L.removeIf(e -> e.equals(state.getId()));
        }
        return null;
    }

    @Override
    public IDict<String, Type> typecheck(IDict<String, Type> typeEnv) throws RuntimeException {
        if(!typeEnv.lookup(var).equals(new IntType()))
            throw new TypeException("Release parameter must be int\n");

        return typeEnv;
    }

    @Override
    public String toString() {return "release("+var+")";}
}
