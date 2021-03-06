
package li.lazzarini.microservices_sample.bus;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

/**
 * Test method for {@link RabbitMqConfig}.
 */
final class RabbitMqConfigTest {

    /**
     * Test method for {@link RabbitMqConfig#getHost()}.
     */
    @Test
    void testGetHost() {
        assertThat(new RabbitMqConfig("rabbitmq.test.properties").getHost()).isEqualTo("1111");
    }

    /**
     * Test method for {@link RabbitMqConfig#getUsername()}.
     */
    @Test
    void testGetUsername() {
        assertThat(new RabbitMqConfig("rabbitmq.test.properties").getUsername()).isEqualTo("2222");
    }

    /**
     * Test method for {@link RabbitMqConfig#getPassword()}.
     */
    @Test
    void testGetPassword() {
        assertThat(new RabbitMqConfig("rabbitmq.test.properties").getPassword()).isEqualTo("3333");
    }

    /**
     * Test method for {@link RabbitMqConfig#getExchange()}.
     */
    @Test
    void testGetExchange() {
        assertThat(new RabbitMqConfig("rabbitmq.test.properties").getExchange()).isEqualTo("4444");
    }

}
