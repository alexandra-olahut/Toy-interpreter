package Model.ProgramState;

import Model.ADT.Interfaces.*;
import Model.Statements.Stmt;
import Model.Values.Value;

public class PrgState {

    private IStack<Stmt> exeStack;
    private IDict<String, Value> symTable;
    private IList<Value> out;

    private IFileTable fileTable;
    private IHeap heap;

    private IBarrierTable barrierTable;
    private ISemaphoreTable semaphoreTable;
    private ILockTable lockTable;
    private ILatchTable latchTable;

    int id;

    static int currentId = 0;
    public static synchronized int getNextId() {
        currentId++;
        return currentId;
    }
    public int getId() {return id;}

    public PrgState(IStack<Stmt> stack, IDict<String,Value> table, IList<Value> output, IFileTable files, IHeap hp, IBarrierTable bt, ISemaphoreTable st, ILockTable lockt, ILatchTable latcht, Stmt program) {
        id = getNextId();

        exeStack = stack;
        symTable = table;
        out = output;
        fileTable = files;
        heap = hp;

        barrierTable = bt;
        semaphoreTable = st;
        lockTable = lockt;
        latchTable = latcht;

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
    public IBarrierTable getBarrierTable() {return barrierTable;}
    public ISemaphoreTable getSemaphoreTable() {return semaphoreTable;}
    public ILockTable getLockTable() {return lockTable;}
    public ILatchTable getLatchTable() {return latchTable;}

    public void setExeStack(IStack<Stmt> newStack) {exeStack = newStack;}
    public void setSymTable(IDict<String,Value> newTable) {symTable = newTable;}
    public void setOut(IList<Value> newOut) {out = newOut;}
    public void setFileTable(IFileTable newft) {fileTable = newft;}
    public void setHeap(IHeap newHp) {heap = newHp;}
    public void setBarrierTable(IBarrierTable newBt) {barrierTable = newBt;}
    public void setSemaphoreTable(ISemaphoreTable newSt) {semaphoreTable = newSt;}
    public void setLockTable(ILockTable lt) {lockTable = lt;}
    public void setLatchTable(ILatchTable lt) {latchTable = lt;}

    @Override
    public String toString(){

        String result = "   PROGRAM STATE - " + id + "\n";
        result+="Execution stack = " + exeStack + "\n";
        result+="Symbols table = " + symTable + "\n";
        result+="Output = " + out + "\n";
        result+="FileTable = " + fileTable + "\n";
        result+="Heap = " + heap + "\n";
        result+="Barrier table = " + barrierTable + "\n";
        result+="Semaphore table = " + semaphoreTable + "\n";
        result+="Lock table = " + lockTable + "\n";
        result+="Latch table = " + latchTable + "\n";
        return result;
    }

    public String toFile(){

        return '\n' + "PROGRAM - " + id + '\n'
                    + exeStack.toFile() + '\n'
                    + symTable.toFile() + '\n'
                    + out.toFile() + '\n'
                    + fileTable.toFile() + '\n'
                    + heap.toFile() + '\n'
                    + barrierTable.toFile() + '\n'
                    + semaphoreTable.toFile() + '\n'
                    + lockTable.toFile() + '\n'
                    + latchTable.toFile() + '\n';
    }
}
