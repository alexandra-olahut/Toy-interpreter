package Model.ADT.Interfaces;

import java.util.Iterator;
import java.util.List;

public interface IList<T> {

    void append(T newElem);
    List<T> getContent();

    String toFile();
}
