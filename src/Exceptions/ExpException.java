package Exceptions;

public class ExpException extends RuntimeException {

    public ExpException(String msg) {super(msg);}
    @Override
    public String getMessage(){
        return "(!) Expression evaluation error : \n" + super.getMessage();
    }
}
