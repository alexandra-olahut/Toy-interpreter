package View;

public class ExitCommand extends Command {

    public ExitCommand(String k, String d) {super(k,d);}

    @Override
    public void typecheck() {}

    @Override
    public void execute() {
        System.exit(0);
    }

    @Override
    public void reset() {}
}
