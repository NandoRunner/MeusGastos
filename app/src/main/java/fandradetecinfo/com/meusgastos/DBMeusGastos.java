package fandradetecinfo.com.meusgastos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class DBMeusGastos {

    private String sql;

    private Context ctx;

    //consumo
    public static final String DATA = "data";
    public static final String ID_VEICULO = "id_veiculo";
    public static final String COMBUSTIVEL = "combustivel";
    public static final String KM_PERCORRIDOS = "km_percorridos";
    public static final String LITROS_GASTOS = "litros_gastos";

    //abastecimento
    //public static final String DATA = "data";
    //public static final String ID_VEICULO = "id_veiculo";
    public static final String ID_POSTO = "id_posto";
    //public static final String COMBUSTIVEL = "combustivel";
    public static final String PRECO_LITRO = "preco_litro";
    public static final String VALOR_PAGO = "valor_pago";
    public static final String ODOMETRO = "odometro";

    public DBMeusGastos(Context ctx)
    {
        this.ctx = ctx;
    }



/*    public String buscarRegistro(String pVeiculo, String pPosto, String pData)
    {
        sql = "SELECT count(*) FROM abastecimento "
                + " WHERE id_veiculo = ? "
                + " AND id_posto = ? "
                + " AND data = ?";
        String args[] = { pVeiculo, pPosto, pData };
        try {
            return MyDBHandler.getInstance(ctx).buscarValorEscalar(sql, args);
        } catch (Exception ex) {
            throw ex;
        }
    }*/


/*    public long inserirDados(String data, String id_veiculo, String id_posto,
                             String combustivel, String preco_litro, String valor_pago,
                             String odometro, String litros_gastos)
    {
        String aux = buscarUltimoOdometro(id_veiculo);

        aux = aux.equals("0") ? odometro : aux;

        int km_percorridos = Integer.parseInt(odometro) - Integer.parseInt(aux);

        long ret = this.inserirAbastecimento(data, id_veiculo, id_posto, combustivel, preco_litro,
                valor_pago, odometro);

        return this.inserirConsumo(data, id_veiculo, combustivel, String.valueOf(km_percorridos),
                litros_gastos);
    }*/





}
