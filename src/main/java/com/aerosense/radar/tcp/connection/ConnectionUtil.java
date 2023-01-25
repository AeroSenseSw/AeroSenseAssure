package com.aerosense.radar.tcp.connection;

import com.alipay.remoting.Connection;
import com.google.common.base.Strings;
import com.aerosense.radar.tcp.protocol.RadarProtocolData;

/**
 *
 * @author： jia.wu
 * @date： 2021/8/12 10:53
 * @version: 1.0
 */
public class ConnectionUtil {

    public static final String ATTR_RADAR_Id = "radarId";
    public static final String ATTR_RADAR_VERSION= "version";
    public static final String ATTR_RADAR_TYPE= "type";

    public static String getRadarId(Connection connection){
        Object obj = connection.getAttribute(ATTR_RADAR_Id);
        return obj==null?null:obj.toString();
    }

    public static String getRadarVersion(Connection connection){
        Object obj = connection.getAttribute(ATTR_RADAR_VERSION);
        return obj==null?null:obj.toString();
    }

    public static Byte getRadarType(Connection connection){
        Object obj = connection.getAttribute(ATTR_RADAR_TYPE);
        return obj==null?null:(Byte)obj;
    }


    public static void bindRadarData(Connection connection, String radarId, String radarVersion, Byte radarType){
        connection.setAttribute(ATTR_RADAR_Id, radarId);
        connection.setAttribute(ATTR_RADAR_VERSION, radarVersion);
        connection.setAttribute(ATTR_RADAR_TYPE, radarType);
    }

    public static boolean fillBindData(Connection connection, RadarProtocolData radarProtocolData) {
        String radarId = getRadarId(connection);
        if(Strings.isNullOrEmpty(radarId)){
            return false;
        }
        radarProtocolData.setRadarId(radarId);
        String radarVersion = getRadarVersion(connection);
        radarProtocolData.setRadarVersion(radarVersion);
        Byte radarType = getRadarType(connection);
        radarProtocolData.setRadarType(radarType);
        return true;
    }
}
