package org.giwi.xml;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 * The type Main.
 */
public class Main {
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String... args) {
        try {
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer t = factory.newTransformer(new StreamSource(Main.class.getResourceAsStream("/style.xsl")));
            t.transform(new StreamSource(Main.class.getResourceAsStream("/beers.xml")),
                    new StreamResult("build/beers.html"));
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }
}
