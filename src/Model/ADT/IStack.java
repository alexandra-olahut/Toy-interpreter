package Model.ADT;

import Exceptions.ADTException;

import java.util.List;

public interface IStack<T> {

    T pop() throws ADTException;
    void push(T v);
    boolean isEmpty();

    List<T> getContent();

    String toFile();
}
