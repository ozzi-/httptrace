package interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ConditionalOnProperty(value = "httptrace.enabled", havingValue = "true")
public class NoBodyRequestInterceptorConfig implements WebMvcConfigurer {

  private static final Logger LOGGER = LoggerFactory.getLogger(NoBodyRequestInterceptorConfig.class);
  private final RequestWithoutBodyInterceptor requestWithoutBodyInterceptor;

  NoBodyRequestInterceptorConfig(RequestWithoutBodyInterceptor requestWithoutBodyInterceptor) {
    LOGGER.info("Initializing Interceptors");
    this.requestWithoutBodyInterceptor = requestWithoutBodyInterceptor;
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(requestWithoutBodyInterceptor);
  }
}
