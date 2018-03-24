package by.bsac.tcs.server.process.handler.impl;

import by.bsac.tcs.domain.controller.ControllerFactory;
import by.bsac.tcs.domain.controller.RequestController;
import by.bsac.tcs.server.model.Request;
import by.bsac.tcs.server.process.handler.RequestHandler;
import by.bsac.tcs.server.process.handler.exception.RequestHandlerException;
import by.bsac.tcs.server.process.parser.ProtocolParser;
import by.bsac.tcs.server.process.parser.ProtocolParserFactory;
import by.bsac.tcs.server.process.response.ResponseWriter;
import by.bsac.tcs.server.process.response.ResponseWriterFactory;
import java.io.IOException;
import java.net.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestHandlerImpl implements RequestHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(RequestHandlerImpl.class);

  private static final int INPUT_STREAM_TIME_OUT_MILLISECONDS = 100;

  private static final ProtocolParserFactory PARSER_FACTORY = ProtocolParserFactory.getInstance();
  private static final ControllerFactory CONTROLLER_FACTORY = ControllerFactory.getInstance();
  private static final ResponseWriterFactory RESPONSE_WRITER_FACTORY = ResponseWriterFactory
      .getInstance();

  private final Socket clientSocket;
  private final ProtocolParser parser;
  private final RequestController requestController;
  private final ResponseWriter responseWriter;

  public RequestHandlerImpl(Socket clientSocket) {
    this.clientSocket = clientSocket;
    this.parser = PARSER_FACTORY.getProtocolParser();
    this.requestController = CONTROLLER_FACTORY.getController();
    this.responseWriter = RESPONSE_WRITER_FACTORY.getResponseWriter();
  }

  public RequestHandlerImpl(Socket clientSocket,
      ProtocolParser parser,
      RequestController requestController) {
    this.clientSocket = clientSocket;
    this.parser = parser;
    this.requestController = requestController;
    this.responseWriter = RESPONSE_WRITER_FACTORY.getResponseWriter();
  }

  public RequestHandlerImpl(Socket clientSocket,
      ProtocolParser parser,
      RequestController requestController,
      ResponseWriter responseWriter) {
    this.clientSocket = clientSocket;
    this.parser = parser;
    this.requestController = requestController;
    this.responseWriter = responseWriter;
  }

  @Override
  public void run() {
    try {
      clientSocket.setSoTimeout(INPUT_STREAM_TIME_OUT_MILLISECONDS);

      Request request = parser.parse(clientSocket);
      LOGGER.debug("Request was parsed");
      requestController.process(request);
      LOGGER.info("Request was processed");
      responseWriter.write(clientSocket, request);
      LOGGER.debug("Response was written");

      closeSocketItPossible();
    } catch (Exception e) {
      String message = "Can't manage client process";
      LOGGER.error(message, e);
      throw new RequestHandlerException(message, e);
    }
  }

  private void closeSocketItPossible() throws IOException {
    if (!clientSocket.isClosed()) {
      clientSocket.close();
    }
  }
}
