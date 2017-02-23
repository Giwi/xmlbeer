package org.giwi.xml;

import org.apache.cxf.jaxws.JaxWsServerFactoryBean;
import org.giwi.xml.data.BeersList;
import org.giwi.xml.server.BeerService;
import org.giwi.xml.server.BeerServiceImpl;

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
    public static void main(String... args) throws InterruptedException, URISyntaxException {
        BeersList.getInstance().setBeerList(new SaxParser().getList(new File(Main.class.getResource("/beers.xml").toURI())));
        BeerService beerService = new BeerServiceImpl();
        //create WebService service factory
        JaxWsServerFactoryBean factory = new JaxWsServerFactoryBean();
        //register WebService interface
        factory.setServiceClass(BeerService.class);
        //publish the interface
        factory.setAddress("http://localhost:9000/BeerService");
        factory.setServiceBean(beerService);
        //create WebService instance
        factory.create();
        System.out.println("Server ready...");
    }
}
