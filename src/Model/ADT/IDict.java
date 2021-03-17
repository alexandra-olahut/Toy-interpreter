package Model.ADT;

import java.util.HashMap;
import java.util.Map;

public interface IDict<K,V> extends Cloneable {

    void add(K key, V value);
    void update(K key, V newValue);
    V lookup(K key);
    boolean isDefined(K key);

    Map<K,V> getContent();
    IDict<K,V> copy();

    String toFile();
    Object clone();
}
