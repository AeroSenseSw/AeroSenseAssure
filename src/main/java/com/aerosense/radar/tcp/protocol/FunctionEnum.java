
package com.aerosense.radar.tcp.protocol;

import java.util.Arrays;

/**
 *
 * @author： jia.wu
 * @date： 2021/8/4 14:59
 * @version: 1.0
 * @update: ywb 2022/1/5 17:56
 */

public enum FunctionEnum {
    /**未定义*/
    UNDEFINED(-1),
    /**错误*/
    ERROR(-2),
    /** 建立连接 */
    createConnection(0x0001),
    /**设置雷达安装高度*/
    SetInstallHeight(0x0002),
    /**获取雷达安装高度*/
    GetInstallHeight(0x0003),
    /**设置摔倒上报时间间隔*/
    SetFallReportTimer(0x0004),
    /**获取摔倒上报时间间隔*/
    GetFallReportTimer(0x0005),
    /**设置雷达工作范围*/
    SetWorkingRange(0x0006),
    /**获取雷达工作范围*/
    GetWorkingRange(0x0007),
    /**摔倒报警*/
    FallDetect(0x0009),
    /**入侵报警*/
    Invade(0x000a),
    /**热力图上报*/
    ReportHeatMap(0x000b),
    /**设置热力图开关*/
    SetHeatMapEnable(0x000c),
    /**获取热力图开关*/
    GetHeatMapEnable(0x000d),
    /**设置入侵开关*/
    SetInvadeEnable(0x000e),
    /**获取入侵开关*/
    GetInvadeEnable(0x000f),
    /**位置学习：App开始学习-(1，0，0）/ 雷达同步位置-(2，x,y).../ App确认到门口，开始挥手-(3,0,0) / 雷达确认学习失败-(4,0,0)  */
    PositionStudy(0x0010),
    /** 软重启 */
    softReboot(0x0411),
    /**
     * 通知升级，下发固件（固件内容 + crc校验）240b,是否升级成功
     */
    notifyUpdate(0x0021), issueFirmware(0x0022), updateResult(0x0023);

    private final short function;

    FunctionEnum(int function) {
        this.function = (short) function;
    }

    public static FunctionEnum from(short function) {
        return Arrays.stream(FunctionEnum.values())
                .filter(f -> f.getFunction() == function)
                .findFirst()
                .orElse(UNDEFINED);
    }

    public short getFunction() {
        return function;
    }
}
