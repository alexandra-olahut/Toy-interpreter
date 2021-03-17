package Model.ADT;

import Model.Values.Value;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Heap implements IHeap {

    private Map<Integer, Value> addresses;
    private Integer firstFree;

    public Heap() {addresses = Collections.synchronizedMap(new HashMap<>()); firstFree = 1;}


    @Override
    public boolean isAllocated(int address) {return addresses.containsKey(address);}
    @Override
    public Value getValue(int address) {return addresses.get(address);}
    @Override
    public void set(int address, Value val) {addresses.put(address,val);}
    @Override
    public int add(Value val) {
        int addr = firstFree;
        set(firstFree, val);
        firstFree++;
        return addr;
    }

    @Override
    public Map<Integer, Value> getContent() {return addresses;}
    @Override
    public void setContent(HashMap<Integer, Value> newContent) {addresses = newContent;}


    @Override
    public String toString() {
        String result = "{";
        int pos = 0;
        for (Integer key : addresses.keySet()) {
            if (pos == 0) result += "" + key + "->" + addresses.get(key);
            else result += "," + key + "->" + addresses.get(key);
            pos++;
        }
        result+="}";
        return result;
    }

    @Override
    public String toFile() {
        String result = "  Heap:";
        for (Integer key : addresses.keySet()) {
            result += "\n" + key + "-->" + addresses.get(key);
        }
        return result;
    }
}
