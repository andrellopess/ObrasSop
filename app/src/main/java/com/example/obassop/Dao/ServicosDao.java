package com.example.obassop.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.obassop.Data.ServicosData;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface ServicosDao {

    @Insert(onConflict = REPLACE)
    void insert(ServicosData ServicosData);

    @Delete
    void delete(ServicosData ServicosData);

    @Query("DELETE FROM Servicos WHERE ObraID = :ID")
    void deleteServicoByID(int ID);

    @Query("SELECT * FROM Servicos WHERE ObraID = :OBRAID and BlocoID = :BLOCOID")
    List<ServicosData> selectSercicoByID(int OBRAID, int BLOCOID);

    @Query("SELECT servicoNum FROM Servicos WHERE ServicoID = :SERVICOID")
    String selectNumSercicoByID(int SERVICOID);

    @Query("SELECT * FROM Servicos WHERE servicoTexto = :SERVICOTEXTO and BlocoID = :BLOCOID and ObraID = :OBRAID")
    int ServicoID(String SERVICOTEXTO, int BLOCOID, int OBRAID);

    @Query("UPDATE Servicos SET servicoObs = :SERVICOOBS WHERE ServicoID = :SERVICOID")
    void UpdateObsByID(String SERVICOOBS, int SERVICOID);

    @Query("SELECT * FROM Servicos WHERE ObraID = :OBRAID ")
    List<ServicosData> selectObsServicoByObraID(int OBRAID);

    @Query("SELECT * FROM Servicos WHERE BlocoID = :BLOCOID ")
    List<ServicosData> selectObsServicoByBlocoID(int BLOCOID);

    @Query("SELECT servicoTexto FROM Servicos WHERE ServicoID = :SERVICOID")
    String SelectServicoTextoByID(int SERVICOID);

    @Delete
    void reset(List<ServicosData> ServicosData);

    @Query("SELECT * FROM Servicos")
    List<ServicosData> getAll();
}
