package ncei.gis

import groovy.util.logging.Log4j
import org.springframework.amqp.core.Message
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.amqp.AmqpRejectAndDontRequeueException
import groovy.json.*


@Log4j
@SpringBootApplication
class Application {
    @Value('${ncei.gis.status_queue:status_queue}')
    private final String statusQueue

    @Autowired
    private final RabbitTemplate rabbitTemplate

    @Autowired
    private final ProcessingService processingService

     JsonSlurper jsonSlurper = new JsonSlurper()

    static void main(String[] args) {
        SpringApplication.run Application, args
    }


    public void sendMessage(String response) {
        rabbitTemplate.convertAndSend(statusQueue, response)
    }


    //amqp-client libraries send a byte array while spring rabbitmqTemplate
    //sends String.  Only one RabbitListener annotated method or unpredictable
    //as to which method called
    @RabbitListener(queues = '${ncei.gis.job_queue}')
    public void receiveMessage(Message message) {
        log.debug "String received: ${new String(message.body)}..."
        try {
            def json = jsonSlurper.parse(message.body)
            String response = processingService.processMessage(json)
            log.debug("returning response: ${response}")
            sendMessage(response)
        } catch (Exception e) {
            log.error(e)
            sendMessage('failed')
            throw new AmqpRejectAndDontRequeueException(e)
        }
    }
}