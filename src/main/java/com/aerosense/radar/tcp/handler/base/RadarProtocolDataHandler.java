package com.aerosense.radar.tcp.handler.base;

import com.aerosense.radar.tcp.protocol.FunctionEnum;
import com.aerosense.radar.tcp.protocol.RadarProtocolData;
import com.alipay.remoting.exception.RemotingException;

import java.util.Set;

/**
 * 
 *
 * @author： jia.wu
 * @date： 2021/8/11 18:00
 * @version: 1.0
 */
public interface RadarProtocolDataHandler {
    /**

     * @param protocolData
     * @return
     */
    Object process(RadarProtocolData protocolData) throws RemotingException, InterruptedException;

    /**

     * @return
     */
    Set<FunctionEnum> interests();
}
