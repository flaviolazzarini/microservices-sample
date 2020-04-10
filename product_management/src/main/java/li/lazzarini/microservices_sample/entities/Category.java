package li.lazzarini.microservices_sample.entities;

import li.lazzarini.microservices_sample.utils.ObjectIdDeserializer;
import li.lazzarini.microservices_sample.utils.ObjectIdSerializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.bson.types.ObjectId;

import java.util.Objects;

/**
 * Datamodel for Category
 *
 * @since: 14.05.2019
 * @author: Matej Mrnjec
 */
public class Category implements Entity {
    @JsonSerialize(using= ObjectIdSerializer.class)
    private ObjectId id;
    private String name;

    private Category() {
    }

    public Category(ObjectId id, String name) {
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
        Category category = (Category) o;
        return id.equals(category.id) &&
                name.equals(category.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
