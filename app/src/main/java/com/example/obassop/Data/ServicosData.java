package com.example.obassop.Data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Servicos")

public class ServicosData {

    @PrimaryKey(autoGenerate = true)
    private int ServicoID;

    @ColumnInfo(name = "BlocoID")
    private int BlocoID;

    @ColumnInfo(name = "ObraID")
    private int ObraID;

    @ColumnInfo(name = "servicoNum")
    private String servicoNum;

    @ColumnInfo(name = "servicoTexto")
    private String servicoTexto;

    @ColumnInfo(name = "servicoQtd")
    private Double servicoQtd;

    @ColumnInfo(name = "servicoPercent")
    private double servicoPercent;

    @ColumnInfo(name = "servicoStatus")
    private int servicoStatus;

    @ColumnInfo(name = "servicoObs")
    private String servicoObs;

    @ColumnInfo(name = "servicoUnidade")
    private String servicoUnidade;

    public int getServicoID() {
        return ServicoID;
    }

    public void setServicoID(int servicoID) {
        ServicoID = servicoID;
    }

    public int getBlocoID() {
        return BlocoID;
    }

    public void setBlocoID(int blocoID) {
        BlocoID = blocoID;
    }

    public int getObraID() {
        return ObraID;
    }

    public void setObraID(int obraID) {
        ObraID = obraID;
    }

    public String getServicoNum() {
        return servicoNum;
    }

    public void setServicoNum(String servicoNum) {
        this.servicoNum = servicoNum;
    }

    public String getServicoTexto() {
        return servicoTexto;
    }

    public void setServicoTexto(String servicoTexto) {
        this.servicoTexto = servicoTexto;
    }

    public Double getServicoQtd() {
        return servicoQtd;
    }

    public void setServicoQtd(Double servicoQtd) {
        this.servicoQtd = servicoQtd;
    }

    public double getServicoPercent() {
        return servicoPercent;
    }

    public void setServicoPercent(double servicoPercent) {
        this.servicoPercent = servicoPercent;
    }

    public int getServicoStatus() {
        return servicoStatus;
    }

    public void setServicoStatus(int servicoStatus) {
        this.servicoStatus = servicoStatus;
    }

    public String getServicoObs() {
        return servicoObs;
    }

    public void setServicoObs(String servicoObs) {
        this.servicoObs = servicoObs;
    }

    public String getServicoUnidade() {
        return servicoUnidade;
    }

    public void setServicoUnidade(String servicoUnidade) {
        this.servicoUnidade = servicoUnidade;
    }
}
