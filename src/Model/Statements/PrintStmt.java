package Model.Statements;

import Exceptions.ExpException;
import Exceptions.HeapException;
import Model.ADT.IDict;
import Model.ADT.IList;
import Model.Expressions.Exp;
import Model.ProgramState.PrgState;
import Model.Types.Type;
import Model.Values.Value;

public class PrintStmt implements Stmt {

    private final Exp output;
    public PrintStmt(Exp expression) {output = expression;}

    @Override
    public PrgState execute(PrgState state) throws ExpException, HeapException {
            IList<Value> outputStream = state.getOut();
            IDict<String,Value> symTable = state.getSymTable();

            outputStream.append(output.evaluate(symTable, state.getHeap()));
            return null;
    }

    @Override
    public IDict<String, Type> typecheck(IDict<String, Type> typeEnv) throws RuntimeException {
        output.typecheck(typeEnv);
        return typeEnv;
    }

    @Override
    public String toString() {
        return "print(" + output + ")";
    }
}
