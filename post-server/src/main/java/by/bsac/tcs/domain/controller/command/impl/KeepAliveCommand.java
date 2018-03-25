package by.bsac.tcs.domain.controller.command.impl;

import by.bsac.tcs.domain.controller.command.CommandException;
import by.bsac.tcs.domain.model.EventLog;
import by.bsac.tcs.domain.service.EventService;
import by.bsac.tcs.domain.service.EventServiceFactory;
import by.bsac.tcs.domain.service.exception.ServiceException;
import by.bsac.tcs.domain.util.converter.RequestConverter;
import by.bsac.tcs.domain.util.converter.RequestConverterFactory;
import by.bsac.tcs.server.model.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KeepAliveCommand extends ResponseCommand {

  private static final Logger LOGGER = LoggerFactory.getLogger(KeepAliveCommand.class);

  private final EventService eventService;
  private final RequestConverter<EventLog> requestConverter;

  public KeepAliveCommand() {
    this.eventService = EventServiceFactory.getInstance().getEventService();
    this.requestConverter = RequestConverterFactory.getInstance().getConverter(EventLog.class);
  }

  public KeepAliveCommand(EventService eventService,
      RequestConverter<EventLog> requestConverter) {
    this.eventService = eventService;
    this.requestConverter = requestConverter;
  }

  @Override
  public void execute(Request request) throws CommandException {
    LOGGER.info("{} is executing...", this.getClass().getSimpleName());

    final EventLog eventLog = requestConverter.convert(request);

    try {
      eventService.keepAlive(eventLog);
      writeResponse(request);
    } catch (ServiceException e) {
      final String message = String.format("An error occurred while eventLog event %s", eventLog);
      LOGGER.error(message);
      throw new CommandException(message, e);
    }
  }
}
