package com.radar.fall.tcp.hander.base;

import com.alipay.remoting.exception.RemotingException;
import com.radar.fall.tcp.protocol.FunctionEnum;
import com.radar.fall.tcp.protocol.RadarProtocolData;

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
