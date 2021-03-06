package li.lazzarini.microservices_sample.micro;

import li.lazzarini.microservices_sample.entities.Order;
import li.lazzarini.microservices_sample.entities.State;
import li.lazzarini.microservices_sample.micro.filters.OrderFilter;
import li.lazzarini.microservices_sample.mongo.AdapterFactory;
import li.lazzarini.microservices_sample.mongo.OrderAdapter;
import com.mongodb.client.model.Filters;
import org.bson.types.ObjectId;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OrderService {

    private final AdapterFactory adapterFactory;
    public static final String USERNAME_FIELD = "userName";
    public static final String STAUS_FIELD = "state";

    public OrderService() {
        this.adapterFactory = new AdapterFactory();
    }


    public void createOrder(Order order) throws IOException {
        order.setState(State.PENDING);
        order.setId(new ObjectId());
        OrderAdapter adapter = (OrderAdapter) this.adapterFactory.getAdapter(Order.class);
        adapter.create(order);
    }

    public List<Order> getAllOrders() throws IOException {
        OrderAdapter adapter = (OrderAdapter) this.adapterFactory.getAdapter(Order.class);
        return adapter.getAll();
    }

    public void completeOrder(String id) throws IOException {
        OrderAdapter adapter = (OrderAdapter) this.adapterFactory.getAdapter(Order.class);
        adapter.completeOrder(id);
    }

    public void cancelOrder(String id) throws IOException {
        OrderAdapter adapter = (OrderAdapter) this.adapterFactory.getAdapter(Order.class);
        adapter.cancelOrder(id);
    }

    public List<Order> getOrdersByUser(String username) throws IOException {
        OrderAdapter adapter = (OrderAdapter) this.adapterFactory.getAdapter(Order.class);
        return adapter.getBy(Filters.eq(USERNAME_FIELD, username));
    }
    public List<Order> getOrdersByStatus(int statecode) throws IOException {
        OrderAdapter adapter = (OrderAdapter) this.adapterFactory.getAdapter(Order.class);
        return adapter.getBy(Filters.eq(STAUS_FIELD, statecode));
    }

    public List<Order> getCompletedOrdersByUser(String username) throws IOException {
        OrderAdapter adapter =(OrderAdapter) this.adapterFactory.getAdapter(Order.class);
        return adapter.getByUserAndState(username, State.ORDERD);
    }

    public List<Order> getOrders(OrderFilter filter) throws IOException {
        switch (filter.getField()) {
            case USERNAME:
                return this.getOrdersByUser(filter.getContent());
            case STATUS:
                return this.getOrdersByStatus(Integer.parseInt(filter.getContent()));
            case NO_FILTER:
                return this.getAllOrders();
            case COMPLETED_BY_USER:
                return this.getCompletedOrdersByUser(filter.getContent());
            default:
                return new ArrayList<>();
        }
    }
}
