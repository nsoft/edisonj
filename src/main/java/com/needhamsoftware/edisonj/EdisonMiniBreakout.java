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

package com.needhamsoftware.edisonj;

/*
 * Created with IntelliJ IDEA.
 * User: gus
 * Date: 8/2/15
 */

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * A class to gather methods and constants for interacting with the edison input
 * and output pins for the mini-breakout board. This may or may not work on the
 * arduino board, for now I have conservatively named it after the MiniBreakout.
 */
public class EdisonMiniBreakout {
  public static final String GPIO_DIR = "/sys/class/gpio/";
  public static final String GPIO_KERNEL_DIR = "/sys/kernel/debug/gpio_debug/";
  /*
 Pin Constants based on: https://communities.intel.com/message/282529

  */
  public static final String GP130 = GPIO_DIR + "gpio130";
  public static final String IO0 = GP130;                   // J18 - pin 13

  public static final String GP131 = GPIO_DIR + "gpio131";
  public static final String IO1 = GP131;                   // J19 - pin 8

  public static final String GP128 = GPIO_DIR + "gpio128";
  public static final String IO2 = GP128;                   // J17 - pin 14

  public static final String GP12 = GPIO_DIR + "gpio12";
  public static final String IO3 = GP12;                    // J18 - pin 7 PWM0

  public static final String GP129 = GPIO_DIR + "gpio129";
  public static final String IO4 = GP129; //  J18 - pin 12

  public static final String GP13 = GPIO_DIR + "gpio13";
  public static final String IO5 = GP13;                    // J18 - pin 1 PWM1

  public static final String GP182 = GPIO_DIR + "gpio182";
  public static final String IO6 = GP182;
  public static final String PWM2 = GP182;                  // J17 - pin 1 PWM2

  public static final String GP48 = GPIO_DIR + "gpio48";
  public static final String IO7 = GP48;                    // J19 - pin 6

  public static final String GP49 = GPIO_DIR + "gpio49";
  public static final String IO8 = GP49;                    // J20 - pin 6

  public static final String GP183 = GPIO_DIR + "gpio183";
  public static final String IO9 = GP183;
  public static final String PWM3 = GP183;                  // J18 - pin 8

  public static final String GP41 = GPIO_DIR + "gpio41";
  public static final String IO10 = GP41;                   // J20 - pin 10

  public static final String GP43 = GPIO_DIR + "gpio43";
  public static final String IO11 = GP43;                   // J19 - pin 11

  public static final String GP42 = GPIO_DIR + "gpio42";
  public static final String IO12 = GP42;                   // J20 - pin 9

  public static final String GP40 = GPIO_DIR + "gpio40";
  public static final String IO13 = GP40;                   // J19 - pin 10

  public static final String GP44 = GPIO_DIR + "gpio44";
  public static final String IO14 = GP44;
  public static final String A0 = GP44;                     // J19 - pin 4

  public static final String GP45 = GPIO_DIR + "gpio45";
  public static final String IO15 = GP45;
  public static final String A1 = GP45;                     // J20 - pin 4

  public static final String GP46 = GPIO_DIR + "gpio46";
  public static final String IO16 = GP46;
  public static final String A2 = GP46;                     // J19 - pin 5

  public static final String GP47 = GPIO_DIR + "gpio47";
  public static final String IO17 = GP47;
  public static final String A3 = GP47;                     // J20 - pin 5

  public static final String GP14 = GPIO_DIR + "gpio14";
  public static final String IO18 = GP14;
  public static final String A5 = GP14;                     // J19 - pin 9

  public static final String GP165 = GPIO_DIR + "gpio165";
  public static final String IO19 = GP165;
  public static final String A6 = GP165;                    // J18 - pin 2

  /* Additional from breakout board Hardware Guide */

  public static final String GP15 = GPIO_DIR + "gpio15";    // J20 - pin 7

/*
  GND  J19 - pin 3  
  Vsys  J20 - pin 1  System input power. 3.15 to 4.5 V
  1.8v  J19 - pin 2  System 1.8 V I/O output power.
  3.3v  J20 - pin 2  System 3.3 V output.
  VIN  J17 - pin 4  7 to 15 V.
*/
  
  

  /*
   * internal methods for filesystem interaction
   */

  private static void _write(String filename, String value) throws IOException {
    Files.write(Paths.get(filename), value.getBytes());
  }

  private static String _read(String filename) throws IOException {
    return new String(Files.readAllBytes(Paths.get(filename)));
  }

  public static void write(String pin, String value) throws IOException {
    _write(pin + "/value", value);
  }

  public static String read(String pin) throws IOException {
    return _read(pin + "/value").trim(); // trim to get rid of irritating newline
  }


  public static void initForGPIOWrite(String pin) {
    ensureGPIO(pin);
    try {
      _write(pin + "/direction", "out");
    } catch (IOException e) {
      System.out.println("Could not set direction on " + pin);
      throw new RuntimeException(e);
    }
  }

  private static void ensureGPIO(String pin) {
    String pinID = pin.substring(pin.lastIndexOf("gpio") + 4);
    if (!(new File(pin).exists())) {
      try {
        _write("/sys/class/gpio/export", pinID);
      } catch (IOException e) {
        System.out.println("Could not export " + pinID + " (" + pin + ")");
        throw new RuntimeException(e);
      }
    }
  }

  /**
   * Basic read with 50k pullup resistor
   */
  public static void initForGPIORead(String pin) {
    // for now assume pull up. Have not yet found docs on how to set the
    // pull up resistance? 
    initForGPIORead(pin, new PullMode(PullMode.Dir.PULLUP, PullMode.R.R50k));
  }
  
  public static void initForGPIORead(String pin, PullMode mode) {
    String name = pin.substring(pin.lastIndexOf('/'));
    ensureGPIO(pin);
    try {
      _write(pin + "/direction", "in");
      _write(GPIO_KERNEL_DIR + "/" + name + "/current_pullmode", mode.getDirection().toString());
      _write(GPIO_KERNEL_DIR + "/" + name + "/current_pullstrength", mode.getResistor().toString());
    } catch (IOException e) {
      throw new RuntimeException("Could not initialize for reading: " + pin,e);
    }
    
  }
}
