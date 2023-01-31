package com.aerosense.radar.tcp.protocol;

/**
 * @author jia.wu
 */
public class RadarProtocolConsts {


    public static final int POSITION_STUDY_START_HEAD = 1;

    public static final int POSITION_STUDY_DATA_SYNC_HEAD = 2;

    public static final int POSITION_STUDY_END_HEAD = 3;

    public static final int POSITION_STUDY_ERROR_HEAD = 4;


    public static final int RET_SUCCESS = 1;

    public static final int RET_FAILURE = 0;


    public static final float INSTALL_HEIGHT_1_4 = 1.4f;

    public static final float INSTALL_HEIGHT_2_2 = 2.2f;


    public static final int INSTALL_MODE_AUTO = 0;

    public static final int INSTALL_MODE_HEADBOARD = 1;

    public static final int INSTALL_MODE_CEILING = 2;


    public static final float INSTALL_HEIGHT_HEADBOARD_DEFAULT= 1F;

    public static final float INSTALL_HEIGHT_CEILING_DEFAULT_2 = 2.5F;


    public static final float INSTALL_HEIGHT_AF_DEFAULT = 1.4F;


    public static final int ALGORITHM_STATUS_CLOSE = 0;

    public static final int ALGORITHM_STATUS_OPEN = 1;


    public static final int REPORT_TIME_UNIT = 50;
    /**



     */
    public static final int RADAR_TARGET_DISTANCE_AUTO = 0;

    /**

     */
    public static final int RADAR_ONLINE = 0;
    /**

     */
    public static final int RADAR_OFFLINE = 1;

    /**

     */
    public static final float SIN_60 = 0.8660254037844386F;

    /**

     */
    public static final float ANTI_FALL_WORK_RANGE_MAX_VALUE =  7.0f;

    /**

     */
    public static final float ANTI_FALL_WORK_RANGE_MIN_VALUE =  1.0f;
}
