package websocket.client;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public final class WsClient extends WebSocketClient {
    private static final String WEBSOCKET_URL = "ws://websocket.testcadae.top/ws";
//  private static final String WEBSOCKET_URL = "ws://127.0.0.1:8082/ws/";
  private static WsClient instance;

//  public interface onMessageListener {
//    void onMessageCallback(String message);
//  }
//
//  public void setListener(onMessageListener listener) {
//    this.listener = listener;
//  }

//  private onMessageListener listener;
//
//  public static onMessageListener emptyCallback = message -> {
//  };

  private List<String> channels = new ArrayList<>();

  public static WsClient getInstance() {
    if (instance == null) {
      try {
        instance = new WsClient(new URI(WEBSOCKET_URL));
        instance.setConnectionLostTimeout(5);
      } catch (URISyntaxException e) {
        System.out.println(e);
      }
    }
    return instance;
  }

  private WsClient(URI serverUri) {
    super(serverUri);
  }

  @Override
  public void onOpen(ServerHandshake handshakedata) {
    System.out.println("onOpen: webSocket connected");
    Main.logger.info("onOpen: webSocket connected");
  }

  @Override
  public void onMessage(String message) {
    System.out.println("WebSocket onStringMessage: " + message);
  }

  @Override
  public void onMessage(ByteBuffer bytes) {
    System.out.println("WebSocket onBinaryMessage: " + bytes);
  }

  //  private android.os.Handler reconnectHandler = new android.os.Handler();

  @Override
  public void onClose(int code, String reason, boolean remote) {
    System.out.println("onClose");
    System.out.println("[WebSocket 断线重连]: webSocket 掉线了...");
//    reconnectHandler.postDelayed(new Runnable() {
//      @Override
//      public void run() {
//        try {
//          Log.i(TAG, "[WebSocket 断线重连]: 尝试重连中...");
//          try {
//            reconnectBlocking();
//          } catch (NullPointerException e) {
//            // java.lang.NullPointerException: Attempt to invoke virtual method 'long java.lang.Thread.getId()' on a null object reference
//            Log.e(TAG, Log.getStackTraceString(e));
//          }
//          if (isClosed()) {
//            // 如果还是掉线
//            Log.i(TAG, "[WebSocket 断线重连]: 重连失败！");
//            reconnectHandler.postDelayed(this, 5000);
//          } else {
//            Log.i(TAG, "[WebSocket 断线重连]: 重连成功！正在恢复订阅...");
//            for (String channel : channels) {
//              resubscribe(channel);
//            }
//          }
//        } catch (InterruptedException e) {
//          Log.e(TAG, Log.getStackTraceString(e));
//        }
//      }
//    }, 5000);
  }

  @Override
  public void onError(Exception e) {
    System.out.println("onError " + e);
  }

  public void subscribe(String channelName) {
    if (isClosed() || channels.contains(channelName)) {
      return;
    }
    send("{\"subscribe\":\"" + channelName + "\"}");
    channels.add(channelName);
  }

  private void resubscribe(String channelName) {
    send("{\"subscribe\":\"" + channelName + "\"}");
  }

  public void unsubscribe(String channelName) {
    if (!channels.contains(channelName)) {
      return;
    }
    send("{\"unsubscribe\":\"" + channelName + "\"}");
    channels.remove(channelName);
  }

  public void unsubscribeAll() {
    for (String channelName : channels) {
      send("{\"unsubscribe\":\"" + channelName + "\"}");
    }
    channels.clear();
  }
}

