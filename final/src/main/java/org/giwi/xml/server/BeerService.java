package org.giwi.xml.server;

import org.apache.cxf.feature.Features;
import org.apache.cxf.feature.LoggingFeature;
import org.giwi.xml.Beer;

import javax.jws.WebService;
import java.util.List;

/**
 * The interface Beer service.
 */
@WebService
@Features(classes = {LoggingFeature.class})
public interface BeerService {
    /**
     * Gets list.
     *
     * @return the list
     */
    List<Beer> getList();

    /**
     * Gets beer.
     *
     * @param id the id
     *
     * @return the beer
     */
    Beer getBeer(String id);

    /**
     * Add beer.
     *
     * @param beer the beer
     */
    void addBeer(Beer beer);

    /**
     * Del beer.
     *
     * @param id the id
     */
    void delBeer(String id);
}
