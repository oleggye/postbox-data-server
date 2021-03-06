package by.bsac.tcs.domain.util.converter.impl;

import by.bsac.tcs.domain.model.Event;
import by.bsac.tcs.domain.model.EventLog;
import by.bsac.tcs.domain.util.converter.RequestConverter;
import by.bsac.tcs.server.model.Method;
import by.bsac.tcs.server.model.Request;
import com.google.common.collect.ImmutableMap;
import java.util.Map;

public class RequestToEventLogConverter implements RequestConverter {

  private static final Map<Method, Event> METHOD_EVENT_MAP =
      ImmutableMap.of(
          Method.REG, Event.REGISTRATION,
          Method.LETTER, Event.QUANTITY_CHANGED,
          Method.WITHDRAWN, Event.HAS_OPENED,
          Method.EMPTY, Event.HAS_CLOSED,
          Method.KEEP_ALIVE, Event.KEEP_ALIVE
      );

  public EventLog convert(final Request request) {
    final long postBoxId = request.getPostBoxId();

    Method method = request.getMethod();
    final Event event = METHOD_EVENT_MAP.get(method);

    final int quantity = request.getLettersCount();

    final long epochTime = request.getEpochTime();

    return new EventLog(postBoxId, event, quantity, epochTime);
  }
}
