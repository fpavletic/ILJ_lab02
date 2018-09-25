package hr.fer.tel.lab02.commands;

import hr.fer.tel.lab02.Command;
import hr.fer.tel.lab02.ConsoleAppState;
import hr.fer.tel.lab02.xml.XMLWalker;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileNotFoundException;
import java.util.function.BiFunction;
import java.util.function.Predicate;

public class ElementCommand extends Command {
    public ElementCommand(int id){
        super(id, "element", "element help");
    }

    @Override
    public ConsoleAppState run(XMLWalker walker, String... args){
        int count = getCount(args.length > 1 ? args[1] : null);

        Predicate<XMLEvent> condition = xmlEvent -> {
            if ( xmlEvent.isStartElement() ) {
                StartElement startElement = xmlEvent.asStartElement();
                return startElement.getName().getLocalPart().equals(args[0]);
            }
            return false;
        };

        BiFunction<XMLEvent, XMLEventReader, String> mapper = (xmlEvent, xmlEventReader) -> {
            StringBuilder output = new StringBuilder(xmlEvent.toString());
            int depth = 1;
            while ( depth > 0 && xmlEventReader.hasNext() ) {
                try {
                    XMLEvent newXmlEvent = xmlEventReader.nextEvent();
                    if ( newXmlEvent.isStartElement() ) {
                        depth++;
                    }
                    if ( newXmlEvent.isEndElement() ) {
                        depth--;
                    }
                    output.append(newXmlEvent.toString());
                } catch ( XMLStreamException e ) {
                    //¯\_(ツ)_/¯
                }
            }
            return output.toString();
        };

        try {
            System.out.println(walker.walk(condition, mapper, count));
        } catch ( FileNotFoundException | XMLStreamException e ) {
            System.out.println(e.getMessage());
        }
        return ConsoleAppState.CONTINUE;
    }
}
