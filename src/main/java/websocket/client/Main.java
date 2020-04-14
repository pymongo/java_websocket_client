package websocket.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

  public static final Logger logger = LoggerFactory.getLogger(Main.class);

  public static void main(String[] args) {
    final String message = "I am log4j + slf4j";
    logger.trace(message);
    logger.debug(message);
    logger.info(message);
    logger.warn(message);
    logger.error(message);
    System.out.println("asdfasdf");
  }
}
