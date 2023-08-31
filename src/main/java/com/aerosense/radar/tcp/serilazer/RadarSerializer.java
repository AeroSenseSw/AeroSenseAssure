
package com.aerosense.radar.tcp.serilazer;

import com.alipay.remoting.exception.CodecException;
import com.alipay.remoting.serialization.Serializer;
import com.aerosense.radar.tcp.protocol.FunctionEnum;
import com.aerosense.radar.tcp.protocol.RadarProtocolData;

import java.nio.charset.StandardCharsets;

/**
 *
 * @author： jia.wu
 * @date： 2021/8/4 11:33
 * @version: 1.0
 */
public class RadarSerializer implements Serializer {
    public static final int      IDX     = 4;
    public static final Byte     IDX_BYTE  = 4;
    private static final byte[] EMPTY_ARRAY = new byte[0];
    private static final int RADAR_PROTOCOL_FUNCTION_BYTE_LENGTH = 2;

    @Override
    public byte[] serialize(Object object) throws CodecException {
        if(object==null){
            return EMPTY_ARRAY;
        }if(object instanceof byte[]){
            return (byte[]) object;
        }else if (object instanceof RadarProtocolData) {
            RadarProtocolData protocolData = (RadarProtocolData) object;
            int dataLen = protocolData.getData() == null ? 0 : protocolData.getData().length;
            byte[] bytes = new byte[RADAR_PROTOCOL_FUNCTION_BYTE_LENGTH + dataLen];
            byte[] functionBytes = toByteArray(protocolData.getFunction().getFunction());
            System.arraycopy(functionBytes, 0, bytes, 0, 2);
            if (dataLen > 0) {
                System.arraycopy(protocolData.getData(), 0, bytes, 2, dataLen);
            }
            return bytes;
        } else {
            throw new CodecException("unsupport serialize data type "
                    + object.getClass().toString());
        }
    }

    @Override
    public RadarProtocolData deserialize(byte[] bytes, String clazz) {
        RadarProtocolData radarProtocolData = new RadarProtocolData();
        if (bytes.length > 0) {
            short function = fromBytes(bytes[0], bytes[1]);
            FunctionEnum deserializedFunction = FunctionEnum.from(function);
            if (deserializedFunction != FunctionEnum.UNDEFINED) {
                radarProtocolData.setFunction(deserializedFunction);
                int dataLen = bytes.length - RADAR_PROTOCOL_FUNCTION_BYTE_LENGTH;
                if (dataLen > 0) {
                    byte[] data = new byte[dataLen];
                    System.arraycopy(bytes, 2, data, 0, dataLen);
                    radarProtocolData.setData(data);
                }
            } else {
                radarProtocolData.setFunction(FunctionEnum.UNDEFINED);
                radarProtocolData.setData(bytes);
            }
        }
        return radarProtocolData;
    }

    /**

     * @param value
     * @return
     */
    public static byte[] toByteArray(short value) {
        return new byte[]{(byte)(value >> 8), (byte)(value&0xff)};
    }

    /**

     * @param b1
     * @param b2
     * @return
     */
    public static short fromBytes(byte b1, byte b2) {
        return (short)(b1 << 8 | b2 & 255);
    }
}
