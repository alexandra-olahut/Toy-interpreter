package View;

import Controller.Controller;
import Exceptions.TypeException;
import Model.ADT.*;
import Model.ProgramState.PrgState;
import Model.Statements.Stmt;
import Repository.IRepository;
import Repository.Repository;

import java.util.ArrayList;
import java.util.List;

public class RunExampleCommand extends Command {

    private final Controller controller;
    private final Stmt initialPrg;
    public RunExampleCommand(String k, String d, Controller c, Stmt initialPrg) {
        super(k,d);
        controller = c;
        this.initialPrg = initialPrg;
    }


    @Override
    public void execute() {
        try{
            controller.allStep();
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }

        reset();
    }

    public void reset() {
        List<PrgState> prgs = new ArrayList<PrgState>();
        prgs.add(new PrgState(new MyStack<>(), new MyDict<>(), new MyList<>(), new FileTable(), new Heap(), initialPrg));
        controller.getRepo().setPrgList(prgs);
    }

    @Override
    public void typecheck() {
        try{
            initialPrg.typecheck(new MyDict<>());
        }
        catch(TypeException typeError) {
            System.out.println(" !!! PRG "+ getKey() + ". " + getDescription() +'\n' + typeError.getMessage());
            throw typeError;
        }
    }

    @Override
    public Controller getController() {return controller;}
}
