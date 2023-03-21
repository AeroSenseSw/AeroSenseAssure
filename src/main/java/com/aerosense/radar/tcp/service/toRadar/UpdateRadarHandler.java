package com.aerosense.radar.tcp.service.toRadar;

import com.aerosense.radar.tcp.protocol.FunctionEnum;
import com.aerosense.radar.tcp.protocol.RadarProtocolData;
import com.aerosense.radar.tcp.server.RadarTcpServer;
import com.aerosense.radar.tcp.util.ByteUtil;
import com.aerosense.radar.tcp.util.RadarCRC16;
import com.alipay.remoting.exception.RemotingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StreamUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * @description: update radar firmware handler
 * @author jia.wu
 * @date 2023/3/20 16:05
 * @version 1.0.0
 */
@Slf4j
@Service
public class UpdateRadarHandler {
    /**
     * retry count
     */
    private static final int RETRY_COUNT = 2;
    /**
     * Frame length
     */
    private static final int FRAME_LENGTH = 1000;

    @Autowired
    private RadarTcpServer radarTcpServer;

    /**
     * Process update radar firmware by file path
     * @param radarId
     * @param firmwareFilePath
     * @return
     */
    public boolean process(String radarId, String firmwareFilePath) {
        try {
            File file = ResourceUtils.getFile(firmwareFilePath);
            byte[] firmwareFileBytes = StreamUtils.copyToByteArray(new FileInputStream(file));
            return process(radarId, firmwareFileBytes);
        } catch (IOException e) {
            log.error("Read radar firmware file fail "+firmwareFilePath, e);
        }
        return false;
    }

    /**
     * Process update radar firmware
     * @param radarId
     * @param firmwareBytes
     * @return
     */
    public boolean process(String radarId, byte[] firmwareBytes) {
        try {
            boolean update = notifyUpdate(radarId, firmwareBytes);
            log.debug("notify firmware update result {} - {}", radarId, update);
            return update;
        } catch (RemotingException | InterruptedException e) {
            log.error("do update radar firmware fail "+radarId, e);
        }
        return false;
    }


    /**
     * Notify radar to prepare update and do update frame
     * @param radarId
     * @param firmwareBytes
     * @return
     * @throws RemotingException
     * @throws InterruptedException
     */
    private boolean notifyUpdate(String radarId, byte[] firmwareBytes )
            throws RemotingException, InterruptedException {
        byte[] firmwareLength = ByteUtil.intToByteBig(firmwareBytes.length);
        RadarProtocolData protocolData = RadarProtocolData.newInstance(radarId, FunctionEnum.notifyUpdate, firmwareLength);
        boolean notifyOk = retryInvokeRadar(protocolData, 15000);
        if (notifyOk) {
            return updateFrameBytes(protocolData, firmwareBytes);
        }
        return false;
    }

    /**
     * update rame
     * @param protocolData
     * @param firmwareBytes
     * @return
     */
    private boolean updateFrameBytes(RadarProtocolData protocolData, byte[] firmwareBytes) {
        protocolData.setFunction(FunctionEnum.issueFirmware);
        int blockCount = (firmwareBytes.length / FRAME_LENGTH) + (firmwareBytes.length % FRAME_LENGTH > 0 ? 1 : 0);
        log.debug("Updating frame all block count {}", blockCount);
        for (int i=0; i < blockCount; i++) {
            int blockIndex = i + 1;
            int startIndex = i * FRAME_LENGTH;
            int endIndex =  blockIndex * FRAME_LENGTH;
            //last frame may be less than frame length
            if (endIndex > firmwareBytes.length) {
                endIndex = firmwareBytes.length;
            }
            byte[] blockData = Arrays.copyOfRange(firmwareBytes, startIndex, endIndex);
            int blockCrc16 = RadarCRC16.crc16BaseRadar(blockData);
            byte[] crc16Bytes = ByteUtil.shortToByteLittle((short) blockCrc16);
            byte[] sendBytes = new byte[blockData.length + crc16Bytes.length];
            //copy bytes
            System.arraycopy(blockData, 0, sendBytes, 0, blockData.length);
            //copy crcBytes
            System.arraycopy(crc16Bytes, 0, sendBytes, blockData.length, crc16Bytes.length);
            protocolData.setData(sendBytes);
            log.debug("Updating frame block index {}", blockIndex);
            boolean updateFrameResult  = retryInvokeRadar(protocolData, 10000);
            if (!updateFrameResult){
                return false;
            }
        }
        return checkUpdateResult(protocolData);
    }

    /**
     * Check the update result
     * @param protocolData
     * @return
     */
    private boolean checkUpdateResult(RadarProtocolData protocolData) {
        protocolData.setFunction(FunctionEnum.updateResult);
        protocolData.setData(new byte[4]);
        return retryInvokeRadar(protocolData, 20000);
    }

    /**
     * Retry invoke the radar
     * @param protocolData
     * @param timeoutMillis
     * @return
     */
    private boolean retryInvokeRadar(RadarProtocolData protocolData, int timeoutMillis) {
        for (int i = 0; i <= RETRY_COUNT; i++) {
            try {
                RadarProtocolData retProtocolData = (RadarProtocolData) radarTcpServer.invokeSync(protocolData, timeoutMillis);
                if (retProtocolData.getData()[3]!=0 && retProtocolData.getData()[3]!=1){
                    log.warn("Radar return data error {}", retProtocolData);
                }
                return retProtocolData.getFunction() == protocolData.getFunction() &&
                        ByteUtil.bytes2IntBig(retProtocolData.getData()) == 1;
            } catch (RemotingException | InterruptedException e) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException ex) {

                }
                log.error("invoke radar exception", e);
            }
        }
        return false;
    }
}
