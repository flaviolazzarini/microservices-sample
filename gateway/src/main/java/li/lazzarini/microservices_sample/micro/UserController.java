package li.lazzarini.microservices_sample.micro;

import li.lazzarini.microservices_sample.bus.Communication;
import li.lazzarini.microservices_sample.bus.Status;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import li.lazzarini.microservices_sample.bus.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@Controller("/api/v1/user")
public class UserController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);
    private static final String ROUTE_USER_CREATE = "user.create";

    Communication rabbitMQCommunication;

    UserController(Communication communicationFactory) {
        this.rabbitMQCommunication = communicationFactory;
    }

    @Put("/")
    public HttpResponse<?> create(@Body String body){
        try {
            String json = this.rabbitMQCommunication.syncCall(ROUTE_USER_CREATE, body);
            ObjectMapper mapper = new ObjectMapper();
            Response response = mapper.readValue(json, Response.class);
            if (response.getStatus() == li.lazzarini.microservices_sample.bus.Status.OK) {
                return HttpResponse.ok("User erfolgreich erstellt");
            } else if (response.getStatus() == Status.BAD_REQUEST) {
                return HttpResponse.badRequest("User konnte nicht erstellt werden");
            }
            else {
                return HttpResponse.serverError("Internal Server error");
            }
        } catch (IOException e) {
            LOG.error(e.getMessage());
            return HttpResponse.serverError("Internal Server error");
        } catch (InterruptedException e) {
            LOG.error(e.getMessage());
            return HttpResponse.serverError("Internal Server error");
        }
    }
}
