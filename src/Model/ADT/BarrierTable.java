package Model.ADT;

import Model.ADT.Interfaces.IBarrierTable;
import javafx.util.Pair;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BarrierTable implements IBarrierTable {

    private Map<Integer, Pair<Integer, List<Integer>>> elements;
    private Integer firstFree;
    public BarrierTable() {elements = Collections.synchronizedMap(new HashMap<>()); firstFree=0;}

    @Override
    public Map<Integer, Pair<Integer, List<Integer>>> getContent(){
        return elements;
    }

    @Override
    public void add(Integer key, Pair<Integer, List<Integer>> v) {
        elements.put(key, v);
    }
    @Override
    public void update(Integer key, Pair<Integer, List<Integer>> v) {
        elements.replace(key, v);
    }
    @Override
    public Pair<Integer, List<Integer>> get(Integer key) {
        return elements.get(key);
    }
    @Override
    public boolean contains(Integer key) {
        return elements.containsKey(key);
    }

    @Override
    public Integer getNextFree() {return firstFree++;}

    @Override
    public String toString(){
        String result = "{";
        int pos = 0;
        for (Integer elem : elements.keySet()) {
            if (pos == 0) result += "" + elem + "-> ("+elements.get(elem).getKey()+" : "+elements.get(elem).getValue()+")";
            else result += "," + elem + "-> ("+elements.get(elem).getKey()+" : "+elements.get(elem).getValue()+")";
            pos++;
        }
        result+="}";
        return result;
    }

    @Override
    public String toFile(){
        String result = "  BarrierTable:";
        for (Integer key : elements.keySet()) {
            result += "\n" + key + "-->" + elements.get(key);
        }
        return result;
    }
}
