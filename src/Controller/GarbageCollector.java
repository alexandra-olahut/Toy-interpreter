package Controller;

import Model.ADT.Interfaces.IDict;
import Model.ADT.Interfaces.IHeap;
import Model.ProgramState.PrgState;
import Model.Values.RefValue;
import Model.Values.Value;
import Repository.IRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GarbageCollector {

    public static Map<Integer, Value> safestGarbageCollector(IRepository repo){
        List<Integer> addresses = getAllAddresses(repo.getPrgList());
        IHeap heap = repo.getMainPrg().getHeap();

        return heap.getContent().entrySet().stream()
                .filter(e->addresses.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public static List<Integer> getAllAddresses (List<PrgState> prgs) {
        List<Integer> allAddresses = new ArrayList<>();

        prgs.stream()
                .map(prg -> getAddresses(prg.getSymTable(), prg.getHeap()))
                .forEach(allAddresses::addAll);
        return allAddresses;
    }

    public static List<Integer> getAddresses (IDict<String,Value> symTable, IHeap hp) {
        return symTable.getContent().values().stream()
                .filter(v->v instanceof RefValue)
                .flatMap(val->{
                    Stream.Builder<Integer> builder = Stream.builder();
                    while(val instanceof RefValue) {
                        int addr = ((RefValue) val).getAddress();
                        if (addr == 0)
                            break;
                        builder.accept(addr);
                        val = hp.getValue(addr);
                    }
                    return builder.build();
                    })
                .collect(Collectors.toList());
    }
}
