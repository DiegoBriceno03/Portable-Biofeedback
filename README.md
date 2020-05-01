# Portable Biofeedback Device Repository

A repository that stores hardware and software files developed for the Portable Biofeedback Device, as worked on and built by students in the Electrical & Computer Engineering (ECE) Department of the College of Engineering & Computer Science (CECS) at the University of Central Florida (UCF) in Orlando, Florida.

The accompanying Android app was developed in Android Studio 3.5.3, making use of Android SDK/API 21 (Android 5).

All the schematics and layout is done in Kicad 5.14 along with custom libraries. These libraries are added under /Libraries.

These projects use custom path for its libraries. The custom Kicad Path variable ${KI_SDREPO} is used to increase portability. Custom variable points to the physical location of the /Libraries directory and must be declared under "Preferences > Configure Paths" setting before use.


## Group 11 Members

Roles are listed under each group member

+ Grace Brazil (EE)
  + Power circuit design
+ Diego Briceno (EE)
  + PCB design
+ Gustavo Diaz Galeas (CpE)
  + Phone app development
+ Nicholas Miller (CpE)
  + Firmware development

## Hardware

### Breakout Boards
- [x] MAX30101 Heart Rate / Pulse Oximeter Breakout Board REV -
- [x] Galvanic Skin Response Sensor (Groove Seed GSR Sensor design used: https://www.seeedstudio.com/Grove-GSR-sensor-p-1614.html.html)
- [x] Temperature Sensor Supporting Hardware
- [x] Battery Charging Managing circuit (BMS)
- [x] Power Regulation
Integration of these hardware is seen under ~/Prototyp_REV- 

## Software
### Phone App
- [x] Bluetooth backend
- [X] UI / UX

### Firmware
- [x] Heart Rate Data Processing Algorithm
- [x] Temperature Data Processing Algorithm
- [x] Galvanic Skin Resistance Data Processing Algorithm
