package li.lazzarini.microservices_sample.micro;

import li.lazzarini.microservices_sample.bus.Communication;
import li.lazzarini.microservices_sample.bus.Response;
import li.lazzarini.microservices_sample.micro.filters.Filter;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;

import java.io.IOException;

public class ServiceCaller {

    public HttpResponse<?> callService(Communication rabbitMQCommunication, Filter filter, String route) throws IOException, InterruptedException {
        ObjectMapper mapper = new ObjectMapper();
        String filterJson = mapper.writeValueAsString(filter);
        String json = rabbitMQCommunication.syncCall(route, filterJson);
        Response response = mapper.readValue(json, Response.class);
        return getHttpResponse(response);
    }

    public HttpResponse<?> callService(Communication rabbitMQCommunication, String route, String message) throws IOException, InterruptedException {
        ObjectMapper mapper = new ObjectMapper();
        String json = rabbitMQCommunication.syncCall(route, message);
        Response response = mapper.readValue(json, Response.class);
        return getHttpResponse(response);
    }

    private HttpResponse<?> getHttpResponse(Response response){
        return HttpResponse.status(HttpStatus.valueOf(response.getStatus().getValue()), response.getReason()).body(response.getData());
    }
}
