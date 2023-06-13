
package com.radar.fall.tcp.server;

import com.alipay.remoting.ConnectionEventType;
import com.alipay.remoting.InvokeContext;
import com.alipay.remoting.LifeCycleException;
import com.alipay.remoting.config.Configs;
import com.alipay.remoting.config.switches.GlobalSwitch;
import com.alipay.remoting.exception.RemotingException;
import com.alipay.remoting.rpc.RpcServer;
import com.google.common.base.Strings;
import com.radar.fall.tcp.config.RadarTcpServerProperties;
import com.radar.fall.tcp.connection.DisconnectServerEventProcessor;
import com.radar.fall.tcp.connection.RadarAddressHashMap;
import com.radar.fall.tcp.connection.RadarAddressMap;
import com.radar.fall.tcp.hander.base.RadarProtocolDataHandler;
import com.radar.fall.tcp.hander.base.RadarProtocolDataHandlerManager;
import com.radar.fall.tcp.processor.RadarProtocolDataServerAsyncProcessor;
import com.radar.fall.tcp.protocol.RadarProtocolData;
import com.radar.fall.tcp.protocol.RadarProtocolManager;
import com.radar.fall.tcp.serilazer.RadarSerializer;
import com.radar.fall.tcp.serilazer.RadarSerializerManager;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * 
 *
 * @author： jia.wu
 * @date： 2021/8/3 16:23
 * @version: 1.0
 */
@Slf4j
public class RadarTcpServer extends RpcServer {
    private String serverAddress;
    private RadarAddressMap radarAddressMap;
    private RadarTcpServerProperties radarTcpServerProperties;

    static {

        RadarProtocolManager.initProtocols();
        log.info("initialize radar protocol register successful.");

        RadarSerializerManager.initSerializer();
        log.info("initialize radar protocol serializer successful.");
    }

    private void initRadarProtocolDataProcessor() {
        this.registerUserProcessor(new RadarProtocolDataServerAsyncProcessor(radarAddressMap, this));
        log.info("init radar protocol data processor successful");
    }

    private void initServerConnectEventProcess() {
        this.addConnectionEventProcessor(ConnectionEventType.CLOSE, new DisconnectServerEventProcessor(radarAddressMap));
        log.info("add radar disconnect server event processor successful");
    }

    public RadarTcpServer() {
    }

    public RadarTcpServer(RadarAddressMap radarAddressMap, RadarTcpServerProperties properties) {
        super(properties.getHost(), properties.getPort(), true);
        this.radarAddressMap = radarAddressMap;
        this.serverAddress = radarAddressMap.getServerAddress();
        this.radarTcpServerProperties = properties;
        log.info("new instance radar tcp server successful");
    }

    public String getRadarAddress(String radarId) {
        return radarAddressMap.getRadarAddress(radarId);
    }

    public String getServerAddress(String radarId) {
        return radarAddressMap.getServerAddress(radarId);
    }

    public Set<String> getOnlineRadarList() {
        return radarAddressMap.getOnlineRadarList();
    }

    public long getOnlineRadarCount() {
        return radarAddressMap.getOnlineRadarCount();
    }

    public String getServerAddress() {
        return serverAddress;
    }

    @Override
    protected void doInit() throws LifeCycleException {
        log.info("initializing radar tcp server");
        System.setProperty(Configs.TCP_SERVER_IDLE, String.valueOf(radarTcpServerProperties.getIdleTimeout()));

        switches().turnOn(GlobalSwitch.SERVER_SYNC_STOP);
        initRadarProtocolDataProcessor();
        initServerConnectEventProcess();
        super.doInit();
    }

    @Override
    protected boolean doStop() throws LifeCycleException {
        log.info("stopping radar tcp server");
        boolean stopped = super.doStop();
        RadarProtocolDataHandlerManager.clear();
        log.info("radar protocol data handler clear successful");
        radarAddressMap.clear();
        log.info("radar address map clear successful");
        return stopped;
    }

    public void registerHandler(RadarProtocolDataHandler handler) {
        RadarProtocolDataHandlerManager.registerHandler(handler);
    }

    private void destroy() {
        log.info("destroying radar tcp server");
        if (this.isStarted()) {
            this.shutdown();
        }
    }

    public static RadarTcpServer radarServerStarter( RadarProtocolDataHandler... handlers) {
        List<RadarProtocolDataHandler> radarProtocolDataHandlers = Arrays.asList(handlers);
        return radarServerStarter(radarProtocolDataHandlers);
    }

    public static RadarTcpServer radarServerStarter( List<RadarProtocolDataHandler> handlers) {
        System.setProperty("logging.path", "./logs/server");
        String serverAddress = "127.0.0.1:8899";
        RadarAddressMap radarAddressMap = new RadarAddressHashMap(serverAddress);
        RadarTcpServerProperties properties = new RadarTcpServerProperties();
        RadarTcpServer radarTcpServer = new RadarTcpServer(radarAddressMap, properties);
        for (RadarProtocolDataHandler handler : handlers) {
            radarTcpServer.registerHandler(handler);
        }
        log.info("radarTcpServer start at 8899");
        radarTcpServer.startup();
        return radarTcpServer;
    }
    /**

     * @param requestObj
     * @param timeoutMillis
     * @return
     * @throws RemotingException
     * @throws InterruptedException
     */
    public Object invokeSync(RadarProtocolData requestObj, int timeoutMillis)
            throws RemotingException, InterruptedException {
        InvokeContext invokeContext = new InvokeContext();
        return invokeSync(requestObj, invokeContext, timeoutMillis);
    }

    /**

     * @param requestObj
     * @param invokeContext
     * @param timeoutMillis
     * @return
     * @throws RemotingException
     * @throws InterruptedException
     */
    public Object invokeSync(RadarProtocolData requestObj, InvokeContext invokeContext, int timeoutMillis)
            throws RemotingException, InterruptedException {
        String radarAddress = getRadarAddress(requestObj.getRadarId());
        if(Strings.isNullOrEmpty(radarAddress)){
           throw new RemotingException("radar not online");
        }
        invokeContext.put(InvokeContext.BOLT_CUSTOM_SERIALIZER, RadarSerializer.IDX_BYTE);
        return super.invokeSync(radarAddress, requestObj, invokeContext, timeoutMillis);
    }
}