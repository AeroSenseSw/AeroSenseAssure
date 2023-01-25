package com.aerosense.radar.tcp.service.toRadar;

import com.aerosense.radar.tcp.protocol.FunctionEnum;
import com.aerosense.radar.tcp.server.RadarTcpServer;
import com.alipay.remoting.exception.RemotingException;
import com.aerosense.radar.tcp.protocol.RadarProtocolConsts;
import com.aerosense.radar.tcp.protocol.RadarProtocolData;
import com.aerosense.radar.tcp.util.ByteUtil;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 请求雷达工具类，直接调用工具方法完成调用雷达
 * @author jia.wu
 */
@Component
public class RequestRadarUtil {

    private volatile static RadarTcpServer radarTcpServer;

    public RequestRadarUtil(RadarTcpServer radarTcpServer) {
        synchronized (RequestRadarUtil.class) {
            if(radarTcpServer!=null) {
                RequestRadarUtil.radarTcpServer = radarTcpServer;
            }
        }
    }

    /**
     * 调用雷达，内部动态寻址雷达连接的服务器地址
     *
     * @param radarProtocolData
     * @return
     * @throws RemotingException
     */
    public static RadarProtocolData invokeToRadar(RadarProtocolData radarProtocolData)
            throws RemotingException {
       return invokeToRadar(radarProtocolData, 15000);
    }

    /**
     * 调用雷达，内部动态寻址雷达连接的服务器地址
     *
     * @param radarProtocolData
     * @param timeoutMills
     * @return
     * @throws RemotingException
     */
    public static RadarProtocolData invokeToRadar(RadarProtocolData radarProtocolData, int timeoutMills)
            throws RemotingException {
        try {
            Object obj = radarTcpServer.invokeSync(radarProtocolData, timeoutMills);
            if (obj instanceof RadarProtocolData) {
                return (RadarProtocolData) obj;
            }
            if (obj instanceof Exception) {
                throw new RemotingException(((Exception) obj).getMessage());
            }
            throw new RuntimeException("invoke to radar return data error : " + obj.toString());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }


    /**
     * 获取防摔雷达工作距离
     *
     * @param radarId
     * @return
     * @throws RemotingException
     */
    public static float getWorkRange(String radarId) throws RemotingException {
        RadarProtocolData radarProtocolData = RadarProtocolData.newInstance(radarId,
                FunctionEnum.GetWorkingRange, ByteUtil.intToByteBig(0));
        RadarProtocolData retObj = invokeToRadar(radarProtocolData);
        return ByteUtil.byte4ToFloat(retObj.getData());
    }

    /**
     * 设置防摔雷达工作距离
     *
     * @param radarId
     * @param workRange
     * @return
     * @throws RemotingException
     */
    public static boolean setWorkRange(String radarId, float workRange) throws RemotingException {
        if (workRange < 0) {
            throw new IllegalArgumentException("workRange value invalid : " + workRange);
        }
        if(workRange < RadarProtocolConsts.ANTI_FALL_WORK_RANGE_MIN_VALUE){
            workRange = RadarProtocolConsts.ANTI_FALL_WORK_RANGE_MAX_VALUE;
        }else if(workRange > RadarProtocolConsts.ANTI_FALL_WORK_RANGE_MAX_VALUE){
            workRange = RadarProtocolConsts.ANTI_FALL_WORK_RANGE_MAX_VALUE;
        }
        RadarProtocolData radarProtocolData = RadarProtocolData.newInstance(radarId,
                FunctionEnum.SetWorkingRange, ByteUtil.floatToByte4(workRange));
        RadarProtocolData retObj = invokeToRadar(radarProtocolData);
        return ByteUtil.byte4ToInt(retObj.getData()) == RadarProtocolConsts.RET_SUCCESS;
    }

    /**
     * 获取防摔雷达安装高度
     *
     * @param radarId
     * @return
     * @throws RemotingException
     */
    public static float getInstallHeight(String radarId) throws RemotingException {
        RadarProtocolData radarProtocolData = RadarProtocolData.newInstance(radarId,
                FunctionEnum.GetInstallHeight, ByteUtil.intToByteBig(0));
        RadarProtocolData retObj = invokeToRadar(radarProtocolData);
        return ByteUtil.byte4ToFloat(retObj.getData());
    }

    /**
     * 设置防摔雷达安装高度
     *
     * @param radarId
     * @param installHeight
     * @return
     * @throws RemotingException
     */
    public static boolean setInstallHeight(String radarId,  float installHeight) throws RemotingException {
        //非精度问题设置值不合法
        if (installHeight < 1.2f || installHeight > 2.4f) {
            throw new IllegalArgumentException("install height value invalid : " + installHeight);
        }
        //精度问题设置值覆盖为默认值
        if(installHeight < RadarProtocolConsts.INSTALL_HEIGHT_1_4){
            installHeight = RadarProtocolConsts.INSTALL_HEIGHT_1_4;
        }else if(installHeight > RadarProtocolConsts.INSTALL_HEIGHT_2_2){
            installHeight = RadarProtocolConsts.INSTALL_HEIGHT_2_2;
        }
        RadarProtocolData radarProtocolData = RadarProtocolData.newInstance(radarId,
                FunctionEnum.SetInstallHeight, ByteUtil.floatToByte4(installHeight));
        RadarProtocolData retObj = invokeToRadar(radarProtocolData);
        return ByteUtil.byte4ToInt(retObj.getData()) == RadarProtocolConsts.RET_SUCCESS;
    }

    /**
     * 获取防摔雷达摔倒上报时间间隔
     *
     * @param radarId
     * @return
     * @throws RemotingException
     */
    public static int getFallReportTimer(String radarId) throws RemotingException {
        RadarProtocolData radarProtocolData = RadarProtocolData.newInstance(radarId,
                FunctionEnum.GetFallReportTimer, ByteUtil.intToByteBig(0));
        RadarProtocolData retObj = invokeToRadar(radarProtocolData);
        return ByteUtil.bytes2IntBig(retObj.getData());
    }

    /**
     * 设置防摔雷达摔倒上报时间间隔
     *
     * @param radarId
     * @param fallReportTimer
     * @return
     * @throws RemotingException
     */
    public static boolean setFallReportTimer(String radarId, int fallReportTimer) throws RemotingException {
        if (fallReportTimer > 300 || fallReportTimer < 10) {
            throw new IllegalArgumentException("fallReportTimer value invalid : " + fallReportTimer);
        }
        RadarProtocolData radarProtocolData = RadarProtocolData.newInstance(radarId,
                FunctionEnum.SetFallReportTimer, ByteUtil.intToByteBig(fallReportTimer));
        RadarProtocolData retObj = invokeToRadar(radarProtocolData);
        return ByteUtil.byte4ToInt(retObj.getData()) == RadarProtocolConsts.RET_SUCCESS;
    }


    /**
     * 设置防摔雷达热力图开关
     *
     * @param radarId
     * @return
     * @throws RemotingException
     */
    public static boolean setHeatMapEnable(String radarId, int status) throws RemotingException {
        if (status != 0 && status != 1) {
            throw new IllegalArgumentException("status value must 0 or 1");
        }
        RadarProtocolData radarProtocolData = RadarProtocolData.newInstance(radarId,
                FunctionEnum.SetHeatMapEnable, ByteUtil.intToByteBig(status));
        RadarProtocolData retObj = invokeToRadar(radarProtocolData, 5000);
        return ByteUtil.bytes2IntBig(retObj.getData()) == RadarProtocolConsts.RET_SUCCESS;
    }

    /**
     * 获取防摔雷达热力图开关  true：开   false：关
     *
     * @param radarId
     * @return
     * @throws RemotingException
     */
    public static boolean isHeatMapEnable(String radarId) throws RemotingException {
        RadarProtocolData radarProtocolData = RadarProtocolData.newInstance(radarId,
                FunctionEnum.GetHeatMapEnable, ByteUtil.intToByteBig(0));
        RadarProtocolData retObj = invokeToRadar(radarProtocolData, 5000);
        return ByteUtil.byte4ToInt(retObj.getData()) == 1;
    }

    /**
     * 获取防摔雷达入侵报警开关  true：开   false：关
     *
     * @param radarId
     * @return
     * @throws RemotingException
     */
    public static boolean isInvadeEnable(String radarId) throws RemotingException {
        RadarProtocolData radarProtocolData = RadarProtocolData.newInstance(radarId,
                FunctionEnum.GetInvadeEnable, ByteUtil.intToByteBig(0));
        RadarProtocolData retObj = invokeToRadar(radarProtocolData);
        return ByteUtil.byte4ToInt(retObj.getData()) == RadarProtocolConsts.ALGORITHM_STATUS_OPEN;
    }

    /**
     * 开始门位置学习
     * @param radarId
     * @return
     * @throws RemotingException
     */
    public static boolean startPositionStudy(String radarId) throws RemotingException {
        byte[] bytes = {0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0};
        RadarProtocolData radarProtocolData = RadarProtocolData.newInstance(radarId,
                FunctionEnum.PositionStudy, bytes);
        RadarProtocolData retObj = invokeToRadar(radarProtocolData);
        return retObj.getData()!=null && Objects.deepEquals(retObj.getData(), bytes);
    }

    /**
     * 结束门位置学习
     * @param radarId
     * @throws RemotingException
     * @return
     */
    public static boolean endPositionStudy(String radarId) throws RemotingException {
        byte[] bytes = {0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0};
        RadarProtocolData radarProtocolData = RadarProtocolData.newInstance(radarId,
                FunctionEnum.PositionStudy, bytes);
        RadarProtocolData retObj = invokeToRadar(radarProtocolData);
        return retObj.getData()!=null && Objects.deepEquals(retObj.getData(), bytes);
    }
}
