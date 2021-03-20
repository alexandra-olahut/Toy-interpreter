package Model.Statements.Barrier;

import Exceptions.ExpException;
import Exceptions.FileException;
import Exceptions.HeapException;
import Exceptions.StmtException;
import Model.ADT.Interfaces.IBarrierTable;
import Model.ADT.Interfaces.IDict;
import Model.ProgramState.PrgState;
import Model.Statements.Stmt;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.IntValue;
import Model.Values.Value;
import javafx.util.Pair;

import java.util.*;


public class AwaitStmt implements Stmt {

    private final String var;
    public AwaitStmt(String v) {var=v;}

    @Override
    public PrgState execute(PrgState state) throws StmtException, ExpException, FileException, HeapException {

        IBarrierTable bt = state.getBarrierTable();
        IDict<String, Value> symTable = state.getSymTable();
        if(!symTable.isDefined(var))
            throw new StmtException(var+" is not defined\n");
        Value val = symTable.lookup(var);
        if (!val.getType().equals(new IntType()))
            throw new StmtException(var+" must be integer\n");

        Integer foundIndex = ((IntValue) val).getValue();
        if(!bt.contains(foundIndex))
            throw new StmtException(var+" is not in barrier table\n");

        Pair<Integer, List<Integer>> foundEntry = bt.get(foundIndex);
        int length = foundEntry.getValue().size();
        int n1 = foundEntry.getKey();
        List<Integer> list = foundEntry.getValue();

        if(n1>length){
            if (list.contains(state.getId()))
                state.getExeStack().push(new AwaitStmt(var));
            else {
                list.add(state.getId());
                state.getExeStack().push(new AwaitStmt(var));
            }
        }
        return null;
    }

    @Override
    public IDict<String, Type> typecheck(IDict<String, Type> typeEnv) throws RuntimeException {
        return typeEnv;
    }

    @Override
    public String toString() {return "await("+var+")";}
}
