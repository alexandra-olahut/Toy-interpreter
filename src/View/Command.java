package View;

import Controller.Controller;

public abstract class Command {

    private final String key;
    private final String description;

    public Command(String k, String d) {key=k; description=d;}

    public abstract void execute();
    public abstract void typecheck();
    public String getKey() {return key;}
    public String getDescription() {return description;}
    public Controller getController() {return null;}

    public String toString() {return key + ". " + description;}

    public abstract void reset();
}
