package interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import InMemoryReqRespRepository;

@Component
@ConditionalOnProperty(value = "httptrace.enabled", havingValue = "true")
public class RequestWithoutBodyInterceptor implements HandlerInterceptor {

  @Autowired
  InMemoryReqRespRepository tracer;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
    if (isRequestWithoutBody(request)) {
      tracer.handleRequest(request, null);
    }
    return true;
  }

  private boolean isRequestWithoutBody(HttpServletRequest request) {
    return request.getMethod().equals(HttpMethod.GET.name()) || request.getMethod().equals(HttpMethod.DELETE.name())
        || request.getMethod().equals(HttpMethod.OPTIONS.name()) || request.getMethod().equals(HttpMethod.HEAD.name())
        || request.getMethod().equals(HttpMethod.TRACE.name());
  }
}
