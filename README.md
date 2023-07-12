AeroSenseAssure Java SDK
======================
AeroSenseAssure Java SDK is a Spring Boot application, this application will listen on port 8899 and accept a TCP socket connection.
Compiling this project requires java8、 Maven3 or later, or you can import to the IDE then run it.

**!!! important !!!**
You must turn off the firewall. The firewall will block the connection.

## Running the project from command line
Run `mvn clean install spring-boot:run` in the project root directory. The server will be started at port 8899.

## Running the project from your IDE
Navigate to the `Application` class and run it as a Java application.

## Where to change config of server?
src/main/java/com/aerosense/radar/tcp/config/RadarTcpServerProperties.java

## Run the Upgrade Firmware test
> mvn test -Dtest=UpdateRadarHandlerTest
Please notice that network environment factors may cause the upgrade to fail. In this case, please try several times.

## Where to handle packet?
src/main/java/com/aerosense/radar/tcp/service
