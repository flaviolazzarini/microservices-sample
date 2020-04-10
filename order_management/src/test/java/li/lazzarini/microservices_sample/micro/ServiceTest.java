package li.lazzarini.microservices_sample.micro;

import li.lazzarini.microservices_sample.bus.BusConnector;
import li.lazzarini.microservices_sample.bus.Response;
import li.lazzarini.microservices_sample.bus.Status;
import li.lazzarini.microservices_sample.entities.Order;
import li.lazzarini.microservices_sample.entities.OrderPosition;
import li.lazzarini.microservices_sample.entities.Product;
import li.lazzarini.microservices_sample.entities.State;
import li.lazzarini.microservices_sample.micro.filters.OrderFilter;
import li.lazzarini.microservices_sample.micro.filters.OrderFilterField;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeoutException;

import static org.mockito.Mockito.*;

class ServiceTest {

    @Test
    public void TestOnOrderCreate() throws IOException, InterruptedException, TimeoutException {
        BusConnector bus = mock(BusConnector.class);
        OrderService orderService = mock(OrderService.class);
        doNothing().when(orderService).createOrder(any(Order.class));
        Service service = new Service(bus, orderService);
        Order order = new Order(new ObjectId(), new ArrayList<OrderPosition>(), "user", "customer", "store", new Date(), State.PENDING);
        ObjectMapper mapper = new ObjectMapper();
        String orderJson = mapper.writeValueAsString(order);
        service.onOrderCreate(orderJson, "replyTo", "correlationId");
        verify(bus, times(1)).talkAsync("appe", "order.created", orderJson);
    }

    @Test
    public void TestOnOrderCreateException() throws IOException, TimeoutException {
        BusConnector bus = mock(BusConnector.class);
        OrderService orderService = mock(OrderService.class);
        doThrow(new IOException()).when(orderService).createOrder(any(Order.class));
        Service service = new Service(bus, orderService);
        Order order = new Order(new ObjectId(), new ArrayList<OrderPosition>(), "user", "customer", "store", new Date(), State.PENDING);
        ObjectMapper mapper = new ObjectMapper();
        String orderJson = mapper.writeValueAsString(order);
        service.onOrderCreate(orderJson, "replyTo", "correlationId");
        Response response = new Response(Status.BAD_REQUEST, "", "Order data is not correct");
        String responseJson = mapper.writeValueAsString(response);
        verify(bus, times(1)).talkAsync("appe", "replyTo", responseJson, "correlationId");
    }

    @Test
    public void TestOnOrderList() throws IOException, TimeoutException {
        BusConnector bus = mock(BusConnector.class);
        OrderService orderService = mock(OrderService.class);
        OrderPosition position = new OrderPosition(new Product(new ObjectId(), "product", "desc", 55.0, "sortiment", "dfd"), 4);
        List<OrderPosition> positions = new ArrayList<>();
        positions.add(position);
        Order order = new Order(new ObjectId(), positions, "user", "customer", "store", new Date(), State.PENDING);
        List<Order> orders = new ArrayList<>();
        orders.add(order);
        ObjectMapper mapper = new ObjectMapper();
        OrderFilter filter = new OrderFilter(OrderFilterField.NO_FILTER, "");
        when(orderService.getOrders(filter)).thenReturn(orders);
        String filterJson = mapper.writeValueAsString(filter);

        Service service = new Service(bus, orderService);
        service.onOrderList(filterJson, "replyTo", "correlationId");
        String responseOrderJson = mapper.writeValueAsString(orders);
        Response response = new Response(Status.OK, responseOrderJson, "");
        String responseJson = mapper.writeValueAsString(response);
        verify(bus, times(1)).talkAsync("appe", "replyTo", responseJson, "correlationId");
    }
}