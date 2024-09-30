package com.radar.fall.tcp.service.fromRadar;


import com.alipay.remoting.exception.RemotingException;
import com.google.common.collect.Sets;
import com.radar.fall.tcp.hander.base.RadarProtocolDataHandler;
import com.radar.fall.tcp.protocol.FunctionEnum;
import com.radar.fall.tcp.protocol.RadarProtocolData;
import com.radar.fall.tcp.util.ByteUtil;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * IntrusionAlert alarm handler
 * @author jia.wu
 */
@Service
public class IntrusionAlertHandler  implements RadarProtocolDataHandler {

    @Override
    public Object process(RadarProtocolData protocolData) throws RemotingException, InterruptedException {
        //TODO process the IntrusionAlert alarm
        System.out.println("IntrusionAlert alarm radarId:"+protocolData.getRadarId());
        System.out.println("process the IntrusionAlert alarm you want to");
        System.out.println(("radar ID: "+protocolData.getRadarId()+ "radar Version: "+protocolData.getRadarVersion()));

        RadarProtocolData radarProtocolData = new RadarProtocolData();
        radarProtocolData.setFunction(FunctionEnum.IntrusionAlert);
        radarProtocolData.setData(ByteUtil.intToByteBig(1));
        return radarProtocolData;
    }

    @Override
    public Set<FunctionEnum> interests() {
        return Sets.newHashSet(FunctionEnum.IntrusionAlert);
    }
}
