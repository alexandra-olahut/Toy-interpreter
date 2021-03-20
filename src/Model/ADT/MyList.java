package Model.ADT;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MyList<T> implements IList<T> {

    private final List<T> elements;
    public MyList() {elements = Collections.synchronizedList(new ArrayList<>());}

    @Override
    public void append(T newElem) { elements.add(newElem); }

    @Override
    public List<T> getContent() {return elements;}

    @Override
    public String toString() {
        String result = "{";
        int pos = 0;
        for (T elem : elements) {
            if (pos == 0) result += "" + elem;
            else result += "," + elem;
            pos++;
        }
        result+="}";
        return result;
    }

    @Override
    public String toFile() {
        String result = "  Out:";
        for (T elem : elements) {
            result += "\n" + elem;
        }
        return result;
    }
}
