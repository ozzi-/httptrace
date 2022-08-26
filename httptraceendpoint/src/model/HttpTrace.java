package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HttpTrace {

  public HttpTrace() {
    LocalDateTime now = LocalDateTime.now();
    dateTime = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss.SSS").format(now);
  }

  private Type type;
  private String dateTime;
  private String method;
  private String path;
  private Map<String, String> headers;
  private Map<String, String> parameters;
  private String body;


}
