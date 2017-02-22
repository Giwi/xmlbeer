package org.giwi.xml;

import java.io.File;
import java.net.URISyntaxException;

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
            long start = System.currentTimeMillis();
            new DomParser().getList(new File(Main.class.getResource("/beers.xml").toURI()));
            System.out.println("DOM : " + (System.currentTimeMillis() - start) + " ms");

            start = System.currentTimeMillis();
            new SaxParser().getList(new File(Main.class.getResource("/beers.xml").toURI()));
            System.out.println("SAX : " + (System.currentTimeMillis() - start) + " ms");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }
}
