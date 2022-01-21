package com.example.obassop.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.obassop.Data.SubservicosData;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface SubservicosDao {

    @Insert(onConflict = REPLACE)
    void insert(SubservicosData SubservicosData);

    @Delete
    void delete(SubservicosData SubservicosData);

    @Query("DELETE FROM Subservicos WHERE ObraID = :ID")
    void deleteSubservicoByID(int ID);

    @Query("SELECT * FROM Subservicos WHERE ObraID = :ID")
    List<SubservicosData> selectSubservicoByID(int ID);

    @Query("SELECT * FROM Subservicos WHERE subservicoTexto = :SUBSERVICOTEXTO and ServicoID = :SERVICOID and BlocoID = :BLOCOID and ObraID = :OBRAID")
    int SubservicoID(String SUBSERVICOTEXTO, int SERVICOID, int BLOCOID, int OBRAID);

    @Query("SELECT * FROM Subservicos WHERE ObraID = :OBRAID and BlocoID = :BLOCOID and ServicoID = :SERVICOID")
    List<SubservicosData> selectSubsercicoByID(int OBRAID, int BLOCOID, int SERVICOID);

    @Query("UPDATE Subservicos SET subservicoObs = :SUBSERVICOOBS WHERE SubservicoID = :SUBSERVICOID")
    void UpdateObsSubservicoByID(String SUBSERVICOOBS, int SUBSERVICOID);


    @Query("SELECT * FROM Subservicos WHERE ServicoID = :SERVICOID")
    List<SubservicosData> selectSubsercicoByIDServ(int SERVICOID);

    @Query("SELECT COUNT(*) FROM Subservicos WHERE ServicoID = :SERVICOID")
    int countSubsercicoByID(int SERVICOID);

    @Query("SELECT subservicoNum FROM Subservicos WHERE SubservicoID = :SUBSERVICOID")
    String selectNumSubsercicoByID(int SUBSERVICOID);

    @Query("SELECT * FROM Subservicos WHERE ServicoID = :SERVICOID ")
    List<SubservicosData> selectObsSubservicoByBlocoID(int SERVICOID);

    @Query("SELECT * FROM Subservicos WHERE ObraID = :OBRAID ")
    List<SubservicosData> selectObsSubservicoByObraID(int OBRAID);

    @Delete
    void reset(List<SubservicosData> SubservicosData);

    @Query("SELECT * FROM Subservicos")
    List<SubservicosData> getAll();
}

