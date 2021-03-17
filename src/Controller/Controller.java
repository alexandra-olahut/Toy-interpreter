package Controller;

import Model.ProgramState.PrgState;
import Model.Values.Value;
import Repository.IRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controller {

    private final IRepository repo;
    private ExecutorService executor;
    private final boolean displayFlag;

    public Controller(IRepository r) {repo=r; displayFlag=true;}


//    void setFlag(boolean f) {displayFlag=f;}

    public List<PrgState> removeCompletedPrg(List<PrgState> programs) {
        return programs.stream()
                .filter(PrgState::isNotCompleted)
                .collect(Collectors.toList());
    }

    public void oneStepForAllPrg(List<PrgState> prgList) throws Exception {

        List<Callable<PrgState>> callList = prgList.stream()
                .map((PrgState p)->(Callable<PrgState>)(p::oneStep))
                .collect(Collectors.toList());

        List<PrgState> newPrgList = executor.invokeAll(callList).stream()
                .map(future-> {
                    try {
                        return future.get();
                    } catch (Exception e) {
                        throw new RuntimeException(e.getMessage());
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        prgList.addAll(newPrgList);
        logState();
        repo.setPrgList(prgList);
    }


    public void allStep() throws Exception {

        executor = Executors.newFixedThreadPool(2);

        List<PrgState> prgList = removeCompletedPrg(repo.getPrgList());
        prgList.forEach(repo::logPrgStateExec);
        if (displayFlag)
            prgList.forEach(System.out::println);

        while(prgList.size() > 0) {
            PrgState prg = repo.getMainPrg();

            prg.getHeap().setContent((HashMap<Integer, Value>) GarbageCollector.safestGarbageCollector(repo));

            oneStepForAllPrg(prgList);
            prgList = removeCompletedPrg(repo.getPrgList());
        }
        executor.shutdownNow();

        repo.setPrgList(prgList);
    }


    public void runOneStep() throws Exception {

        List<PrgState> prgList = removeCompletedPrg(repo.getPrgList());

        repo.getMainPrg().getHeap().setContent((HashMap<Integer, Value>) GarbageCollector.safestGarbageCollector(repo));
        oneStepForAllPrg(prgList);

//        prgList = removeCompletedPrg(repo.getPrgList());
//        repo.setPrgList(prgList);

        if(prgList.size() == 0)
            executor.shutdownNow();
    }

    public void logState(){
        List<PrgState> prgList = repo.getPrgList();
        prgList.forEach(repo::logPrgStateExec);
        if(displayFlag)
            prgList.forEach(System.out::println);

    }

    public IRepository getRepo() {return repo;}
    public ExecutorService getExecutor() {return executor;}
    public void setExecutor() {executor = Executors.newFixedThreadPool(2);}




/*
    public void allStep() throws Exception{
        PrgState prg = repo.getCurrentPrg();
        repo.logPrgStateExec();
        if (displayFlag)
            System.out.println("Initial state:");
        System.out.println(prg);

        while (!prg.getExeStack().isEmpty()){
            oneStep(prg);

            prg.getHeap().setContent((HashMap<Integer, Value>) GarbageCollector.safeGarbageCollector(prg.getSymTable(), prg.getHeap()));
          unsafeGarbageCollector(
                    GarbageCollector.getAllAddr(prg.getSymTable().getContent().values(), prg.getSymTable(), prg.getHeap()),
                    prg.getHeap().getContent()));

            repo.logPrgStateExec();
            if(displayFlag)
                System.out.println(prg);
        }
    }

    public void printOutput(){
        System.out.println(" --- Program output: ---");
        PrgState prg = repo.getCurrentPrg();
        IList<Value> output = prg.getOut();

        Iterator<Value> it = output.iterator();
        while(it.hasNext()){
            Value item = it.next();
            System.out.println(item);
        }
    }
*/
}
