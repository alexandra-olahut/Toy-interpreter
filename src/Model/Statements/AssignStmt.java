package Model.Statements;

import Exceptions.ExpException;
import Exceptions.HeapException;
import Exceptions.StmtException;
import Exceptions.TypeException;
import Model.ADT.Interfaces.IDict;
import Model.Expressions.Exp;
import Model.ProgramState.PrgState;
import Model.Types.Type;
import Model.Values.Value;

public class AssignStmt implements Stmt {

    private final String identifier;
    private final Exp expression;
    public AssignStmt(String id, Exp ex) {identifier=id; expression=ex;}

    @Override
    public PrgState execute(PrgState state) throws StmtException, ExpException, HeapException {
        IDict<String, Value> symTable = state.getSymTable();

        if (!symTable.isDefined(identifier))
            throw new StmtException("Variable is not defined\n");
        Value v = expression.evaluate(symTable, state.getHeap());
        if (!v.getType().equals(symTable.lookup(identifier).getType()))
            throw new StmtException("Variable type does not match with value\n");

        symTable.update(identifier, v);
        return null;
    }

    @Override
    public IDict<String, Type> typecheck(IDict<String, Type> typeEnv) throws RuntimeException {
        if (!typeEnv.isDefined(identifier))
            throw new TypeException(identifier + " is not defined\n");

        Type idType = typeEnv.lookup(identifier);
        Type expType = expression.typecheck(typeEnv);

        if(!idType.equals(expType))
            throw new TypeException("Assignment: right hand side and left hand side have different types \n");

        return typeEnv;
    }

    @Override
    public String toString() {
        return identifier + "=" + expression;
    }
}
