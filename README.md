# ocpp-bridge
OCPP Bridge

a simple java application that relays the OCPP messages and output then in clear text to the console.

Usefull if you need to see the OCPP messages between the charge point and the central system.

```
Start the ocpp-bridge with 2 parameters:
  java -jar OcppBridge.jar [port] [url]
where
  [port] is the local port to listen to
  [url] is the url of the central system to relay messages to
  
  Then configure your charge point to connect to the bridge ip and port:
    ws://[IP]:[port]/ocpp
```
