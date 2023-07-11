package com.aerosense.radar.tcp.service.fromRadar;

import com.aerosense.radar.tcp.handler.base.RadarProtocolDataHandler;
import com.aerosense.radar.tcp.protocol.FunctionEnum;
import com.aerosense.radar.tcp.protocol.RadarProtocolData;
import com.aerosense.radar.tcp.util.ByteUtil;
import com.alipay.remoting.exception.RemotingException;
import com.google.common.collect.Sets;
import org.springframework.stereotype.Service;

import java.util.Set;
/**
 * @description: FallDetection Alarm Elimination Handler
 * @author jia.wu
 * @date 2023/7/11 10:19
 * @version 1.0.0
 */
@Service
public class EliminateFallDetectHandler implements RadarProtocolDataHandler {

    @Override
    public Object process(RadarProtocolData protocolData) throws RemotingException, InterruptedException {
        //TODO process the FallDetection Alarm Elimination
        System.out.println("FallDetection Alarm Elimination");
        System.out.println("process the FallDetection Alarm Elimination you want to");
        RadarProtocolData radarProtocolData = new RadarProtocolData();
        radarProtocolData.setFunction(FunctionEnum.FallDetect);
        radarProtocolData.setData(ByteUtil.intToByteBig(1));
        return radarProtocolData;
    }

    @Override
    public Set<FunctionEnum> interests() {
        return Sets.newHashSet(FunctionEnum.EliminateFallDetection);
    }
}
