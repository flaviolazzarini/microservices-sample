package li.lazzarini.microservices_sample.mongo;

import li.lazzarini.microservices_sample.entities.Entity;
import li.lazzarini.microservices_sample.entities.Order;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * Factory which produces the fitting Adapter.
 *
 * @since: 08.05.2019
 * @author: Matej Mrnjec
 */
public final class AdapterFactory {
    private static final Logger LOGGER = LogManager.getLogger(AdapterFactory.class);
    private static final MongoDbConfig config = new MongoDbConfig();
    private static MongoClient mongoClient = new MongoClient(new MongoClientURI(config.getConnectionAddress()));

    // Mappings between Entity and EntityAdapter
    private static Map<Class<? extends Entity>, Class<? extends EntityAdapter>> adapterMap = new HashMap<>(){{
        put(Order.class, OrderAdapter.class);
    }};

    /**
     * Returns the fitting EntityAdapter
     * @param entity Entity for which the Adapter is needed
     * @param <T> Entity
     * @return fitting EntityAdapter
     * @throws IOException If a Error occures while instantiating
     */
    public final <T extends Entity> EntityAdapter getAdapter(Class<T> entity) throws IOException{
        MongoAdapter<T> adapter = new MongoAdapter<>(mongoClient);
        adapter.setType(entity);
        try {
            return adapterMap.get(entity).getDeclaredConstructor(MongoAdapter.class).newInstance(adapter);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new IOException(e);
        }
    }
}
