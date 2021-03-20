package Model.ADT.Interfaces;

import java.util.Map;

public interface ILatchTable {

    void add(Integer key, Integer v);
    void update(Integer key, Integer v);
    Integer get(Integer key);
    boolean contains(Integer key);

    Integer getNextFree();
    Map<Integer,Integer> getContent();

    String toFile();
}
