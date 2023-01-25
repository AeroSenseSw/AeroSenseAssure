package com.aerosense.radar.tcp.service.fromRadar;

import com.aerosense.radar.tcp.domain.HeatMapsData;
import com.google.common.collect.Sets;
import com.aerosense.radar.tcp.hander.base.RadarProtocolDataHandler;
import com.aerosense.radar.tcp.protocol.FunctionEnum;
import com.aerosense.radar.tcp.protocol.RadarProtocolData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 雷达上报热力图解析处理
 * @author ：jia.w
 */
@Slf4j
@Service
public class ReportHeatMapHandler  implements RadarProtocolDataHandler {

    @Override
    public Object process(RadarProtocolData protocolData) {
        //process the heat map data
        byte[] heatMapBytes = protocolData.getData();
        if (heatMapBytes == null || heatMapBytes.length == 0) {
            return null;
        }
        int heatMapLen = heatMapBytes.length;
        if (heatMapLen % 3 != 0) {
            log.error("热力图数据不完整");
            return null;
        }
        /**雷达x、y、n点数量*/
        int pointNum = heatMapLen / 3;
        List<Integer> heatX = new ArrayList<>(pointNum);
        List<Integer> heatY = new ArrayList<>(pointNum);
        List<Integer> heatN = new ArrayList<>(pointNum);
        int x,y,n;
        for (int i = 0, len = pointNum - 1; i <= len; i++) {
            int pointIndex = i * 3;
            x = returnX(heatMapBytes[pointIndex]);
            y = heatMapBytes[pointIndex + 1];
            n = heatMapBytes[pointIndex + 2];
            if (n != 0) {
                //0~255
                heatX.add(x + 128);
                //0~255
                heatY.add(y);
                heatN.add(n);
            }
        }
        /**解析后的热力图数据*/
        HeatMapsData heatMapsData = new HeatMapsData(heatX, heatY, heatN);
        // TODO process the heat map data
        System.out.println("radar heat map data "+heatMapsData);
        System.out.println("process the heat map data you want to");
        return null;
    }

    public int returnX(byte x) {
        //只要X是负数或正数小于128，就不经过处理
        if (x < 128 || -x < 128) {
            return x;
        }
        //若x大于128，就让它减256
        return x - 256;
    }

    @Override
    public Set<FunctionEnum> interests() {
        return Sets.newHashSet(FunctionEnum.ReportHeatMap);
    }
}
