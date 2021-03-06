package Main;

import Controller.Controller;
import Model.ADT.*;
import Model.Expressions.*;
import Model.ProgramState.PrgState;
import Model.Statements.*;
import Model.Statements.Conditionals.IfStmt;
import Model.Statements.Files.CloseRFile;
import Model.Statements.Files.OpenRFile;
import Model.Statements.Files.ReadFile;
import Model.Statements.Heap.NewStmt;
import Model.Statements.Heap.WriteHpStmt;
import Model.Statements.Loops.WhileStmt;
import Model.Types.*;
import Model.Values.*;
import Repository.*;
import View.*;

public class Interpreter {

    public static void main(String[] args){

        /// Examples from lab pdf
        Stmt s1 = ex1();
        PrgState prg1 = new PrgState(new MyStack<>(), new MyDict<>(), new MyList<>(), new FileTable(), new Heap(), new BarrierTable(), new SemaphoreTable(), new LockTable(), new LatchTable(), s1);
        IRepository repo1 = new Repository("log1.txt", prg1);
        Controller ctr1 = new Controller(repo1);

        Stmt s2 = ex2();
        PrgState prg2 = new PrgState(new MyStack<>(), new MyDict<>(), new MyList<>(), new FileTable(), new Heap(), new BarrierTable(), new SemaphoreTable(), new LockTable(), new LatchTable(), s2);
        IRepository repo2 = new Repository("log2.txt", prg2);
        Controller ctr2 = new Controller(repo2);

        Stmt s3 = ex3();
        PrgState prg3 = new PrgState(new MyStack<>(), new MyDict<>(), new MyList<>(), new FileTable(), new Heap(), new BarrierTable(), new SemaphoreTable(), new LockTable(), new LatchTable(), s3);
        IRepository repo3 = new Repository("log3.txt", prg3);
        Controller ctr3 = new Controller(repo3);

        /// Example with files
        Stmt files = fileTest();
        PrgState prg4 = new PrgState(new MyStack<>(), new MyDict<>(), new MyList<>(), new FileTable(), new Heap(), new BarrierTable(), new SemaphoreTable(), new LockTable(), new LatchTable(), files);
        IRepository repo4 = new Repository("log4.txt", prg4);
        Controller ctr4 = new Controller(repo4);

        /// Examples with exceptions
        Stmt ex1 = exAlreadyDefinedException();
        PrgState prg5 = new PrgState(new MyStack<>(), new MyDict<>(), new MyList<>(), new FileTable(), new Heap(), new BarrierTable(), new SemaphoreTable(), new LockTable(), new LatchTable(), ex1);
        IRepository repo5 = new Repository("ExceptionAlreadyDefined.txt", prg5);
        Controller ctr5 = new Controller(repo5);

        Stmt ex2 = exAssignException();
        PrgState prg6 = new PrgState(new MyStack<>(), new MyDict<>(), new MyList<>(), new FileTable(), new Heap(), new BarrierTable(), new SemaphoreTable(), new LockTable(), new LatchTable(), ex2);
        IRepository repo6 = new Repository("ExceptionAssign.txt", prg6);
        Controller ctr6 = new Controller(repo6);

        Stmt ex3 = exUndefinedException();
        PrgState prg7 = new PrgState(new MyStack<>(), new MyDict<>(), new MyList<>(), new FileTable(), new Heap(), new BarrierTable(), new SemaphoreTable(), new LockTable(), new LatchTable(), ex3);
        IRepository repo7 = new Repository("ExceptionUndefined.txt", prg7);
        Controller ctr7 = new Controller(repo7);

        Stmt ex4 = exDivisionByZero();
        PrgState prg8 = new PrgState(new MyStack<>(), new MyDict<>(), new MyList<>(), new FileTable(), new Heap(), new BarrierTable(), new SemaphoreTable(), new LockTable(), new LatchTable(), ex4);
        IRepository repo8 = new Repository("ExeptionDiv0.txt", prg8);
        Controller ctr8 = new Controller(repo8);

        Stmt ex5 = exIfException();
        PrgState prg9 = new PrgState(new MyStack<>(), new MyDict<>(), new MyList<>(), new FileTable(), new Heap(), new BarrierTable(), new SemaphoreTable(), new LockTable(), new LatchTable(), ex5);
        IRepository repo9 = new Repository("ExeptionIf.txt", prg9);
        Controller ctr9 = new Controller(repo9);


        Stmt ex6 = fileNotOpenedException();
        PrgState prg10 = new PrgState(new MyStack<>(), new MyDict<>(), new MyList<>(), new FileTable(), new Heap(), new BarrierTable(), new SemaphoreTable(), new LockTable(), new LatchTable(), ex6);
        IRepository repo10 = new Repository("ExeptionNotOpened.txt", prg10);
        Controller ctr10 = new Controller(repo10);

        Stmt ex7 = fileAlreadyOpenedException();
        PrgState prg11 = new PrgState(new MyStack<>(), new MyDict<>(), new MyList<>(), new FileTable(), new Heap(), new BarrierTable(), new SemaphoreTable(), new LockTable(), new LatchTable(), ex7);
        IRepository repo11 = new Repository("ExeptionAlreadyOpened.txt", prg11);
        Controller ctr11 = new Controller(repo11);


        Stmt bonus = relLogicExp();
        PrgState prg12 = new PrgState(new MyStack<>(), new MyDict<>(), new MyList<>(), new FileTable(), new Heap(), new BarrierTable(), new SemaphoreTable(), new LockTable(), new LatchTable(), bonus);
        IRepository repo12 = new Repository("logBonus.txt", prg12);
        Controller ctr12 = new Controller(repo12);


        Stmt exWhile = exWhile();
        PrgState prg13 = new PrgState(new MyStack<>(), new MyDict<>(), new MyList<>(), new FileTable(), new Heap(), new BarrierTable(), new SemaphoreTable(), new LockTable(), new LatchTable(), exWhile);
        IRepository repo13 = new Repository("logWhile.txt", prg13);
        Controller ctr13 = new Controller(repo13);

        Stmt exNew = exNew();
        PrgState prg14 = new PrgState(new MyStack<>(), new MyDict<>(), new MyList<>(), new FileTable(), new Heap(), new BarrierTable(), new SemaphoreTable(), new LockTable(), new LatchTable(), exNew);
        IRepository repo14 = new Repository("logNew.txt", prg14);
        Controller ctr14 = new Controller(repo14);

        Stmt exRH = exRH();
        PrgState prg15 = new PrgState(new MyStack<>(), new MyDict<>(), new MyList<>(), new FileTable(), new Heap(), new BarrierTable(), new SemaphoreTable(), new LockTable(), new LatchTable(), exRH);
        IRepository repo15 = new Repository("logRHExp.txt", prg15);
        Controller ctr15 = new Controller(repo15);

        Stmt exWH = exWH();
        PrgState prg16 = new PrgState(new MyStack<>(), new MyDict<>(), new MyList<>(), new FileTable(), new Heap(), new BarrierTable(), new SemaphoreTable(), new LockTable(), new LatchTable(), exWH);
        IRepository repo16 = new Repository("logWHStmt.txt", prg16);
        Controller ctr16 = new Controller(repo16);

        Stmt exGarbage = exGarbage();
        PrgState prg17 = new PrgState(new MyStack<>(), new MyDict<>(), new MyList<>(), new FileTable(), new Heap(), new BarrierTable(), new SemaphoreTable(), new LockTable(), new LatchTable(), exGarbage);
        IRepository repo17 = new Repository("logGarbage.txt", prg17);
        Controller ctr17 = new Controller(repo17);


        //A5
        Stmt exFork = exFork();
        PrgState prg18 = new PrgState(new MyStack<>(), new MyDict<>(), new MyList<>(), new FileTable(), new Heap(), new BarrierTable(), new SemaphoreTable(), new LockTable(), new LatchTable(), exFork);
        IRepository repo18 = new Repository("logFork.txt", prg18);
        Controller ctr18 = new Controller(repo18);

        Stmt exLab8 = exLab8();
        PrgState prg19 = new PrgState(new MyStack<>(), new MyDict<>(), new MyList<>(), new FileTable(), new Heap(), new BarrierTable(), new SemaphoreTable(), new LockTable(), new LatchTable(), exLab8);
        IRepository repo19 = new Repository("logLab8.txt", prg19);
        Controller ctr19 = new Controller(repo19);


        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunExampleCommand("1", s1.toString(), ctr1, s1));
        menu.addCommand(new RunExampleCommand("2", s2.toString(), ctr2, s2));
        menu.addCommand(new RunExampleCommand("3", s3.toString(), ctr3, s3));
        menu.addCommand(new RunExampleCommand("4", files.toString(), ctr4, files));
//        menu.addCommand(new RunExampleCommand("5", ex1.toString(), ctr5, ex1));
//        menu.addCommand(new RunExampleCommand("6", ex2.toString(), ctr6, ex2));
//        menu.addCommand(new RunExampleCommand("7", ex3.toString(), ctr7, ex3));
        menu.addCommand(new RunExampleCommand("8", ex4.toString(), ctr8, ex4));
//        menu.addCommand(new RunExampleCommand("9", ex5.toString(), ctr9, ex5));
//        menu.addCommand(new RunExampleCommand("10", ex6.toString(), ctr10, ex6));
//        menu.addCommand(new RunExampleCommand("11", ex7.toString(), ctr11, ex7));
        menu.addCommand(new RunExampleCommand("12", bonus.toString(), ctr12, bonus));
        menu.addCommand(new RunExampleCommand("13", exWhile.toString(), ctr13, exWhile));
        menu.addCommand(new RunExampleCommand("14", exNew.toString(), ctr14, exNew));
        menu.addCommand(new RunExampleCommand("15", exRH.toString(), ctr15, exRH));
        menu.addCommand(new RunExampleCommand("16", exWH.toString(), ctr16, exWH));
        menu.addCommand(new RunExampleCommand("17", exGarbage.toString(), ctr17, exGarbage));
        menu.addCommand(new RunExampleCommand("18", exFork.toString(), ctr18, exFork));
        menu.addCommand(new RunExampleCommand("19", exLab8.toString(), ctr19, exLab8));


        menu.getCommands().forEach(c->c.typecheck());

        menu.show();
    }





    public static Stmt ex1(){
        return new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(2))), new PrintStmt(new
                        VarExp("v"))));
    }
    public static Stmt ex2(){

        return new CompStmt( new VarDeclStmt("a",new IntType()),
                new CompStmt(new VarDeclStmt("b",new IntType()),
                        new CompStmt(new AssignStmt("a", new ArithExp('+',new ValueExp(new IntValue(2)),
                                new ArithExp('*',new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))),
                                new CompStmt(new AssignStmt("b",new ArithExp('+',new VarExp("a"),
                                        new ValueExp(new IntValue(1)))), new PrintStmt(new VarExp("b"))))));
    }
    public static Stmt ex3(){
        return new CompStmt(new VarDeclStmt("a", new BoolType()), new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                        (new CompStmt(new IfStmt(new VarExp("a"),
                                new AssignStmt("v",new ValueExp(new IntValue(2))),
                                new AssignStmt("v", new ValueExp(new IntValue(3)))),
                                new PrintStmt(new VarExp("v")))))));
    }


    public static Stmt exAssignException(){
        return new CompStmt(new VarDeclStmt("a", new BoolType()), new AssignStmt("a", new ValueExp(new IntValue(13))));
    }
    public static Stmt exUndefinedException(){
        return new AssignStmt("var", new ValueExp(new IntValue(2)));
    }
    public static Stmt exAlreadyDefinedException(){
        return new CompStmt(new VarDeclStmt("a", new IntType()), new VarDeclStmt("a", new IntType()));
    }
    public static Stmt exIfException(){
        return new CompStmt(new VarDeclStmt("c", new IntType()), new IfStmt(new VarExp("c"),new NopStmt(), new NopStmt()));
    }

    public static Stmt exDivisionByZero(){
        return new CompStmt(new VarDeclStmt("z", new IntType()), new CompStmt(new AssignStmt("z",new ValueExp(new IntValue(6))),
                new PrintStmt(new ArithExp('/', new VarExp("z"), new ValueExp(new IntValue(0))))));
    }


    public static Stmt fileTest(){
        return new CompStmt(new VarDeclStmt("varf", new StringType()),
                new CompStmt(new AssignStmt("varf", new ValueExp(new StringValue("file.in"))),
                        new CompStmt(new VarDeclStmt("varc", new IntType()),
                                new CompStmt(new OpenRFile(new VarExp("varf")),
                                        new CompStmt(new ReadFile(new VarExp("varf"), "varc"),
                                                new CompStmt(new PrintStmt(new VarExp("varc")),
                                                        new CompStmt(new ReadFile(new VarExp("varf"),"varc"),
                                                                new CompStmt(new PrintStmt(new VarExp("varc")),
                                                                        new CloseRFile(new VarExp("varf"))))))))));
    }

    public static Stmt fileNotOpenedException(){
        return new CloseRFile(new ValueExp(new StringValue("file.txt")));
    }
    public static Stmt fileAlreadyOpenedException(){
        return new CompStmt(new OpenRFile(new ValueExp(new StringValue("a.a"))), new OpenRFile(new ValueExp(new StringValue("a.a"))));
    }


    public static Stmt relLogicExp(){
        return new CompStmt(new VarDeclStmt("a", new IntType()),
                new CompStmt(new AssignStmt("a", new ValueExp(new IntValue(3))),
                        new IfStmt(new LogicExp(new RelationalExp(">",new VarExp("a"),new ValueExp(new IntValue(0))),
                                new RelationalExp("<",new VarExp("a"),new ValueExp(new IntValue(9))), "and"),
                                new PrintStmt(new ValueExp(new BoolValue(true))),
                                new PrintStmt(new ValueExp(new BoolValue(false))))));
    }


// A4

    public static Stmt exWhile(){
        //int v; v=4; (while (v>0) print(v);v=v-1);print(v)
        return new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(4))),
                        new CompStmt(new WhileStmt(new RelationalExp(">",new VarExp("v"),new ValueExp(new IntValue(0))),
                                                    new CompStmt(new PrintStmt(new VarExp("v")),
                                                    new AssignStmt("v", new ArithExp('-',new VarExp("v"),new ValueExp(new IntValue(1)))))),
                        new PrintStmt(new VarExp("v")))));
    }

    public static Stmt exNew(){
        //Ref int v;new(v,20);Ref Ref int a; new(a,v);print(v);print(a)
        return new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(new NewStmt("a", new VarExp("v")),
                                        new CompStmt(new PrintStmt(new VarExp("v")),new PrintStmt(new VarExp("a")))))));
    }

    public static Stmt exRH(){
        // : Ref int v;new(v,20);Ref Ref int a; new(a,v);print(rH(v));print(rH(rH(a))+5)
        return new CompStmt(new VarDeclStmt("v",new RefType(new IntType())),
                new CompStmt(new NewStmt("v",new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a",new RefType(new RefType(new IntType()))),
                                new CompStmt(new NewStmt("a",new VarExp("v")),
                                        new CompStmt(new PrintStmt(new ReadHpExp(new VarExp("v"))),
                                                new PrintStmt(new ArithExp('+',new ReadHpExp(new ReadHpExp(new VarExp("a"))),new ValueExp(new IntValue(5)))))))));
    }

    public static Stmt exWH(){
        // Ref int v;new(v,20);print(rH(v)); wH(v,30);print(rH(v)+5);
        return new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new PrintStmt(new ReadHpExp(new VarExp("v"))),
                                new CompStmt(new WriteHpStmt("v", new ValueExp(new IntValue(30))),
                                        new PrintStmt(new ArithExp('+', new ReadHpExp(new VarExp("v")),new ValueExp(new IntValue(5))))))));
    }

    public static Stmt exGarbage(){
        //Ref int v;new(v,20);Ref Ref int a; new(a,v); new(v,30);print(rH(rH(a)))
        return new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStmt("v",new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a",new RefType(new RefType(new IntType()))),
                                new CompStmt(new NewStmt("a",new VarExp("v")),
                                        new CompStmt(new WriteHpStmt("v",new ValueExp(new IntValue(30))),
                                                new PrintStmt(new ReadHpExp(new ReadHpExp(new VarExp("a")))))))));
    }

    public static Stmt exFork(){
        // int a; a=2; fork(while(a>0) {a=a-1; print(a);}); while(a<4) {a=a+1; print(a);}
        return new CompStmt(new VarDeclStmt("a",new IntType()),
                new CompStmt(new AssignStmt("a",new ValueExp(new IntValue(3))),
                        new CompStmt(new ForkStmt(new WhileStmt(new RelationalExp(">",new VarExp("a"),new ValueExp(new IntValue(0))),
                                new CompStmt(new AssignStmt("a",new ArithExp('-',new VarExp("a"),new ValueExp(new IntValue(1)))),
                                        new PrintStmt(new VarExp("a"))))),
                                new WhileStmt(new RelationalExp("<",new VarExp("a"),new ValueExp(new IntValue(6))),
                                        new CompStmt(new AssignStmt("a",new ArithExp('+',new VarExp("a"),new ValueExp(new IntValue(1)))),
                                                new PrintStmt(new VarExp("a")))))));
    }

    public static Stmt exLab8(){
        // int v; Ref int a; v=10;new(a,22);
        // fork(wH(a,30);v=32;print(v);print(rH(a)));
        // print(v);print(rH(a))
        return new CompStmt(new VarDeclStmt("v",new IntType()),
                new CompStmt(new VarDeclStmt("a",new RefType(new IntType())),
                        new CompStmt(new AssignStmt("v",new ValueExp(new IntValue(10))),
                                new CompStmt(new NewStmt("a",new ValueExp(new IntValue(22))),
                                        new CompStmt(new ForkStmt(new CompStmt(new WriteHpStmt("a",new ValueExp(new IntValue(30))),
                                                new CompStmt(new AssignStmt("v",new ValueExp(new IntValue(32))),
                                                        new CompStmt(new PrintStmt(new VarExp("v")),new PrintStmt(new ReadHpExp(new VarExp("a"))))))),
                                                new CompStmt(new PrintStmt(new VarExp("v")),new PrintStmt(new ReadHpExp(new VarExp("a")))))))));
    }
}
