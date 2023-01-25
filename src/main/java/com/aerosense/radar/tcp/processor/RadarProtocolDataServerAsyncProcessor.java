
package com.aerosense.radar.tcp.processor;

import com.aerosense.radar.tcp.connection.RadarAddressMap;
import com.alipay.remoting.AsyncContext;
import com.alipay.remoting.BizContext;
import com.alipay.remoting.Connection;
import com.alipay.remoting.rpc.protocol.AsyncUserProcessor;
import com.google.common.primitives.Ints;
import com.aerosense.radar.tcp.server.RadarTcpServer;
import com.aerosense.radar.tcp.connection.ConnectionUtil;
import com.aerosense.radar.tcp.exception.RadarException;
import com.aerosense.radar.tcp.hander.base.RadarProtocolDataHandler;
import com.aerosense.radar.tcp.hander.base.RadarProtocolDataHandlerManager;
import com.aerosense.radar.tcp.protocol.FunctionEnum;
import com.aerosense.radar.tcp.protocol.RadarProtocolData;
import com.aerosense.radar.tcp.util.ByteUtil;
import com.aerosense.radar.tcp.util.ByteUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 *
 * @author： jia.wu
 * @date： 2021/8/6 10:41
 * @version: 1.0
 */
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
        if (log.isDebugEnabled()) {
            log.debug("request: {}", radarProtocolData);
        }
        Connection connection = bizContext.getConnection();
        /**注册雷达，绑定雷达id到Connection*/
        registerRadarOnFirstRadarReport(bizContext, radarProtocolData);
        /**填充绑定的数据*/
        boolean filled = ConnectionUtil.fillBindData(connection, radarProtocolData);
        if(!filled) {
            //没有找到注册绑定数据，等待注册绑定
            return;
        }
        RadarProtocolDataHandler handler = RadarProtocolDataHandlerManager.getHandler(radarProtocolData.getFunction());
        if (handler == null) {
            if (log.isWarnEnabled()) {
                log.warn("no handler response: null {} {}", radarProtocolData.getRadarId(),
                        radarProtocolData.getFunction());
            }
            //返回请求失败
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
            asyncContext.sendResponse(new RadarException(e));
        }
    }

    /**
     * 雷达注册keepAlive处理
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
            //解析雷达注册信息，包含雷达类型（1个字节）、雷达版本（4个字节）、雷达id三个字段
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
     * 获取雷达固件版本
     * @param bytes
     * @return
     */
    public static String getHardwareVersion(byte[] bytes){
        if (null == bytes) {
            return "unknown";
        }
        StringBuilder version = new StringBuilder();
        //将每个字节转换成Int
        byte[] temp = new byte[4];
        for (int i = 0,len = bytes.length; i < len; i++){
            temp[3] = bytes[i];
            //如果是个位数前补零
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
