package com.example.obassop.Data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Obras")
public class ObrasData implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int ObraID;

    @ColumnInfo(name = "ObraContrato")
    private String ObraContrato;

    @ColumnInfo(name = "ObraValor")
    private double ObraValor;

    @ColumnInfo(name = "ObraPercent")
    private double ObraPercent;

    @ColumnInfo(name = "ObraSaldo")
    private double ObraSaldo;

    @ColumnInfo(name = "ObraObjeto")
    private String ObraObjeto;

    @ColumnInfo(name = "ObraNome")
    private String ObraNome;

    @ColumnInfo(name = "ObraTelefone")
    private String ObraTelefone;

    public int getObraID() {
        return ObraID;
    }

    public void setObraID(int obraID) {
        ObraID = obraID;
    }

    public String getObraContrato() {
        return ObraContrato;
    }

    public void setObraContrato(String obraContrato) {
        ObraContrato = obraContrato;
    }

    public double getObraValor() {
        return ObraValor;
    }

    public void setObraValor(double obraValor) {
        ObraValor = obraValor;
    }

    public double getObraPercent() {
        return ObraPercent;
    }

    public void setObraPercent(double obraPercent) {
        ObraPercent = obraPercent;
    }

    public double getObraSaldo() {
        return ObraSaldo;
    }

    public void setObraSaldo(double obraSaldo) {
        ObraSaldo = obraSaldo;
    }

    public String getObraObjeto() {
        return ObraObjeto;
    }

    public void setObraObjeto(String obraObjeto) {
        ObraObjeto = obraObjeto;
    }

    public String getObraNome() {
        return ObraNome;
    }

    public void setObraNome(String obraNome) {
        ObraNome = obraNome;
    }

    public String getObraTelefone() {
        return ObraTelefone;
    }

    public void setObraTelefone(String obraTelefone) {
        ObraTelefone = obraTelefone;
    }
}

