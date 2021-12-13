/*
 * Main application
 */

package se.toel.ocpp.bridge;

import java.net.URI;
import se.toel.util.Dev;

/**
 *
 * @author toel
 */
public class Application {

    /***************************************************************************
     * Constants and variables
     **************************************************************************/
    private static boolean running = true;

     /***************************************************************************
     * Constructor
     **************************************************************************/

     /***************************************************************************
     * Public methods
     **************************************************************************/
    
    public void execute(int port, URI url) {
        
        // Shutdown hook to close the db connection
        Runtime.getRuntime().addShutdownHook( new Thread (  )  {  
            @Override
            public void run() {
                shutdown();
            }
        }); 
        
        Dev.debugEnabled(true);
        Dev.setWriteToFile(true);
        
        try {
        
            System.out.println("starting server on port: " + port);
            Server server = new Server(port, url);
            server.start();
            System.out.println(" done");

            while (running) {
                Dev.sleep(100);
            }
            
            server.stop(1000);
            
            
        
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        
    }
    
    
    public static void shutdown() {
        
        running = false;
        
    }
    
        
    /***************************************************************************
     * Private methods
     **************************************************************************/

}
