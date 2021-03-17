package Exceptions;

public class TypeException extends RuntimeException {

    public TypeException(String msg) {super(msg);}
    @Override
    public String getMessage(){
        return "(!) Type checking error : \n" + super.getMessage();
    }
}
