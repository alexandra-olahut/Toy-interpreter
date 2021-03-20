package Model.Statements.Barrier;

import Exceptions.ExpException;
import Exceptions.FileException;
import Exceptions.HeapException;
import Exceptions.StmtException;
import Model.ADT.Interfaces.IBarrierTable;
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

public class NewBarrierStmt implements Stmt {

    private final String var;
    private final Exp exp;

    public NewBarrierStmt(String v, Exp e) {var=v; exp=e;}

    @Override
    public PrgState execute(PrgState state) throws StmtException, ExpException, FileException, HeapException {

        Value number = exp.evaluate(state.getSymTable(), state.getHeap());
        if(!number.getType().equals(new IntType()))
            throw new StmtException("Second parameter of newBarrier must be integer\n");

        IBarrierTable bt = state.getBarrierTable();
        int newFreeLocation = bt.getNextFree();
        int nr = ((IntValue)number).getValue();

        bt.add(newFreeLocation, new Pair<>(nr, new ArrayList<>()));

        IDict<String, Value> symTable = state.getSymTable();
        if(symTable.isDefined(var))
            symTable.update(var, new IntValue(newFreeLocation));
        else
            symTable.add(var, new IntValue(newFreeLocation));

        return null;
    }

    @Override
    public IDict<String, Type> typecheck(IDict<String, Type> typeEnv) throws RuntimeException {

        return typeEnv;
    }

    @Override
    public String toString() {return "newBarrier("+var+","+exp+")";}
}
