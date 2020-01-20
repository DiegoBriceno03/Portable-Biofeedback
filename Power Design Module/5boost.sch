EESchema Schematic File Version 4
LIBS:5boost-cache
EELAYER 29 0
EELAYER END
$Descr A4 11693 8268
encoding utf-8
Sheet 1 2
Title ""
Date ""
Rev ""
Comp ""
Comment1 ""
Comment2 ""
Comment3 ""
Comment4 ""
$EndDescr
Wire Wire Line
	3550 2650 2800 2650
Wire Wire Line
	3850 2650 4750 2650
Wire Wire Line
	4750 2650 4750 3350
Wire Wire Line
	4750 3450 4750 3350
Connection ~ 4750 3350
Wire Wire Line
	3750 4500 3750 4350
Wire Wire Line
	3750 4350 2800 4350
Wire Wire Line
	2800 4350 2800 3950
Wire Wire Line
	2800 3950 2950 3950
Wire Wire Line
	3750 4350 5650 4350
Connection ~ 3750 4350
Connection ~ 2800 4350
Wire Wire Line
	2950 3800 2450 3800
Wire Wire Line
	5050 3550 5050 3300
Wire Wire Line
	4550 3650 5050 3650
Wire Wire Line
	5050 3650 5050 3550
Connection ~ 5050 3550
Wire Wire Line
	1900 3650 1900 3350
Wire Wire Line
	1900 3950 1900 4350
Connection ~ 1900 4350
Wire Wire Line
	4550 3350 4750 3350
Wire Wire Line
	4550 3450 4750 3450
Wire Wire Line
	4550 3550 5050 3550
Wire Wire Line
	2950 3350 2800 3350
Connection ~ 1900 3350
Wire Wire Line
	2800 2650 2800 3350
Connection ~ 2800 3350
Wire Wire Line
	2800 3350 1900 3350
Wire Wire Line
	2950 3450 2800 3450
Wire Wire Line
	2800 3450 2800 3350
$Comp
L TPS61232DRCR:TPS61232DRCR U1
U 1 1 5E215DC2
P 2950 3350
F 0 "U1" H 3750 3737 60  0000 C CNN
F 1 "TPS61232DRCR" H 3750 3631 60  0000 C CNN
F 2 "TPS61232DRCR:TPS61232DRCR" H 3750 3600 60  0001 C CNN
F 3 "https://www.ti.com/lit/ds/symlink/tps61232.pdf" H 2950 3350 60  0001 C CNN
	1    2950 3350
	1    0    0    -1  
$EndComp
$Comp
L Device:L L1
U 1 1 5E201A61
P 3700 2650
F 0 "L1" V 3890 2650 50  0000 C CNN
F 1 "1uH" V 3799 2650 50  0000 C CNN
F 2 "IHLP2020BZER1R0M01:IHLP2020BZER1R0M01" H 3700 2650 50  0001 C CNN
F 3 "https://www.vishay.com/docs/34253/ihlp-2020bz-01.pdf" H 3700 2650 50  0001 C CNN
	1    3700 2650
	0    -1   -1   0   
$EndComp
$Comp
L 5boost-rescue:C1608X5R1A106M080AC-C1608X5R1A106M080AC C7
U 1 1 5E214585
P 1900 3650
F 0 "C7" V 1997 3754 60  0000 L CNN
F 1 "10μF" V 2150 3750 60  0000 L CNN
F 2 "Capacitor_SMD:C_0603_1608Metric_Pad1.05x0.95mm_HandSolder" H 2050 3290 60  0001 C CNN
F 3 "https://product.tdk.com/info/en/catalog/datasheets/mlcc_commercial_general_en.pdf" H 1900 3650 60  0001 C CNN
	1    1900 3650
	0    1    1    0   
$EndComp
Wire Wire Line
	5050 3300 5650 3300
$Comp
L 5boost-rescue:C2012X7R1A106M125AC-C2012X7R1A106M125AC C9
U 1 1 5E220947
P 5650 3350
F 0 "C9" V 5747 3454 60  0000 L CNN
F 1 "10uF" V 5900 3500 60  0000 L CNN
F 2 "Capacitor_SMD:C_0805_2012Metric_Pad1.15x1.40mm_HandSolder" H 5800 2990 60  0001 C CNN
F 3 "https://product.tdk.com/info/en/catalog/datasheets/mlcc_commercial_general_en.pdf" H 5650 3350 60  0001 C CNN
	1    5650 3350
	0    1    1    0   
$EndComp
Wire Wire Line
	5650 3350 5650 3300
$Comp
L TPPM0110DWPR:TPPM0110DWPR U2
U 1 1 5E211CE4
P 6550 2950
F 0 "U2" H 7650 2900 60  0000 C CNN
F 1 "TPPM0110DWPR" H 7650 2800 60  0000 C CNN
F 2 "TPPM0110DWPR:TPPM0110DWPR" H 7850 3190 60  0001 C CNN
F 3 "https://www.ti.com/lit/ds/symlink/tppm0110.pdf" H 6550 2950 60  0001 C CNN
	1    6550 2950
	1    0    0    -1  
$EndComp
Wire Wire Line
	5650 3650 5650 4350
$Comp
L Device:C_Small C1
U 1 1 5E21D683
P 6250 3550
F 0 "C1" H 6342 3596 50  0000 L CNN
F 1 "10uF" H 6342 3505 50  0000 L CNN
F 2 "Capacitor_SMD:C_0603_1608Metric_Pad1.05x0.95mm_HandSolder" H 6250 3550 50  0001 C CNN
F 3 "https://search.murata.co.jp/Ceramy/image/img/A01X/G101/ENG/GRM188R61A106KE69-01.pdf" H 6250 3550 50  0001 C CNN
F 4 "https://www.digikey.com/product-detail/en/murata-electronics/GRM188R61A106KE69J/490-14372-1-ND/6606833" H 6250 3550 50  0001 C CNN "Allied Price/Stock"
F 5 "10µF ±10% 10V Ceramic Capacitor X5R 0603 (1608 Metric)" H 6250 3550 50  0001 C CNN "Description"
F 6 "Murata" H 6250 3550 50  0001 C CNN "Manufacturer_Name"
F 7 "GRM188R61A106KE69J" H 6250 3550 50  0001 C CNN "Manufacturer_Part_Number"
F 8 "https://www.digikey.com/product-detail/en/murata-electronics/GRM188R61A106KE69J/490-14372-1-ND/6606833" H 6250 3550 50  0001 C CNN "Mouser Price/Stock"
	1    6250 3550
	1    0    0    -1  
$EndComp
$Comp
L Device:C_Small C2
U 1 1 5E222350
P 6750 3550
F 0 "C2" H 6842 3596 50  0000 L CNN
F 1 "0.1uF" H 6842 3505 50  0000 L CNN
F 2 "Capacitor_SMD:C_0603_1608Metric_Pad1.05x0.95mm_HandSolder" H 6750 3550 50  0001 C CNN
F 3 "https://content.kemet.com/datasheets/KEM_C1003_C0G_SMD.pdf" H 6750 3550 50  0001 C CNN
F 4 "https://www.digikey.com/product-detail/en/kemet/C0201C101J3GACTU/399-8925-1-ND/3522442" H 6750 3550 50  0001 C CNN "Allied Price/Stock"
F 5 "	100pF ±5% 25V Ceramic Capacitor C0G, NP0 0201 (0603 Metric)" H 6750 3550 50  0001 C CNN "Description"
F 6 "KEMET" H 6750 3550 50  0001 C CNN "Manufacturer_Name"
F 7 "C0201C101J3GACTU" H 6750 3550 50  0001 C CNN "Manufacturer_Part_Number"
F 8 "https://www.digikey.com/product-detail/en/kemet/C0201C101J3GACTU/399-8925-1-ND/3522442" H 6750 3550 50  0001 C CNN "Mouser Price/Stock"
	1    6750 3550
	1    0    0    -1  
$EndComp
$Comp
L Device:C_Small C3
U 1 1 5E224949
P 8600 3550
F 0 "C3" H 8692 3596 50  0000 L CNN
F 1 "0.1uF" H 8692 3505 50  0000 L CNN
F 2 "Capacitor_SMD:C_0603_1608Metric_Pad1.05x0.95mm_HandSolder" H 8600 3550 50  0001 C CNN
F 3 "https://content.kemet.com/datasheets/KEM_C1003_C0G_SMD.pdf" H 8600 3550 50  0001 C CNN
F 4 "https://www.digikey.com/product-detail/en/kemet/C0201C101J3GACTU/399-8925-1-ND/3522442" H 8600 3550 50  0001 C CNN "Allied Price/Stock"
F 5 "	100pF ±5% 25V Ceramic Capacitor C0G, NP0 0201 (0603 Metric)" H 8600 3550 50  0001 C CNN "Description"
F 6 "KEMET" H 8600 3550 50  0001 C CNN "Manufacturer_Name"
F 7 "C0201C101J3GACTU" H 8600 3550 50  0001 C CNN "Manufacturer_Part_Number"
F 8 "https://www.digikey.com/product-detail/en/kemet/C0201C101J3GACTU/399-8925-1-ND/3522442" H 8600 3550 50  0001 C CNN "Mouser Price/Stock"
	1    8600 3550
	1    0    0    -1  
$EndComp
$Comp
L Device:C_Small C5
U 1 1 5E2253F6
P 9100 3550
F 0 "C5" H 9192 3596 50  0000 L CNN
F 1 "10uF" H 9192 3505 50  0000 L CNN
F 2 "Capacitor_SMD:C_0603_1608Metric_Pad1.05x0.95mm_HandSolder" H 9100 3550 50  0001 C CNN
F 3 "https://search.murata.co.jp/Ceramy/image/img/A01X/G101/ENG/GRM188R61A106KE69-01.pdf" H 9100 3550 50  0001 C CNN
F 4 "https://www.digikey.com/product-detail/en/murata-electronics/GRM188R61A106KE69J/490-14372-1-ND/6606833" H 9100 3550 50  0001 C CNN "Allied Price/Stock"
F 5 "10µF ±10% 10V Ceramic Capacitor X5R 0603 (1608 Metric)" H 9100 3550 50  0001 C CNN "Description"
F 6 "Murata" H 9100 3550 50  0001 C CNN "Manufacturer_Name"
F 7 "GRM188R61A106KE69J" H 9100 3550 50  0001 C CNN "Manufacturer_Part_Number"
F 8 "https://www.digikey.com/product-detail/en/murata-electronics/GRM188R61A106KE69J/490-14372-1-ND/6606833" H 9100 3550 50  0001 C CNN "Mouser Price/Stock"
	1    9100 3550
	1    0    0    -1  
$EndComp
$Comp
L Device:C_Small C4
U 1 1 5E225E5D
P 8600 4250
F 0 "C4" H 8692 4296 50  0000 L CNN
F 1 "0.1uF" H 8692 4205 50  0000 L CNN
F 2 "Capacitor_SMD:C_0603_1608Metric_Pad1.05x0.95mm_HandSolder" H 8600 4250 50  0001 C CNN
F 3 "https://content.kemet.com/datasheets/KEM_C1003_C0G_SMD.pdf" H 8600 4250 50  0001 C CNN
F 4 "https://www.digikey.com/product-detail/en/kemet/C0201C101J3GACTU/399-8925-1-ND/3522442" H 8600 4250 50  0001 C CNN "Allied Price/Stock"
F 5 "	100pF ±5% 25V Ceramic Capacitor C0G, NP0 0201 (0603 Metric)" H 8600 4250 50  0001 C CNN "Description"
F 6 "KEMET" H 8600 4250 50  0001 C CNN "Manufacturer_Name"
F 7 "C0201C101J3GACTU" H 8600 4250 50  0001 C CNN "Manufacturer_Part_Number"
F 8 "https://www.digikey.com/product-detail/en/kemet/C0201C101J3GACTU/399-8925-1-ND/3522442" H 8600 4250 50  0001 C CNN "Mouser Price/Stock"
	1    8600 4250
	1    0    0    -1  
$EndComp
$Comp
L Device:C_Small C6
U 1 1 5E226DBD
P 9100 4250
F 0 "C6" H 9192 4296 50  0000 L CNN
F 1 "10uF" H 9192 4205 50  0000 L CNN
F 2 "Capacitor_SMD:C_0603_1608Metric_Pad1.05x0.95mm_HandSolder" H 9100 4250 50  0001 C CNN
F 3 "https://search.murata.co.jp/Ceramy/image/img/A01X/G101/ENG/GRM188R61A106KE69-01.pdf" H 9100 4250 50  0001 C CNN
F 4 "https://www.digikey.com/product-detail/en/murata-electronics/GRM188R61A106KE69J/490-14372-1-ND/6606833" H 9100 4250 50  0001 C CNN "Allied Price/Stock"
F 5 "10µF ±10% 10V Ceramic Capacitor X5R 0603 (1608 Metric)" H 9100 4250 50  0001 C CNN "Description"
F 6 "Murata" H 9100 4250 50  0001 C CNN "Manufacturer_Name"
F 7 "GRM188R61A106KE69J" H 9100 4250 50  0001 C CNN "Manufacturer_Part_Number"
F 8 "https://www.digikey.com/product-detail/en/murata-electronics/GRM188R61A106KE69J/490-14372-1-ND/6606833" H 9100 4250 50  0001 C CNN "Mouser Price/Stock"
	1    9100 4250
	1    0    0    -1  
$EndComp
Wire Wire Line
	6250 3450 6250 3300
Wire Wire Line
	6250 3300 6750 3300
Wire Wire Line
	6750 3450 6750 3300
Connection ~ 6750 3300
Wire Wire Line
	6750 3300 7100 3300
Wire Wire Line
	6250 3650 6250 3750
Wire Wire Line
	8150 3300 8600 3300
Wire Wire Line
	8600 3300 8600 3450
Wire Wire Line
	8600 3300 9100 3300
Wire Wire Line
	9100 3300 9100 3450
Connection ~ 8600 3300
Wire Wire Line
	8850 3700 8850 3750
Wire Wire Line
	8150 4050 8600 4050
Wire Wire Line
	8600 4050 8600 4150
Wire Wire Line
	9100 4150 9100 4050
Wire Wire Line
	9100 4050 8600 4050
Connection ~ 8600 4050
Wire Wire Line
	8600 4350 8600 4450
Wire Wire Line
	9100 4350 9100 4450
Wire Wire Line
	8600 3650 8600 3700
Wire Wire Line
	8600 3700 8850 3700
Wire Wire Line
	9100 3650 9100 3700
Wire Wire Line
	9100 3700 8850 3700
Connection ~ 8850 3700
Wire Wire Line
	6750 3650 6750 3750
Wire Wire Line
	5650 3300 6250 3300
Connection ~ 5650 3300
Connection ~ 6250 3300
$Sheet
S 800  3250 550  1200
U 5E25984C
F0 "ChargingCircuit" 50
F1 "ChargingCircuit.sch" 50
F2 "Voutbat" I R 1350 3350 50 
F3 "GND" I R 1350 4350 50 
$EndSheet
Wire Wire Line
	6250 3750 6500 3750
Wire Wire Line
	8600 4450 8850 4450
Wire Wire Line
	1350 3350 1900 3350
$Comp
L power:GND #PWR0101
U 1 1 5E251E92
P 3750 4500
F 0 "#PWR0101" H 3750 4250 50  0001 C CNN
F 1 "GND" H 3755 4327 50  0000 C CNN
F 2 "" H 3750 4500 50  0001 C CNN
F 3 "" H 3750 4500 50  0001 C CNN
	1    3750 4500
	1    0    0    -1  
$EndComp
$Comp
L power:GND #PWR0103
U 1 1 5E2526DE
P 6500 3850
F 0 "#PWR0103" H 6500 3600 50  0001 C CNN
F 1 "GND" H 6505 3677 50  0000 C CNN
F 2 "" H 6500 3850 50  0001 C CNN
F 3 "" H 6500 3850 50  0001 C CNN
	1    6500 3850
	1    0    0    -1  
$EndComp
$Comp
L power:GND #PWR0104
U 1 1 5E25339E
P 8850 3750
F 0 "#PWR0104" H 8850 3500 50  0001 C CNN
F 1 "GND" H 8855 3577 50  0000 C CNN
F 2 "" H 8850 3750 50  0001 C CNN
F 3 "" H 8850 3750 50  0001 C CNN
	1    8850 3750
	1    0    0    -1  
$EndComp
$Comp
L power:GND #PWR0105
U 1 1 5E2536D6
P 8850 4500
F 0 "#PWR0105" H 8850 4250 50  0001 C CNN
F 1 "GND" H 8855 4327 50  0000 C CNN
F 2 "" H 8850 4500 50  0001 C CNN
F 3 "" H 8850 4500 50  0001 C CNN
	1    8850 4500
	1    0    0    -1  
$EndComp
Wire Wire Line
	6500 3850 6500 3750
Connection ~ 6500 3750
Wire Wire Line
	6500 3750 6750 3750
Wire Wire Line
	8850 4500 8850 4450
Connection ~ 8850 4450
Wire Wire Line
	8850 4450 9100 4450
Wire Wire Line
	1350 4350 1900 4350
$Comp
L 5boost-rescue:C1608X5R1A106M080AC-C1608X5R1A106M080AC C8
U 1 1 5E252172
P 2450 3950
F 0 "C8" V 2547 4054 60  0000 L CNN
F 1 "15nF" V 2700 4050 60  0000 L CNN
F 2 "Capacitor_SMD:C_0603_1608Metric_Pad1.05x0.95mm_HandSolder" H 2600 3590 60  0001 C CNN
F 3 "https://www.murata.com/~/media/webrenewal/support/library/catalog/products/capacitor/mlcc/c02e.ashx?la=en-us" H 2450 3950 60  0001 C CNN
	1    2450 3950
	0    1    1    0   
$EndComp
Wire Wire Line
	1900 4350 2450 4350
Wire Wire Line
	2450 4250 2450 4350
Connection ~ 2450 4350
Wire Wire Line
	2450 4350 2800 4350
Wire Wire Line
	2450 3800 2450 3950
$Comp
L power:GND #PWR0102
U 1 1 5E252275
P 7100 4300
F 0 "#PWR0102" H 7100 4050 50  0001 C CNN
F 1 "GND" H 7105 4127 50  0000 C CNN
F 2 "" H 7100 4300 50  0001 C CNN
F 3 "" H 7100 4300 50  0001 C CNN
	1    7100 4300
	1    0    0    -1  
$EndComp
Wire Wire Line
	7100 4050 7100 4300
$Comp
L power:GND #PWR03
U 1 1 5E25CE32
P 9650 4300
F 0 "#PWR03" H 9650 4050 50  0001 C CNN
F 1 "GND" H 9655 4127 50  0000 C CNN
F 2 "" H 9650 4300 50  0001 C CNN
F 3 "" H 9650 4300 50  0001 C CNN
	1    9650 4300
	1    0    0    -1  
$EndComp
$Comp
L power:GND #PWR01
U 1 1 5E2632C9
P 9650 2600
F 0 "#PWR01" H 9650 2350 50  0001 C CNN
F 1 "GND" H 9655 2427 50  0000 C CNN
F 2 "" H 9650 2600 50  0001 C CNN
F 3 "" H 9650 2600 50  0001 C CNN
	1    9650 2600
	1    0    0    -1  
$EndComp
Wire Wire Line
	9650 2450 9650 2600
Wire Wire Line
	9650 4150 9650 4300
Wire Wire Line
	9650 3400 9650 3550
Wire Wire Line
	5650 2350 5650 3300
Text GLabel 10800 2350 2    50   Input ~ 0
5V
Text GLabel 10800 3300 2    50   Input ~ 0
3.3V
Text GLabel 10800 4050 2    50   Input ~ 0
1.8V
$Comp
L power:GND #PWR05
U 1 1 5E28008A
P 10450 3550
F 0 "#PWR05" H 10450 3300 50  0001 C CNN
F 1 "GND" H 10455 3377 50  0000 C CNN
F 2 "" H 10450 3550 50  0001 C CNN
F 3 "" H 10450 3550 50  0001 C CNN
	1    10450 3550
	1    0    0    -1  
$EndComp
$Comp
L power:GND #PWR06
U 1 1 5E280799
P 10450 4300
F 0 "#PWR06" H 10450 4050 50  0001 C CNN
F 1 "GND" H 10455 4127 50  0000 C CNN
F 2 "" H 10450 4300 50  0001 C CNN
F 3 "" H 10450 4300 50  0001 C CNN
	1    10450 4300
	1    0    0    -1  
$EndComp
$Comp
L power:GND #PWR04
U 1 1 5E2810CF
P 10450 2600
F 0 "#PWR04" H 10450 2350 50  0001 C CNN
F 1 "GND" H 10455 2427 50  0000 C CNN
F 2 "" H 10450 2600 50  0001 C CNN
F 3 "" H 10450 2600 50  0001 C CNN
	1    10450 2600
	1    0    0    -1  
$EndComp
Wire Wire Line
	10450 2450 10450 2600
Wire Wire Line
	10450 3400 10450 3550
Wire Wire Line
	10450 4150 10450 4300
$Comp
L power:GND #PWR02
U 1 1 5E25FF35
P 9650 3550
F 0 "#PWR02" H 9650 3300 50  0001 C CNN
F 1 "GND" H 9655 3377 50  0000 C CNN
F 2 "" H 9650 3550 50  0001 C CNN
F 3 "" H 9650 3550 50  0001 C CNN
	1    9650 3550
	1    0    0    -1  
$EndComp
Wire Wire Line
	10450 3300 10800 3300
Wire Wire Line
	10450 4050 10800 4050
Wire Wire Line
	10450 2350 10800 2350
Wire Wire Line
	9650 2350 5650 2350
Wire Wire Line
	9650 3300 9100 3300
Connection ~ 9100 3300
Wire Wire Line
	9650 4050 9100 4050
Connection ~ 9100 4050
Wire Wire Line
	4550 3950 5050 3950
Wire Wire Line
	5050 3950 5050 3650
Connection ~ 5050 3650
$Comp
L MOLEX_Connector_105430:105430-1102 J1
U 1 1 5E264EDD
P 9650 2350
F 0 "J1" H 10050 2615 50  0000 C CNN
F 1 "105430-1102" H 10050 2524 50  0000 C CNN
F 2 "105430-1102:1054301102" H 10300 2450 50  0001 L CNN
F 3 "https://componentsearchengine.com/Datasheets/2/105430-1102.pdf" H 10300 2350 50  0001 L CNN
F 4 "https://www.alliedelec.com/molexincorporated-105430-1102/71607964/" H 9650 2350 50  0001 C CNN "Allied Price/Stock"
F 5 "71607964" H 9650 2350 50  0001 C CNN "Allied_Number"
F 6 "MOLEX - 105430-1102 - NANO-FIT HDR SMT RA SGL 2CKT TIN BLK 84AC4685" H 9650 2350 50  0001 C CNN "Description"
F 7 "6.99" H 9650 2350 50  0001 C CNN "Height"
F 8 "Molex" H 9650 2350 50  0001 C CNN "Manufacturer_Name"
F 9 "105430-1102" H 9650 2350 50  0001 C CNN "Manufacturer_Part_Number"
F 10 "538-105430-1102" H 9650 2350 50  0001 C CNN "Mouser Part Number"
F 11 "https://www.mouser.com/Search/Refine.aspx?Keyword=538-105430-1102" H 9650 2350 50  0001 C CNN "Mouser Price/Stock"
	1    9650 2350
	1    0    0    -1  
$EndComp
$Comp
L MOLEX_Connector_105430:105430-1102 J2
U 1 1 5E2656E1
P 9650 3300
F 0 "J2" H 10050 3565 50  0000 C CNN
F 1 "105430-1102" H 10050 3474 50  0000 C CNN
F 2 "105430-1102:1054301102" H 10300 3400 50  0001 L CNN
F 3 "https://componentsearchengine.com/Datasheets/2/105430-1102.pdf" H 10300 3300 50  0001 L CNN
F 4 "https://www.alliedelec.com/molexincorporated-105430-1102/71607964/" H 9650 3300 50  0001 C CNN "Allied Price/Stock"
F 5 "71607964" H 9650 3300 50  0001 C CNN "Allied_Number"
F 6 "MOLEX - 105430-1102 - NANO-FIT HDR SMT RA SGL 2CKT TIN BLK 84AC4685" H 9650 3300 50  0001 C CNN "Description"
F 7 "6.99" H 9650 3300 50  0001 C CNN "Height"
F 8 "Molex" H 9650 3300 50  0001 C CNN "Manufacturer_Name"
F 9 "105430-1102" H 9650 3300 50  0001 C CNN "Manufacturer_Part_Number"
F 10 "538-105430-1102" H 9650 3300 50  0001 C CNN "Mouser Part Number"
F 11 "https://www.mouser.com/Search/Refine.aspx?Keyword=538-105430-1102" H 9650 3300 50  0001 C CNN "Mouser Price/Stock"
	1    9650 3300
	1    0    0    -1  
$EndComp
$Comp
L MOLEX_Connector_105430:105430-1102 J3
U 1 1 5E265BFA
P 9650 4050
F 0 "J3" H 10050 4315 50  0000 C CNN
F 1 "105430-1102" H 10050 4224 50  0000 C CNN
F 2 "105430-1102:1054301102" H 10300 4150 50  0001 L CNN
F 3 "https://componentsearchengine.com/Datasheets/2/105430-1102.pdf" H 10300 4050 50  0001 L CNN
F 4 "https://www.alliedelec.com/molexincorporated-105430-1102/71607964/" H 9650 4050 50  0001 C CNN "Allied Price/Stock"
F 5 "71607964" H 9650 4050 50  0001 C CNN "Allied_Number"
F 6 "MOLEX - 105430-1102 - NANO-FIT HDR SMT RA SGL 2CKT TIN BLK 84AC4685" H 9650 4050 50  0001 C CNN "Description"
F 7 "6.99" H 9650 4050 50  0001 C CNN "Height"
F 8 "Molex" H 9650 4050 50  0001 C CNN "Manufacturer_Name"
F 9 "105430-1102" H 9650 4050 50  0001 C CNN "Manufacturer_Part_Number"
F 10 "538-105430-1102" H 9650 4050 50  0001 C CNN "Mouser Part Number"
F 11 "https://www.mouser.com/Search/Refine.aspx?Keyword=538-105430-1102" H 9650 4050 50  0001 C CNN "Mouser Price/Stock"
	1    9650 4050
	1    0    0    -1  
$EndComp
$EndSCHEMATC
