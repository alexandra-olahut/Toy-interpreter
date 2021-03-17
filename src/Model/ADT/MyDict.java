package Model.ADT;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class MyDict<K,V> implements IDict<K,V>{

    private final HashMap<K,V> elements;
    public MyDict() {elements = new HashMap<>();}
    public MyDict(HashMap<K,V> el) {elements=el;}

    @Override
    public void add(K key, V value) { elements.put(key,value); }
    @Override
    public void update(K key, V newValue) { elements.replace(key, newValue); }
    @Override
    public V lookup(K key) { return elements.get(key); }
    @Override
    public boolean isDefined(K key) { return elements.containsKey(key); }

    @Override
    public Map<K,V> getContent() {return elements;}
    @Override
    public IDict<K,V> copy() {
        Map<K,V> elemCopy = elements.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        return new MyDict<>((HashMap<K, V>) elemCopy);
    }

    @Override
    public String toString() {
        String result = "{";
        int pos = 0;
        for (K key : elements.keySet()) {
            if (pos == 0) result += "" + key + "->" + elements.get(key);
            else result += "," + key + "->" + elements.get(key);
            pos++;
        }
        result+="}";
        return result;
    }

    @Override
    public String toFile() {
        String result = "  Sym Table:";
        for (K key : elements.keySet()) {
            result += "\n" + key + "-->" + elements.get(key);
        }
        return result;
    }

    @Override
    public Object clone() {
        try { return super.clone(); }
        catch(Exception ignored) {}
        return null;
    }
}
