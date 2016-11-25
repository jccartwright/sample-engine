package ncei.gis

import spock.lang.Specification

class ProcessingServiceSpec extends Specification {

    def "returns success from processMessage"() {
        given: "a Processing Service"
        ProcessingService processingService = new ProcessingService()

        expect:
        processingService.processMessage("testme") == 'success'

    }
}
