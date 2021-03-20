package Model.Statements.Lock;

import Exceptions.*;
import Model.ADT.Interfaces.IDict;
import Model.ProgramState.PrgState;
import Model.Statements.Stmt;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.IntValue;
import Model.Values.Value;

public class NewLockStmt implements Stmt {

    private final String var;
    public NewLockStmt(String v) {var=v;}

    @Override
    public PrgState execute(PrgState state) throws StmtException, ExpException, FileException, HeapException {

        int newFree = state.getLockTable().getNextFree();
        state.getLockTable().add(newFree, -1);

        IDict<String, Value> symTable = state.getSymTable();
        if(!symTable.isDefined(var))
            throw new StmtException(var+" is not defined\n");
        if(!symTable.lookup(var).getType().equals(new IntType()))
            throw new StmtException(var+ " is not integer\n");

        symTable.update(var, new IntValue(newFree));

        return null;
    }

    @Override
    public IDict<String, Type> typecheck(IDict<String, Type> typeEnv) throws RuntimeException {
        if(!typeEnv.lookup(var).equals(new IntType()))
            throw new TypeException(var+ " must be integer\n");
        return typeEnv;
    }

    @Override
    public String toString(){return "newLock("+var+")";}
}
