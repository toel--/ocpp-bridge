/*
 * Using https://github.com/TooTallNate/Java-WebSocket
 */
package se.toel.ocpp.bridge;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
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
        Map<String, String> idMap = getIdMap(args, 2);
        
        Application app = new Application();
        app.execute(port, uri, idMap);
        
    }
    
    private static Map<String, String> getIdMap(String[] args, int index) {
     
        Map<String, String> map = new HashMap<>();
        
        if (args.length>index) {
            String s = args[index];
            String[] ss = s.split(",");
            for (String s1 : ss) {
                String[] s2 = s.split(":");
                if (s2.length==2) {
                    map.put(s2[0], s2[1]);
                }
            }
        }
        
        return map;
        
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
        System.out.println("     [idMap] optional: device id mapping, example: 000013133:91738A48W4015325");
        System.exit(1);
        
    }
    
    
}