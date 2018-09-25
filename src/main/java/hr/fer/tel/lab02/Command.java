package hr.fer.tel.lab02;

import hr.fer.tel.lab02.xml.XMLWalker;

import java.util.Objects;
import java.util.Optional;

public abstract class Command {

    private int id;
    private String name;
    private String help;

    public Command(int id, String name, String help){
        this.id = id;
        this.name = name;
        this.help = help;
    }

    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String getHelp(){
        return help;
    }

    @Override
    public boolean equals(Object o){
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;
        Command command = (Command) o;
        return Objects.equals(name, command.name);
    }

    @Override
    public int hashCode(){
        return Objects.hash(name);
    }

    protected static int getCount(String s){
        Optional<Integer> tmp = (s == null ? Optional.empty() : parseInt(s));
        return tmp.isPresent() ? tmp.get() : -1;
    }

    protected static Optional<Integer> parseInt(String s){
        return !s.matches("\\d+") ? Optional.empty() : Optional.of(Integer.parseInt(s));
    }

    public abstract ConsoleAppState run(XMLWalker walker, String... args);

}
