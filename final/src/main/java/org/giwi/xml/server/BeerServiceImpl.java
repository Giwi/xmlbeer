package org.giwi.xml.server;

import org.giwi.xml.Beer;
import org.giwi.xml.data.BeersList;

import javax.jws.WebService;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Beer service.
 */
@WebService
public class BeerServiceImpl implements BeerService {

    @Override
    public List<Beer> getList() {
        return BeersList.getInstance().getBeerList();
    }

    @Override
    public Beer getBeer(String id) {
        List<Beer> beers = BeersList.getInstance().getBeerList();
        for (Beer beer : beers) {
            if (beer.getId().equals(id)) {
                return beer;
            }
        }
        return null;
    }

    @Override
    public void addBeer(Beer beer) {
        BeersList.getInstance().getBeerList().add(beer);
    }

    @Override
    public void delBeer(String id) {
        List<Beer> beers = BeersList.getInstance().getBeerList();
        List<Beer> newBeers = new ArrayList<>();
        for (Beer beer : beers) {
            if (!beer.getId().equals(id)) {
                newBeers.add(beer);
            }
        }
        BeersList.getInstance().setBeerList(newBeers);
    }
}
