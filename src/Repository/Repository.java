package Repository;

import Model.ProgramState.PrgState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository {

    private List<PrgState> programs;
    private final String logFilePath;
    private boolean firstFileCall = true;

    public Repository(String fp, PrgState initial) {programs = new ArrayList<>(); programs.add(initial); logFilePath = fp;}

    @Override
    public List<PrgState> getPrgList() { return programs; }
    @Override
    public void setPrgList(List<PrgState> newList) { programs = newList; }

    @Override
    public PrgState getMainPrg() {return programs.get(0);}

    @Override
    public void logPrgStateExec(PrgState prg) {
        try{
            if (this.firstFileCall)
            {
                PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, false)));
                logFile.write(prg.toFile());
                logFile.close();
                this.firstFileCall = false;
                return;
            }
            PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
            logFile.write(prg.toFile());
            logFile.close();
        }
        catch(IOException e){
           System.out.println(" ! Log file path was not found\n");
        }
    }
    @Override
    public void resetLogFile(){
        this.firstFileCall = true;
    }
}
