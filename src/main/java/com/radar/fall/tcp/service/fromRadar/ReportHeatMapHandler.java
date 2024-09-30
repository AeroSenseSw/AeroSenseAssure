package com.radar.fall.tcp.service.fromRadar;

import com.google.common.collect.Sets;
import com.radar.fall.tcp.hander.base.RadarProtocolDataHandler;
import com.radar.fall.tcp.protocol.FunctionEnum;
import com.radar.fall.tcp.protocol.RadarProtocolData;
import com.radar.fall.tcp.util.ByteUtil;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 *  report heat map handler
 * @author ：jia.w
 */
@Service
public class ReportHeatMapHandler  implements RadarProtocolDataHandler {

    @Override
    public Object process(RadarProtocolData protocolData) {
        // TODO process the heat map data
        System.out.println("radar heat map data ");
        System.out.println("process the heat map data you want to");
        RadarProtocolData radarProtocolData = new RadarProtocolData();
        radarProtocolData.setFunction(FunctionEnum.ReportHeatMap);
        radarProtocolData.setData(ByteUtil.intToByteBig(1));
        return radarProtocolData;
    }

    @Override
    public Set<FunctionEnum> interests() {
        return Sets.newHashSet(FunctionEnum.ReportHeatMap);
    }
}
