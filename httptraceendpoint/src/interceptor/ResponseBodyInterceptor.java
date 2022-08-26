package interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import InMemoryReqRespRepository;

@ControllerAdvice
@ConditionalOnProperty(value = "httptrace.enabled", havingValue = "true")
public class ResponseBodyInterceptor implements ResponseBodyAdvice<Object> {

  @Autowired
  InMemoryReqRespRepository tracer;

  @Override
  public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
    return true;
  }

  @Override
  public Object beforeBodyWrite(Object body,
      MethodParameter returnType,
      MediaType selectedContentType,
      Class<? extends HttpMessageConverter<?>> selectedConverterType,
      ServerHttpRequest request,
      ServerHttpResponse response) {
    if (!request.getURI().toString().contains("/actuator/httptrace")) {
      tracer.handleResponse(((ServletServerHttpRequest) request).getServletRequest(),
          ((ServletServerHttpResponse) response).getServletResponse(), body);
    }
    return body;
  }
}
