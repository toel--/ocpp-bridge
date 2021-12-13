/*
 * WS Client for the OCPP bridge
 */

package se.toel.ocpp.bridge;

import java.net.URI;
import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.toel.util.Dev;

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

     /***************************************************************************
     * Constructor
     **************************************************************************/
    public Client(URI uri) {
        super(uri);
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
        Dev.info("                 connection to backend opened");
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        Dev.info("                 connection to backend closed: "+reason);
        serverConnection.close();
    }

    @Override
    public void onError(Exception ex) {
        Dev.error("                 client error", ex);
    }

    /***************************************************************************
     * Private methods
     **************************************************************************/

}
