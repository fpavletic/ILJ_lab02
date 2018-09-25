package hr.fer.tel.lab02.xml;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Predicate;

public class XMLWalker {

    private final String path;
    private final XMLInputFactory factory;

    public XMLWalker(String path){
        this.path = path;
        factory = XMLInputFactory.newInstance();
    }

    public List<String> walk(Predicate<XMLEvent> condition, BiFunction<XMLEvent, XMLEventReader, String> mapper, int count) throws FileNotFoundException, XMLStreamException{
        List<String> output = new ArrayList<>();

        XMLEventReader reader = factory.createXMLEventReader(new FileInputStream(path));
        while ( count != 0 && reader.hasNext() ) {
            XMLEvent event = reader.nextEvent();
            if ( condition.test(event) ) {
                count--;
                output.add(mapper.apply(event, reader));
            }
        }

        return output;
    }


}
