package com.aerosense.radar.tcp.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 *
 * @author： jia.wu
 * @date： 2021/8/9 13:36
 * @version: 1.0
 */

@ConfigurationProperties(prefix = "radar.tcp")
@Data
public class RadarTcpServerProperties implements Serializable {


    private boolean enable;

    private String addressMap = "HashMap";

    private String host = "0.0.0.0";

    private int port = 8899;

    private long idleTimeout = 90000;


    private List<String> preferredeNetworks = new ArrayList<>();

    private List<String> ignoredInterfaces = new ArrayList<>();

    private boolean preferHostnameOverIP;

    private boolean useOnlySiteLocalInterface;
}
