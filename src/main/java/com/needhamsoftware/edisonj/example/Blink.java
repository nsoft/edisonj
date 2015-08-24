/*
 * Copyright (c) 2015 Needham Software LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *                           
 *     http://www.apache.org/licenses/LICENSE-2.0
 *                           
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.needhamsoftware.edisonj.example;

/*
 * Created with IntelliJ IDEA.
 * User: gus
 * Date: 8/22/15
 */

import java.io.IOException;

import static com.needhamsoftware.edisonj.EdisonMiniBreakout.*;

/**
 * A super quick and dirty blink program for Intel Edison on mini-breakout board. 
 * This will oscillate the J19-6 pin, you can test this with your volt meter, 
 * or wire up a circuit that lights an LED on when it sees 1.8v and then attach 
 * it to J19-6 (signal) and J19-3(gnd)
 */

/*
These are the pins to see this work on the mini-breakout board 
looking at it from the bottom side, USB plugs to left, writing right side up
          
o o o o o o o o o o o o o o J17 
o o o o o o o o o o o o o o J18
o o o o o o o o S o o G o o J19
o o o o o o o o o o o o o o J20

o = pin we don't care about.
S = output signal pin alternates +1.8v and 0v
G = ground pin (to which you can read the oscilating voltage with volt meter)
*/

public class Blink {


  public static void main(String[] args) throws InterruptedException {
    initForGPIOWrite(GP48);
    boolean on = false;

    // change to while loop if you want to burn cpu cycles/battery/etc forever.
    for (int i = 0; i < 100; i++) {
      Thread.sleep(1000);
      try {
        write(GP48+"/value", on ? "0" : "1");
        on = !on;
      } catch (IOException e) {
        System.out.println("Could not write value to GP48 (arduino IO7");
      }
    }
  }

}
