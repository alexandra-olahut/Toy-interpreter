package Model.Statements.Latch;

import Exceptions.*;
import Model.ADT.Interfaces.IDict;
import Model.Expressions.Exp;
import Model.ProgramState.PrgState;
import Model.Statements.Stmt;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.IntValue;
import Model.Values.Value;

public class NewLatchStmt implements Stmt {

    private final String var;
    private final Exp exp;

    public NewLatchStmt(String v, Exp e) {var=v; exp=e;}

    @Override
    public PrgState execute(PrgState state) throws StmtException, ExpException, FileException, HeapException {

        Value val = exp.evaluate(state.getSymTable(), state.getHeap());
        if (!val.getType().equals(new IntType()))
            throw new StmtException("Must be integer\n");

        IDict<String, Value> symTable = state.getSymTable();
        if (!symTable.isDefined(var))
            throw new StmtException(var + " is not defined\n");
        if (!symTable.lookup(var).getType().equals(new IntType()))
            throw new StmtException(var + " is not integer\n");

        synchronized (state.getLatchTable()) {
            Integer nr = ((IntValue) val).getValue();
            Integer newFree = state.getLatchTable().getNextFree();
            state.getLatchTable().add(newFree, nr);

            symTable.update(var, new IntValue(newFree));
        }
        return null;
    }

    @Override
    public IDict<String, Type> typecheck(IDict<String, Type> typeEnv) throws RuntimeException {
        if(!typeEnv.lookup(var).equals(new IntType()) || !exp.typecheck(typeEnv).equals(new IntType()))
            throw new TypeException("Latch params must be integers\n");

        return typeEnv;
    }

    @Override
    public String toString() {return "newLatch("+var+","+exp+")";}
}
