package Exceptions;

public class HeapException extends RuntimeException {

    public HeapException(String msg) {super(msg);}
    @Override
    public String getMessage() {return "(!) Heap error : \n" + super.getMessage();}
}
