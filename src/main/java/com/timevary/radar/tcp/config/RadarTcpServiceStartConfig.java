package com.timevary.radar.tcp.config;

import com.timevary.radar.tcp.hander.base.RadarProtocolDataHandler;
import com.timevary.radar.tcp.server.RadarTcpServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author ：ywb
 * @date ：Created in 2022/1/10 10:18
 * @modified By：
 */
@Configuration
public class RadarTcpServiceStartConfig {

    @Bean
    public RadarTcpServer radarTcpServer(List<RadarProtocolDataHandler> handlers) {
        return RadarTcpServer.radarServerStarter(handlers);
    }

}
