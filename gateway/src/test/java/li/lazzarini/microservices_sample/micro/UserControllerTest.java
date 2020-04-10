package li.lazzarini.microservices_sample.micro;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.test.annotation.MicronautTest;
import io.micronaut.test.annotation.MockBean;
import li.lazzarini.microservices_sample.bus.Communication;
import li.lazzarini.microservices_sample.bus.RabbitMQCommunication;
import li.lazzarini.microservices_sample.bus.Response;
import li.lazzarini.microservices_sample.bus.Status;
import org.junit.jupiter.api.Test;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import javax.inject.Inject;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@MicronautTest
class UserControllerTest {

    @Inject
    Communication communication;

    @Inject
    @Client("/")
    RxHttpClient client;

    @MockBean(RabbitMQCommunication.class)
    public Communication communication(){
        return mock(Communication.class);
    }

    @Test
    void testCreateUser() throws IOException, InterruptedException, TimeoutException {
        Response response = new Response(Status.OK, "created", "");
        ObjectMapper mapper = new ObjectMapper();
        String responseString = mapper.writeValueAsString(response);
        when(communication.syncCall(eq("user.create"), anyString())).thenReturn(responseString);
        String json = "{\n" +
                "\t\"username\": \"pTester\",\n" +
                "\t\"name\": \"Kurt\",\n" +
                "\t\"lastname\": \"Müller\"\n" +
                "}";
        final HttpRequest<String> request = HttpRequest.PUT("/api/v1/user/", json);
        try {
            final HttpResponse<String> responseFromServer = client.toBlocking().exchange(request);
            assertThat(responseFromServer.getStatus().getCode()).isEqualTo(HttpStatus.OK.getCode());
        } catch (HttpClientResponseException ex) {
            fail(ex.getMessage());
        }
    }

}