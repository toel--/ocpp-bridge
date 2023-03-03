/*
 * WS Client for the OCPP bridge
 */

package se.toel.ocpp.bridge;

import java.net.URI;
import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.toel.util.Dev;
import se.toel.util.StringUtil;

/**
 *
 * @author toel
 */
public class Client extends WebSocketClient {

    /***************************************************************************
     * Constants and variables
     **************************************************************************/
    private final Logger log = LoggerFactory.getLogger(Client.class);
    private WebSocket serverConnection;
    private final int conId = Counter.geInstance().getNext();

     /***************************************************************************
     * Constructor
     **************************************************************************/
    public Client(URI uri, ClientHandshake handshake) {
        
        super(uri);
        
        // addHeader("Sec-WebSocket-Protocol", "ocpp1.6"); default to 1.6
        
        String[] keys = new String[]{"Authorization", "Sec-WebSocket-Protocol", "User-Agent"};
        for (String key : keys) {
            String value = handshake.getFieldValue(key);
            if (value!=null) {
                System.out.println("   forwarding header "+key+": "+value);
                if (key.equals("Authorization")) {
                    String s = value.replace("Basic ", "");
                    s = StringUtil.base64decode(s);
                    System.out.println("                 "+s);
                }
                addHeader(key, value);
            }
        }
        
    }
    
    

     /***************************************************************************
     * Public methods
     **************************************************************************/
    public void setServerConnection(WebSocket webSocket) {
        
        this.serverConnection = webSocket;
        
    }
    
    @Override
    public void onMessage( String message ) {
        Dev.info("                 "+message);
        serverConnection.send(message);
    }

    @Override
    public void onOpen(ServerHandshake handshake) {
        Dev.info("                 connection "+conId+" to central system opened");
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        Dev.info("                 connection "+conId+" to central system closed by "+(remote ? "central system" : "charge point")+" "+reason);
        serverConnection.close();
    }

    @Override
    public void onError(Exception ex) {
        Dev.error("                 error on connection "+conId+" to central system", ex);
    }

    /***************************************************************************
     * Private methods
     **************************************************************************/

}
