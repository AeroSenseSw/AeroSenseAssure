
package com.aerosense.radar.tcp.connection;

import java.util.Set;

/**
 * 
 *
 * @author： jia.wu
 * @date： 2021/8/6 17:15
 * @version: 1.0
 */
public interface RadarAddressMap {
    /**

     * @param radarAddress
     * @param radarId
     */
    void bindAddress(String radarAddress, String radarId);

    /**

     * @param address
     * @param radarId
     */
    void unbindAddress(String address, String radarId);

    /**

     * @param radarId
     * @return
     */
    String getRadarAddress(String radarId);

    /**

     * @param radarId
     * @return
     */
    String getServerAddress(String radarId);

    /**

     * @return
     */
    Set<String> getOnlineRadarList();

    /**

     * @return
     */
    long getOnlineRadarCount();

    /**

     */
    void clear();

    /**

     * @return
     */
    String getServerAddress();

}
