package Model.Statements;

import Exceptions.ExpException;
import Exceptions.FileException;
import Exceptions.HeapException;
import Exceptions.TypeException;
import Model.ADT.IDict;
import Model.ADT.IFileTable;
import Model.Expressions.Exp;
import Model.ProgramState.PrgState;
import Model.Types.StringType;
import Model.Types.Type;
import Model.Values.StringValue;
import Model.Values.Value;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class OpenRFile implements Stmt {

    private final Exp exp;
    public OpenRFile(Exp e) {exp = e;}


    @Override
    public PrgState execute(PrgState state) throws ExpException, FileException, HeapException {

        IDict<String, Value> symTable = state.getSymTable();
        Value val = exp.evaluate(symTable, state.getHeap());
        if (!val.getType().equals(new StringType()))
            throw new FileException("File path must be a string\n");

        StringValue filename = (StringValue)val;
        IFileTable fileTable = state.getFileTable();

        if (fileTable.isOpen(filename))
            throw new FileException("File is already open\n");

        try{
            BufferedReader file = new BufferedReader(new FileReader(filename.getValue()));
            fileTable.addFile(filename, file);
        }
        catch(IOException ex){
            throw new FileException("File couldn't be opened\n");
        }
        return null;
    }

    @Override
    public IDict<String, Type> typecheck(IDict<String, Type> typeEnv) throws RuntimeException {
        Type varType = exp.typecheck(typeEnv);
        if (!varType.equals(new StringType()))
            throw new TypeException("File name must be a string\n");
        return typeEnv;
    }

    @Override
    public String toString() { return "openRFile(" + exp + ")"; }
}
