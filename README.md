EdisonJ Java Library for Edison
===============================

This project aims to create a useful pure java library for programing the Intel Edison. The
inital support will be for Edison on the intel mini-breakout. It is not known if all
features will become available via pure java. Presently implemented features:

1. Write to GPIO
2. Read from GPIO (50k pull up default)
3. Configure input as pull up or pull down
4. Configure the programmable pull up resistors (2k 20k 50k)

Contributions are of course welcome. Next priorities are
 
1. PWM output 
1. GPIO interrupt support to avoid polling (not quite sure how to avoid native code here)
1. Support for Analog over I2C using an ADS1115 board from adafruit.

Other tasks:

 - Diagrams for circuits for the examples
 - Maven central availability
 
Usage
-----
 
 1. Set up networking/ssh on your edison (see intel's site for details)
 2. clone the repository, 
 3. cd into the top level
 4. run `gradlew blinkJar`
 5. copy the resulting jar to the edison `scp blink.jar root@192.168.1.123:~/`
 5. Ssh into your edison
 6. Run the following commands (by doing this you are agreeing to [Oracle's License](http://www.oracle.com/technetwork/java/javase/terms/license/index.html), if you haven't already read it you should download from their site where you can read the license and scp the dowloaded file): 
 
```
    wget --no-cookies --no-check-certificate --header "Cookie: gpw_e24=http%3A%2F%2Fwww.oracle.com%2F; oraclelicense=accept-securebackup-cookie" "http://download.oracle.com/otn-pub/java/jdk/8u51-b16/jdk-8u51-linux-i586.tar.gz"
    tar xzvf jdk-8u51-linux-i586.tar.gz
    jdk1.8.0_51/bin/java -jar blink.jar
```
    
The other examples have similar targets i.e. `gradlew touchJar` If you want a versioned jar 
with no Main-Class defined just run `gradlew jar`

License
-------

This project is licensed as free open source software under the Apache 2.0 License.
See the LICENSE file and code headers for details.