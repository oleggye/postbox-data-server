package by.bsac.tcs.server.model;

import java.util.HashMap;
import java.util.Map;

public class Request {

  private Event event;

  private final Map<String, String> paramMap = new HashMap<>();

  public String getRequestParam(String paramName) {
    return paramMap.get(paramName);
  }

  public void setRequestParam(String key, String val) {
    paramMap.put(key, val);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Request request = (Request) o;

    if (event != request.event) {
      return false;
    }
    return paramMap.equals(request.paramMap);
  }

  @Override
  public int hashCode() {
    int result = event != null ? event.hashCode() : 0;
    result = 31 * result + paramMap.hashCode();
    return result;
  }

  public Event getEvent() {

    return event;
  }

  public void setEvent(Event event) {
    this.event = event;
  }
}