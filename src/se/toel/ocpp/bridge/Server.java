/*
 * Server part of the OCPP relay
 */

package se.toel.ocpp.bridge;

import java.net.InetSocketAddress;
import java.net.URI;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import se.toel.util.Dev;

/**
 *
 * @author toel
 */
public class Server extends WebSocketServer {

    /***************************************************************************
     * Constants and variables
     **************************************************************************/
    URI targetUrl = null;
    Map<WebSocket, Client> connections = new HashMap<>();
    
     /***************************************************************************
     * Constructor
     **************************************************************************/
    public Server(int port, URI targetUrl) throws UnknownHostException {
        super(new InetSocketAddress(port));
        this.targetUrl = targetUrl;
    }

     /***************************************************************************
     * Public methods
     **************************************************************************/
    
    public void shutdown() {
        try {
            this.stop();
        } catch (Exception e) {}
        
        for (Client client : connections.values()) {
            client.close();
        }
    }
    
    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        
        String deviceId = getDeviceId(conn.getResourceDescriptor());
        Dev.info(deviceId+" connect from "+conn.getRemoteSocketAddress().getHostString()+":"+conn.getRemoteSocketAddress().getPort());
        String url = targetUrl.toString()+"/"+deviceId;
        try {
            Client client = new Client(new URI(url));
            connections.put(conn, client);
            client.setServerConnection(conn);
            client.connect();
        } catch (Exception e) {
            Dev.error("While opening connection", e);
        }
        
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        
        String deviceId = getDeviceId(conn.getResourceDescriptor());
        Dev.info(deviceId+" disconnected "+(remote ? "by charge point" : "by central system"));
        System.out.println();
        Client client = connections.get(conn);
        client.close();
        connections.remove(conn);
        
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        
        String deviceId = getDeviceId(conn.getResourceDescriptor());
        Dev.info(deviceId+" "+message);
        Client client = connections.get(conn);
        while (!client.isOpen()) Dev.sleep(10);
        client.send(message);
    }

    @Override
    public void onMessage(WebSocket conn, ByteBuffer message) {
        Dev.info("ByteBuffer received...");
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        Dev.error("                 error on connection to charge point", ex);
        ex.printStackTrace();
        if (conn != null) {
            // some errors like port binding failed may not be assignable to a specific websocket
        }
    }

    @Override
    public void onStart() {
        Dev.info("Server started!");
        setConnectionLostTimeout(100);
    }

    
    
    /***************************************************************************
     * Private methods
     **************************************************************************/
    private String getDeviceId(String url) {
     
        int p = url.lastIndexOf('/');
        return url.substring(p+1);
        
    }

}
