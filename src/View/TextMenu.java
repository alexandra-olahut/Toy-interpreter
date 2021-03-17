package View;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class TextMenu {

    private final Map<String, Command> commands;
    public TextMenu() {commands = new HashMap<>();}

    public void addCommand(Command c){
        commands.put(c.getKey(), c);
    }
    public List<Command> getCommands() {return commands.values().stream().collect(Collectors.toList());}

    private void printMenu(){
        System.out.println("------------------------------------------------------------------------------");
        for(Command c : commands.values()){
            String line = String.format("%4s : %s", c.getKey(), c.getDescription());
            System.out.println(line);
        }
    }

    public void show(){
        Scanner scanner = new Scanner(System.in);
        while (true){
            printMenu();
            System.out.println("Input option: ");
            String key = scanner.nextLine();
            Command c = commands.get(key);

            if(c == null){
                System.out.println("Invalid option");
                continue;
            }
            c.execute();
        }
    }
}
