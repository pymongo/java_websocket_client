package websocket.client;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class Main {

    public static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        try {
            WsClient.getInstance().connectBlocking();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (true);
//        Map<String, String> params = new HashMap();
//        params.put("member_id", "123");
//        FormBody.Builder formBuilder = new FormBody.Builder();
//        for (Map.Entry<String, String> entry : params.entrySet()) {
//            formBuilder.add(entry.getKey(), entry.getValue());
//        }
//        Request request = new Request.Builder()
//                .url("http://rust")
//                .post(formBuilder.build())
//                .build();
    }
}
