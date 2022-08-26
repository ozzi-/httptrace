package actuator;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import InMemoryReqRespRepository;
import model.HttpTrace;

@Endpoint(id = "httptrace")
@ConditionalOnProperty(value = "httptrace.enabled", havingValue = "true")
public class HttpTraceEndpoint {

  private final InMemoryReqRespRepository inMemoryReqRespRepository;
  private static final Logger LOGGER = LoggerFactory.getLogger(HttpTraceEndpoint.class);

  public HttpTraceEndpoint(InMemoryReqRespRepository inMemoryReqRespRepository) {
    this.inMemoryReqRespRepository = inMemoryReqRespRepository;
    LOGGER.debug("HttpTraceEndpoint initialized");
  }

  @ReadOperation
  public List<HttpTrace> returnTrace() {
    LOGGER.debug("HttpTraceEndpoint read operation");
    return inMemoryReqRespRepository.findAll();
  }

}
