EESchema Schematic File Version 4
LIBS:5boost-cache
EELAYER 29 0
EELAYER END
$Descr A4 11693 8268
encoding utf-8
Sheet 2 2
Title ""
Date ""
Rev ""
Comp ""
Comment1 ""
Comment2 ""
Comment3 ""
Comment4 ""
$EndDescr
Text HLabel 9300 2600 2    50   Input ~ 0
Voutbat
Text HLabel 9400 2900 0    50   Input ~ 0
GND
$Comp
L power:GND #PWR0106
U 1 1 5E251990
P 9650 3000
F 0 "#PWR0106" H 9650 2750 50  0001 C CNN
F 1 "GND" H 9655 2827 50  0000 C CNN
F 2 "" H 9650 3000 50  0001 C CNN
F 3 "" H 9650 3000 50  0001 C CNN
	1    9650 3000
	1    0    0    -1  
$EndComp
Wire Wire Line
	9400 2900 9650 2900
Wire Wire Line
	9650 2900 9650 3000
$EndSCHEMATC
