//https://techtutorialsx.com/2017/04/29/esp32-sending-json-messages-over-mqtt/
//https://www.baldengineer.com/mqtt-tutorial.html
#include <ArduinoJson.h>
#include "dht.h"
#include <WiFi.h>
#include <PubSubClient.h>
 
// Connect to the WiFi
const char* ssid = "LearningFactory";
const char* password =  "Factory1";
const char* mqttServer = "104.238.164.118";
const int mqttPort = 8883;
const char* payload1 = "Warning! Passage broken!";
const char* payload2 = "Passage Intact";
const char* motion = "somthing is moving";
const char* nomotion = "It is gone now.";
const char* window_broke = "Warning, Glass is shattered.";
const char* window_intact = "Glass Intact.";
const char* fire_state = "WARNING!!! EXTREME HEAT DETECTED IN HOUSE!";
const char* fire_state_off = "No Fire Statues";
const int motion_pin = 32;
const int relay_pin = 25;
const int laser_pin = 35;
const int fire = 34;
const int trig = 2;
const int echo = 27;
const int temperature = 15;

float windowStatues;
static int pre_motion_state;
float brightness;
float heat;
int motionsensor;
String temp_hum;

dht DHT;

WiFiClient espClient;
PubSubClient client(espClient);

void setup() {
  
 pinMode(LED0, OUTPUT);

   Serial.begin(9600);
 
 client.setCallback(callback);

  Serial.println();
 
  WiFi.begin(ssid, password);
 
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.println("Connecting to WiFi..");
  }
 
  Serial.println("Connected to the WiFi network");

  client.setServer(mqttServer, mqttPort);
 
  while (!client.connected()) {
    Serial.println("Connecting to MQTT...");
 
    if (client.connect("")) {
 
      Serial.println("connected");
 
    } else {
 
      Serial.print("failed with state ");
      Serial.print(client.state());
      delay(2000);
 
    }
  }
  //client.subscribe("sensorA/102");
  
  pinMode(motion_pin, INPUT);//Why? just why?
  pinMode(relay_pin, OUTPUT);//Why? just why?
  //pinMode(laser_pin, INPUT);//Why? just why?
  pinMode (trig, OUTPUT);
  pinMode (echo, INPUT);
  pinMode(fire, INPUT);//Why? just why?
  pinMode(temperature, INPUT);//Why? just why?


}


void callback(char* topic, byte* payload, unsigned int length) {
 Serial.print("Message arrived [");
 Serial.print(topic);
 Serial.print("] ");
 for (int i=0;i<length;i++) {
  char receivedChar = (char)payload[i];
  Serial.print(receivedChar);
  if (receivedChar == '0')
  // ESP8266 Huzzah outputs are "reversed"
  digitalWrite(LED0, HIGH);
  if (receivedChar == '1')
   digitalWrite(LED0, LOW);
  }
  Serial.println();
}

float measuredistance(int trig, int echo){
  long duration;
  float distance;
  digitalWrite (trig, LOW);
  delayMicroseconds (2);

  digitalWrite (trig, HIGH);
  delayMicroseconds (10);
  digitalWrite (trig, LOW);
  duration = pulseIn(echo, HIGH);
  distance = duration * 0.034 / 2;
  return distance;
}

void send_motion(const char* content) {
 if (client.publish("house/room/motion", content) == true) {
    Serial.println("Success sending motion");
  } else {
    Serial.println("Error sending message");
  }
 
  client.loop();
  Serial.println("-------------");
 
  delay(100);
}

void send_window(const char* content) {
 if (client.publish("house/room/window", content) == true) {
    Serial.println("Success sending glass");
  } else {
    Serial.println("Error sending message");
  }
 
  client.loop();
  Serial.println("-------------");
 
  delay(100);
}

void send_fire(const char* content) {
 if (client.publish("house/room/fire", content) == true) {
    Serial.println("Success sending glass");
  } else {
    Serial.println("Error sending message");
  }
 
  client.loop();
  Serial.println("-------------");
 
  delay(100);
}


 void send_laser(const char* content) {
 if (client.publish("house/room/laser", content) == true) {
    Serial.println("Success sending laser");
  } else {
    Serial.println("Error sending message");
  }
 
  client.loop();
  Serial.println("-------------");
 
  delay(100);
}

void send_temp(String payload) {
 if (client.publish("house/room/temp", (char*) payload.c_str()) == true) {
    Serial.println("Success sending glass");
  } else {
    Serial.println("Error sending message");
  }
 
  client.loop();
  Serial.println("-------------");
 
  delay(100);
}

 
void loop()
{
  windowStatues = measuredistance(trig, echo);
  pre_motion_state = 0;
  brightness = analogRead(laser_pin);
  heat = analogRead(fire); 
  motionsensor = digitalRead(motion_pin);
  DHT.read11(temperature);
  temp_hum = "humidity : " + String(DHT.humidity) + "  temperature: "+ String(DHT.temperature);
  Serial.println(motionsensor);
    digitalWrite(relay_pin, motionsensor);

  
  //Serial.println(brightness);
  //Laser sensor
  //---------------------------------------------------------------------------------------------------------------
  if(brightness<1500){
    digitalWrite(LED0, 1);
    //Serial.println("here now");

   send_laser(payload1);
  }
  else{
    send_laser(payload2);
    digitalWrite(LED0, 0);
  }

  //fire sensor
  //---------------------------------------------------------------------------------------------------------------
  if(heat < 1500){
    send_fire(fire_state);
  }
  else{
    send_fire(fire_state_off);
  }

  //motion sensor
  //---------------------------------------------------------------------------------------------------------------

 // if(pre_motion_state != motionsensor){//make sure only send a signal at the change of state.
    //pre_motion_state = motionsensor;
   
 // }
   if(motionsensor == 1){
      send_motion(motion);
    }
    else {
      
      send_motion(nomotion);
    }

  //windows sensor
  //---------------------------------------------------------------------------------------------------------------
 
    Serial.println(windowStatues);
  if(windowStatues > 10){
    send_window(window_broke);
  }
  else{
    send_window(window_intact);
  }

  //temperature & humidity
  //---------------------------------------------------------------------------------------------------------------
  send_temp(temp_hum);

  delay(10);
}




