package Model.ProgramState;

import Model.ADT.*;
import Model.Statements.Stmt;
import Model.Values.Value;

public class PrgState {

    private IStack<Stmt> exeStack;
    private IDict<String, Value> symTable;
    private IList<Value> out;

    private IFileTable fileTable;
    private IHeap heap;

    int id;

    static int currentId = 0;
    public static synchronized int getNextId() {
        currentId++;
        return currentId;
    }
    public int getId() {return id;}

    public PrgState(IStack<Stmt> stack, IDict<String,Value> table, IList<Value> output, IFileTable files, IHeap hp, Stmt program) {
        id = getNextId();

        exeStack = stack;
        symTable = table;
        out = output;
        fileTable = files;
        heap = hp;

        exeStack.push(program);
    }

    public Boolean isNotCompleted() {return !exeStack.isEmpty();}

    public PrgState oneStep() throws Exception{
        if(exeStack.isEmpty())
            throw new Exception("Execution stack is empty\n");
        Stmt currentStmt = exeStack.pop();
        return currentStmt.execute(this);
    }

    public IStack<Stmt> getExeStack() {return exeStack;}
    public IDict<String,Value> getSymTable() {return symTable;}
    public IList<Value> getOut() {return out;}
    public IFileTable getFileTable() {return fileTable;}
    public IHeap getHeap() {return heap;}

    public void setExeStack(IStack<Stmt> newStack) {exeStack = newStack;}
    public void setSymTable(IDict<String,Value> newTable) {symTable = newTable;}
    public void setOut(IList<Value> newOut) {out = newOut;}
    public void setFileTable(IFileTable newft) {fileTable = newft;}
    public void setHeap(IHeap newHp) {heap = newHp;}

    @Override
    public String toString(){

        String result = "   PROGRAM STATE - " + id + "\n";
        result+="Execution stack = " + exeStack + "\n";
        result+="Symbols table = " + symTable + "\n";
        result+="Output = " + out + "\n";
        result+="FileTable = " + fileTable + "\n";
        result+="Heap = " + heap + "\n";
        return result;
    }

    public String toFile(){

        return '\n' + "PROGRAM - " + id + '\n'
                    + exeStack.toFile() + '\n'
                    + symTable.toFile() + '\n'
                    + out.toFile() + '\n'
                    + fileTable.toFile() + '\n'
                    + heap.toFile() + '\n';
    }
}
