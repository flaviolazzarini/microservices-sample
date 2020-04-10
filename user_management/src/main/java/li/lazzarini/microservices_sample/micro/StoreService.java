package li.lazzarini.microservices_sample.micro;

import li.lazzarini.microservices_sample.entities.Store;
import li.lazzarini.microservices_sample.mongo.AdapterFactory;
import li.lazzarini.microservices_sample.mongo.StoreAdapter;

import java.io.IOException;
import java.util.List;

public class StoreService {
    private final AdapterFactory adapterFactory;

    public StoreService() {
        this.adapterFactory = new AdapterFactory();
    }

    public List<Store> getAllStores() throws IOException {
        StoreAdapter adapter = (StoreAdapter) this.adapterFactory.getAdapter(Store.class);
        return adapter.getAll();
    }
}
