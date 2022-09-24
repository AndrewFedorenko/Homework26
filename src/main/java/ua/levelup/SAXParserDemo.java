package ua.levelup;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;

public class SAXParserDemo {

    public static void main(String[] args) {
        SAXParserFactory parserFactory = SAXParserFactory.newInstance();

        try {
            SAXParser saxParser = parserFactory.newSAXParser();
            saxParser.parse(Thread.currentThread().getContextClassLoader().getResourceAsStream("type.xml"),
                    new ConfigurationSAXParser());
        } catch (ParserConfigurationException | SAXException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println();
    }
}
