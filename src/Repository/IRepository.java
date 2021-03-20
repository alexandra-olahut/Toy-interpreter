package Repository;

import Model.ProgramState.PrgState;

import java.util.List;

public interface IRepository {

    List<PrgState> getPrgList();
    void setPrgList(List<PrgState> newList);

    PrgState getMainPrg();
    void logPrgStateExec(PrgState prg);
    void resetLogFile();
}
