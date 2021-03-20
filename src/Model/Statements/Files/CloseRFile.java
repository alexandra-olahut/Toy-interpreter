package Model.Statements.Files;

import Exceptions.ExpException;
import Exceptions.FileException;
import Exceptions.HeapException;
import Exceptions.TypeException;
import Model.ADT.Interfaces.IDict;
import Model.ADT.Interfaces.IFileTable;
import Model.Expressions.Exp;
import Model.ProgramState.PrgState;
import Model.Statements.Stmt;
import Model.Types.StringType;
import Model.Types.Type;
import Model.Values.StringValue;
import Model.Values.Value;

import java.io.BufferedReader;
import java.io.IOException;


public class CloseRFile implements Stmt {

    private final Exp exp;
    public CloseRFile(Exp e) {exp=e;}


    @Override
    public PrgState execute(PrgState state) throws ExpException, FileException, HeapException {

        IDict<String,Value> symTable = state.getSymTable();
        Value val = exp.evaluate(symTable, state.getHeap());
        if (!val.getType().equals(new StringType()))
            throw new FileException("File path must be a string\n");

        IFileTable fileTable = state.getFileTable();
        StringValue filename = (StringValue)val;

            if (!fileTable.isOpen(filename))
                throw new FileException("The requested file is not open\n");
            BufferedReader fd = fileTable.getFd(filename);

            try {
                fd.close();
            } catch (IOException ex) {
                throw new FileException("File couldn't be closed\n");
            }
            fileTable.removeFile(filename);

        return null;
    }

    @Override
    public IDict<String, Type> typecheck(IDict<String, Type> typeEnv) throws RuntimeException {
        Type varType = exp.typecheck(typeEnv);
        if(!varType.equals(new StringType()))
            throw new TypeException("File name must be a string\n");
        return typeEnv;
    }

    @Override
    public String toString() {return "CloseRFile(" + exp + ")";}
}
