package com.aerosense.radar.tcp.service.fromRadar;

import com.aerosense.radar.tcp.domain.HeatMapsData;
import com.aerosense.radar.tcp.handler.base.RadarProtocolDataHandler;
import com.aerosense.radar.tcp.util.ByteUtil;
import com.google.common.collect.Sets;
import com.aerosense.radar.tcp.protocol.FunctionEnum;
import com.aerosense.radar.tcp.protocol.RadarProtocolData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 *  report heat map handler
 * @author ：jia.w
 */
@Slf4j
@Service
public class ReportHeatMapHandler  implements RadarProtocolDataHandler {

    @Override
    public Object process(RadarProtocolData protocolData) {
        // TODO process the heat map data
        System.out.println("radar heat map data "+ Arrays.toString(protocolData.getData()));
        System.out.println("process the heat map data you want to");
        System.out.println(("radar ID: "+protocolData.getRadarId()+ "radar Version: "+protocolData.getRadarVersion()));

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
