package Model.Statements.Latch;

import Exceptions.*;
import Model.ADT.Interfaces.IDict;
import Model.ADT.Interfaces.ILatchTable;
import Model.ProgramState.PrgState;
import Model.Statements.Stmt;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.IntValue;
import Model.Values.Value;

public class CountDownStmt implements Stmt {

    private final String var;
    public CountDownStmt(String v) {var=v;}

    @Override
    public PrgState execute(PrgState state) throws StmtException, ExpException, FileException, HeapException {
        IDict<String, Value> symTable = state.getSymTable();
        if(!symTable.isDefined(var))
            throw new StmtException(var+" is not defined\n");
        if(!symTable.lookup(var).getType().equals(new IntType()))
            throw new StmtException(var+ " is not integer\n");

        Integer foundIndex = ((IntValue)symTable.lookup(var)).getValue();

        ILatchTable lt = state.getLatchTable();
        if(!lt.contains(foundIndex))
            throw new StmtException(var+ " is not in Latch table\n");

        synchronized (state.getLatchTable()) {
            if (lt.get(foundIndex) > 0) {
                lt.update(foundIndex, lt.get(foundIndex) - 1);
                state.getOut().append(new IntValue(state.getId()));
            } else
                state.getOut().append(new IntValue(state.getId()));
        }
        return null;
    }

    @Override
    public IDict<String, Type> typecheck(IDict<String, Type> typeEnv) throws RuntimeException {
        if(!typeEnv.lookup(var).equals(new IntType()))
            throw new TypeException(var+ " must be integer\n");

        return typeEnv;
    }

    @Override
    public String toString() { return "countDown("+var+")";}
}
