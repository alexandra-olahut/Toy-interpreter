package Exceptions;

public class ADTException extends RuntimeException {

    public ADTException(String msg) {super(msg);}
    @Override
    public String getMessage(){
        return "(!) ADT error : \n" + super.getMessage();
    }
}
