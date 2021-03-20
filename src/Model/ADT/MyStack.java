package Model.ADT;

import Exceptions.ADTException;
import Model.ADT.Interfaces.IStack;

import java.util.ArrayList;
import java.util.List;

public class MyStack<T> implements IStack<T> {

    private final List<T> elements;
    public MyStack() {elements = new ArrayList<>();}

    @Override
    public T pop() throws ADTException {
        if(elements.isEmpty())
            throw new ADTException("Execution stack is empty\n");
        T top = elements.get(elements.size()-1);
        elements.remove(elements.size()-1);
        return top;
    }
    @Override
    public void push(T newElem) { elements.add(newElem); }
    @Override
    public boolean isEmpty() { return elements.isEmpty(); }

    public List<T> getContent() {return elements;}

    @Override
    public String toString() {
        String result = "{";
        for (int pos=elements.size()-1; pos>=0; pos--) {
            if (pos == elements.size()-1) result += "" + elements.get(pos);
            else result += "|" + elements.get(pos);
        }
        result+="}";
        return result;
    }

    @Override
    public String toFile() {
        String result = "  Exe Stack:";
        for (int pos=elements.size()-1; pos>=0; pos--) {
            result += "\n" + elements.get(pos);
        }
        return result;
    }
}
