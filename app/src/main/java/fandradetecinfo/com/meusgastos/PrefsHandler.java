package fandradetecinfo.com.meusgastos;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import fandradetecinfo.com.meusgastos.R;

import fandradetecinfo.com.meusgastos.Models.Abastecimento;



public class PrefsHandler {

    private String MINHAS_PREFS = "MEUSGASTOS_PREFS";

    Context context;
    SharedPreferences shp;

    public PrefsHandler(Context ctx) {
        this.context = ctx;
        shp = context.getSharedPreferences(MINHAS_PREFS, 0);
    }

    public void carregar(View v, String veiculo) {

        ((EditText) v.findViewById(R.id.txtPreco)).setText(String.format("%.3f", shp.getFloat("preco_litro"+ veiculo, 0)));
        ((EditText) v.findViewById(R.id.txtPago)).setText(String.format("%.2f", shp.getFloat("valor_pago"+ veiculo, 0)));
        ((EditText) v.findViewById(R.id.txtOdometro)).setText(String.valueOf(shp.getInt("odometro"+ veiculo, 0)));
        ((EditText) v.findViewById(R.id.txtLitrosGastos)).setText(String.format("%.1f", shp.getFloat("litros_gastos"+ veiculo, 0)));

        int p_posto = shp.getInt("posto"+ veiculo, 0);
        int p_combustivel = shp.getInt("combustivel"+ veiculo, 0);

        ((Spinner) v.findViewById(R.id.spinnerPosto)).setSelection(p_posto);
        ((Spinner) v.findViewById(R.id.spinnerCombustivel)).setSelection(p_combustivel);

    }

    public void salvar(Abastecimento abastecimento, String litros_gastos)
    {
        String u = abastecimento.getId_veiculo();
        SharedPreferences.Editor editor = shp.edit();
        editor.putInt("posto"+u, Integer.parseInt(abastecimento.getId_posto()));
        editor.putInt("combustivel"+u, Integer.parseInt(abastecimento.getCombustivel()));
        editor.putFloat("preco_litro"+u, Float.parseFloat(abastecimento.getPreco_litro()));
        editor.putFloat("valor_pago"+u, Float.parseFloat(abastecimento.getValor_pago()));
        editor.putInt("odometro"+u, Integer.parseInt(abastecimento.getOdometro()));
        editor.putFloat("litros_gastos"+u, Float.parseFloat(litros_gastos));
        editor.apply();
    }

    public boolean registroAnteriorIdentico(Abastecimento abastecimento)
    {
        String u = abastecimento.getId_veiculo();
        if (!abastecimento.getId_posto().equals(shp.getInt("posto"+u, 0)))
            return false;
        else if (!abastecimento.getCombustivel().equals(shp.getInt("combustivel"+u, 0)))
            return false;
        else if (!abastecimento.getOdometro().equals(shp.getInt("odometro"+u, 0)))
            return false;

        return true;
    }

}
