EESchema Schematic File Version 4
EELAYER 30 0
EELAYER END
$Descr A4 11693 8268
encoding utf-8
Sheet 1 1
Title ""
Date ""
Rev ""
Comp ""
Comment1 ""
Comment2 ""
Comment3 ""
Comment4 ""
$EndDescr
$Comp
L Transistor_FET:2N7002E Q1
U 1 1 5DE20AFC
P 4700 4000
F 0 "Q1" H 4906 4046 50  0000 L CNN
F 1 "2N7002E" H 4906 3955 50  0000 L CNN
F 2 "Package_TO_SOT_SMD:SOT-23" H 4900 3925 50  0001 L CIN
F 3 "http://www.diodes.com/assets/Datasheets/ds30376.pdf" H 4700 4000 50  0001 L CNN
	1    4700 4000
	1    0    0    -1  
$EndComp
$Comp
L Device:R_Small_US R4
U 1 1 5DE2877C
P 4800 3600
F 0 "R4" H 4868 3646 50  0000 L CNN
F 1 "10k" H 4868 3555 50  0000 L CNN
F 2 "Resistor_SMD:R_0805_2012Metric_Pad1.15x1.40mm_HandSolder" H 4800 3600 50  0001 C CNN
F 3 "~" H 4800 3600 50  0001 C CNN
	1    4800 3600
	1    0    0    -1  
$EndComp
$Comp
L Device:R_Small_US R2
U 1 1 5DE2E9BC
P 4400 3250
F 0 "R2" H 4468 3296 50  0000 L CNN
F 1 "10k" H 4468 3205 50  0000 L CNN
F 2 "Resistor_SMD:R_0805_2012Metric_Pad1.15x1.40mm_HandSolder" H 4400 3250 50  0001 C CNN
F 3 "~" H 4400 3250 50  0001 C CNN
	1    4400 3250
	1    0    0    -1  
$EndComp
$Comp
L Device:R_Small_US R7
U 1 1 5DE30CD0
P 5900 3550
F 0 "R7" H 5968 3596 50  0000 L CNN
F 1 "20k" H 5968 3505 50  0000 L CNN
F 2 "Resistor_SMD:R_0805_2012Metric_Pad1.15x1.40mm_HandSolder" H 5900 3550 50  0001 C CNN
F 3 "~" H 5900 3550 50  0001 C CNN
	1    5900 3550
	1    0    0    -1  
$EndComp
$Comp
L Device:R_Small_US R5
U 1 1 5DE31049
P 5500 3550
F 0 "R5" H 5568 3596 50  0000 L CNN
F 1 "20k" H 5568 3505 50  0000 L CNN
F 2 "Resistor_SMD:R_0805_2012Metric_Pad1.15x1.40mm_HandSolder" H 5500 3550 50  0001 C CNN
F 3 "~" H 5500 3550 50  0001 C CNN
	1    5500 3550
	1    0    0    -1  
$EndComp
$Comp
L Device:R_Small_US R6
U 1 1 5DE3153C
P 5500 4150
F 0 "R6" H 5568 4196 50  0000 L CNN
F 1 "10k" H 5568 4105 50  0000 L CNN
F 2 "Resistor_SMD:R_0805_2012Metric_Pad1.15x1.40mm_HandSolder" H 5500 4150 50  0001 C CNN
F 3 "~" H 5500 4150 50  0001 C CNN
	1    5500 4150
	1    0    0    -1  
$EndComp
$Comp
L Device:R_Small_US R3
U 1 1 5DE32289
P 4400 4250
F 0 "R3" H 4468 4296 50  0000 L CNN
F 1 "10k" H 4468 4205 50  0000 L CNN
F 2 "Resistor_SMD:R_0805_2012Metric_Pad1.15x1.40mm_HandSolder" H 4400 4250 50  0001 C CNN
F 3 "~" H 4400 4250 50  0001 C CNN
	1    4400 4250
	1    0    0    -1  
$EndComp
$Comp
L Device:R_Small_US R1
U 1 1 5DE32EF7
P 4200 4000
F 0 "R1" V 3995 4000 50  0000 C CNN
F 1 "10k" V 4086 4000 50  0000 C CNN
F 2 "Resistor_SMD:R_0805_2012Metric_Pad1.15x1.40mm_HandSolder" H 4200 4000 50  0001 C CNN
F 3 "~" H 4200 4000 50  0001 C CNN
	1    4200 4000
	0    1    1    0   
$EndComp
$Comp
L power:GND #PWR02
U 1 1 5DE33487
P 4400 4500
F 0 "#PWR02" H 4400 4250 50  0001 C CNN
F 1 "GND" H 4405 4327 50  0000 C CNN
F 2 "" H 4400 4500 50  0001 C CNN
F 3 "" H 4400 4500 50  0001 C CNN
	1    4400 4500
	1    0    0    -1  
$EndComp
$Comp
L Connector:Conn_01x03_Male J1
U 1 1 5DE33EDA
P 3600 3200
F 0 "J1" H 3572 3132 50  0000 R CNN
F 1 "POW / Control" H 3800 3000 50  0000 R CNN
F 2 "Connector_PinHeader_2.54mm:PinHeader_1x03_P2.54mm_Vertical" H 3600 3200 50  0001 C CNN
F 3 "~" H 3600 3200 50  0001 C CNN
	1    3600 3200
	1    0    0    -1  
$EndComp
$Comp
L Connector:Conn_01x02_Male J3
U 1 1 5DE348DC
P 6650 3750
F 0 "J3" H 6600 3850 50  0000 L CNN
F 1 "ADC Inputs" H 6550 3550 50  0000 L CNN
F 2 "Connector_PinHeader_2.54mm:PinHeader_1x02_P2.54mm_Vertical" H 6650 3750 50  0001 C CNN
F 3 "~" H 6650 3750 50  0001 C CNN
	1    6650 3750
	-1   0    0    -1  
$EndComp
Wire Wire Line
	4400 4150 4400 4000
Wire Wire Line
	4400 4000 4300 4000
Wire Wire Line
	4500 4000 4400 4000
Connection ~ 4400 4000
Wire Wire Line
	4800 3700 4800 3800
Wire Wire Line
	4400 3350 4400 3450
Wire Wire Line
	4400 3450 4800 3450
Wire Wire Line
	4800 3450 4800 3500
$Comp
L Transistor_FET:BSS83P Q2
U 1 1 5DE25183
P 4800 3200
F 0 "Q2" V 5143 3200 50  0000 C CNN
F 1 "BSS83P" V 5052 3200 50  0000 C CNN
F 2 "Package_TO_SOT_SMD:SOT-23" H 5000 3125 50  0001 L CIN
F 3 "http://www.farnell.com/datasheets/1835997.pdf" H 4800 3200 50  0001 L CNN
	1    4800 3200
	0    1    -1   0   
$EndComp
Wire Wire Line
	4800 3400 4800 3450
Connection ~ 4800 3450
Wire Wire Line
	4400 3150 4400 3100
Wire Wire Line
	4400 3100 4600 3100
Wire Wire Line
	3800 3100 4400 3100
Connection ~ 4400 3100
$Comp
L power:GND #PWR01
U 1 1 5DE3D626
P 3850 3500
F 0 "#PWR01" H 3850 3250 50  0001 C CNN
F 1 "GND" H 3855 3327 50  0000 C CNN
F 2 "" H 3850 3500 50  0001 C CNN
F 3 "" H 3850 3500 50  0001 C CNN
	1    3850 3500
	1    0    0    -1  
$EndComp
Wire Wire Line
	3800 3300 3850 3300
Wire Wire Line
	3850 3300 3850 3500
Wire Wire Line
	3800 3200 3950 3200
Wire Wire Line
	3950 3200 3950 4000
Wire Wire Line
	3950 4000 4100 4000
Wire Wire Line
	5000 3100 5500 3100
Wire Wire Line
	5900 3100 5500 3100
Connection ~ 5500 3100
$Comp
L power:GND #PWR04
U 1 1 5DE4420B
P 5500 4500
F 0 "#PWR04" H 5500 4250 50  0001 C CNN
F 1 "GND" H 5505 4327 50  0000 C CNN
F 2 "" H 5500 4500 50  0001 C CNN
F 3 "" H 5500 4500 50  0001 C CNN
	1    5500 4500
	1    0    0    -1  
$EndComp
$Comp
L power:GND #PWR05
U 1 1 5DE448D5
P 5900 4500
F 0 "#PWR05" H 5900 4250 50  0001 C CNN
F 1 "GND" H 5905 4327 50  0000 C CNN
F 2 "" H 5900 4500 50  0001 C CNN
F 3 "" H 5900 4500 50  0001 C CNN
	1    5900 4500
	1    0    0    -1  
$EndComp
$Comp
L power:GND #PWR03
U 1 1 5DE44D7E
P 4800 4500
F 0 "#PWR03" H 4800 4250 50  0001 C CNN
F 1 "GND" H 4805 4327 50  0000 C CNN
F 2 "" H 4800 4500 50  0001 C CNN
F 3 "" H 4800 4500 50  0001 C CNN
	1    4800 4500
	1    0    0    -1  
$EndComp
Wire Wire Line
	4400 4350 4400 4500
Wire Wire Line
	4800 4200 4800 4500
Wire Wire Line
	5500 4250 5500 4500
Wire Wire Line
	5900 4200 5900 4500
Wire Wire Line
	5500 3100 5500 3450
Wire Wire Line
	5900 3100 5900 3450
Text Label 3950 3250 0    50   ~ 0
GPIO_EN
Text Label 3900 3100 0    50   ~ 0
+3.3V
Wire Wire Line
	5500 3650 5500 3750
Wire Wire Line
	5900 3650 5900 3850
Wire Wire Line
	6450 3750 5500 3750
Connection ~ 5500 3750
Wire Wire Line
	5500 3750 5500 4050
Wire Wire Line
	6450 3850 5900 3850
Connection ~ 5900 3850
Wire Wire Line
	5900 3850 5900 4100
Text Label 6200 3750 0    50   ~ 0
Vref
Text Label 6200 3850 0    50   ~ 0
Vth
$Comp
L Connector:Conn_01x02_Male J2
U 1 1 5DE4DB18
P 6100 4100
F 0 "J2" H 6050 4200 50  0000 L CNN
F 1 "Termistor 10k" H 5750 3900 50  0000 L CNN
F 2 "Connector_PinHeader_2.54mm:PinHeader_1x02_P2.54mm_Vertical" H 6100 4100 50  0001 C CNN
F 3 "~" H 6100 4100 50  0001 C CNN
	1    6100 4100
	-1   0    0    -1  
$EndComp
Text Notes 3950 2700 0    80   ~ 16
Thermistor Supporting Hardware
$EndSCHEMATC
