## 1. Architecture analysis

### (1) Service startup flowchart

![](img/server_start.svg)
## Use springboot to load the configuration file, then initialize the tcp server. Set the radar protocol processor when the tcp server is in initializing. 
### (2) Processing diagram between radar and server
## The bottom layer is decoded and unpacked accoridng to the RadarCommandDecoder protocol, and then deserialized through the RadarSerializer, It is finally processed by each handler according to the following diagram.
![img.png](img/process_request.svg)

```java
package com.timevary.radar.tcp.service.fromRadar;

import com.google.common.collect.Sets;
import com.timevary.radar.tcp.hander.base.RadarProtocolDataHandler;
import com.timevary.radar.tcp.protocol.FunctionEnum;
import com.timevary.radar.tcp.protocol.RadarProtocolData;
import com.timevary.radar.tcp.util.ByteUtil;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @author ：ywb
 * @date ：Created in 2022/2/12 10:07
 * @modified By：
 * establish connection
 */
@Service
public class CreateConnectionHandler implements RadarProtocolDataHandler {


    @Override
    public Object process(RadarProtocolData protocolData) {
        //big endian return
        RadarProtocolData radarProtocolData = new RadarProtocolData();
        radarProtocolData.setFunction(FunctionEnum.createConnection);
        radarProtocolData.setData(ByteUtil.intToByteBig(1));
        return radarProtocolData;
    }

    @Override
    public Set<FunctionEnum> interests() {
        return Sets.newHashSet(FunctionEnum.createConnection);
    }
}

```

## 2. Quick start

### (1) Clone this project
### (2) Implement the command function handler sent by the radar defined in FunctionEnum, annotate it with @Service and inject it into the spring container to process the corresponding business logic.
(Several handler implementations have been given in this sample project. Under the com.timevary.radar.tcp.service.fromRadar package, The related alarm logic needs to be implemented by yourself.)
### (3) See the [README.md](../../README.md) doc then run application.


## 3. The server actively sends data to the radar
### Through the RequestRadarUtil tool class, directly call the encapsulated static method of the tool class, and call the radar to return data.

## 4. customized protocol handler

### No, because each protocol can only have one processor, there cannot be more than one.

