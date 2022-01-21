package com.example.obassop.Data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "Subservicos")
public class SubservicosData {

    @PrimaryKey(autoGenerate = true)
    private int SubservicoID;

    @ColumnInfo(name = "ServicoID")
    private int ServicoID;

    @ColumnInfo(name = "ObraID")
    private int ObraID;

    @ColumnInfo(name = "BlocoID")
    private int BlocoID;

    @ColumnInfo(name = "subservicoNum")
    private String subservicoNum;

    @ColumnInfo(name = "subservicoTexto")
    private String subservicoTexto;

    @ColumnInfo(name = "subservicoQtd")
    private Double subsubservicoQtd;

    @ColumnInfo(name = "subservicoUnidade")
    private String subservicoUnidade;

    @ColumnInfo(name = "subservicoPercent")
    private double subservicoPercent;

    @ColumnInfo(name = "subservicoStatus")
    private Double subservicoStatus;

    @ColumnInfo(name = "subservicoObs")
    private String subservicoObs;

    public int getSubservicoID() {
        return SubservicoID;
    }

    public void setSubservicoID(int subservicoID) {
        SubservicoID = subservicoID;
    }

    public int getServicoID() {
        return ServicoID;
    }

    public void setServicoID(int servicoID) {
        ServicoID = servicoID;
    }

    public int getObraID() {
        return ObraID;
    }

    public void setObraID(int obraID) {
        ObraID = obraID;
    }

    public int getBlocoID() {
        return BlocoID;
    }

    public void setBlocoID(int blocoID) {
        BlocoID = blocoID;
    }

    public String getSubservicoNum() {
        return subservicoNum;
    }

    public void setSubservicoNum(String subservicoNum) {
        this.subservicoNum = subservicoNum;
    }

    public String getSubservicoTexto() {
        return subservicoTexto;
    }

    public void setSubservicoTexto(String subservicoTexto) {
        this.subservicoTexto = subservicoTexto;
    }

    public Double getSubsubservicoQtd() {
        return subsubservicoQtd;
    }

    public void setSubsubservicoQtd(Double subsubservicoQtd) {
        this.subsubservicoQtd = subsubservicoQtd;
    }

    public String getSubservicoUnidade() {
        return subservicoUnidade;
    }

    public void setSubservicoUnidade(String subservicoUnidade) {
        this.subservicoUnidade = subservicoUnidade;
    }

    public double getSubservicoPercent() {
        return subservicoPercent;
    }

    public void setSubservicoPercent(double subservicoPercent) {
        this.subservicoPercent = subservicoPercent;
    }

    public Double getSubservicoStatus() {
        return subservicoStatus;
    }

    public void setSubservicoStatus(Double subservicoStatus) {
        this.subservicoStatus = subservicoStatus;
    }

    public String getSubservicoObs() {
        return subservicoObs;
    }

    public void setSubservicoObs(String subservicoObs) {
        this.subservicoObs = subservicoObs;
    }
}
