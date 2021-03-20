package Model.Statements.Loops;

import Exceptions.ExpException;
import Exceptions.FileException;
import Exceptions.HeapException;
import Exceptions.StmtException;
import Model.ADT.Interfaces.IDict;
import Model.Expressions.Exp;
import Model.Expressions.NotExp;
import Model.Expressions.RelationalExp;
import Model.Expressions.ValueExp;
import Model.ProgramState.PrgState;
import Model.Statements.CompStmt;
import Model.Statements.Stmt;
import Model.Types.Type;
import Model.Values.BoolValue;

public class RepeatUntilStmt implements Stmt {

    private final Stmt stmt1;
    private final Exp exp2;

    public RepeatUntilStmt(Stmt s, Exp e) {stmt1=s; exp2=e;}

    @Override
    public PrgState execute(PrgState state) throws StmtException, ExpException, FileException, HeapException {
        Stmt newStmt = new CompStmt(stmt1, new WhileStmt(new NotExp(exp2), stmt1));
        state.getExeStack().push(newStmt);
        return null;
    }

    @Override
    public IDict<String, Type> typecheck(IDict<String, Type> typeEnv) throws RuntimeException {
        stmt1.typecheck(typeEnv);
        return typeEnv;
    }

    @Override
    public String toString() {return "repeat {"+stmt1+"} until ("+exp2+")";}
}
