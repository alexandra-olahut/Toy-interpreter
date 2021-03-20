package Model.Statements;

import Exceptions.StmtException;
import Model.ADT.IDict;
import Model.ProgramState.PrgState;
import Model.Types.Type;
import Model.Values.Value;

public class VarDeclStmt implements Stmt {

    private final Type type;
    private final String identifier;
    public VarDeclStmt(String id, Type t) {type=t; identifier=id;}


    @Override
    public PrgState execute(PrgState state) throws StmtException {
        IDict<String, Value> symTable = state.getSymTable();
        if(symTable.isDefined(identifier))
            throw new StmtException("Variable with this name is already defined\n");

        symTable.add(identifier, type.defaultValue());
        return null;
    }

    @Override
    public IDict<String, Type> typecheck(IDict<String, Type> typeEnv) throws RuntimeException {
        typeEnv.add(identifier, type);
        return typeEnv;
    }

    @Override
    public String toString() {
        return type +" "+ identifier;
    }
}
