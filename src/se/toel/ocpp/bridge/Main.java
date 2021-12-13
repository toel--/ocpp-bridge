/*
 * Using https://github.com/TooTallNate/Java-WebSocket
 */
package se.toel.ocpp.bridge;

import java.net.URI;
import java.net.URISyntaxException;
import se.toel.util.StringUtil;

/**
 *
 * @author toel
 */
public class Main {

    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws URISyntaxException {
        
        if (args.length<2) showSyntaxAndExit();
        
        int port = StringUtil.str2int(args[0]);
        String url = args[1];
        URI uri = new URI(url);
        
        Application app = new Application();
        app.execute(port, uri);
        
    }
    
    
    private static void showSyntaxAndExit() {
        
        System.out.println("OCPP Bridge");
        System.out.println("Toel Hartmann 2021");
        System.out.println();
        System.out.println("  Syntax:");
        System.out.println("     java -jar OcppBridge.jar [port] [url]");
        System.out.println("  where");
        System.out.println("     [port] is the local port to listen to");
        System.out.println("     [url] is the url of the endpoint to relay messages to");
        System.exit(1);
        
    }
    
    
}