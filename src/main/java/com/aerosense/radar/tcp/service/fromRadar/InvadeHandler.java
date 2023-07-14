package com.aerosense.radar.tcp.service.fromRadar;

import com.alipay.remoting.exception.RemotingException;
import com.google.common.collect.Sets;
import com.aerosense.radar.tcp.handler.base.RadarProtocolDataHandler;
import com.aerosense.radar.tcp.protocol.FunctionEnum;
import com.aerosense.radar.tcp.protocol.RadarProtocolData;
import com.aerosense.radar.tcp.util.ByteUtil;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * invade alarm handler
 * @author jia.wu
 */
@Service
public class InvadeHandler  implements RadarProtocolDataHandler {

    @Override
    public Object process(RadarProtocolData protocolData) throws RemotingException, InterruptedException {
        //TODO process the invade alarm
        System.out.println("invade alarm");
        System.out.println("process the invade alarm you want to");
        System.out.println(("radar ID: "+protocolData.getRadarId()+ "radar Version: "+protocolData.getRadarVersion()));

        RadarProtocolData radarProtocolData = new RadarProtocolData();
        radarProtocolData.setFunction(FunctionEnum.Invade);
        radarProtocolData.setData(ByteUtil.intToByteBig(1));
        return radarProtocolData;
    }

    @Override
    public Set<FunctionEnum> interests() {
        return Sets.newHashSet(FunctionEnum.Invade);
    }
}
