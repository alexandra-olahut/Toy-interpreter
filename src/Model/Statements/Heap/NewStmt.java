package Model.Statements.Heap;

import Exceptions.ExpException;
import Exceptions.HeapException;
import Exceptions.StmtException;
import Exceptions.TypeException;
import Model.ADT.Interfaces.IDict;
import Model.ADT.Interfaces.IHeap;
import Model.Expressions.Exp;
import Model.ProgramState.PrgState;
import Model.Statements.Stmt;
import Model.Types.RefType;
import Model.Types.Type;
import Model.Values.RefValue;
import Model.Values.Value;

public class NewStmt implements Stmt {

    private final Exp exp;
    private final String varname;
    public NewStmt(String v, Exp e) {exp=e; varname=v;}


    @Override
    public PrgState execute(PrgState state) throws StmtException, ExpException, HeapException {

        IDict<String, Value> symTable = state.getSymTable();
        if (!symTable.isDefined(varname))
            throw new StmtException(varname + " is not defined\n");
        if (!(symTable.lookup(varname).getType() instanceof RefType))
            throw new StmtException(varname + " must be of reference type\n");
        RefValue refVar = (RefValue) symTable.lookup(varname);

        Value val = exp.evaluate(symTable, state.getHeap());
        if (! val.getType().equals(refVar.getLocationType()))
            throw new HeapException("Type of expression and type "+varname+" references to don't match\n");

        IHeap hp = state.getHeap();
        int address = hp.add(val);

        symTable.update(varname, new RefValue(address, refVar.getLocationType()));

        return null;
    }

    @Override
    public IDict<String, Type> typecheck(IDict<String, Type> typeEnv) throws RuntimeException {
        if (!typeEnv.isDefined(varname))
            throw new TypeException(varname + " is not defined\n");

        Type varType = typeEnv.lookup(varname);
        Type expType = exp.typecheck(typeEnv);

        if (!varType.equals(new RefType(expType)))
            throw  new TypeException("NEW stmt: right hand side and left hand side have different types \n");
        return typeEnv;
    }

    @Override
    public String toString() {return varname +" = new("+ exp + ")";}
}
