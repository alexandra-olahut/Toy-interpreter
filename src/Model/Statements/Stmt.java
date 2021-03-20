package Model.Statements;

import Exceptions.ExpException;
import Exceptions.FileException;
import Exceptions.HeapException;
import Exceptions.StmtException;
import Model.ADT.Interfaces.IDict;
import Model.ProgramState.PrgState;
import Model.Types.Type;

public interface Stmt {

    PrgState execute(PrgState state) throws StmtException, ExpException, FileException, HeapException;
    IDict<String,Type> typecheck(IDict<String, Type> typeEnv) throws RuntimeException;
}
