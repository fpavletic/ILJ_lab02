package hr.fer.tel.lab02;

import hr.fer.tel.lab02.xml.XMLWalker;

import java.util.Arrays;
import java.util.Scanner;

public class ConsoleApp implements Runnable {

    XMLWalker walker;

    public ConsoleApp(String xmlPath){
        walker = new XMLWalker(xmlPath);
    }


    @Override
    public void run(){
        System.out.println("Welcome to XMLParser, a simple XML parsing tool.");
        ConsoleAppState state = ConsoleAppState.CONTINUE;
        try ( Scanner sysInScanner = new Scanner(System.in) ) {
            while ( state == ConsoleAppState.CONTINUE ) {
                System.out.print(">");
                String[] inputSplit = sysInScanner.nextLine().split(" ");
                Command command = CommandUtil.getCommand(inputSplit[0]);
                if ( command == null ) {
                    System.out.println("Unknown command, use \"help\" for a list of available commands");
                    continue;
                }
                state = command.run(walker, Arrays.copyOfRange(inputSplit, 1, inputSplit.length));
            }
        }
    }

    public static void main(String[] args){
        new ConsoleApp(args[0]).run();
    }
}
