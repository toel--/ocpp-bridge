# ocpp-bridge
OCPP Bridge

a simple java application that relays the OCPP messages and output then in clear text to the console.

Usefull if you need to see the OCPP messages between the charge point and the central system.

```
Start the ocpp-bridge with 2 parameters:
  java -jar OcppBridge.jar [port] [url] [id-mapping]
where
  [port] is the local port to listen to
  [url] is the url of the central system to relay messages to
  [id-mapping] to map device id to another id
  
  Then configure your charge point to connect to the bridge ip and port:
    ws://[IP]:[port]/ocpp
    
Example:
   java -jar OcppBridge.jar 8081 wss://127.0.0.1:8080/ocpp 000013133:91738A48W4015325
   will start the OcppBrigde, listening on port 8081 and forwarding the requests to wss://127.0.0.1:8080/ocpp
   device 000013133 will be forwarded as 91738A48W4015325
```
