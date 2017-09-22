package fandradetecinfo.com.meusgastos.Models;

import android.content.Context;
import android.database.Cursor;

import java.io.Serializable;


public class Consumo extends _BaseModel implements Serializable  {

    private String TABLE_NAME = "consumo";

    /*  id INTEGER PRIMARY KEY AUTOINCREMENT,
        data DATETIME,
        id_veiculo INTEGER,
        combustivel INTEGER,
        km_percorridos INTEGER,
        litros_gastos DOUBLE
        km_hora DOUBLE
    */

    private long id;
    private long id_abastecimento;
    //private String data;
    //private String id_veiculo;
    //private String combustivel;
    private String km_percorridos;
    private String litros_gastos;
    private String km_hora;


    public Consumo(Context ctx) {
        super(ctx);
        this.table = TABLE_NAME;
    }

    public long getId() {
        return id;
    }

//    public String getData() {
//        return data;
//    }
//
//    public void setData(String data) {
//        this.data = data;
//    }
//
//    public void setDataTratada(String data) {
//        this.data = tratarData(data);
//    }
//
//    public String getId_veiculo() {
//        return id_veiculo;
//    }
//
//    public void setId_veiculo(String id_veiculo) {
//        this.id_veiculo = id_veiculo;
//    }
//
//    public String getCombustivel() {
//        return combustivel;
//    }
//
//    public void setCombustivel(String combustivel) {
//        this.combustivel = combustivel;
//    }

    public String getKm_percorridos() {
        return km_percorridos;
    }

    public void setKm_percorridos(String km_percorridos) {
        this.km_percorridos = km_percorridos;
    }

    public String getLitros_gastos() {
        return litros_gastos;
    }

    public void setLitros_gastos(String litros_gastos) {
        this.litros_gastos = litros_gastos.replace(',', '.');
    }
    public String getKm_hora() {
        return km_hora;
    }

    public void setKm_hora(String km_hora) {
        this.km_hora = km_hora.replace(',', '.');
    }

    public long getId_abastecimento() {
        return id_abastecimento;
    }

    public void setId_abastecimento(long id_abastecimento) {
        this.id_abastecimento = id_abastecimento;
    }
}
