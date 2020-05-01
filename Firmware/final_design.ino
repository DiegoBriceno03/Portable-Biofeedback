// Final Design
// Nick Miller for Group 11


// Heart Rate Libraries
#include <Wire.h>
#include "MAX30105.h"
#include "heartRate.h"

// BLE Libraries
#include <BLEDevice.h>
#include <BLEUtils.h>
#include <BLEServer.h>
#include <BLE2902.h>


// Setup variables for the Thermistor
// The formula for temp in kelvin is
//                 1
// T = ----------------------------
//     1/To + (1/beta) * ln(Rt/Ro)
const int thermistorPin = 35;
float adcMax = 4095.0; // Max value of ADC, 12 bit: 0 -> 4095 
float Vs = 3.3; // Supply voltage for the voltage divider
float R1 = 9800.0;   // voltage divider resistor value
float Beta = 3950.0;  // Beta value
float To = 298.15;    // Temperature in Kelvin for 25 degree Celsius
float Ro = 10000.0;   // Resistance of Thermistor at 25 degree Celsius
float Vout, Rt, T, Tf = 0;
char string_Tf[4]; // Should be equivalent to a float (4bytes)


// Setup variables for GSR sensor
const int gsrPin = 34;
int sum = 0;
int gsrValue = 0;
int gsrAverage = 0;


// Setup Variables for Heart Rate Sensor
MAX30105 particleSensor;
const byte RATE_SIZE = 4; //Increase this for more averaging. 4 is good.
byte rates[RATE_SIZE]; //Array of heart rates
byte rateSpot = 0;
long lastBeat = 0; //Time at which the last beat occurred
float beatsPerMinute;
int previousHeartRate = 0;
int beatAvg;


// Setup variables for BLE
#define SERVICE_UUID "63228c99-ee20-4dcf-a90a-711948af59e0"
#define THERMISTOR_UUID "00002A1C-0000-1000-8000-00805f9b34fb"
#define GSR_UUID "0d823996-e5a7-4ce3-9ae3-6649d46d8f85"
#define HEARTRATE_UUID "00002A37-0000-1000-8000-00805f9b34fb"
#define BATTERY_UUID "00002A19-0000-1000-8000-00805f9b34fb"

BLECharacteristic *thermistorChar;
BLECharacteristic *gsrChar;
BLECharacteristic *heartRateChar;
BLECharacteristic *batteryChar;

uint32_t i = 0;

bool deviceConnected = false;
bool oldDeviceConnected = false;

class MyServerCallbacks: public BLEServerCallbacks {
    void onConnect(BLEServer* pServer) {
      deviceConnected = true;
    };

    void onDisconnect(BLEServer* pServer) {
      deviceConnected = false;
    }
};

void setup() {
  // Start Serial
  Serial.begin(115200);

  // Setup BLE
  Serial.println("Setting up BLE");
  
  // Setup the service
  BLEDevice::init("Group 11 BLE");
  BLEServer *pServer = BLEDevice::createServer();
  pServer->setCallbacks(new MyServerCallbacks());
  BLEService *pService = pServer->createService(SERVICE_UUID);

  // Setup the Thermistor Characteristic
//  Serial.println("1");
  thermistorChar = pService->createCharacteristic(
                                         THERMISTOR_UUID,
                                         BLECharacteristic::PROPERTY_NOTIFY |
                                         BLECharacteristic::PROPERTY_READ |
                                         BLECharacteristic::PROPERTY_WRITE |
                                         BLECharacteristic::PROPERTY_INDICATE
                                       );
  //thermistorChar->setValue(0);

  thermistorChar->addDescriptor(new BLE2902());

  // Setup the GSR Characteristic
//  Serial.println("2");
  gsrChar = pService->createCharacteristic(
                                         GSR_UUID,
                                         BLECharacteristic::PROPERTY_NOTIFY |
                                         BLECharacteristic::PROPERTY_READ |
                                         BLECharacteristic::PROPERTY_WRITE |
                                         BLECharacteristic::PROPERTY_INDICATE
                                       );
  //gsrChar->setValue(0);

  gsrChar->addDescriptor(new BLE2902());

  // Setup the Heart Rate Characteristic
//  Serial.println("3");
  heartRateChar = pService->createCharacteristic(
                                         HEARTRATE_UUID,
                                         BLECharacteristic::PROPERTY_NOTIFY |
                                         BLECharacteristic::PROPERTY_READ |
                                         BLECharacteristic::PROPERTY_WRITE |
                                         BLECharacteristic::PROPERTY_INDICATE
                                       );
  //heartRateChar->setValue(0);

  heartRateChar->addDescriptor(new BLE2902());

  // Setup the Battery Characteristic
  // Serial.println("4");
  batteryChar = pService->createCharacteristic(
                                         BATTERY_UUID,
                                         BLECharacteristic::PROPERTY_NOTIFY |
                                         BLECharacteristic::PROPERTY_READ |
                                         BLECharacteristic::PROPERTY_WRITE |
                                         BLECharacteristic::PROPERTY_INDICATE
                                       );
  //batteryChar->setValue(0);

  batteryChar->addDescriptor(new BLE2902());
  
  pService->start();
  // BLEAdvertising *pAdvertising = pServer->getAdvertising();  // this still is working for backward compatibility
  BLEAdvertising *pAdvertising = BLEDevice::getAdvertising();
  pAdvertising->addServiceUUID(SERVICE_UUID);
  pAdvertising->setScanResponse(true);
  pAdvertising->setMinPreferred(0x06);  // functions that help with iPhone connections issue
  pAdvertising->setMinPreferred(0x12);
  BLEDevice::startAdvertising();
  Serial.println("BLE Setup Complete");


  // Initialize heart rate sensor
  if (!particleSensor.begin(Wire, I2C_SPEED_FAST)) //Use default I2C port, 400kHz speed
  {
    Serial.println("MAX30105 was not found. Please check wiring/power. ");
    while (1);
  }
  Serial.println("Place your index finger on the sensor with steady pressure.");

  particleSensor.setup(); //Configure sensor with default settings
  particleSensor.setPulseAmplitudeRed(0); // Turn Red LED off
  particleSensor.setPulseAmplitudeIR(0); // Turn IR LED off
//  particleSensor.setPulseAmplitudeGreen(0); // Turn off Green LED

}

void loop() {

  i++;

//  Serial.println("Start");

  // Read the thermistor and convert it to fahrenheit

  if(!(i % 256)){
    Vout = .15 + (analogRead(thermistorPin) * Vs/adcMax); // .2 is added to make the sensor more accurate
//    Serial.println(Vout); // use to calibrate
    Rt = R1 * Vout / (Vs - Vout); 
    T = 1/(1/To + log(Rt/Ro)/Beta);  // Temperature in Kelvin
    Tf = (T - 273.15) * 9 / 5 + 32; // Convert to Fahrenheit

  // Debugging purposes
    //Serial.printf("Before Temp: %.2f\n", Tf);

    dtostrf(Tf, 4, 2, string_Tf);

  // For debugging purposes
    //Serial.printf("String Temp: %s\n", buff);
    thermistorChar->setValue(string_Tf);
    thermistorChar->notify();
  }

  
  //read the gsr sensor
  
  if(!(i % 256)){
    //reset sum
    sum=0;
    //Average the 10 measurements to remove the glitch
    for(int i=0;i<10;i++) {   
      gsrValue=analogRead(gsrPin);
      sum += gsrValue;
      delay(5);
    }
    gsrAverage = sum/10;

  // For debugging purposes
//    Serial.printf("GSR: %d\n", gsrAverage);
    gsrChar->setValue(gsrAverage);
    gsrChar->notify();
  }


  //read the heart rate sensor
  long irValue = particleSensor.getGreen();

  if (checkForBeat(irValue) == true){

//    Serial.println("found beat");
    
    //We sensed a beat!
    long delta = millis() - lastBeat;
    lastBeat = millis();

    beatsPerMinute = 60 / (delta / 1000.0);

    if (beatsPerMinute < 255 && beatsPerMinute > 20) {
      rates[rateSpot++] = (byte)beatsPerMinute; //Store this reading in the array
      rateSpot %= RATE_SIZE; //Wrap variable

      //Take average of readings
      beatAvg = 0;
      for (byte x = 0 ; x < RATE_SIZE ; x++)
        beatAvg += rates[x];
      beatAvg /= RATE_SIZE;
    }
  }

  // For debugging purposes
  //Serial.printf("Heartbeart: %d\n", beatAvg);

  // if the value is new then send it
  if(beatAvg != previousHeartRate){
    heartRateChar->setValue(beatAvg);
    heartRateChar->notify();
  }

  // set the previous
  previousHeartRate = beatAvg;
  

// left in debugging data sends for testing if needed
  
//  i++  
//  thermistorChar->setValue(i);
//  thermistorChar->notify();
//  delay(10);
//  gsrChar->setValue(i);
//  gsrChar->notify();
//  delay(10);
//  heartRateChar->setValue(i);
//  heartRateChar->notify();
//  delay(10);
//  batteryChar->setValue(i);
//  batteryChar->notify();

  //delay(2000);

}
