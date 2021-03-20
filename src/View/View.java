/*
package View;

import Controller.Controller;
import Model.ADT.*;
import Model.Expressions.ArithExp;
import Model.Expressions.ValueExp;
import Model.Expressions.VarExp;
import Model.ProgramState.PrgState;
import Model.Statements.*;
import Model.Types.BoolType;
import Model.Types.IntType;
import Model.Types.StringType;
import Model.Values.BoolValue;
import Model.Values.IntValue;
import Model.Values.StringValue;
import Model.Values.Value;
import Repository.*;

import java.util.Scanner;

public class View {

//    private final Controller controller;
//    public View(Controller c) {controller=c;}

    public void run(){

        menu();
        Scanner input = new Scanner(System.in);
        while(true) {
            int answer = input.nextInt();
            Stmt program = new NopStmt();
            switch (answer) {
                case 1:
                    program = ex1();
                    break;
                case 2:
                    program = ex2();
                    break;
                case 3:
                    program = ex3();
                    break;
                case 0:
                    return;
                case 4:
                    program = exAssignException();
                    break;
                case 5:
                    program = exUndefinedException();
                    break;
                case 6:
                    program = exAlreadyDefinedException();
                    break;
                case 7:
                    program = exIfException();
                    break;
                case 8:
                    program = exDivisionByZero();
                    break;
                case 9:
                    program = fileTest();
                    break;
            }
            System.out.println("...Running example:...\n"+program);
            runProgram(program);
            menu();
        }
    }

    public void runProgram(Stmt prg) {
        IStack<Stmt> stack = new MyStack<>();
        IDict<String, Value> symTable = new MyDict<>();
        IList<Value> out = new MyList<>();
        IFileTable fileTable = new FileTable();
        PrgState prgState= new PrgState(stack, symTable, out, fileTable, prg);

        IRepository repo = new Repository("logFile.txt", prgState);
        Controller ctrl = new Controller(repo);

        try {
            ctrl.allStep();
            ctrl.printOutput();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void menu(){
        System.out.println("""

                --------------------------------------------
                You can choose the example you want to run:
                Enter 0 to exit the program
                """);
    }





    public Stmt ex1(){
        return new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(2))), new PrintStmt(new
                        VarExp("v"))));
    }
    public Stmt ex2(){

        return new CompStmt( new VarDeclStmt("a",new IntType()),
                new CompStmt(new VarDeclStmt("b",new IntType()),
                        new CompStmt(new AssignStmt("a", new ArithExp('+',new ValueExp(new IntValue(2)),
                                new ArithExp('*',new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))),
                                new CompStmt(new AssignStmt("b",new ArithExp('+',new VarExp("a"),
                                        new ValueExp(new IntValue(1)))), new PrintStmt(new VarExp("b"))))));
    }
    public Stmt ex3(){
        return new CompStmt(new VarDeclStmt("a", new BoolType()), new CompStmt(new VarDeclStmt("v", new IntType()),
                        new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                                (new CompStmt(new IfStmt(new VarExp("a"),
                                                    new AssignStmt("v",new ValueExp(new IntValue(2))),
                                                    new AssignStmt("v", new ValueExp(new IntValue(3)))),
                                        new PrintStmt(new VarExp("v")))))));
    }


    public Stmt exAssignException(){
        return new CompStmt(new VarDeclStmt("a", new BoolType()), new AssignStmt("a", new ValueExp(new IntValue(13))));
    }
    public Stmt exUndefinedException(){
        return new AssignStmt("var", new ValueExp(new IntValue(2)));
    }
    public Stmt exAlreadyDefinedException(){
        return new CompStmt(new VarDeclStmt("a", new IntType()), new VarDeclStmt("a", new IntType()));
    }
    public Stmt exIfException(){
        return new CompStmt(new VarDeclStmt("c", new IntType()), new IfStmt(new VarExp("c"),new NopStmt(), new NopStmt()));
    }

    public Stmt exDivisionByZero(){
        return new CompStmt(new VarDeclStmt("z", new IntType()), new CompStmt(new AssignStmt("z",new ValueExp(new IntValue(6))),
                new PrintStmt(new ArithExp('/', new VarExp("z"), new ValueExp(new IntValue(0))))));
    }


    public Stmt fileTest(){

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
}
*/