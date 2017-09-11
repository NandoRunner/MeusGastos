package fandradetecinfo.com.meusgastos.Models;

import android.content.Context;
import android.database.Cursor;

import java.io.Serializable;


public class Abastecimento extends _BaseModel implements Serializable  {

    private String TABLE_NAME = "abastecimento";

    /*   id INTEGER PRIMARY KEY AUTOINCREMENT,
        data DATETIME,
        id_veiculo INTEGER,
        id_posto INTEGER,
        combustivel INTEGER,
        preco_litro DOUBLE,
        valor_pago DOUBLE,
        odometro INTEGER
    */

    private long id;
    private String data;
    private String id_veiculo;
    private String id_posto;
    private String combustivel;
    private String preco_litro;
    private String valor_pago;
    private String odometro;
    private String ultimoOdometro;
    private String ultimoCombustivel;

    public long getId() {
        return id;
    }

    public Abastecimento(Context ctx) {
        super(ctx);
        this.table = TABLE_NAME;
    }

    public String getId_veiculo() {
        return id_veiculo;
    }

    public void setId_veiculo(String id_veiculo) {
        this.id_veiculo = id_veiculo ;
        //this.id_veiculo = String.valueOf(Integer.parseInt(id_veiculo)) ;
    }

    public String getData() {
        return data;
    }

    public void setDataTratada(String data) {
        this.data = tratarData(data);
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getId_posto() {
        return id_posto;
    }

    public void setId_posto(String id_posto) {
        this.id_posto = id_posto;
    }

    public String getCombustivel() {
        return combustivel;
    }

    public void setCombustivel(String combustivel) {
        this.combustivel = combustivel;
    }

    public String getPreco_litro() {
        return preco_litro;
    }

    public void setPreco_litro(String preco_litro) {
        this.preco_litro = preco_litro.replace(',', '.');
    }

    public String getValor_pago() {
        return valor_pago;
    }

    public void setValor_pago(String valor_pago) {
        this.valor_pago = valor_pago.replace(',', '.');
    }

    public String getOdometro() {
        return odometro;
    }

    public void setOdometro(String odometro) {
        this.odometro = odometro;
    }

    public String getLitros() {
        float litros = Float.parseFloat(valor_pago) / Float.parseFloat(preco_litro);
        return String.format("%.2f", litros);
    }

    public String getUltimoOdometro() {
        return ultimoOdometro;
    }

    public void setUltimoOdometro(String ultimoOdometro) {
        this.ultimoOdometro = ultimoOdometro;
    }

    public String getUltimoCombustivel() {
        return ultimoCombustivel;
    }

    public void setUltimoCombustivel(String ultimoCombustivel) {
        this.ultimoCombustivel = ultimoCombustivel;
    }

    public Cursor exibirRegistros()
    {
        try
        {
            String sql = "SELECT id, id_veiculo, id_posto, data, combustivel, preco_litro, "
                    + " printf('%.2f', valor_pago) valor_pago, odometro "
                    + " FROM abastecimento"
                    + " ORDER BY data desc";
            return buscarCursor(sql);
        } catch (Exception ex) {
            throw ex;
        }
    }

    public Cursor buscarUltimoOdometro(String veiculo)
    {
        try
        {
            String sql = "SELECT ifnull(max(odometro), '0') "
                    + " FROM abastecimento "
                    + " WHERE id_veiculo = ?";
            String args[] = { veiculo };
            return buscarCursor(sql, args);
        } catch (Exception ex) {
            throw ex;
        }

    }

    public Cursor buscarUltimoCombustivel(String veiculo)
    {
        try
        {
            String sql = "SELECT combustivel "
                    + " FROM abastecimento "
                    + " WHERE id in "
                    + " (SELECT MAX(id) FROM abastecimento WHERE id_veiculo = ?)";
            String args[] = { veiculo };
            return buscarCursor(sql, args);
        } catch (Exception ex) {
            throw ex;
        }

    }

}
