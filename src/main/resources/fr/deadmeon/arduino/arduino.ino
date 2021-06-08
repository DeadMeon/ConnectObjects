#include <SPI.h>
#include <WiFiNINA.h>
#include <WiFiUdp.h>
#include "arduino_secrets.h" 



int status = WL_IDLE_STATUS;
int keyIndex = 0; 
unsigned int localPort = 2390;
char ssid[] = SECRET_SSID;        
char pass[] = SECRET_PASS;    
char key[22] = SECRET_KEY;
char servKey[22] = SECRET_SERV_KEY;
char separateur[] = ":";
IPAddress multicastAddress = IPAddress(229, 97, 225, 142);

char packetBuffer[256]; //buffer to hold incoming packet
char *token;
WiFiUDP Udp;



void setup() {
  Serial.begin(9600);

  // check for the WiFi module:
  while (WiFi.status() == WL_NO_MODULE) {
    Serial.println("Communication with WiFi module failed!");
    delay(5000);
  }

  // check the firmware
  if (WiFi.firmwareVersion() < WIFI_FIRMWARE_LATEST_VERSION) {
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
    
    int len = Udp.read(packetBuffer, 255);
    if (len > 0) {
      packetBuffer[len] = 0;
    }

    printData(packetSize);
    token = strtok(packetBuffer, separateur);

    if (strcmp(token, key) == 0) {
      token = strtok(NULL, separateur);
      if (strcmp(token, servKey) == 0) {
        token = strtok(NULL, separateur);
        if (strcmp(token, "SERVER_TEST") == 0) {
          sendMsg("TEST_RECEIVED");
        } else if (strcmp(token, "SERVER_STILLCONNECTED") == 0) {
          sendMsg("STILLCONNECTED");
        } else {
          runCode(token);
        }
      }
    }
  }
}



void printData(int packetSize) {
  Serial.print("Received packet of size ");
  Serial.println(packetSize);
  
  Serial.print("From ");
  IPAddress remoteIp = Udp.remoteIP();
  Serial.print(remoteIp);
  Serial.print(", port ");
  Serial.println(Udp.remotePort());
  
  Serial.println("Contents : ");
  Serial.println(packetBuffer);
}

void sendMsg(char *msg){
  char str[256];
  sprintf(str,"%s%s%s%s%s",servKey, separateur, key, separateur, msg);
  Udp.beginPacket(multicastAddress, localPort);
  Udp.write(str);
  Udp.endPacket();
}