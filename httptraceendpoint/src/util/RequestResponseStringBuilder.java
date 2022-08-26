package util;

import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import model.HttpTrace;
import model.Type;

@Component
public class RequestResponseStringBuilder {


  @Value("${httptrace.bodyCutOff:100}")
  private int bodyCutOff;


  RequestResponseStringBuilder() {

  }

  public HttpTrace buildRequestString(HttpServletRequest httpServletRequest, Object body) {
    Map<String, String> parameters = buildParametersMap(httpServletRequest);
    HttpTrace ret = new HttpTrace();
    ret.setType(Type.Request);
    ret.setMethod(httpServletRequest.getMethod());
    ret.setPath(httpServletRequest.getRequestURI());
    ret.setHeaders(buildHeadersMap(httpServletRequest));
    if (!parameters.isEmpty()) {
      ret.setParameters(parameters);
    }
    if (body != null) {
      if (body instanceof String) {
        ret.setBody(trimBody((String) body));
      } else {
        ret.setBody("{" + body.getClass().getSimpleName() + "}=" + trimBody(body.toString()));
      }
    }
    return ret;
  }

  private String trimBody(String body) {
    return body.substring(0, Math.min(body.length(), bodyCutOff));
  }

  public HttpTrace buildResponseString(HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse,
      Object body) {
    HttpTrace ret = new HttpTrace();
    ret.setType(Type.Response);
    ret.setMethod(httpServletRequest.getMethod());
    ret.setHeaders(buildHeadersMap(httpServletResponse));
    if (body instanceof String) {
      ret.setBody(trimBody((String) body));
    } else {
      ret.setBody("{" + body.getClass().getSimpleName() + "}=" + trimBody(body.toString()));
    }
    return ret;
  }

  private static Map<String, String> buildHeadersMap(HttpServletRequest request) {
    Map<String, String> map = new HashMap<>();

    Enumeration<String> headerNames = request.getHeaderNames();
    while (headerNames.hasMoreElements()) {
      String key = headerNames.nextElement();
      String value = request.getHeader(key);
      map.put(key, value);
    }

    return map;
  }

  private static Map<String, String> buildHeadersMap(HttpServletResponse response) {
    Map<String, String> map = new HashMap<>();
    Collection<String> headerNames = response.getHeaderNames();
    for (String header : headerNames) {
      map.put(header, response.getHeader(header));
    }
    return map;
  }

  private static Map<String, String> buildParametersMap(HttpServletRequest httpServletRequest) {
    Map<String, String> resultMap = new HashMap<>();
    Enumeration<String> parameterNames = httpServletRequest.getParameterNames();

    while (parameterNames.hasMoreElements()) {
      String key = parameterNames.nextElement();
      String value = httpServletRequest.getParameter(key);
      resultMap.put(key, value);
    }

    return resultMap;
  }

}
