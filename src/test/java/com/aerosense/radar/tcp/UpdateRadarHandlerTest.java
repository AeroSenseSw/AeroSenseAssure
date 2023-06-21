package com.aerosense.radar.tcp;

import com.aerosense.radar.tcp.server.RadarTcpServer;
import com.aerosense.radar.tcp.service.toRadar.UpdateRadarHandler;
import com.alipay.remoting.exception.RemotingException;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Set;

/**
 * @description: Test update radar firmware
 * @author jia.wu
 * @version 1.0.0
 * @date 2023/3/20 16:06
 */
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Slf4j
public class UpdateRadarHandlerTest {
    @Autowired
    UpdateRadarHandler updateRadarHandler;
    @Autowired
    RadarTcpServer radarTcpServer;

    @Test
    public void testUpdateRadar() throws InterruptedException, RemotingException, IOException {
        // Windows Style
        // String firmwareFilePath = "D:\\develop\\resources\\aerosense\\firmware\\antifall_radar\\AeroSense.Tcp.A.Release.1.7.4.42.bin";
        // Unix Style
        String firmwareFilePath = "/home/w/tvt/t.bin";
        Set<String> onlineRadarList = radarTcpServer.getOnlineRadarList();
        while (onlineRadarList.isEmpty()){
            log.info("Wait a radar connect server");
            Thread.sleep(2000);
        }
        Thread.sleep(2000);
        String radarId = Lists.newArrayList(onlineRadarList).get(0);
        log.info("Prepare update radar firmware {}", radarId);
        boolean processResult = updateRadarHandler.process(radarId, firmwareFilePath);
        Assertions.assertTrue(processResult,"Process update radar firmware fail");
    }
}
