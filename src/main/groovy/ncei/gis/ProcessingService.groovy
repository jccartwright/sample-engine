package ncei.gis

import groovy.util.logging.Log4j
import org.springframework.stereotype.Service


@Log4j
@Service
class ProcessingService {
    String processMessage(String content) {
        log.debug "processing content: ${content}..."
        return "success"
    }
}
