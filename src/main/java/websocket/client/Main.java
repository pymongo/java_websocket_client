package websocket.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

  public static final Logger logger = LoggerFactory.getLogger(Main.class);

  public static void main(String[] args) {
    try {
      WsClient.getInstance().connectBlocking();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    while(true);
  }
}
