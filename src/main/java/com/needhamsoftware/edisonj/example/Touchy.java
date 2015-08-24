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
 * A program to demonstrate polling an input pin. Note that it also demonstrates
 * that the slower polling may miss fast/ephemeral inputs. 
 */

/*
These are the pins to see this work on the mini-breakout board 
looking at it from the bottom side, USB plugs to left, writing right side up
          
o o o o o o o o o o o o o o J17 
o o o o o o o o o o o o o o J18
o o o o o o o o S o o G o o J19
o o o o o o o o I o o o o o J20

o = pin we don't care about.
S = output signal pin alternates +1.8v and 0v
I = the input pin which should react when it is connected to ground
G = ground pin (to which you can read the oscilating voltage with volt meter)

*/

public class Touchy {


  public static final int SLOW = 1000; // ms
  public static final int FAST = 100;  // ms

  public static void main(String[] args) throws InterruptedException {
    initForGPIOWrite(GP48);
    initForGPIORead(GP49);
    boolean on = false;
    int speed = SLOW;
    boolean press = false;
    boolean release = true;

    //noinspection InfiniteLoopStatement
    while (true) {
      Thread.sleep(speed);
      if (press && release) {
        // button was just pressed, toggle the speed
        release = false;
        if (speed == SLOW) {
          speed = FAST;
          System.out.println("Hey! You touched me!");
        } else {
          speed = SLOW;
          System.out.println("That tickles!");
        }
      }
      
      try {
        String value = read(GP49);
        // 0 is a signal since we are using pullup resistor
        if ("0".equals(value)) {
          press = true;
          // don't set release until we change the speed on the next iteration
        } else {
          press = false;
          release = true;
        }
      } catch (IOException e) {
        e.printStackTrace();
        System.out.println("Could not read vvalue for GP46");
      }
      
      try {
        write(GP48, on ? "0" : "1");
        on = !on;
      } catch (IOException e) {
        e.printStackTrace();
        System.out.println("Could not write value to GP48");
      }
    }
  }

}
 