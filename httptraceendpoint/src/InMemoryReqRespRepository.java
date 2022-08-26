import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import model.HttpTrace;
import util.RequestResponseStringBuilder;

@Service
@ConditionalOnProperty(value = "httptrace.enabled", havingValue = "true")
public class InMemoryReqRespRepository {

  private final List<HttpTrace> traces = new LinkedList<>();

  @Autowired
  RequestResponseStringBuilder reqRespStringBuilder;

  @Value("${httptrace.capacity:10}")
  private int capacity;

  public void handleRequest(HttpServletRequest httpServletRequest, Object body) {
    add(reqRespStringBuilder.buildRequestString(httpServletRequest, body));
  }

  public void handleResponse(HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse,
      Object body) {
    add(reqRespStringBuilder.buildResponseString(httpServletRequest, httpServletResponse, body));
  }

  public List<HttpTrace> findAll() {
    synchronized (this.traces) {
      return Collections.unmodifiableList(new ArrayList<>(this.traces));
    }
  }

  public void add(HttpTrace trace) {
    synchronized (this.traces) {
      while (this.traces.size() >= this.capacity) {
        this.traces.remove(0);
      }
      this.traces.add(trace);
    }
  }
}
