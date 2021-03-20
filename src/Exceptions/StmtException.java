package Exceptions;

public class StmtException extends RuntimeException {

    public StmtException(String msg) {super(msg);}
    @Override
    public String getMessage(){
        return "(!) Statement execution error : \n" + super.getMessage();
    }
}
