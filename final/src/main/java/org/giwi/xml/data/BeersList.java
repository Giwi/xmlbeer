package org.giwi.xml.data;

import org.giwi.xml.Beer;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Beers list.
 */
public class BeersList {

    private List<Beer> beerList = new ArrayList<>();
    /**
     * Constructeur privé
     */
    private BeersList() {
    }

    /**
     * Holder
     */
    private static class SingletonHolder {
        /**
         * Instance unique non préinitialisée
         */
        private final static BeersList instance = new BeersList();
    }

    /**
     * Point d'accès pour l'instance unique du singleton
     *
     * @return the instance
     */
    public static BeersList getInstance() {
        return SingletonHolder.instance;
    }

    /**
     * Gets beer list.
     *
     * @return the beer list
     */
    public List<Beer> getBeerList() {
        return beerList;
    }

    /**
     * Sets beer list.
     *
     * @param beerList the beer list
     */
    public void setBeerList(List<Beer> beerList) {
        this.beerList = beerList;
    }
}
