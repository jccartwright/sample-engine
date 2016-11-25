package ncei.gis

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.amqp.rabbit.core.RabbitTemplate


@RunWith(SpringRunner)
@SpringBootTest
class ApplicationTests {
    @Autowired
    private final RabbitTemplate rabbitTemplate;

    @Test
    void contextLoads() {
        assert rabbitTemplate
    }

}
