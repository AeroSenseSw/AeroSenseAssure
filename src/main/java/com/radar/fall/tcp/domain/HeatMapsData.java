package com.radar.fall.tcp.domain;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**

 * @author jia.wu
 */
public class HeatMapsData implements Serializable {


    List<Integer> HeatX=new ArrayList<>();
    List<Integer> HeatY=new ArrayList<>();
    List<Integer> HeatN=new ArrayList<>();

    public HeatMapsData(List<Integer> heatX, List<Integer> heatY, List<Integer> heatN) {
        HeatX = heatX;
        HeatY = heatY;
        HeatN = heatN;
    }

    public List<Integer> getHeatX() {
        return HeatX;
    }

    public void setHeatX(List<Integer> heatX) {
        HeatX = heatX;
    }

    public List<Integer> getHeatY() {
        return HeatY;
    }

    public void setHeatY(List<Integer> heatY) {
        HeatY = heatY;
    }

    public List<Integer> getHeatN() {
        return HeatN;
    }

    public void setHeatN(List<Integer> heatN) {
        HeatN = heatN;
    }

    @Override
    public String toString() {
        return "HeatMapsData{" +
                "HeatX=" + HeatX +
                ", HeatY=" + HeatY +
                ", HeatN=" + HeatN +
                '}';
    }
}
