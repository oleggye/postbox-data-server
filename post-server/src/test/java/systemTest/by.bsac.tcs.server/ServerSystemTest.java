package by.bsac.tcs.server;

import java.util.concurrent.TimeUnit;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class ServerSystemTest {

  private static final String SERVER_ADDRESS = "127.0.0.1";
  private static final int SERVER_PORT = 7777;
  private static final long SLEEP_TIMEOUT_SEC = 4;

  private static Server server;
  private static Thread serverThread;

  /**
   * Start the server as a new thread add save ref to it
   */
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    serverThread = new Thread(() -> {
      server = new Server(SERVER_PORT);
      server.start();
    });
    serverThread.start();
  }

  /**
   * Use delay before server stop
   * to allow the server process all requests from test classes
   */
  @AfterClass
  public static void tearDownAfterClass() throws Exception {
    TimeUnit.SECONDS.sleep(SLEEP_TIMEOUT_SEC);
    if (server != null) {
      server.stop();
    }
    if (serverThread != null && serverThread.isAlive()) {
      serverThread.interrupt();
    }
  }

  @Test
  public void testWhenSendRequestWithEmptyData() throws Exception {
    final String data = "";
    new TcpRequestBuilder()
        .serverAddress(SERVER_ADDRESS)
        .serverPort(SERVER_PORT)
        .requestData(data)
        .perform();
  }

  @Test
  public void testWhenSendRequestWithData() throws Exception {
    final String data = "postBoxId=1;code=6;state=opened;^";
    new TcpRequestBuilder()
        .serverAddress(SERVER_ADDRESS)
        .serverPort(SERVER_PORT)
        .requestData(data)
        .perform();
  }
}