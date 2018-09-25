package hr.fer.tel.lab02.commands;

import hr.fer.tel.lab02.Command;
import hr.fer.tel.lab02.ConsoleAppState;
import hr.fer.tel.lab02.xml.XMLWalker;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileNotFoundException;
import java.util.function.BiFunction;
import java.util.function.Predicate;

public class AttributeCommand extends Command {
    public AttributeCommand(int id){
        super(id, "attribute", "attribute <name> <count>");
    }

    @Override
    public ConsoleAppState run(XMLWalker walker, String... args){
        int count = getCount(args.length > 1 ? args[1] : null);

        Predicate<XMLEvent> condition = xmlEvent -> {
            if ( xmlEvent.isStartElement() ) {
                StartElement startElement = xmlEvent.asStartElement();
                return startElement.getAttributeByName(new QName(
                        startElement.getName().getNamespaceURI(),
                        args[0],
                        startElement.getName().getPrefix())) != null;
            }
            return false;
        };

        BiFunction<XMLEvent, XMLEventReader, String> mapper = (xmlEvent, xmlEventReader) -> {
//            StartElement startElement = xmlEvent.asStartElement();
//            return startElement.getAttributeByName(new QName(
//                    startElement.getName().getNamespaceURI(),
//                    args[0],
//                    startElement.getName().getPrefix()
//            )).getValue();
            return xmlEvent.toString();
        };

        try {
            System.out.println(walker.walk(condition, mapper, count));
        } catch ( FileNotFoundException | XMLStreamException e ) {
            System.out.println(e.getMessage());
        }
        return ConsoleAppState.CONTINUE;
    }
}
