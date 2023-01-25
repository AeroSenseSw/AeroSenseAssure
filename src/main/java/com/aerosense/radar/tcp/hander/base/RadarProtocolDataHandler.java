package com.aerosense.radar.tcp.hander.base;

import com.aerosense.radar.tcp.protocol.FunctionEnum;
import com.aerosense.radar.tcp.protocol.RadarProtocolData;
import com.alipay.remoting.exception.RemotingException;

import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 *
 * @author： jia.wu
 * @date： 2021/8/11 18:00
 * @version: 1.0
 */
public interface RadarProtocolDataHandler {
    /**
     * 处理雷达协议数据
     * @param protocolData
     * @return
     */
    Object process(RadarProtocolData protocolData) throws RemotingException, InterruptedException;

    /**
     * 希望处理的函数列表
     * @return
     */
    Set<FunctionEnum> interests();
}
