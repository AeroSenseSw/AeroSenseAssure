
package com.radar.fall.tcp.protocol;

import java.util.Arrays;

/**
 *
 * @author： jia.wu
 * @date： 2021/8/4 14:59
 * @version: 1.0
 * @update: ywb 2022/1/5 17:56
 */

public enum FunctionEnum {

    UNDEFINED(-1),

    ERROR(-2),

    createConnection(0x0001),

    SetInstallHeight(0x0002),

    GetInstallHeight(0x0003),

    SetFallReportTimer(0x0004),

    GetFallReportTimer(0x0005),

    SetWorkingRange(0x0006),

    GetWorkingRange(0x0007),

    FallDetect(0x0009),

    Invade(0x000a),

    ReportHeatMap(0x000b),

    SetHeatMapEnable(0x000c),

    GetHeatMapEnable(0x000d),

    SetInvadeEnable(0x000e),

    GetInvadeEnable(0x000f),

    PositionStudy(0x0010),

    softReboot(0x0411),
    /**

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
