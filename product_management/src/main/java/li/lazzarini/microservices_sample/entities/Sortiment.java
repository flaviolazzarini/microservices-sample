package li.lazzarini.microservices_sample.entities;

import li.lazzarini.microservices_sample.utils.ObjectIdDeserializer;
import li.lazzarini.microservices_sample.utils.ObjectIdSerializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.bson.types.ObjectId;

import java.util.Objects;

/**
 * Datamodel for Sortiment
 *
 * @since: 14.05.2019
 * @author: Matej Mrnjec
 */
public class Sortiment implements Entity {
    @JsonSerialize(using= ObjectIdSerializer.class)
    private ObjectId id;
    private String name;

    private Sortiment() {
    }

    public Sortiment(ObjectId id, String name) {
        this.id = id;
        this.name = name;
    }

    @JsonProperty("_id")
    @JsonDeserialize(using = ObjectIdDeserializer.class)
    public ObjectId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sortiment sortiment = (Sortiment) o;
        return id.equals(sortiment.id) &&
                name.equals(sortiment.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Sortiment{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
