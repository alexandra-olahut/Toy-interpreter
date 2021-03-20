package Model.Statements;

import Exceptions.ExpException;
import Exceptions.HeapException;
import Exceptions.StmtException;
import Exceptions.TypeException;
import Model.ADT.IDict;
import Model.ADT.IHeap;
import Model.Expressions.Exp;
import Model.ProgramState.PrgState;
import Model.Types.RefType;
import Model.Types.Type;
import Model.Values.RefValue;
import Model.Values.Value;

public class WriteHpStmt implements Stmt {

    private final String varname;
    private final Exp exp;
    public WriteHpStmt(String v, Exp e) {varname=v; exp=e;}

    @Override
    public PrgState execute(PrgState state) throws StmtException, ExpException, HeapException {

        IDict<String, Value> symTable = state.getSymTable();
        IHeap hp = state.getHeap();

        if(!symTable.isDefined(varname))
            throw new StmtException(varname + " is not defined\n");
        Value var = symTable.lookup(varname);
        if (!(var.getType() instanceof RefType))
            throw new HeapException(varname + " must be of Ref type\n");

        RefValue refVar = (RefValue)var;
        int address = refVar.getAddress();
        if (!hp.isAllocated(address))
            throw new HeapException("Referenced address was not found in heap memory\n");

        Value val = exp.evaluate(symTable, hp);
        if (!val.getType().equals(refVar.getLocationType()))
            throw new HeapException("Type of expression and type "+varname+" references to don't match\n");

        hp.set(address, val);

        return null;
    }

    @Override
    public IDict<String, Type> typecheck(IDict<String, Type> typeEnv) throws RuntimeException {
        if (!typeEnv.isDefined(varname))
            throw new TypeException(varname + " is not defined\n");

        Type varType = typeEnv.lookup(varname);
        Type expType = exp.typecheck(typeEnv);

        if (!varType.equals(new RefType(expType)))
            throw  new TypeException("WHeap stmt: right hand side and left hand side have different types \n");
        return typeEnv;
    }

    @Override
    public String toString() {return "*" + varname + " = " + exp;}
}
