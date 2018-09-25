package hr.fer.tel.lab02.commands;

import hr.fer.tel.lab02.Command;
import hr.fer.tel.lab02.CommandUtil;
import hr.fer.tel.lab02.ConsoleAppState;
import hr.fer.tel.lab02.xml.XMLWalker;

public class HelpCommand extends Command {
    public HelpCommand(int id){
        super(id, "help", "help");
    }

    @Override
    public ConsoleAppState run(XMLWalker walker, String... args){
        switch ( args.length ) {
            case 0:
                System.out.print(" | ");
                CommandUtil.getCommands().forEach(c -> System.out.format("%s[%d] | ", c.getName(), c.getId()));
                System.out.println();
                break;
            default:
                Command command = args[0].matches("\\d+") ?
                        CommandUtil.getCommand(Integer.parseInt(args[0])) : CommandUtil.getCommand(args[0]);
                if ( command == null ) {
                    System.out.println("Unknown command, use \"help\" for a list of available commands");
                    return ConsoleAppState.CONTINUE;
                }
                System.out.format("%s[%d]:%4$s\t%s %4$s", command.getName(), command.getId(), command.getHelp(), System.lineSeparator());
        }

        return ConsoleAppState.CONTINUE;
    }
}
