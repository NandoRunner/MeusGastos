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

    public String buscarUltimoOdometro(String pVeiculo)
    {
        sql = "SELECT ifnull(max(odometro), '0') "
                + " FROM abastecimento "
                + " WHERE id_veiculo = ?";
        String args[] = { pVeiculo };
        try {
            return MyDBHandler.getInstance(ctx).buscarValorEscalar(sql, args);
        } catch (Exception ex) {
            throw ex;
        }
    }

    public String buscarRegistro(String pVeiculo, String pPosto, String pData)
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
    }

    public Cursor exibirAbastecimento()
    {
        sql = "SELECT id, ifnull(data, 'n/a'), id_veiculo, id_posto, combustivel, preco_litro, valor_pago, "
                + " valor_pago/preco_litro as litrosAbastecidos "
                + " FROM abastecimento "
                + " ORDER BY datetime(data) DESC";
        try {
            return MyDBHandler.getInstance(ctx).buscarCursor(sql);
        } catch (Exception e) {
            throw e;
        }
    }

    public Cursor exibirConsumo()
    {
        sql = "SELECT id, data, id_veiculo, combustivel, km_percorridos, litros_gastos, "
                + " km_percorridos/litros_gastos as autonomia "
                + " FROM consumo "
                + " ORDER BY datetime(data) DESC";
        try {
            return MyDBHandler.getInstance(ctx).buscarCursor(sql);
        } catch (Exception e) {
            throw e;

        }
    }

    public Cursor exibirTotais()
    {
        //strftime('%Y-%m', data)
        sql = "SELECT strftime('%Y-%m', a.data) as yr_mn, a.id_veiculo, a.combustivel, sum(a.valor_pago) as totalPago, "
                + " sum(a.valor_pago/a.preco_litro) as TotalLitros, sum(km_percorridos) as KM "
                + " FROM abastecimento a "
                + " INNER JOIN consumo m on m.data = a.data "
                + " GROUP BY yr_mn, a.id_veiculo, a.combustivel "
                + " ORDER BY yr_mn DESC";
        try {
            return MyDBHandler.getInstance(ctx).buscarCursor(sql);
        } catch (Exception e) {
            throw e;
        }
    }

    public long inserirDados(String data, String id_veiculo, String id_posto,
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
    }

    private long inserirAbastecimento(String data, String id_veiculo, String id_posto,
                                      String combustivel, String preco_litro, String valor_pago,
                                      String odometro)
    {
        ContentValues content = new ContentValues();
        content.put(DATA, data);
        content.put(ID_VEICULO, id_veiculo);
        content.put(ID_POSTO, id_posto);
        content.put(COMBUSTIVEL, combustivel);
        content.put(PRECO_LITRO, preco_litro);
        content.put(VALOR_PAGO, valor_pago);
        content.put(ODOMETRO, odometro);

        try {
            return MyDBHandler.getInstance(ctx).inserir(MyDBHandler.TABLE_NAME_1, content);
        } catch (Exception ex) {
            throw  ex;
        }
    }

    private long inserirConsumo(String data, String id_veiculo, String combustivel,
                                String km_percorridos, String litros_gastos)
    {
        ContentValues content = new ContentValues();
        content.put(DATA, data);
        content.put(ID_VEICULO, id_veiculo);
        content.put(COMBUSTIVEL, combustivel);
        content.put(KM_PERCORRIDOS, km_percorridos);
        content.put(LITROS_GASTOS, litros_gastos);

        try {
            return MyDBHandler.getInstance(ctx).inserir(MyDBHandler.TABLE_NAME_2, content);
        } catch (Exception ex) {
            throw  ex;
        }
    }

}
