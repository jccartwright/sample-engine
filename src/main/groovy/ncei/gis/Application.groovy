package ncei.gis

import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.amqp.rabbit.annotation.*
import groovy.util.logging.Log4j


@Log4j
@SpringBootApplication
class Application {
    @Autowired
    private final RabbitTemplate rabbitTemplate

    @Autowired
    private final ProcessingService processingService

    static void main(String[] args) {
        SpringApplication.run Application, args
    }

    //TODO store queue name in application.properties?
    public void sendMessage(String response) {
        rabbitTemplate.convertAndSend("status_queue", response)
    }


    //amqp-client libraries send a byte array while spring rabbitmqTemplate
    //sends String.  Only one RabbitListener annotated method or unpredictable
    //as to which method called
/*
    @RabbitListener(queues = "job_queue")
    public void receiveMessage(byte[] content) {
        log.debug "byte[] received..."
        processMessage(new String(content))
    }
*/

    //TODO store queue name in application.properties?
    //message must have a "content_type = text/plain" if sent via RabbitMQ console
    @RabbitListener(queues = "job_queue")
    public void receiveMessage(String content) {
        log.debug "String received..."
        String response = processingService.processMessage(content as String)
        sendMessage(response)
    }
}
