import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.websocket.*;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.server.ServerEndpointConfig;

@ServerEndpoint(value = "/ws/",
        configurator = MyWebSocket.Configurator.class)
public class MyWebSocket {
    public static String req = "someText";
    public static String res = "someText";
    public static String token = "noToken";

    public static class Configurator extends ServerEndpointConfig.Configurator {
        @Override
        public void modifyHandshake(ServerEndpointConfig config, HandshakeRequest request, HandshakeResponse response) {
            System.out.println(request.getHeaders());
            req = request.getHeaders().toString();
            token = request.getHeaders().get("token").get(0);
            System.out.println(response.getHeaders());
            res = response.getHeaders().toString();
        }

    }

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("onOpen::" + session.getId());
        boolean containToken = req.contains("token");
        try {
            session.getBasicRemote().sendText("Hello Client: " + session.getId() + "!");
            session.getBasicRemote().sendText("contain Token: " + containToken);
            session.getBasicRemote().sendText("request header:  " + req);
            session.getBasicRemote().sendText("response header:  " + res);
            if (containToken) {
                session.getBasicRemote().sendText("you token: " + token);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnClose
    public void onClose(Session session) {
        System.out.println("onClose::" + session.getId());
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("onMessage::From=" + session.getId() + " Message=" + message);

        try {
            session.getBasicRemote().sendText("ECHO " + message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnError
    public void onError(Throwable t) {
        System.out.println("onError::" + t.getMessage());
    }
}

