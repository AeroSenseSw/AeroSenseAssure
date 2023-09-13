
package com.radar.fall.tcp.processor;

import com.alipay.remoting.AsyncContext;
import com.alipay.remoting.BizContext;
import com.alipay.remoting.Connection;
import com.alipay.remoting.rpc.protocol.AsyncUserProcessor;
import com.google.common.primitives.Ints;
import com.radar.fall.tcp.connection.ConnectionUtil;
import com.radar.fall.tcp.connection.RadarAddressMap;
import com.radar.fall.tcp.exception.RadarException;
import com.radar.fall.tcp.hander.base.RadarProtocolDataHandler;
import com.radar.fall.tcp.hander.base.RadarProtocolDataHandlerManager;
import com.radar.fall.tcp.protocol.FunctionEnum;
import com.radar.fall.tcp.protocol.RadarProtocolData;
import com.radar.fall.tcp.server.RadarTcpServer;
import com.radar.fall.tcp.util.ByteUtil;
import com.radar.fall.tcp.util.ByteUtils;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;


@Slf4j
public class RadarProtocolDataServerAsyncProcessor extends AsyncUserProcessor<RadarProtocolData> {

    private final RadarAddressMap radarAddressMap;
    private final RadarTcpServer radarTcpServer;

    public RadarProtocolDataServerAsyncProcessor(RadarAddressMap radarAddressMap,
                                                 RadarTcpServer radarTcpServer) {
        this.radarAddressMap = radarAddressMap;
        this.radarTcpServer = radarTcpServer;
    }

    @Override
    public void handleRequest(BizContext bizContext, AsyncContext asyncContext, RadarProtocolData radarProtocolData) {
        Connection connection = bizContext.getConnection();
        registerRadarOnFirstRadarReport(bizContext, radarProtocolData);
        boolean filled = ConnectionUtil.fillBindData(connection, radarProtocolData);
        if(!filled) {
            return;
        }
        if (log.isDebugEnabled()) {
            log.debug("request: {}", radarProtocolData);
        }
        RadarProtocolDataHandler handler = RadarProtocolDataHandlerManager.getHandler(radarProtocolData.getFunction());
        if (handler == null) {
            if (log.isWarnEnabled()) {
                log.warn("no handler response: null {} {}", radarProtocolData.getRadarId(),
                        radarProtocolData.getFunction());
            }

            radarProtocolData.setData(Ints.toByteArray(0));
            asyncContext.sendResponse(radarProtocolData);
            return;
        }
        try {
            Object result = handler.process(radarProtocolData);
            if (log.isDebugEnabled()) {
                log.debug("response: {} - {} - {}", result,
                        radarProtocolData.getRadarId(), radarProtocolData.getFunction());
            }
            asyncContext.sendResponse(result);
        } catch (Exception e) {
            log.error("handler process happen exception", e);
            radarProtocolData.setData(Ints.toByteArray(0));
            asyncContext.sendResponse(radarProtocolData);
        }
    }

    /**

     * @param bizContext
     * @param radarProtocolData
     */
    private void registerRadarOnFirstRadarReport(BizContext bizContext, RadarProtocolData radarProtocolData) {
        Connection connection = bizContext.getConnection();
        if (radarProtocolData.getFunction() == FunctionEnum.createConnection) {
            String remoteAddress = bizContext.getRemoteAddress();
            if (remoteAddress == null || remoteAddress.length() == 0) {
                log.warn("register radar failure, remote address parse to be null");
                return;
            }

            byte[] data = radarProtocolData.getData();
            byte type = data[0];
            String version = getHardwareVersion(Arrays.copyOfRange(data, 1, 5));
            byte[] idBytes = Arrays.copyOfRange(data, 5, data.length);
            String radarId = ByteUtils.bytesToHexString(idBytes);
            ConnectionUtil.bindRadarData(connection, radarId, version, type);
            radarAddressMap.bindAddress(remoteAddress, radarId);
            log.info("register radar successful {} - {}", radarId, remoteAddress);
        }
    }

    /**

     * @param bytes
     * @return
     */
    public static String getHardwareVersion(byte[] bytes){
        if (null == bytes) {
            return "unknown";
        }
        StringBuilder version = new StringBuilder();

        byte[] temp = new byte[4];
        for (int i = 0,len = bytes.length; i < len; i++){
            temp[3] = bytes[i];

            String hexString = String.format("%02d", ByteUtil.byte4ToInt(temp));
            version.append(hexString).append(".");
        }
        version.deleteCharAt(version.length()-1);
        return version.toString();
    }

    @Override
    public String interest() {
        return RadarProtocolData.class.getName();
    }

    @Override
    public boolean timeoutDiscard() {
        return true;
    }
}
