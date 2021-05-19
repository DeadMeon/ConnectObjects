#include <SPI.h>
#include <WiFiNINA.h>
#include <WiFiUdp.h>
#include "arduino_secrets.h" 



int status = WL_IDLE_STATUS;
char ssid[] = SECRET_SSID;        
char pass[] = SECRET_PASS;    
char key[22] = SECRET_KEY;
char servKey[22] = SECRET_SERV_KEY;
 
int keyIndex = 0;  
          
unsigned int localPort = 2390;
IPAddress multicastAddress = IPAddress(230, 1, 1, 1);

char packetBuffer[256]; //buffer to hold incoming packet
char separateur[] = ":";
char *token;

WiFiUDP Udp;


void setup() {
  Serial.begin(9600);

  // check for the WiFi module:
  if (WiFi.status() == WL_NO_MODULE) {
    Serial.println("Communication with WiFi module failed!");
    while (true); // don't continue
  }
  String fv = WiFi.firmwareVersion();
  if (fv < WIFI_FIRMWARE_LATEST_VERSION) {
    Serial.println("Please upgrade the firmware");
  }

  // attempt to connect to WiFi network:
  while (status != WL_CONNECTED) {
    Serial.print("Attempting to connect to SSID: ");
    Serial.println(ssid);
    // Connect to WPA/WPA2 network. Change this line if using open or WEP network:
    status = WiFi.begin(ssid, pass);
    delay(5000);
  }
  Serial.println("Connected to WiFi");
  
  Serial.println("\nStarting connection to server...");
  Udp.beginMulticast(multicastAddress, localPort);  
  
  initCode();
}




void loop() {
  int packetSize = Udp.parsePacket();
  if (packetSize) {
    Serial.print("Received packet of size ");
    Serial.println(packetSize);
    Serial.print("From ");
    IPAddress remoteIp = Udp.remoteIP();
    Serial.print(remoteIp);
    Serial.print(", port ");
    Serial.println(Udp.remotePort());
    
    int len = Udp.read(packetBuffer, 255);
    if (len > 0) {
      packetBuffer[len] = 0;
    }
    Serial.println("Contents:");
    Serial.println(packetBuffer);

    if (strcmp(strtok(packetBuffer, separateur), key) == 0) {
      token = strtok(NULL, separateur);
      if (strcmp(token, "SERVER_TEST") == 0) {
        Serial.println("Test received !");
      } else if (strcmp(token, "SERVER_STILLCONNECTED") == 0) {
        sendMsg("end");
        Serial.println("I'm still here !");
      } else {
        runCode(token);
      }
    }
    Udp.beginPacket(Udp.remoteIP(), Udp.remotePort());
    Udp.write("end1");
    Udp.endPacket();
    */
    
  } 
}



void sendMsg(char *msg){
  Serial.println(Udp.beginPacket(multicastAddress, localPort));
  Udp.write(msg);
  Serial.println(Udp.endPacket());
}



void initCode() {
  pinMode(6, OUTPUT);
}



void runCode(char *msg) {
  digitalWrite(6, HIGH); // sets the digital pin 13 on
  delay(5000);            // waits for a second
  digitalWrite(6, LOW);
}
