package Controller;

import Model.ADT.IDict;
import Model.ADT.IHeap;
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










    /*
    public static Map<Integer, Value> unsafeGarbageCollector(List<Integer> addresses, Map<Integer,Value> heap){
        return heap.entrySet().stream()
                .filter(e->addresses.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public static Map<Integer, Value> safeGarbageCollector(IDict<String,Value> symTable, IHeap heap){
        List<Integer> addresses = getAllAddr(symTable.getContent().values(), symTable, heap);

        return heap.getContent().entrySet().stream()
                .filter(e->addresses.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }


    public static List<Integer> getAddrFromSymTable(Collection<Value> symTableValues) {
        return symTableValues.stream()
                .filter(v->v instanceof RefValue)
                .map(v->{RefValue v1 = (RefValue)v; return v1.getAddress();})
                .collect(Collectors.toList());
    }

    public static List<Integer> getAllAddr(IDict<String,Value> symTable, IHeap hp) {
        List<Integer> addr = new ArrayList<>();

        for(Value val : symTable.getContent().values()){
            while(val instanceof RefValue){
                int address = ((RefValue) val).getAddress();
                addr.add(address);

                if(address==0)
                    break;
                try {
                    val = new ReadHpExp(new ValueExp(val)).evaluate(symTable, hp); }
                catch(Exception ignored) {}
            }
        }
        return addr;
    }
    */
}
