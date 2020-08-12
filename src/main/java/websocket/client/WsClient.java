package websocket.client;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;


public final class WsClient extends WebSocketClient {
    private static final String WEBSOCKET_URL = "wss://ws-rust.testcadae.top/ws/";
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

    private final List<String> channels = new ArrayList<>();

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

    WsClient(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        System.out.println("onOpen: webSocket connected");
        Main.logger.info("onOpen: webSocket connected");
        send("subscribe order_books·iicg·zh");
    }

    @Override
    public void onMessage(String message) {
        System.out.println("WebSocket onStringMessage: " + message);
    }

    @Override
    public void onMessage(ByteBuffer bytes) {
        String msg = gzipDecompress(bytes.array());
        System.out.println(msg);
        try {
            JSONObject json = new JSONObject(msg);
            System.out.println(json.toString(2));
            System.out.println(json.getString("channel"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("WebSocket onBinaryMessage: " + msg);
    }

    //  private android.os.Handler reconnectHandler = new android.os.Handler();

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("onClose");
        System.out.println("[WebSocket 断线重连]: webSocket 掉线了...");
    }

    @Override
    public void onError(Exception e) {
        System.out.println("onError " + e);
    }

    private static String gzipDecompress(byte[] compressed) {
        ByteArrayInputStream bis = new ByteArrayInputStream(compressed);
        StringBuilder sb = new StringBuilder();
        try {
            GZIPInputStream gis = new GZIPInputStream(bis);
            BufferedReader br = new BufferedReader(new InputStreamReader(gis, StandardCharsets.UTF_8));
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
            gis.close();
            bis.close();
        } catch (Exception e) {
            System.out.println(e);
//            Log.e(TAG, "decompress: " + Log.getStackTraceString(e));
        }
        return sb.toString();
    }
}

