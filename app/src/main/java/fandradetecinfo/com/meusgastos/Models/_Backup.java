package fandradetecinfo.com.meusgastos.Models;

import android.content.Context;
import android.database.Cursor;

import java.io.Serializable;


public class _Backup extends _BaseModel implements Serializable  {

    public _Backup(Context ctx) {
        super(ctx);
    }

    public Cursor exibirAbastecimento()
    {
        try
        {
            String sql = "SELECT * FROM abastecimento ORDER BY id";

            return buscarCursor(sql);
        } catch (Exception ex) {
            throw ex;
        }
    }

    public Cursor exibirConsumo(String veiculo)
    {
        try
        {
            String sql = "SELECT a.data, a.combustivel, c.km_percorridos, c.litros_gastos, "
                    //+ " c.id_abastecimento as autonomia "
                    + " round(c.km_percorridos/c.litros_gastos, 1) as autonomia "
                    + " FROM abastecimento a "
                    + " INNER JOIN consumo c on c.id_abastecimento = a.id "
                    + " WHERE a.id_veiculo = ?"
                    + " ORDER BY datetime(a.data) DESC";

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
                    + " INNER JOIN consumo m on m.id_abastecimento = a.id "
                    + " GROUP BY yr_mn, a.id_veiculo, a.combustivel "
                + " UNION SELECT strftime('%Y', a.data) || '-Tot' as yr_mn, a.id_veiculo, a.combustivel, "
                    + " sum(a.valor_pago) as totalPago, "
                    + " round(sum(a.valor_pago/a.preco_litro), 1) as TotalLitros, sum(km_percorridos) as KM "
                    + " FROM abastecimento a "
//                    + " INNER JOIN consumo m on m.data = a.data "
                    + " INNER JOIN consumo m on m.id_abastecimento = a.id "
                    + " GROUP BY yr_mn, a.id_veiculo, a.combustivel "
                + " UNION SELECT strftime('%Y', a.data) || '-Med' as yr_mn, a.id_veiculo, a.combustivel, "
                    + " sum(a.valor_pago) / count(distinct(strftime('%Y-%m', a.data))) as totalPago, "
                    + " round(sum(a.valor_pago/a.preco_litro) / count(distinct(strftime('%Y-%m', a.data))), 1) as TotalLitros, "
                    + " sum(km_percorridos) / count(distinct(strftime('%Y-%m', a.data))) as KM "
                    + " FROM abastecimento a "
                    + " INNER JOIN consumo m on m.id_abastecimento = a.id "
                    + " GROUP BY yr_mn, a.id_veiculo, a.combustivel "
                + " UNION SELECT strftime('%Y', a.data) || '-Tot' as yr_mn, a.id_veiculo, 0 as combustivel, "
                    + " sum(a.valor_pago) as totalPago, "
                    + " round(sum(a.valor_pago/a.preco_litro), 1) as TotalLitros, sum(km_percorridos) as KM "
                    + " FROM abastecimento a "
//                    + " INNER JOIN consumo m on m.data = a.data "
                    + " INNER JOIN consumo m on m.id_abastecimento = a.id "
                    + " GROUP BY yr_mn, a.id_veiculo "
                    + " ORDER BY yr_mn DESC";
            return buscarCursor(sql);
        } catch (Exception ex) {
            throw ex;
        }
    }

    public Cursor exibirCompara()
    {
        try {
            String sql = "SELECT strftime('%Y', a.data) || '-Tot' as yr_mn, a.id_veiculo, a.combustivel, "
                    + " round(sum(km_percorridos) / sum (m.litros_gastos), 1) as autonomia, "
                    + " round(sum(a.preco_litro * m.litros_gastos) / sum(m.km_percorridos), 3) as Reais_KM, "
                    + " round(sum(m.km_percorridos) / sum(m.km_percorridos / m.km_hora), 1) as KM_hora "
                    + " FROM abastecimento a "
//                    + " INNER JOIN consumo m on m.data = a.data "
                    + " INNER JOIN consumo m on m.id_abastecimento = a.id "
                    + " WHERE m.km_percorridos <> 0 AND m.litros_gastos <> 0 "
                    + " GROUP BY yr_mn, a.id_veiculo, a.combustivel "
                      + " ORDER BY yr_mn DESC";
            return buscarCursor(sql);
        } catch (Exception ex) {
            throw ex;
        }
        //
    }
}
