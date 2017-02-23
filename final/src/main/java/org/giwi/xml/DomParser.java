package org.giwi.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Dom parser.
 */
public class DomParser {

    /**
     * Gets list.
     *
     * @param xmlFile the xml file
     *
     * @return the list
     */
    List<Beer> getList(File xmlFile) {
        List<Beer> beers = new ArrayList<>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document= builder.parse(xmlFile);
            Element racine = document.getDocumentElement();
            NodeList xmlBeers = racine.getChildNodes();
            for (int i = 0; i<xmlBeers.getLength(); i++) {
                if(xmlBeers.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    final Element b = (Element) xmlBeers.item(i);
                    Beer beer = new Beer();
                    beer.setAlcohol(Double.valueOf(b.getElementsByTagName("alcohol").item(0).getTextContent()));
                    beer.setDescription(b.getElementsByTagName("description").item(0).getTextContent());
                    beer.setId(b.getElementsByTagName("id").item(0).getTextContent());
                    beer.setName(b.getElementsByTagName("name").item(0).getTextContent());
                    beer.setImg(b.getElementsByTagName("img").item(0).getTextContent());
                    beers.add(beer);
                }
            }
        } catch (ParserConfigurationException | SAXException  | IOException e) {
            e.printStackTrace();
        }
        return beers;
    }
}
