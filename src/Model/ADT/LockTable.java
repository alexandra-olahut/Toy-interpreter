package Model.ADT;

import Model.ADT.Interfaces.ILockTable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class LockTable implements ILockTable {

    private Map<Integer, Integer> elements;
    private Integer firstFree;
    public LockTable() {elements = Collections.synchronizedMap(new HashMap<>()); firstFree=0;}

    @Override
    public Map<Integer, Integer> getContent(){
        return elements;
    }

    @Override
    public void add(Integer key, Integer v) {
        elements.put(key, v);
    }
    @Override
    public void update(Integer key, Integer v) {
        elements.replace(key, v);
    }
    @Override
    public Integer get(Integer key) {
        return elements.get(key);
    }
    @Override
    public boolean contains(Integer key) {
        return elements.containsKey(key);
    }

    @Override
    public synchronized Integer getNextFree() {return firstFree++;}

    @Override
    public String toString(){
        String result = "{";
        int pos = 0;
        for (Integer elem : elements.keySet()) {
            if (pos == 0) result += "" + elem + "-> "+elements.get(elem);
            else result += "," + elem + "-> "+elements.get(elem);
            pos++;
        }
        result+="}";
        return result;
    }

    @Override
    public String toFile(){
        String result = "  LockTable:";
        for (Integer key : elements.keySet()) {
            result += "\n" + key + "-->" + elements.get(key);
        }
        return result;
    }
}
