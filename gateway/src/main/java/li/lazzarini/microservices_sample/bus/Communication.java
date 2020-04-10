package li.lazzarini.microservices_sample.bus;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public interface Communication {
    String syncCall(String route, String message) throws IOException, InterruptedException;
}
