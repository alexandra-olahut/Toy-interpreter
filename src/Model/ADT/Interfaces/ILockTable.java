package Model.ADT.Interfaces;

import javafx.util.Pair;

import java.util.List;
import java.util.Map;

public interface ILockTable {

    void add(Integer key, Integer v);
    void update(Integer key, Integer v);
    Integer get(Integer key);
    boolean contains(Integer key);

    Map<Integer, Integer> getContent();

    Integer getNextFree();
    String toFile();
}
