
package com.aerosense.radar.tcp.protocol;

import com.alipay.remoting.ProtocolManager;

/**
 * 
 *
 * @author： jia.wu
 * @date： 2021/8/3 16:23
 * @version: 1.0
 */
public class RadarProtocolManager {
    /**

     */
    public static void initProtocols() {
        ProtocolManager.registerProtocol(new RadarProtocol(), RadarProtocol.PROTOCOL_CODE);
    }
}
