package by.bsac.tcs.domain.model;

import static java.util.Objects.isNull;

public enum Event {

  REGISTRATION(1), KEEP_ALIVE(2), HAS_OPENED(3), HAS_CLOSED(4), QUANTITY_CHANGED(5);

  private int typeCode;


  Event(int eventCode) {
    this.typeCode = eventCode;
  }

  /**
   * Converts to {@link Event} instance according its requestCode value
   *
   * @param requestCode protocol representation of {@link Event}
   * @return instance of {@link Event}
   */
  public static Event getRequestType(int requestCode) {
    Event requestType = null;

    for (Event element : Event.values()) {
      if (element.typeCode == requestCode) {
        requestType = element;
      }
    }

    if (isNull(requestType)) {
      throw new IllegalArgumentException(
          String.format("No such requestType constant for requestCode = %d", requestCode));
    }
    return requestType;
  }

  public int getEventId() {
    return this.ordinal() + 1;
  }
}