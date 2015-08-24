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
 * Date: 8/23/15
 */
public class PullMode {
  public enum Dir {
    PULLUP {
      @Override
      public String toString() {
        return "pullup";
      }
    },
    PULLDOWN {
      @Override
      public String toString() {
        return "pullup";
      }
    }
  }
  
  public enum R {
    R50k {
      @Override
      public String toString() {
        return "50";
      }
    },
    R20k {
      @Override
      public String toString() {
        return "20";
      }
    },
    R2k {
      @Override
      public String toString() {
        return "2";
      }
    }
  }
  
  private Dir direction;
  private R reistor;

  public PullMode(Dir direction, R reistor) {
    this.direction = direction;
    this.reistor = reistor;
  }

  public Dir getDirection() {
    return direction;
  }

  public R getResistor() {
    return reistor;
  }
}
