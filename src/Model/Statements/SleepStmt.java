package Model.Statements;

import Exceptions.ExpException;
import Exceptions.FileException;
import Exceptions.HeapException;
import Exceptions.StmtException;
import Model.ADT.Interfaces.IDict;
import Model.ProgramState.PrgState;
import Model.Types.Type;

public class SleepStmt implements Stmt {

    private final int number;

    public SleepStmt(int n) {number=n;}

    @Override
    public PrgState execute(PrgState state) throws StmtException, ExpException, FileException, HeapException {
        if(number!=0)
            state.getExeStack().push(new SleepStmt(number-1));

        return null;
    }

    @Override
    public IDict<String, Type> typecheck(IDict<String, Type> typeEnv) throws RuntimeException {
        return typeEnv;
    }

    @Override
    public String toString() {return "sleep("+number+")";}
}
