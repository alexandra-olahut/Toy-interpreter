package Model.Statements.Conditionals;

import Exceptions.*;
import Model.ADT.Interfaces.IDict;
import Model.Expressions.Exp;
import Model.Expressions.RelationalExp;
import Model.ProgramState.PrgState;
import Model.Statements.Stmt;
import Model.Types.Type;

public class SwitchStmt implements Stmt {

    private final Exp exp;
    private final Exp exp1;
    private final Exp exp2;
    private final Stmt stmt1;
    private final Stmt stmt2;
    private final Stmt stmt3;

    public SwitchStmt(Exp e, Exp e1, Exp e2, Stmt s1, Stmt s2, Stmt s3){
        exp=e; exp1=e1; exp2=e2;
        stmt1=s1; stmt2=s2; stmt3=s3;
    }

    @Override
    public PrgState execute(PrgState state) throws StmtException, ExpException, FileException, HeapException {

        Stmt elseIF = new IfStmt(new RelationalExp("==",exp,exp2), stmt2, stmt3);
        Stmt mainIF = new IfStmt(new RelationalExp("==",exp,exp1), stmt1, elseIF);

        state.getExeStack().push(mainIF);

        return null;
    }

    @Override
    public IDict<String, Type> typecheck(IDict<String, Type> typeEnv) throws RuntimeException {
        stmt1.typecheck(typeEnv);
        stmt2.typecheck(typeEnv);
        stmt3.typecheck(typeEnv);

        Type expType = exp.typecheck(typeEnv);
        Type exp1Type = exp1.typecheck(typeEnv);
        Type exp2Type = exp2.typecheck(typeEnv);
        if(!expType.equals(exp1Type) || !expType.equals(exp2Type))
            throw new TypeException("Expressions in switch must have the same type\n");

        return typeEnv;
    }

    @Override
    public String toString() {return "switch("+exp+") (case "+exp1+": "+stmt1+")(case "+exp2+": "+stmt2+")(default: "+stmt3+")";}
}
