package com.example.obassop.Data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Blocos")
public class BlocosData implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int BlocoID;

    @ColumnInfo(name = "ObraID")
    private int ObraID;

    @ColumnInfo(name = "blocoNum")
    private String blocoNum;

    @ColumnInfo(name = "blocoTexto")
    private String blocoTexto;

    @ColumnInfo(name = "blocoPercent")
    private double blocoPercent;

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

    public String getBlocoNum() {
        return blocoNum;
    }

    public void setBlocoNum(String blocoNum) {
        this.blocoNum = blocoNum;
    }

    public String getBlocoTexto() {
        return blocoTexto;
    }

    public void setBlocoTexto(String blocoTexto) {
        this.blocoTexto = blocoTexto;
    }

    public double getBlocoPercent() {
        return blocoPercent;
    }

    public void setBlocoPercent(double blocoPercent) {
        this.blocoPercent = blocoPercent;
    }
}