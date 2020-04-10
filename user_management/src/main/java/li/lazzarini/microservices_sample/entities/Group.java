package li.lazzarini.microservices_sample.entities;

import li.lazzarini.microservices_sample.utils.ObjectIdDeserializer;
import li.lazzarini.microservices_sample.utils.ObjectIdSerializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.bson.types.ObjectId;

import java.util.Objects;

/**
 * Datamodel for Group
 *
 * @since: 23.04.2019
 * @author: Matej Mrnjec
 */
public class Group implements Entity {
    @JsonSerialize(using= ObjectIdSerializer.class)
    private ObjectId id;
    private String name;

    private Group(){}

    /**
     * Constructor
     * @param id Unigue Group id
     * @param name Group Name
     */
    public Group(ObjectId id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return id.equals(group.id) &&
                name.equals(group.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
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
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
