package ua.levelup;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.FileWriter;
import java.io.IOException;

public class ConfigurationSAXParser extends DefaultHandler {
    private FileWriter fw;
    @Override
    public void startDocument() throws SAXException {
        if (fw==null){
            try {
                fw = new FileWriter("Configuration.java", true);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    @Override
    public void endDocument() throws SAXException {
        if(fw!=null){
            try {
                fw.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } ;
    }
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        switch (qName){
            case "package":
                writePack(attributes.getValue("name"));
                return;
            case "type":
                writeType(attributes.getValue("name"), attributes.getValue("access_modifier"));
                return;
            case "field":
                writeField(attributes.getValue("name"), attributes.getValue("type"), false);
                return;
            case "method":
                writeMethod(attributes.getValue("name"),
                        attributes.getValue("access_modifier"),
                        attributes.getValue("type"),attributes.getValue("return_type"));
                return;
            case "param":
                writeField(attributes.getValue("name"), attributes.getValue("type"),
                        attributes.getValue("isArray").toCharArray()[0]=='T');
                return;
            case "systemprint":
                writePSVM(attributes.getValue("arg"));
        }
    }
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        switch (qName){
            case "type":
            case "method":
                writeToFile("}"+'\n');
                return;
            case "params":
                writeToFile("){"+'\n');
                return;
            case "field":
            case "package":
                writeToFile(";"+'\n');
                return;
            case "statament":
                writeToFile(";"+'\n');
        }
        ;
    }

    //writing methods
    private void writePack(String packName){
        writeToFile("package " + packName);
    }
    private void writeType(String clName, String accMod){
        writeToFile(accMod + " class " + clName + "{");
    }
    private void writeField(String fName, String fType, boolean isArray){
        writeToFile(fType + (isArray?"[]":"") + " " + fName);
    }
    private void writeMethod(String mName, String mAccMod, String mType, String retType){
        writeToFile(mAccMod + " " + " " + mType + " " + retType + " " + mName + "(");
    }
    private void writePSVM(String textParam){
        writeToFile("System.out.println(\""+ textParam + "\")");
    }
    private void writeToFile(String string){
        try {
            fw.write(string);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
