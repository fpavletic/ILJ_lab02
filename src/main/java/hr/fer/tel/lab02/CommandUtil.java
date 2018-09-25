package hr.fer.tel.lab02;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

public class CommandUtil {

    private static boolean isLinked = false;
    private static Map<String, Command> nameToCommand;
    private static Map<Integer, Command> idToCommand;

    public static Command getCommand(String name){
        if ( !isLinked ) {
            linkCommands();
        }
        return nameToCommand.get(name);
    }

    public static Command getCommand(int id){
        if ( !isLinked ) {
            linkCommands();
        }
        return idToCommand.get(id);
    }

    public static List<Command> getCommands(){
        if ( !isLinked ) {
            linkCommands();
        }
        return new ArrayList<>(nameToCommand.values());
    }

    private static void linkCommands(String... sourceFolders){

        nameToCommand = new HashMap<>();
        idToCommand = new HashMap<>();
        int id[] = new int[]{0};

        if ( sourceFolders.length == 0 ) {
            sourceFolders = new String[]{"./out/production/classes/hr/fer/tel/lab02/commands"};
        }

        Arrays.stream(sourceFolders).forEach(f -> {
            try {
                String classNamePrefix = f.substring(f.lastIndexOf("classes") + 8, f.length()).replace('/', '.') + '.';
                Files.walkFileTree(Paths.get(f), new SimpleFileVisitor<Path>() {

                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException{
                        try {
                            String className = file.getFileName().toString();
//                            "hr.fer.tel.lab02.commands."
                            className = classNamePrefix + className.substring(0, className.lastIndexOf("."));
                            ClassLoader classLoader = this.getClass().getClassLoader();
                            Command command = (Command) classLoader.loadClass(className).getConstructor(Integer.TYPE).newInstance(id[0]++);
                            nameToCommand.put(command.getName(), command);
                            idToCommand.put(command.getId(), command);
                        } catch ( Exception e ) {
                            System.out.println("Unable to link command: " + file);
                        }
                        return FileVisitResult.CONTINUE;
                    }
                });

            } catch ( IOException e ) {
                System.out.println("Unable to link commands, exiting...");
                System.exit(1);
            }
        });


        isLinked = true;

    }
}
