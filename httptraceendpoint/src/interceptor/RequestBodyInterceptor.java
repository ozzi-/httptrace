package interceptor;

import java.lang.reflect.Type;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import InMemoryReqRespRepository;

@ControllerAdvice
@ConditionalOnProperty(value = "httptrace.enabled", havingValue = "true")
public class RequestBodyInterceptor extends RequestBodyAdviceAdapter {

  @Autowired
  InMemoryReqRespRepository tracer;

  @Autowired
  HttpServletRequest request;

  @Override
  public Object afterBodyRead(Object body,
      HttpInputMessage inputMessage,
      MethodParameter parameter,
      Type targetType,
      Class<? extends HttpMessageConverter<?>> converterType) {
    tracer.handleRequest(request, body);
    return super.afterBodyRead(body, inputMessage, parameter, targetType, converterType);
  }

  @Override
  public boolean supports(MethodParameter methodParameter,
      Type targetType,
      Class<? extends HttpMessageConverter<?>> converterType) {
    return true;
  }
}
