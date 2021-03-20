package Model.Statements.Loops;

import Exceptions.*;
import Model.ADT.Interfaces.IDict;
import Model.ADT.Interfaces.IHeap;
import Model.Expressions.Exp;
import Model.Expressions.RelationalExp;
import Model.Expressions.VarExp;
import Model.ProgramState.PrgState;
import Model.Statements.AssignStmt;
import Model.Statements.CompStmt;
import Model.Statements.Stmt;
import Model.Statements.Loops.WhileStmt;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.Value;

public class ForStmt implements Stmt {

    private final String var;
    private final Exp exp1;
    private final Exp exp2;
    private final Exp exp3;
    private final Stmt stmt;

    public ForStmt(String v, Exp e1, Exp e2, Exp e3, Stmt s) {var=v; exp1=e1; exp2=e2; exp3=e3; stmt=s;}


    @Override
    public PrgState execute(PrgState state) throws StmtException, ExpException, FileException, HeapException {
        IDict<String, Value> symTable = state.getSymTable();
        if(!symTable.isDefined(var))
            throw new StmtException(var+" is not defined\n");
        if(!symTable.lookup(var).getType().equals(new IntType()))
            throw new StmtException(var+" in for stmt must be integer\n");

        IHeap hp = state.getHeap();
        if(!(exp1.evaluate(symTable, hp).getType().equals(new IntType()) &&
                exp2.evaluate(symTable, hp).getType().equals(new IntType()) &&
                exp3.evaluate(symTable, hp).getType().equals(new IntType())))
            throw new StmtException("Variables and expressions in for statement must be integers\n");

        Stmt newStmt = new CompStmt(new AssignStmt(var,exp1),
                new WhileStmt(new RelationalExp("<",new VarExp(var),exp2),
                        new CompStmt(stmt, new AssignStmt(var, exp3))));

        state.getExeStack().push(newStmt);
        return null;
    }

    @Override
    public IDict<String, Type> typecheck(IDict<String, Type> typeEnv) throws RuntimeException {
        if (!typeEnv.isDefined(var))
            throw new TypeException(var + " is not defined\n");

        Type t1 = exp1.typecheck(typeEnv);
        Type t2 = exp2.typecheck(typeEnv);
        Type t3 = exp3.typecheck(typeEnv);
        Type tv = typeEnv.lookup(var);

        if(!(t1.equals(new IntType()) && t2.equals(new IntType()) && t3.equals(new IntType()) && tv.equals(new IntType())))
            throw new TypeException("Variables and expressions in for statement must be integers\n");
        stmt.typecheck(typeEnv);

        return typeEnv;
    }

    @Override
    public String toString() {return "for("+var+"="+exp1+";"+var+"<"+exp2+";"+var+"="+exp3+") {"+stmt+"}";}
}
