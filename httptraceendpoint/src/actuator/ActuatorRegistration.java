package actuator;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import InMemoryReqRespRepository;

@Configuration
@ConditionalOnProperty(value = "httptrace.enabled", havingValue = "true")
public class ActuatorRegistration {

  private final InMemoryReqRespRepository inMemoryReqRespRepository;

  ActuatorRegistration(InMemoryReqRespRepository inMemoryReqRespRepository) {
    this.inMemoryReqRespRepository = inMemoryReqRespRepository;
  }

  @Bean
  public HttpTraceEndpoint traceEndpoint() {
    return new HttpTraceEndpoint(inMemoryReqRespRepository);
  }
}
