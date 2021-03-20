package Exceptions;

public class FileException extends RuntimeException {

    public FileException(String msg) {super(msg);}
    @Override
    public String getMessage() {return "(!) Files error : \n" + super.getMessage();}
}
