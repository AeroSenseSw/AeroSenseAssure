package com.aerosense.radar.tcp.service.fromRadar;


import com.aerosense.radar.tcp.handler.base.RadarProtocolDataHandler;
import com.aerosense.radar.tcp.protocol.FunctionEnum;
import com.aerosense.radar.tcp.protocol.RadarProtocolData;
import com.aerosense.radar.tcp.util.ByteUtil;
import com.google.common.collect.Sets;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * PresenceDetection handler
 * @author jia.wu
 */
@Service
public class PresenceDetectionHandler implements RadarProtocolDataHandler {
    @Override
    public Object process(RadarProtocolData protocolData) {
        System.out.println("presence detection radarId:"+ protocolData.getRadarId() +" data: "+protocolData.getData());
        RadarProtocolData radarProtocolData = new RadarProtocolData();
        radarProtocolData.setFunction(FunctionEnum.PresenceDetection);
        radarProtocolData.setData(ByteUtil.intToByteBig(1));
        return radarProtocolData;
    }

    @Override
    public Set<FunctionEnum> interests() {
        return Sets.newHashSet(FunctionEnum.PresenceDetection);
    }
}
