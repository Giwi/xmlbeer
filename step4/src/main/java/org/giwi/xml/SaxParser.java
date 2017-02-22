package org.giwi.xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Sax parser.
 */
public class SaxParser extends DefaultHandler {

    private List<Beer> beers;
    private Beer currentBeer;
    private String currentValue;

    /**
     * Gets list.
     *
     * @param xmlFile the xml file
     *
     * @return the list
     */
    List<Beer> getList(File xmlFile) {
        SAXParserFactory spf = SAXParserFactory.newInstance();
        spf.setNamespaceAware(true);
        try {
            SAXParser saxParser = spf.newSAXParser();
            XMLReader xmlReader = saxParser.getXMLReader();
            xmlReader.setContentHandler(this);
            xmlReader.parse(xmlFile.getAbsolutePath());
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return beers;
    }

    @Override
    public void startDocument() throws SAXException {
        beers = new ArrayList<>();
    }

    @Override
    public void characters(char[] caracteres, int debut, int longueur) {
        currentValue += new String(caracteres, debut, longueur);
    }

    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
        currentValue = "";
        if ("beer".equals(localName)) {
            currentBeer = new Beer();
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        switch (localName) {
            case "beer" :
                beers.add(currentBeer);
                break;
            case "description":
                currentBeer.setDescription(currentValue);
                break;
            case "id":
                currentBeer.setId(currentValue);
                break;
            case "name":
                currentBeer.setName(currentValue);
                break;
            case "img":
                currentBeer.setImg(currentValue);
                break;
            case "alcohol":
                currentBeer.setAlcohol(Double.valueOf(currentValue));
                break;
            default:
                break;
        }
    }
}
