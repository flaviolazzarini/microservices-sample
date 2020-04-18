package li.lazzarini.microservices_sample.micro;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Demo für Applikationsstart.
 */
public final class Application {

    /**
     * Privater Konstruktor.
     */
    private Application() {
    }

    /**
     * main-Methode. Startet einen Timer für den HeartBeat.
     *
     * @param args not used.
     */
    public static void main(final String[] args) {
        final Logger logger = LogManager.getLogger(Application.class);
        try (Service service = new Service()) {
            logger.info("Service Order management started");

        } catch (Exception e) {
            logger.error(e);
        }

    }
}
