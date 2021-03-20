package Model.Statements.Semaphore;

import Exceptions.ExpException;
import Exceptions.FileException;
import Exceptions.HeapException;
import Exceptions.StmtException;
import Model.ADT.Interfaces.IDict;
import Model.Expressions.Exp;
import Model.ProgramState.PrgState;
import Model.Statements.Stmt;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.IntValue;
import Model.Values.Value;
import javafx.util.Pair;

import java.util.ArrayList;

public class CreateSemaphoreStmt implements Stmt {

    private final String var;
    private final Exp exp;

    public CreateSemaphoreStmt(String v, Exp e) {var=v; exp=e;}

    @Override
    public PrgState execute(PrgState state) throws StmtException, ExpException, FileException, HeapException {
        IDict<String, Value> symTable = state.getSymTable();
        if(!symTable.isDefined(var))
            throw new StmtException(var+" is not defined\n");
        if(!symTable.lookup(var).getType().equals(new IntType()))
            throw new StmtException(var+" must be int\n");

        Value val = exp.evaluate(state.getSymTable(), state.getHeap());
        if (!val.getType().equals(new IntType()))
            throw new StmtException(exp+" must be integer\n");

        synchronized (state.getSemaphoreTable()) {
            Integer number = ((IntValue) val).getValue();
            Integer newFreeLocation = state.getSemaphoreTable().getNextFree();
            state.getSemaphoreTable().add(newFreeLocation, new Pair<>(number, new ArrayList<>()));

            symTable.update(var, new IntValue(newFreeLocation));
        }
        return null;
    }

    @Override
    public IDict<String, Type> typecheck(IDict<String, Type> typeEnv) throws RuntimeException {
        return typeEnv;
    }

    @Override
    public String toString() {return "createSemaphore("+var+","+exp+")";}
}
