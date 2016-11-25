package ncei.gis

import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.amqp.rabbit.annotation.*
import groovy.util.logging.Log4j
import org.springframework.beans.factory.annotation.*


@Log4j
@SpringBootApplication
class Application {
    //can't seem to set using a property
    //@Value('${ncei.gis.job_queue:job_queue}')
    //private final String jobQueue
    private final String JOB_QUEUE = 'job_queue'

    @Value('${ncei.gis.job_queue:status_queue}')
    private final String statusQueue

    @Autowired
    private final RabbitTemplate rabbitTemplate

    @Autowired
    private final ProcessingService processingService


    static void main(String[] args) {
        SpringApplication.run Application, args
    }


    public void sendMessage(String response) {
        rabbitTemplate.convertAndSend(statusQueue, response)
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

    //message must have a "content_type = text/plain" if sent via RabbitMQ console
    @RabbitListener(queues = JOB_QUEUE)
    public void receiveMessage(String content) {
//        log.debug "String received..."
        String response = processingService.processMessage(content as String)
        sendMessage(response)
    }
}
