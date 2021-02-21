package com.ScreeningHelper.Beyond.ScreeningHelper.repository;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "pykrxInfo")
public class MongoPykrxInfo {

    @Id
    private String id;
    private String name;
    private double BPS;
    private double PER;
    private double PBR;
    private double EPS;
    private double DIV;
    private double DPS;
    private double ROE;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBPS() {
        return BPS;
    }

    public void setBPS(double BPS) {
        this.BPS = BPS;
    }

    public double getPER() {
        return PER;
    }

    public void setPER(double PER) {
        this.PER = PER;
    }

    public double getPBR() {
        return PBR;
    }

    public void setPBR(double PBR) {
        this.PBR = PBR;
    }

    public double getEPS() {
        return EPS;
    }

    public void setEPS(double EPS) {
        this.EPS = EPS;
    }

    public double getDIV() {
        return DIV;
    }

    public void setDIV(double DIV) {
        this.DIV = DIV;
    }

    public double getDPS() {
        return DPS;
    }

    public void setDPS(double DPS) {
        this.DPS = DPS;
    }

    public double getROE() {
        return ROE;
    }

    public void setROE(double ROE) {
        this.ROE = ROE;
    }
}
