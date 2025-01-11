
package com.aerosense.radar.tcp.protocol;

import java.util.Arrays;



public enum FunctionEnum {

    UNDEFINED(-1),

    ERROR(-2),

    createConnection(0x0001),

    SetInstallHeight(0x0002),

    GetInstallHeight(0x0003),

    SetFallReportBufferTime(0x0004),

    GetFallReportBufferTime(0x0005),

    SetWorkingRange(0x0006),

    GetWorkingRange(0x0007),

    FallDetect(0x0009),

    IntrusionAlert(0x000a),

    ReportHeatMap(0x000b),

    SetHeatMapEnable(0x000c),

    GetHeatMapEnable(0x000d),

    SetIntrusionDetect(0x000e),

    GetIntrusionDetect(0x000f),

    RoomLayout(0x0010),

    NegativeFallAlert(0x0011),

    Register(0x0012),

    SetMachineLearning(0x0015),

    GetMachineLearning(0x0016),

    EliminateFallAlert(0x0017),

    PresenceDetection(0x0018),

    PersonPositionDetection(0x001c),

    /**
      * firmware upgrade
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
