package Model.ADT.Interfaces;

import Model.Values.Value;

import java.util.HashMap;
import java.util.Map;

public interface IHeap {

    Value getValue(int address);
    void set(int address, Value val);
    int add(Value val);
    boolean isAllocated(int address);

    Map<Integer, Value> getContent();
    void setContent(HashMap<Integer, Value> newContent);

    String toFile();
}
