/*
 Optical Heart Rate Detection (PBA Algorithm)
 By: Nathan Seidle
 SparkFun Electronics
 Date: October 2nd, 2016
 
 Given a series of IR samples from the MAX30105 we discern when a heart beat is occurring
 Let's have a brief chat about what this code does. We're going to try to detect
 heart-rate optically. This is tricky and prone to give false readings. We really don't
 want to get anyone hurt so use this code only as an example of how to process optical
 data. Build fun stuff with our MAX30105 breakout board but don't use it for actual
 medical diagnosis.
 Excellent background on optical heart rate detection:
 http://www.ti.com/lit/an/slaa655/slaa655.pdf
 Good reading:
 http://www.techforfuture.nl/fjc_documents/mitrabaratchi-measuringheartratewithopticalsensor.pdf
 https://fruct.org/publications/fruct13/files/Lau.pdf
 This is an implementation of Maxim's PBA (Penpheral Beat Amplitude) algorithm. It's been 
 converted to work within the Arduino framework.
*/

/* Copyright (C) 2016 Maxim Integrated Products, Inc., All Rights Reserved.
*
* Permission is hereby granted, free of charge, to any person obtaining a
* copy of this software and associated documentation files (the "Software"),
* to deal in the Software without restriction, including without limitation
* the rights to use, copy, modify, merge, publish, distribute, sublicense,
* and/or sell copies of the Software, and to permit persons to whom the
* Software is furnished to do so, subject to the following conditions:
*
* The above copyright notice and this permission notice shall be included
* in all copies or substantial portions of the Software.
*
* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
* OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
* MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
* IN NO EVENT SHALL MAXIM INTEGRATED BE LIABLE FOR ANY CLAIM, DAMAGES
* OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
* ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
* OTHER DEALINGS IN THE SOFTWARE.
*
* Except as contained in this notice, the name of Maxim Integrated
* Products, Inc. shall not be used except as stated in the Maxim Integrated
* Products, Inc. Branding Policy.
*
* The mere transfer of this software does not imply any licenses
* of trade secrets, proprietary technology, copyrights, patents,
* trademarks, maskwork rights, or any other form of intellectual
* property whatsoever. Maxim Integrated Products, Inc. retains all
* ownership rights.
* 
*/

package phoneapp;

class HRTest
{
    // ISSUE: in Java, the 'byte' data-type is a SIGNED
    // 8-bit value ('uint8_t' was converted to 'byte')
    // SOLUTION: Bit risky, however, it was decided to
    // make it into a 'char' data-type due to it being
    // unsigned, although it is 16-bits

    // ISSUE: Java does not support a 'uint16_t' data type.
    // SOLUTION: Java DOES support a 'char' data type, which
    // is 2-bytes (16-bits). Works well as unsigned.

    int ir_avg_reg = 0;

    // Supposed to be uint8_t
    char offset = 0;

    short IR_Average_Estimated;
    short IR_AC_Signal_Previous;

    short cbuf[];
    short IR_AC_Max = 20;
    short IR_AC_Min = -20;
    short IR_AC_Signal_Current = 0;
    short IR_AC_Signal_min = 0;
    short IR_AC_Signal_max = 0;
    short positiveEdge = 0;
    short negativeEdge = 0;

    static final short FIRCoeffs[] = new short[]{172, 321, 579, 927, 1360, 1858, 2390,
                                            2916, 3391, 3768, 4012, 4096};

    // PERSONAL NOTE: try and rework, or just reconstitute the 'checkForBeat' function
    // to eliminate anything involving the sensor itself.
    // REASONING: the raw, unprocessed data is sent from the device to the phone via
    // BLE comms; app will just have to process the raw data via checking for a beat,
    // then coming up with a BPM algorithm of sorts.

    //  Heart Rate Monitor functions takes a sample value and the sample number
    //  Returns true if a beat is detected
    //  A running average of four samples is recommended for display on the screen.
    boolean checkForBeat(int sample)
    {
        boolean beatDetected = false;
        boolean posCycle = false;
        boolean negCycle = false;

        // Extracting the 16-bits of the 32-bit 'sample'
        // variable.
        char sixteenBSample = (char)(sample & 0xFFFF);

        //  Save current state
        IR_AC_Signal_Previous = IR_AC_Signal_Current;
        
        //This is good to view for debugging
        //Serial.print("Signal_Current: ");
        //Serial.println(IR_AC_Signal_Current);

        //  Process next data sample
        IR_Average_Estimated = avgDCEst(ir_avg_reg, sixteenBSample);
        IR_AC_Signal_Current = lowPassFIRFilter((short)(sixteenBSample - IR_Average_Estimated));

        //  Detect positive zero crossing (rising edge)
        if ((IR_AC_Signal_Previous < 0) & (IR_AC_Signal_Current >= 0))
        {
        
            IR_AC_Max = IR_AC_Signal_max; //Adjust our AC max and min
            IR_AC_Min = IR_AC_Signal_min;

            positiveEdge = 1;
            negativeEdge = 0;
            IR_AC_Signal_max = 0;

            //if ((IR_AC_Max - IR_AC_Min) > 100 & (IR_AC_Max - IR_AC_Min) < 1000)
            if ((IR_AC_Max - IR_AC_Min) > 20 & (IR_AC_Max - IR_AC_Min) < 1000)
            {
            //Heart beat!!!
            beatDetected = true;
            }
        }

        //  Detect negative zero crossing (falling edge)
        if ((IR_AC_Signal_Previous > 0) & (IR_AC_Signal_Current <= 0))
        {
            positiveEdge = 0;
            negativeEdge = 1;
            IR_AC_Signal_min = 0;
        }

        //  Find Maximum value in positive cycle
        // Assigns a 'bool' value based on the value
        // of the 'positiveEdge' short variable
        if (positiveEdge == 1)
            posCycle = true;
        else if (positiveEdge == 0)
            posCycle = false;

        // short & (short > short)
        if (posCycle & (IR_AC_Signal_Current > IR_AC_Signal_Previous))
        {
            IR_AC_Signal_max = IR_AC_Signal_Current;
        }

        //  Find Minimum value in negative cycle
        // Assigns a 'bool' value based on the value of
        // the 'negativeEdge' short variable
        if (negativeEdge == 1)
            negCycle = false;
        else if (negativeEdge == 0)
            negCycle = true;

        if (negCycle & (IR_AC_Signal_Current < IR_AC_Signal_Previous))
        {
            IR_AC_Signal_min = IR_AC_Signal_Current;
        }
        
        return(beatDetected);
    }

    // PERSONAL NOTE:
    // Solution 1: Use the available signed short and stop worrying about the sign,
    // unless you need to do comparison (<, <=, >, >=) or division (/, %, >>)
    // operations. See this answer for how to handle signed numbers as if they were
    // unsigned.

    // Solution 2 (where solution 1 doesn't apply): Use the lower 16 bits of int and
    // remove the higher bits with & 0xffff where necessary.

    //  Average DC Estimator
    short avgDCEst(int p, char x)
    {
        p += ((((long) x << 15) - p) >> 4);
        
        return (short)(p >> 15);
    }

    //  Low Pass FIR Filter
    short lowPassFIRFilter(short din)
    {  
        cbuf[offset] = din;

        int z = mul16(FIRCoeffs[11], cbuf[(offset - 11) & 0x1F]);
        
        for (byte i = 0 ; i < 11 ; i++)
        {
            z += mul16(FIRCoeffs[i], (short)(cbuf[(offset - i) & 0x1F] + cbuf[(offset - 22 + i) & 0x1F]));
        }

        offset++;
        offset %= 32; //Wrap condition

        return (short)(z >> 15);
    }

    //  Integer multiplier
    int mul16(short x, short y)
    {
        return (int)((long)x * (long)y);
    }

    // This is purely for testing purposes
    public static void main(String[] args)
    {
        System.out.println("Hello World!");
    }
}