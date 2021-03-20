package Model.Statements.Lock;

import Exceptions.*;
import Model.ADT.Interfaces.IDict;
import Model.ADT.Interfaces.ILockTable;
import Model.ProgramState.PrgState;
import Model.Statements.Stmt;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.IntValue;
import Model.Values.Value;

public class LockStmt implements Stmt {

    private final String var;
    public LockStmt(String v) {var=v;}

    @Override
    public PrgState execute(PrgState state) throws StmtException, ExpException, FileException, HeapException {

        IDict<String, Value> symTable = state.getSymTable();
        if(!symTable.isDefined(var))
            throw new StmtException(var+" is not defined\n");
        if(!symTable.lookup(var).getType().equals(new IntType()))
            throw new StmtException(var+ " is not integer\n");

        ILockTable lt = state.getLockTable();
        Integer foundIndex = ((IntValue)symTable.lookup(var)).getValue();
        if (!lt.contains(foundIndex))
            throw new StmtException(var+" is not in lock table\n");

        synchronized (state.getLockTable()) {
            if (lt.get(foundIndex) == -1)
                lt.update(foundIndex, state.getId());
            else
                state.getExeStack().push(this);
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
    public String toString(){return "lock("+var+")";}
}
