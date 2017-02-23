package org.giwi.xml.client;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.giwi.xml.Beer;
import org.giwi.xml.server.BeerService;

/**
 * The type Client.
 */
public class Client {
    private Client() {
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        //create WebService client proxy factory
        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
        //register WebService interface
        factory.setServiceClass(BeerService.class);
        //set webservice publish address to factory.
        factory.setAddress("http://localhost:9000/BeerService");
        BeerService beerService = (BeerService) factory.create();
        System.out.println("List size : " + beerService.getList().size());
        System.out.println("Add beer");
        Beer b = new Beer();
        b.setAlcohol(4.5);
        b.setImg("img/kro.png");
        b.setDescription("La bière qu'est pas très bonne");
        b.setName("Kro");
        b.setId("kro");
        beerService.addBeer(b);
        System.out.println("List size : " + beerService.getList().size());
        System.out.println("Del beer");
        beerService.delBeer("kro");
        System.out.println("List size : " + beerService.getList().size());
        System.exit(0);
    }
}
