package com.timevary.radar.tcp.protocol;

/**
 * @author jia.wu
 */
public class RadarProtocolConsts {

    /**雷达学习门位置开始数据头*/
    public static final int POSITION_STUDY_START_HEAD = 1;
    /**雷达学习门位置同步数据头*/
    public static final int POSITION_STUDY_DATA_SYNC_HEAD = 2;
    /**雷达学习门位置结束数据头*/
    public static final int POSITION_STUDY_END_HEAD = 3;
    /**雷达学习门位置错误数据头*/
    public static final int POSITION_STUDY_ERROR_HEAD = 4;

    /**雷达返回操作成功标志*/
    public static final int RET_SUCCESS = 1;
    /**雷达返回操作失败标志*/
    public static final int RET_FAILURE = 0;

    /**防摔雷达高度1.4米*/
    public static final float INSTALL_HEIGHT_1_4 = 1.4f;
    /**防摔雷达高度2.2米*/
    public static final float INSTALL_HEIGHT_2_2 = 2.2f;

    /**雷达安装模式-自定义安装*/
    public static final int INSTALL_MODE_AUTO = 0;
    /**呼吸心率雷达安装模式-床头安装*/
    public static final int INSTALL_MODE_HEADBOARD = 1;
    /**呼吸心率雷达安装模式-顶部安装*/
    public static final int INSTALL_MODE_CEILING = 2;

    /**呼吸心率雷达安装高度默认值：1米*/
    public static final float INSTALL_HEIGHT_HEADBOARD_DEFAULT= 1F;
    /**呼吸心率雷达安装高度默认值：2.5米*/
    public static final float INSTALL_HEIGHT_CEILING_DEFAULT_2 = 2.5F;

    /**防摔雷达安装高度默认值：1.4米*/
    public static final float INSTALL_HEIGHT_AF_DEFAULT = 1.4F;

    /**算法关闭状态值*/
    public static final int ALGORITHM_STATUS_CLOSE = 0;
    /**算法打开状态值*/
    public static final int ALGORITHM_STATUS_OPEN = 1;

    /**上报时间间隔单位：50毫秒*/
    public static final int REPORT_TIME_UNIT = 50;
    /**
     * 设置目标距离
     * 0：不设置，自动匹配
     * >0：设置距离单位m
     */
    public static final int RADAR_TARGET_DISTANCE_AUTO = 0;

    /**
     * 雷达在线
     */
    public static final int RADAR_ONLINE = 0;
    /**
     * 雷达离线
     */
    public static final int RADAR_OFFLINE = 1;

    /**
     * sin60°的值，用于计算呼吸训练雷达的检测目标距离
     */
    public static final float SIN_60 = 0.8660254037844386F;

    /**
     * 防摔雷达工作距离最大值
     */
    public static final float ANTI_FALL_WORK_RANGE_MAX_VALUE =  7.0f;

    /**
     * 防摔雷达工作距离最小值
     */
    public static final float ANTI_FALL_WORK_RANGE_MIN_VALUE =  1.0f;
}
