package li.lazzarini.microservices_sample.mongo;

import li.lazzarini.microservices_sample.entities.Entity;
import com.mongodb.client.model.Filters;
import org.bson.types.ObjectId;

import java.util.NoSuchElementException;

/**
 * Type EntityAdapter (needed for {@link AdapterFactory})
 * It adapts the MongoAdapter so Entity's can be used directly.
 * It extends the EntityAdapter and offers some default get Functions.
 *
 * @since: 14.05.2019
 * @author: Matej Mrnjec
 */
public interface EntityAdapterExtended<T extends Entity> extends EntityAdapter<T> {
    String ID_FIELD = "_id";

    /**
     * Get the Field Name for ID
     * @return ID Field name
     */
    default String getIdField(){
        return ID_FIELD;
    }

    /**
     * Gets a Entity by ID from the Database.
     * Uses {@link EntityAdapterExtended#getIdField()} to determine the Field Id.
     * If Field Id differs from {@link EntityAdapterExtended#ID_FIELD} than just override
     * {@link EntityAdapterExtended#getIdField()}
     * @param id Entity id
     * @return Entity with the ID
     * @throws NoSuchElementException If no Entity is found
     */
    default T getById(String id) {
        return getOneBy(Filters.eq(getIdField(), new ObjectId(id)));
    }
}
