package ncei.gis

import groovy.util.logging.Log4j
import org.springframework.stereotype.Service


@Log4j
@Service
class ProcessingService {
    def random = new Random()
    def counter = 0

    String processMessage(message) {
        //simulate processing that takes up to 30 seconds
        def processingTime = random.nextInt(30)
        log.debug "${counter} - processing content: ${message}. Expected duration is ${processingTime} seconds\n"
        counter++
        Thread.sleep(processingTime * 1000)
        return "success"
    }
}
