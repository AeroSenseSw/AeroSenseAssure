
package com.radar.fall.tcp.protocol;

import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Arrays;



@SuperBuilder
@NoArgsConstructor
public class RadarProtocolData implements Serializable {
    /** For serialization  */
    private static final long serialVersionUID = 1;

    private FunctionEnum    function;

    private String          radarId;

    private String          radarVersion;

    private Byte            radarType;

    private byte[]          data = new byte[4];

    public FunctionEnum getFunction() {
        return function;
    }

    public void setFunction(FunctionEnum function) {
        this.function = function;
    }

    public String getRadarId() {
        return radarId;
    }

    public void setRadarId(String radarId) {
        this.radarId = radarId;
    }

    public String getRadarVersion() {
        return radarVersion;
    }

    public void setRadarVersion(String radarVersion) {
        this.radarVersion = radarVersion;
    }

    public Byte getRadarType() {
        return radarType;
    }

    public void setRadarType(Byte radarType) {
        this.radarType = radarType;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    /**

     * @return
     */
    public static final RadarProtocolData newFunctionInstance(FunctionEnum function, byte[] data){
        RadarProtocolData radarProtocolData = new RadarProtocolData();
        radarProtocolData.setFunction(function);
        radarProtocolData.setData(data);
        return radarProtocolData;
    }

    /**

     * @param id
     * @param function
     * @param data
     * @return
     */
    public static final RadarProtocolData newInstance(String id, FunctionEnum function, byte[] data){
        RadarProtocolData radarProtocolData = new RadarProtocolData();
        radarProtocolData.setRadarId(id);
        radarProtocolData.setFunction(function);
        radarProtocolData.setData(data);
        return radarProtocolData;
    }

    @Override
    public String toString() {
        return "RadarProtocolData{" +
                "function=" + function +
                ", radarId='" + radarId + '\'' +
                ", radarVersion='" + radarVersion + '\'' +
                ", radarType=" + radarType +
                ", data=" + Arrays.toString(data) +
                '}';
    }
}
