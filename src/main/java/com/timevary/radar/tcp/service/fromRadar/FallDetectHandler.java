package com.timevary.radar.tcp.service.fromRadar;

import com.alipay.remoting.exception.RemotingException;
import com.google.common.collect.Sets;
import com.timevary.radar.tcp.hander.base.RadarProtocolDataHandler;
import com.timevary.radar.tcp.protocol.FunctionEnum;
import com.timevary.radar.tcp.protocol.RadarProtocolData;
import com.timevary.radar.tcp.util.ByteUtil;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * fall detect alarm handler
 * @author jia.wu
 */
@Service
public class FallDetectHandler  implements RadarProtocolDataHandler {

    @Override
    public Object process(RadarProtocolData protocolData) throws RemotingException, InterruptedException {
        //TODO process the fall detection
        System.out.println("fall detection alarm");
        System.out.println("process the fall detection alarm you want to");
        RadarProtocolData radarProtocolData = new RadarProtocolData();
        radarProtocolData.setFunction(FunctionEnum.FallDetect);
        radarProtocolData.setData(ByteUtil.intToByteBig(1));
        return radarProtocolData;
    }

    @Override
    public Set<FunctionEnum> interests() {
        return Sets.newHashSet(FunctionEnum.FallDetect);
    }
}
