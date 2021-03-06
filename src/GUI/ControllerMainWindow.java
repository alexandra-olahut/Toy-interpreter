package GUI;

import Controller.Controller;
import Exceptions.TypeException;
import Model.ADT.*;
import Model.Expressions.*;
import Model.ProgramState.PrgState;
import Model.Statements.*;
import Model.Statements.Barrier.NewBarrierStmt;
import Model.Statements.Conditionals.CondAssignStmt;
import Model.Statements.Conditionals.IfStmt;
import Model.Statements.Conditionals.SwitchStmt;
import Model.Statements.Files.CloseRFile;
import Model.Statements.Files.OpenRFile;
import Model.Statements.Files.ReadFile;
import Model.Statements.Heap.NewStmt;
import Model.Statements.Heap.WriteHpStmt;
import Model.Statements.Latch.CountDownStmt;
import Model.Statements.Latch.NewLatchStmt;
import Model.Statements.Lock.LockStmt;
import Model.Statements.Lock.NewLockStmt;
import Model.Statements.Lock.UnlockStmt;
import Model.Statements.Loops.ForStmt;
import Model.Statements.Loops.RepeatUntilStmt;
import Model.Statements.Loops.WhileStmt;
import Model.Statements.Semaphore.AcquireStmt;
import Model.Statements.Semaphore.CreateSemaphoreStmt;
import Model.Statements.Semaphore.ReleaseStmt;
import Model.Types.BoolType;
import Model.Types.IntType;
import Model.Types.RefType;
import Model.Types.StringType;
import Model.Values.BoolValue;
import Model.Values.IntValue;
import Model.Values.StringValue;
import Repository.IRepository;
import Repository.Repository;
import View.Command;
import View.RunExampleCommand;
import View.TextMenu;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;

public class ControllerMainWindow implements Initializable {

    @FXML
    private ListView<Command> prgListView;
    @FXML
    private Button runButton;

    TextMenu menu;


    @FXML
    void runProgram(ActionEvent event) throws IOException {

        Command program = prgListView.getSelectionModel().getSelectedItem();
        if (program == null)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Hey!");
            alert.setHeaderText(null);
            alert.setContentText("No program was selected");
            alert.show();
            return;
        }


        Stage mainStage = (Stage) prgListView.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("ProgramWindow.fxml"));
        loader.setControllerFactory(c -> new ControllerProgramWindow(program, program.getController()));

        Parent newRoot = loader.load();

        Scene newScene = new Scene(newRoot);
        Stage newWindow = new Stage();
        newWindow.setTitle("Program " + program.getKey());
        newWindow.setScene(newScene);

        newWindow.initModality(Modality.WINDOW_MODAL);
        newWindow.initOwner(mainStage);

        newWindow.setX(mainStage.getX() - 250);
        newWindow.setY(mainStage.getY() - 40);

        newWindow.show();
    }

    @FXML
    void typecheckProgram(MouseEvent event) {

        Command program = prgListView.getSelectionModel().getSelectedItem();

        try {
            program.typecheck();
        }
        catch(TypeException typeEx) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Typecheck error");
            alert.setHeaderText(program.toString());
            alert.setContentText(typeEx.getMessage());

            alert.showAndWait();
        }
        catch(Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Unknown error :(");
            alert.showAndWait();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setUp();
        ObservableList<Command> programs = FXCollections.observableArrayList();
        programs.addAll(menu.getCommands());

        prgListView.setItems(programs.sorted(Comparator.comparingInt(c -> Integer.parseInt(c.getKey()))));
        prgListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

    }

    private void setUp() {

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


        // relational expression
        Stmt bonus = relLogicExp();
        PrgState prg12 = new PrgState(new MyStack<>(), new MyDict<>(), new MyList<>(), new FileTable(), new Heap(), new BarrierTable(), new SemaphoreTable(), new LockTable(), new LatchTable(), bonus);
        IRepository repo12 = new Repository("logBonus.txt", prg12);
        Controller ctr12 = new Controller(repo12);

        // while stmt
        Stmt exWhile = exWhile();
        PrgState prg13 = new PrgState(new MyStack<>(), new MyDict<>(), new MyList<>(), new FileTable(), new Heap(), new BarrierTable(), new SemaphoreTable(), new LockTable(), new LatchTable(), exWhile);
        IRepository repo13 = new Repository("logWhile.txt", prg13);
        Controller ctr13 = new Controller(repo13);

        // heap examples
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





        Stmt CondAssign = CondAssign();
        PrgState prg20 = new PrgState(new MyStack<>(), new MyDict<>(), new MyList<>(), new FileTable(), new Heap(), new BarrierTable(), new SemaphoreTable(), new LockTable(), new LatchTable(), CondAssign);
        IRepository repo20 = new Repository("logCONDASSIGN.txt", prg20);
        Controller ctr20 = new Controller(repo20);

        Stmt FOR = forS();
        PrgState prg21 = new PrgState(new MyStack<>(), new MyDict<>(), new MyList<>(), new FileTable(), new Heap(), new BarrierTable(), new SemaphoreTable(), new LockTable(), new LatchTable(), FOR);
        IRepository repo21 = new Repository("logFOR.txt", prg21);
        Controller ctr21 = new Controller(repo21);

        Stmt exMUL = exMUL();
        PrgState prg22 = new PrgState(new MyStack<>(), new MyDict<>(), new MyList<>(), new FileTable(), new Heap(), new BarrierTable(), new SemaphoreTable(), new LockTable(), new LatchTable(), exMUL);
        IRepository repo22 = new Repository("logMUL.txt", prg22);
        Controller ctr22 = new Controller(repo22);

        Stmt exRepeat = exRepeat();
        PrgState prg23 = new PrgState(new MyStack<>(), new MyDict<>(), new MyList<>(), new FileTable(), new Heap(), new BarrierTable(), new SemaphoreTable(), new LockTable(), new LatchTable(), exRepeat);
        IRepository repo23 = new Repository("logRepeat.txt", prg23);
        Controller ctr23 = new Controller(repo23);

        Stmt exSleep = exSleep();
        PrgState prg24 = new PrgState(new MyStack<>(), new MyDict<>(), new MyList<>(), new FileTable(), new Heap(), new BarrierTable(), new SemaphoreTable(), new LockTable(), new LatchTable(), exSleep);
        IRepository repo24 = new Repository("logSleep.txt", prg24);
        Controller ctr24 = new Controller(repo24);

        Stmt SWITCH = exSwitch();
        PrgState prg25 = new PrgState(new MyStack<>(), new MyDict<>(), new MyList<>(), new FileTable(), new Heap(), new BarrierTable(), new SemaphoreTable(), new LockTable(), new LatchTable(), SWITCH);
        IRepository repo25 = new Repository("logSWITCH.txt", prg25);
        Controller ctr25 = new Controller(repo25);

        Stmt exWait = exWait();
        PrgState prg26 = new PrgState(new MyStack<>(), new MyDict<>(), new MyList<>(), new FileTable(), new Heap(), new BarrierTable(), new SemaphoreTable(), new LockTable(), new LatchTable(), exWait);
        IRepository repo26 = new Repository("logWait.txt", prg26);
        Controller ctr26 = new Controller(repo26);


        Stmt exLock = exLock();
        PrgState prg27 = new PrgState(new MyStack<>(), new MyDict<>(), new MyList<>(), new FileTable(), new Heap(), new BarrierTable(), new SemaphoreTable(), new LockTable(), new LatchTable(), exLock);
        IRepository repo27 = new Repository("logLock.txt", prg27);
        Controller ctr27 = new Controller(repo27);

        Stmt exBarrier = exBarrier();
        PrgState prg28 = new PrgState(new MyStack<>(), new MyDict<>(), new MyList<>(), new FileTable(), new Heap(), new BarrierTable(), new SemaphoreTable(), new LockTable(), new LatchTable(), exBarrier);
        IRepository repo28 = new Repository("logBarrier.txt", prg28);
        Controller ctr28 = new Controller(repo28);

        Stmt exLatch = exLatch();
        PrgState prg29 = new PrgState(new MyStack<>(), new MyDict<>(), new MyList<>(), new FileTable(), new Heap(), new BarrierTable(), new SemaphoreTable(), new LockTable(), new LatchTable(), exLatch);
        IRepository repo29 = new Repository("logLatch.txt", prg29);
        Controller ctr29 = new Controller(repo29);

        Stmt exSemaphore = exSemaphore();
        PrgState prg30 = new PrgState(new MyStack<>(), new MyDict<>(), new MyList<>(), new FileTable(), new Heap(), new BarrierTable(), new SemaphoreTable(), new LockTable(), new LatchTable(), exSemaphore);
        IRepository repo30 = new Repository("logSemaphore.txt", prg30);
        Controller ctr30 = new Controller(repo30);



        menu = new TextMenu();
//        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunExampleCommand("1", s1.toString(), ctr1, s1));
        menu.addCommand(new RunExampleCommand("2", s2.toString(), ctr2, s2));
        menu.addCommand(new RunExampleCommand("3", s3.toString(), ctr3, s3));
        menu.addCommand(new RunExampleCommand("4", files.toString(), ctr4, files));
        menu.addCommand(new RunExampleCommand("5", ex1.toString(), ctr5, ex1));
        menu.addCommand(new RunExampleCommand("6", ex2.toString(), ctr6, ex2));
        menu.addCommand(new RunExampleCommand("7", ex3.toString(), ctr7, ex3));
        menu.addCommand(new RunExampleCommand("8", ex4.toString(), ctr8, ex4));
        menu.addCommand(new RunExampleCommand("9", ex5.toString(), ctr9, ex5));
        menu.addCommand(new RunExampleCommand("10", ex6.toString(), ctr10, ex6));
        menu.addCommand(new RunExampleCommand("11", ex7.toString(), ctr11, ex7));
        menu.addCommand(new RunExampleCommand("12", bonus.toString(), ctr12, bonus));
        menu.addCommand(new RunExampleCommand("13", exWhile.toString(), ctr13, exWhile));
        menu.addCommand(new RunExampleCommand("14", exNew.toString(), ctr14, exNew));
        menu.addCommand(new RunExampleCommand("15", exRH.toString(), ctr15, exRH));
        menu.addCommand(new RunExampleCommand("16", exWH.toString(), ctr16, exWH));
        menu.addCommand(new RunExampleCommand("17", exGarbage.toString(), ctr17, exGarbage));
        menu.addCommand(new RunExampleCommand("18", exFork.toString(), ctr18, exFork));
        menu.addCommand(new RunExampleCommand("19", exLab8.toString(), ctr19, exLab8));
        menu.addCommand(new RunExampleCommand("20", CondAssign.toString(), ctr20, CondAssign));
        menu.addCommand(new RunExampleCommand("21", FOR.toString(), ctr21, FOR));
        menu.addCommand(new RunExampleCommand("22", exMUL.toString(), ctr22, exMUL));
        menu.addCommand(new RunExampleCommand("23", exRepeat.toString(), ctr23, exRepeat));
        menu.addCommand(new RunExampleCommand("24", exSleep.toString(), ctr24, exSleep));
        menu.addCommand(new RunExampleCommand("25", SWITCH.toString(), ctr25, SWITCH));
        menu.addCommand(new RunExampleCommand("26", exWait.toString(), ctr26, exWait));
        menu.addCommand(new RunExampleCommand("27", exLock.toString(), ctr27, exLock));
        menu.addCommand(new RunExampleCommand("28", exBarrier.toString(), ctr28, exBarrier));
        menu.addCommand(new RunExampleCommand("29", exLatch.toString(), ctr29, exLatch));
        menu.addCommand(new RunExampleCommand("30", exSemaphore.toString(), ctr30, exSemaphore));
    }


    private static Stmt exLock(){
        return new CompStmt(new VarDeclStmt("v1", new RefType(new IntType())),
                new CompStmt(new VarDeclStmt("v2", new RefType(new IntType())),
                        new CompStmt(new VarDeclStmt("x", new IntType()), new CompStmt(new VarDeclStmt("q", new IntType()),
                                new CompStmt(new NewStmt("v1", new ValueExp(new IntValue(20))),
                                        new CompStmt(new NewStmt("v2", new ValueExp(new IntValue(30))),
                                                new CompStmt(new NewLockStmt("x"),
                                                        new CompStmt(new ForkStmt(new CompStmt(new ForkStmt(new CompStmt(new LockStmt("x"),
                                                                new CompStmt(new WriteHpStmt("v1", new ArithExp('-', new ReadHpExp(new VarExp("v1")),new ValueExp(new IntValue(1)))),
                                                                        new UnlockStmt("x")))),
                                                                new CompStmt(new LockStmt("x"),
                                                                        new CompStmt(new WriteHpStmt("v1", new ArithExp('*', new ReadHpExp(new VarExp("v1")),new ValueExp(new IntValue(10)))),
                                                                                new UnlockStmt("x"))))),
                                                                new CompStmt(new NewLockStmt("q"),
                                                                        new CompStmt(new ForkStmt(new CompStmt(new ForkStmt(new CompStmt(new LockStmt("q"),
                                                                                new CompStmt(new WriteHpStmt("v2", new ArithExp('+', new ReadHpExp(new VarExp("v2")),new ValueExp(new IntValue(5)))),
                                                                                        new UnlockStmt("q")))),
                                                                                new CompStmt(new LockStmt("q"),
                                                                                        new CompStmt(new WriteHpStmt("v2", new ArithExp('*', new ReadHpExp(new VarExp("v2")),new ValueExp(new IntValue(10)))),
                                                                                                new UnlockStmt("q"))))),
                                                                                new CompStmt(new NopStmt(), new CompStmt(new NopStmt(), new CompStmt(new NopStmt(), new CompStmt(new NopStmt(),
                                                                                        new CompStmt(new LockStmt("x"),
                                                                                                new CompStmt(new PrintStmt(new ReadHpExp(new VarExp("v1"))),
                                                                                                        new CompStmt(new UnlockStmt("x"), new CompStmt(new LockStmt("q"),
                                                                                                                new CompStmt(new PrintStmt(new ReadHpExp(new VarExp("v2"))),
                                                                                                                        new UnlockStmt("q"))))))))))))))))))));
    }

    private static Stmt exBarrier(){
        Stmt line1 = new CompStmt(new ForkStmt(new CompStmt(new Model.Statements.Barrier.AwaitStmt("cnt"),
                new WriteHpStmt("v1", new ArithExp('*', new ReadHpExp(new VarExp("v1")), new ValueExp(new IntValue(10)))))),
                new PrintStmt(new ReadHpExp(new VarExp("v1"))));
        Stmt line2 = new CompStmt(new ForkStmt(new CompStmt(new Model.Statements.Barrier.AwaitStmt("cnt"),
                new WriteHpStmt("v2", new ArithExp('*', new ReadHpExp(new VarExp("v2")), new ValueExp(new IntValue(10)))))),
                new CompStmt(new WriteHpStmt("v2", new ArithExp('*', new ReadHpExp(new VarExp("v2")), new ValueExp(new IntValue(10)))),
                        new PrintStmt(new ReadHpExp(new VarExp("v2")))));
        Stmt forks = new CompStmt(line1, new CompStmt(line2,
                new CompStmt(new Model.Statements.Barrier.AwaitStmt("cnt"), new PrintStmt(new ReadHpExp(new VarExp("v3"))))));

        return new CompStmt(new VarDeclStmt("v1", new RefType(new IntType())),
                new CompStmt(new VarDeclStmt("v2", new RefType(new IntType())),
                        new CompStmt(new VarDeclStmt("v3", new RefType(new IntType())),
                                new CompStmt(new NewStmt("v1", new ValueExp(new IntValue(2))),
                                        new CompStmt(new NewStmt("v2", new ValueExp(new IntValue(3))),
                                                new CompStmt(new NewStmt("v3", new ValueExp(new IntValue(4))),
                                                        new CompStmt(new NewBarrierStmt("cnt", new ReadHpExp(new VarExp("v2"))),
                                                                new CompStmt(new ForkStmt(new CompStmt(new Model.Statements.Barrier.AwaitStmt("cnt"),
                                                                        new CompStmt(new WriteHpStmt("v1", new ArithExp('*', new ReadHpExp(new VarExp("v1")), new ValueExp(new IntValue(10)))),
                                                                                new PrintStmt(new ReadHpExp(new VarExp("v1")))))),
                                                                        new CompStmt(new ForkStmt(new CompStmt(new Model.Statements.Barrier.AwaitStmt("cnt"),
                                                                                new CompStmt(new WriteHpStmt("v2", new ArithExp('*', new ReadHpExp(new VarExp("v2")), new ValueExp(new IntValue(10)))),
                                                                                        new CompStmt(new WriteHpStmt("v2", new ArithExp('*', new ReadHpExp(new VarExp("v2")), new ValueExp(new IntValue(10)))),
                                                                                                new PrintStmt(new ReadHpExp(new VarExp("v2"))))))),
                                                                                new CompStmt(new Model.Statements.Barrier.AwaitStmt("cnt"), new PrintStmt(new ReadHpExp(new VarExp("v3")))))))))))));
    }

    private static Stmt exLatch(){
        return new CompStmt(new VarDeclStmt("v1", new RefType(new IntType())),
                new CompStmt(new VarDeclStmt("v2", new RefType(new IntType())),
                        new CompStmt(new VarDeclStmt("v3", new RefType(new IntType())),
                                new CompStmt(new VarDeclStmt("cnt", new IntType()),
                                        new CompStmt(new NewStmt("v1", new ValueExp(new IntValue(2))),
                                                new CompStmt(new NewStmt("v2", new ValueExp(new IntValue(3))),
                                                        new CompStmt(new NewStmt("v3", new ValueExp(new IntValue(4))),
                                                                new CompStmt(new NewLatchStmt("cnt", new ReadHpExp(new VarExp("v2"))),
                                                                        new CompStmt(new ForkStmt(new CompStmt(new WriteHpStmt("v1", new ArithExp('*', new ReadHpExp(new VarExp("v1")), new ValueExp(new IntValue(10)))),
                                                                                new CompStmt(new PrintStmt(new ReadHpExp(new VarExp("v1"))),
                                                                                        new CompStmt(new CountDownStmt("cnt"),
                                                                                                new ForkStmt(new CompStmt(new WriteHpStmt("v2", new ArithExp('*', new ReadHpExp(new VarExp("v2")), new ValueExp(new IntValue(10)))),
                                                                                                        new CompStmt(new PrintStmt(new ReadHpExp(new VarExp("v2"))),
                                                                                                                new CompStmt(new CountDownStmt("cnt"),
                                                                                                                        new ForkStmt(new CompStmt(new WriteHpStmt("v3",
                                                                                                                                new ArithExp('*', new ReadHpExp(new VarExp("v3")), new ValueExp(new IntValue(10)))),
                                                                                                                                new CompStmt(new PrintStmt(new ReadHpExp(new VarExp("v3"))), new CountDownStmt("cnt")))))))))))),
                                                                                new CompStmt(new Model.Statements.Latch.AwaitStmt("cnt"),
                                                                                        new CompStmt(new PrintStmt(new ValueExp(new IntValue(100))),
                                                                                                new CompStmt(new CountDownStmt("cnt"), new PrintStmt(new ValueExp(new IntValue(100)))))))))))))));
    }

    private static Stmt exSemaphore(){
        return new CompStmt(new VarDeclStmt("v1", new RefType(new IntType())),
                new CompStmt(new VarDeclStmt("cnt", new IntType()),
                        new CompStmt(new NewStmt("v1", new ValueExp(new IntValue(1))),
                                new CompStmt(new CreateSemaphoreStmt("cnt", new ReadHpExp(new VarExp("v1"))),
                                        new CompStmt(new ForkStmt(new CompStmt(new AcquireStmt("cnt"),
                                                new CompStmt(new WriteHpStmt("v1", new ArithExp('*', new ReadHpExp(new VarExp("v1")), new ValueExp(new IntValue(10)))),
                                                        new CompStmt(new PrintStmt(new ReadHpExp(new VarExp("v1"))), new ReleaseStmt("cnt"))))),
                                                new CompStmt(new ForkStmt(new CompStmt(new AcquireStmt("cnt"),
                                                        new CompStmt(new WriteHpStmt("v1", new ArithExp('*', new ReadHpExp(new VarExp("v1")),new ValueExp(new IntValue(10)))),
                                                                new CompStmt(new WriteHpStmt("v1", new ArithExp('*', new ReadHpExp(new VarExp("v1")), new ValueExp(new IntValue(2)))),
                                                                        new CompStmt(new PrintStmt(new ReadHpExp(new VarExp("v1"))), new ReleaseStmt("cnt")))))),
                                                        new CompStmt(new AcquireStmt("cnt"), new CompStmt(new PrintStmt(new ArithExp('-', new ReadHpExp(new VarExp("v1")), new ValueExp(new IntValue(1)))),
                                                                new ReleaseStmt("cnt")))))))));
    }


    private static Stmt exWait(){
        return new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(20))),
                new CompStmt(new WaitStmt(10), new PrintStmt(new ArithExp('*', new VarExp("v"), new ValueExp(new IntValue(10)))))));
    }

    private static Stmt exSwitch(){
        Stmt switchS = new SwitchStmt(new ArithExp('*', new VarExp("a"), new ValueExp(new IntValue(10))),
                new ArithExp('*', new VarExp("b"), new VarExp("c")), new ValueExp(new IntValue(10)),
                new CompStmt(new PrintStmt(new VarExp("a")), new PrintStmt(new VarExp("b"))),
                new CompStmt(new PrintStmt(new ValueExp(new IntValue(100))), new PrintStmt(new ValueExp(new IntValue(200)))),
                new PrintStmt(new ValueExp(new IntValue(300))));

        return new CompStmt(new VarDeclStmt("a", new IntType()), new CompStmt(new VarDeclStmt("b", new IntType()),
                new CompStmt(new VarDeclStmt("c", new IntType()),
                        new CompStmt(new AssignStmt("a", new ValueExp(new IntValue(1))),
                                new CompStmt(new AssignStmt("b", new ValueExp(new IntValue(2))),
                                        new CompStmt(new AssignStmt("c", new ValueExp(new IntValue(5))),
                                                new CompStmt(switchS, new PrintStmt(new ValueExp(new IntValue(300))))))))));
    }

    private static Stmt exSleep(){
        Stmt decl = new AssignStmt("v", new ArithExp('-', new VarExp("v"), new ValueExp(new IntValue(1))));
        return new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(10))),
                new CompStmt(new ForkStmt(new CompStmt(decl, new CompStmt(decl, new PrintStmt(new VarExp("v"))))),
                        new CompStmt(new SleepStmt(10), new PrintStmt(new ArithExp('*', new VarExp("v"), new ValueExp(new IntValue(10))))))));
    }

    private static Stmt exRepeat(){
        Stmt declStmt = new CompStmt(new VarDeclStmt("x", new IntType()), new CompStmt(new VarDeclStmt("y", new IntType()),
                new CompStmt(new VarDeclStmt("z", new IntType()), new CompStmt(new VarDeclStmt("w", new IntType()),
                        new CompStmt(new AssignStmt("x", new ValueExp(new IntValue(1))),
                                new CompStmt(new AssignStmt("y", new ValueExp(new IntValue(2))),
                                        new CompStmt(new AssignStmt("z", new ValueExp(new IntValue(3))),
                                                new AssignStmt("w", new ValueExp(new IntValue(4))))))))));
        return new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new RepeatUntilStmt(new CompStmt(new ForkStmt(new CompStmt(new PrintStmt(new VarExp("v")),
                        new AssignStmt("v", new ArithExp('-', new VarExp("v"), new ValueExp(new IntValue(1)))))),
                        new AssignStmt("v", new ArithExp('+', new VarExp("v"), new ValueExp(new IntValue(1))))),
                        new RelationalExp("==", new VarExp("v"), new ValueExp(new IntValue(3)))),
                        new CompStmt(declStmt, new PrintStmt(new ArithExp('*', new VarExp("v"), new ValueExp(new IntValue(10)))))));
    }

    private static Stmt exMUL(){
        return new CompStmt(new VarDeclStmt("v1", new IntType()), new CompStmt(new VarDeclStmt("v2", new IntType()),
                new CompStmt(new AssignStmt("v1", new ValueExp(new IntValue(2))),
                        new CompStmt(new AssignStmt("v2", new ValueExp(new IntValue(3))),
                                new IfStmt(new RelationalExp("!=", new VarExp("v1"), new ValueExp(new IntValue(0))),
                                        new PrintStmt(new MULExp(new VarExp("v1"), new VarExp("v2"))),
                                        new PrintStmt(new VarExp("v1")))))));
    }

    private static Stmt forS(){
        return new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(20))),
                new CompStmt(new ForStmt("v", new ValueExp(new IntValue(0)), new ValueExp(new IntValue(3)),
                        new ArithExp('+', new VarExp("v"), new ValueExp(new IntValue(1))),
                        new ForkStmt(new CompStmt(new PrintStmt(new VarExp("v")),
                                new AssignStmt("v",new ArithExp('+',new VarExp("v"), new ValueExp(new IntValue(1))))))),
                        new PrintStmt(new ArithExp('*',new VarExp("v"), new ValueExp(new IntValue(10)))))));
    }

    private static Stmt CondAssign(){
        Stmt init = new CompStmt(new VarDeclStmt("a", new RefType(new IntType())),
                new CompStmt(new VarDeclStmt("b", new RefType(new IntType())),
                        new CompStmt(new VarDeclStmt("v", new IntType()),
                                new CompStmt(new NewStmt("a", new ValueExp(new IntValue(0))),
                                        new NewStmt("b", new ValueExp(new IntValue(0)))))));
        Stmt writeHeap = new CompStmt(new WriteHpStmt("a", new ValueExp(new IntValue(1))),
                new WriteHpStmt("b", new ValueExp(new IntValue(2))));
        Stmt vs = new CompStmt(new CondAssignStmt("v", new RelationalExp("<",
                new ReadHpExp(new VarExp("a")), new ReadHpExp(new VarExp("b"))),
                new ValueExp(new IntValue(100)), new ValueExp(new IntValue(200))),
                new CompStmt(new PrintStmt(new VarExp("v")),
                        new CompStmt(new CondAssignStmt("v", new RelationalExp(">",
                                new ArithExp('-', new ReadHpExp(new VarExp("b")),new ValueExp(new IntValue(2))),
                                new ReadHpExp(new VarExp("a"))),
                                new ValueExp(new IntValue(100)), new ValueExp(new IntValue(200))),
                                new PrintStmt(new VarExp("v")))));

        return new CompStmt(init, new CompStmt(writeHeap, vs));
    }







    private static Stmt ex1(){
        return new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(2))), new PrintStmt(new
                        VarExp("v"))));
    }
    private static Stmt ex2(){

        return new CompStmt( new VarDeclStmt("a",new IntType()),
                new CompStmt(new VarDeclStmt("b",new IntType()),
                        new CompStmt(new AssignStmt("a", new ArithExp('+',new ValueExp(new IntValue(2)),
                                new ArithExp('*',new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))),
                                new CompStmt(new AssignStmt("b",new ArithExp('+',new VarExp("a"),
                                        new ValueExp(new IntValue(1)))), new PrintStmt(new VarExp("b"))))));
    }
    private static Stmt ex3(){
        return new CompStmt(new VarDeclStmt("a", new BoolType()), new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                        (new CompStmt(new IfStmt(new VarExp("a"),
                                new AssignStmt("v",new ValueExp(new IntValue(2))),
                                new AssignStmt("v", new ValueExp(new IntValue(3)))),
                                new PrintStmt(new VarExp("v")))))));
    }


    private static Stmt exAssignException(){
        return new CompStmt(new VarDeclStmt("a", new BoolType()), new AssignStmt("a", new ValueExp(new IntValue(13))));
    }
    private static Stmt exUndefinedException(){
        return new AssignStmt("var", new ValueExp(new IntValue(2)));
    }
    private static Stmt exAlreadyDefinedException(){
        return new CompStmt(new VarDeclStmt("a", new IntType()), new VarDeclStmt("a", new IntType()));
    }
    private static Stmt exIfException(){
        return new CompStmt(new VarDeclStmt("c", new IntType()), new IfStmt(new VarExp("c"),new NopStmt(), new NopStmt()));
    }

    private static Stmt exDivisionByZero(){
        return new CompStmt(new VarDeclStmt("z", new IntType()), new CompStmt(new AssignStmt("z",new ValueExp(new IntValue(6))),
                new PrintStmt(new ArithExp('/', new VarExp("z"), new ValueExp(new IntValue(0))))));
    }


    private static Stmt fileTest(){
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

    private static Stmt fileNotOpenedException(){
        return new CloseRFile(new ValueExp(new StringValue("file.txt")));
    }
    private static Stmt fileAlreadyOpenedException(){
        return new CompStmt(new OpenRFile(new ValueExp(new StringValue("a.a"))), new OpenRFile(new ValueExp(new StringValue("a.a"))));
    }


    private static Stmt relLogicExp(){
        return new CompStmt(new VarDeclStmt("a", new IntType()),
                new CompStmt(new AssignStmt("a", new ValueExp(new IntValue(3))),
                        new IfStmt(new LogicExp(new RelationalExp(">",new VarExp("a"),new ValueExp(new IntValue(0))),
                                new RelationalExp("<",new VarExp("a"),new ValueExp(new IntValue(9))), "and"),
                                new PrintStmt(new ValueExp(new BoolValue(true))),
                                new PrintStmt(new ValueExp(new BoolValue(false))))));
    }


// A4

    private static Stmt exWhile(){
        //int v; v=4; (while (v>0) print(v);v=v-1);print(v)
        return new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(4))),
                        new CompStmt(new WhileStmt(new RelationalExp(">",new VarExp("v"),new ValueExp(new IntValue(0))),
                                new CompStmt(new PrintStmt(new VarExp("v")),
                                        new AssignStmt("v", new ArithExp('-',new VarExp("v"),new ValueExp(new IntValue(1)))))),
                                new PrintStmt(new VarExp("v")))));
    }

    private static Stmt exNew(){
        //Ref int v;new(v,20);Ref Ref int a; new(a,v);print(v);print(a)
        return new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(new NewStmt("a", new VarExp("v")),
                                        new CompStmt(new PrintStmt(new VarExp("v")),new PrintStmt(new VarExp("a")))))));
    }

    private static Stmt exRH(){
        // : Ref int v;new(v,20);Ref Ref int a; new(a,v);print(rH(v));print(rH(rH(a))+5)
        return new CompStmt(new VarDeclStmt("v",new RefType(new IntType())),
                new CompStmt(new NewStmt("v",new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a",new RefType(new RefType(new IntType()))),
                                new CompStmt(new NewStmt("a",new VarExp("v")),
                                        new CompStmt(new PrintStmt(new ReadHpExp(new VarExp("v"))),
                                                new PrintStmt(new ArithExp('+',new ReadHpExp(new ReadHpExp(new VarExp("a"))),new ValueExp(new IntValue(5)))))))));
    }

    private static Stmt exWH(){
        // Ref int v;new(v,20);print(rH(v)); wH(v,30);print(rH(v)+5);
        return new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new PrintStmt(new ReadHpExp(new VarExp("v"))),
                                new CompStmt(new WriteHpStmt("v", new ValueExp(new IntValue(30))),
                                        new PrintStmt(new ArithExp('+', new ReadHpExp(new VarExp("v")),new ValueExp(new IntValue(5))))))));
    }

    private static Stmt exGarbage(){
        //Ref int v;new(v,20);Ref Ref int a; new(a,v); new(v,30);print(rH(rH(a)))
        return new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStmt("v",new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a",new RefType(new RefType(new IntType()))),
                                new CompStmt(new NewStmt("a",new VarExp("v")),
                                        new CompStmt(new WriteHpStmt("v",new ValueExp(new IntValue(30))),
                                                new PrintStmt(new ReadHpExp(new ReadHpExp(new VarExp("a")))))))));
    }

    private static Stmt exFork(){
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

    private static Stmt exLab8(){
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