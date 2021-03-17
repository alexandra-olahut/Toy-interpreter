package Model.Statements;

import Model.ADT.IDict;
import Model.ProgramState.PrgState;
import Model.Types.Type;

public class NopStmt implements Stmt {

    public NopStmt() {}

    @Override
    public PrgState execute(PrgState state) {
        return null;
    }

    @Override
    public IDict<String, Type> typecheck(IDict<String, Type> typeEnv) throws RuntimeException {
        return typeEnv;
    }

    @Override
    public String toString() {return " ";}
}
