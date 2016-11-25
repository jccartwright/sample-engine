package ncei.gis

import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification


@SpringBootTest
class ApplicationSpec extends Specification {
    @Autowired
    private final RabbitTemplate rabbitTemplate;

    void contextLoads() {
        expect:
        rabbitTemplate
    }
}