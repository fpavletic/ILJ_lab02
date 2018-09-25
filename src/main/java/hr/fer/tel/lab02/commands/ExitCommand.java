package hr.fer.tel.lab02.commands;

import hr.fer.tel.lab02.Command;
import hr.fer.tel.lab02.ConsoleAppState;
import hr.fer.tel.lab02.xml.XMLWalker;

public class ExitCommand extends Command {
    public ExitCommand(int id){
        super(id, "exit", "exit help");
    }

    @Override
    public ConsoleAppState run(XMLWalker walker, String... args){
        System.out.println("Exiting...");
        return ConsoleAppState.EXIT;
    }
}
