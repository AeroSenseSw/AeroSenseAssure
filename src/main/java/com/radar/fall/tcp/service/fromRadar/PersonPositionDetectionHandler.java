package com.radar.fall.tcp.service.fromRadar;


import com.google.common.collect.Sets;
import com.radar.fall.tcp.hander.base.RadarProtocolDataHandler;
import com.radar.fall.tcp.protocol.FunctionEnum;
import com.radar.fall.tcp.protocol.RadarProtocolData;
import com.radar.fall.tcp.util.ByteUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * PersonPositionDetection report handler
 * @author jia.wu
 */
@Service
public class PersonPositionDetectionHandler implements RadarProtocolDataHandler {
    @Override
    public Object process(RadarProtocolData protocolData) {
        System.out.println("person position detection radarId:"+ protocolData.getRadarId() +" data: "+protocolData.getData());
        ByteBuf byteBuf = Unpooled.wrappedBuffer(protocolData.getData());
        float x = byteBuf.readFloat();
        float y = byteBuf.readFloat();
        float z = byteBuf.readFloat();
        System.out.println("person position xyz -> x:"+x+" y:"+y+" z:"+z);
        System.out.println("process the person position detection you want to");
        RadarProtocolData radarProtocolData = new RadarProtocolData();
        radarProtocolData.setFunction(FunctionEnum.PersonPositionDetection);
        radarProtocolData.setData(ByteUtil.intToByteBig(1));
        return radarProtocolData;
    }

    @Override
    public Set<FunctionEnum> interests() {
        return Sets.newHashSet(FunctionEnum.PersonPositionDetection);
    }
}
