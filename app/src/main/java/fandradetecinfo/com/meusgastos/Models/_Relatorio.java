package fandradetecinfo.com.meusgastos.Models;

import android.content.Context;
import android.database.Cursor;

import java.io.Serializable;


public class _Relatorio extends _BaseModel implements Serializable  {

    public _Relatorio(Context ctx) {
        super(ctx);
    }

    public Cursor exibirAbastecimento(String veiculo)
    {
        try
        {
            String sql = "SELECT data, "
                    + "combustivel, preco_litro, valor_pago, "
                    + " round(valor_pago/preco_litro, 1) as litrosAbastecidos "
                    + " FROM abastecimento "
                    + " WHERE id_veiculo = ?"
                    + " ORDER BY datetime(data) DESC";

            String args[] = { veiculo };
            return buscarCursor(sql, args);
        } catch (Exception ex) {
            throw ex;
        }
    }

    public Cursor exibirConsumo(String veiculo)
    {
        try
        {
            String sql = "SELECT data, combustivel, km_percorridos, litros_gastos, "
                    + " round(km_percorridos/litros_gastos, 1) as autonomia "
                    + " FROM consumo "
                    + " WHERE id_veiculo = ?"
                    + " ORDER BY datetime(data) DESC";

            String args[] = { veiculo };
            return buscarCursor(sql, args);
        } catch (Exception ex) {
            throw ex;
        }

    }

    public Cursor exibirTotais()
    {
        try {
            String sql = "SELECT strftime('%Y-%m', a.data) as yr_mn, a.id_veiculo id_veiculo, a.combustivel combustivel, "
                    + " sum(a.valor_pago) as totalPago, "
                    + " round(sum(a.valor_pago/a.preco_litro), 1) as TotalLitros, sum(km_percorridos) as KM "
                    + " FROM abastecimento a "
                    + " INNER JOIN consumo m on m.data = a.data "
                    + " GROUP BY yr_mn, a.id_veiculo, a.combustivel "
                    + " UNION SELECT strftime('%Y', a.data) || '-Tot' as yr_mn, a.id_veiculo, a.combustivel, "
                    + " sum(a.valor_pago) as totalPago, "
                    + " round(sum(a.valor_pago/a.preco_litro), 1) as TotalLitros, sum(km_percorridos) as KM "
                    + " FROM abastecimento a "
                    + " INNER JOIN consumo m on m.data = a.data "
                    + " GROUP BY yr_mn, a.id_veiculo, a.combustivel "
                    + " UNION SELECT strftime('%Y', a.data) || '-Med' as yr_mn, a.id_veiculo, a.combustivel, "
                    + " sum(a.valor_pago) / count(distinct(strftime('%Y-%m', a.data))) as totalPago, "
                    + " round(sum(a.valor_pago/a.preco_litro) / count(distinct(strftime('%Y-%m', a.data))), 1) as TotalLitros, "
                    + " sum(km_percorridos) / count(distinct(strftime('%Y-%m', a.data))) as KM "
                    + " FROM abastecimento a "
                    + " INNER JOIN consumo m on m.data = a.data "
                    + " GROUP BY yr_mn, a.id_veiculo, a.combustivel "
                    + " ORDER BY yr_mn DESC";
            return buscarCursor(sql);
        } catch (Exception ex) {
            throw ex;
        }
    }
}
