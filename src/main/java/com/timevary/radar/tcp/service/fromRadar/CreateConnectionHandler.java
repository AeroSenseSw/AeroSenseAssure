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
 * create connection
 */
@Service
public class CreateConnectionHandler implements RadarProtocolDataHandler {


    @Override
    public Object process(RadarProtocolData protocolData) {
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
