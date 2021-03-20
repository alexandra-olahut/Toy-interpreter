package Model.Statements.Files;

import Exceptions.*;
import Model.ADT.Interfaces.IDict;
import Model.ADT.Interfaces.IFileTable;
import Model.Expressions.Exp;
import Model.ProgramState.PrgState;
import Model.Statements.Stmt;
import Model.Types.IntType;
import Model.Types.StringType;
import Model.Types.Type;
import Model.Values.IntValue;
import Model.Values.StringValue;
import Model.Values.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFile implements Stmt {

    private final Exp exp;
    private final String varName;
    public ReadFile(Exp e, String v) {exp=e; varName=v;}


    @Override
    public PrgState execute(PrgState state) throws StmtException, ExpException, FileException, HeapException {

        IDict<String, Value> symTable = state.getSymTable();
        if (!symTable.isDefined(varName))
            throw new StmtException(varName + " is not defined\n");
        if (!symTable.lookup(varName).getType().equals(new IntType()))
            throw new StmtException(varName + " must be of type int\n");

        Value val = exp.evaluate(symTable, state.getHeap());
        if (!val.getType().equals(new StringType()))
            throw new FileException("File path must be a string\n");

        IFileTable fileTable = state.getFileTable();
        StringValue filename = (StringValue)val;
        if(!fileTable.isOpen(filename))
            throw new FileException("The requested file is not open\n");
        BufferedReader fd = fileTable.getFd(filename);

        int readValue;
        try{
            String line = fd.readLine();
            if (line == null)
                readValue = 0;
            else
                readValue = Integer.parseInt(line);
        }
        catch(IOException ex){
            throw new FileException("Couldn't read from file\n");
        }

        symTable.update(varName, new IntValue(readValue));
        return null;
    }

    @Override
    public IDict<String, Type> typecheck(IDict<String, Type> typeEnv) throws RuntimeException {
        Type nameType = exp.typecheck(typeEnv);
        if (!nameType.equals(new StringType()))
            throw new TypeException("File name must be a string\n");

        if (!typeEnv.isDefined(varName))
            throw new TypeException(varName + " is not defined\n");

        Type varType = typeEnv.lookup(varName);
        if (!varType.equals(new IntType()))
            throw new TypeException("From a file you can only read into integer variables\n");

        return typeEnv;
    }

    @Override
    public String toString() {return "ReadFile(" + exp + "," + varName + ")";}
}
