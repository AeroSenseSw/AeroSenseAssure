package com.radar.fall.tcp.service.fromRadar;


import com.google.common.collect.Sets;
import com.radar.fall.tcp.hander.base.RadarProtocolDataHandler;
import com.radar.fall.tcp.protocol.FunctionEnum;
import com.radar.fall.tcp.protocol.RadarProtocolData;
import com.radar.fall.tcp.util.ByteUtil;

import java.util.Set;

/**
 * PresenceDetection handler
 * @author jia.wu
 */
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
