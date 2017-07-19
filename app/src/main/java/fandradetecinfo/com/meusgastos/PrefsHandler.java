package fandradetecinfo.com.meusgastos;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import fandradetecinfo.com.meusgastos.R;

/**
 * Created by Fernando on 29/12/2016.
 */

public class PrefsHandler {

    private String MINHAS_PREFS = "MEUSGASTOS_PREFS";

    Context context;
    SharedPreferences shp;

    public PrefsHandler(Context ctx) {
        this.context = ctx;
        shp = context.getSharedPreferences(MINHAS_PREFS, 0);
    }

    public void carregar(View v, String veiculo) {
        String u = veiculo;

        ((EditText) v.findViewById(R.id.txtPreco)).setText(String.format("%.3f", shp.getFloat("preco_litro"+u, 0)));
        ((EditText) v.findViewById(R.id.txtPago)).setText(String.format("%.2f", shp.getFloat("valor_pago"+u, 0)));
        ((EditText) v.findViewById(R.id.txtOdometro)).setText(String.valueOf(shp.getInt("odometro"+u, 0)));
        ((EditText) v.findViewById(R.id.txtLitrosGastos)).setText(String.format("%.1f", shp.getFloat("litros_gastos"+u, 0)));

        int p_posto = shp.getInt("posto"+u, 0);
        int p_combustivel = shp.getInt("combustivel"+u, 0);

        ((Spinner) v.findViewById(R.id.spinnerVeiculo)).setSelection(Integer.parseInt(veiculo));
        ((Spinner) v.findViewById(R.id.spinnerPosto)).setSelection(p_posto);
        ((Spinner) v.findViewById(R.id.spinnerCombustivel)).setSelection(p_combustivel);

    }

    public void salvar(String veiculo, String posto, String combustivel, String preco_litro,
                             String valor_pago, String odometro, String litros_gastos)
    {
        String u = veiculo;
        SharedPreferences.Editor editor = shp.edit();
        editor.putInt("posto"+u, Integer.parseInt(posto));
        editor.putInt("combustivel"+u, Integer.parseInt(combustivel));
        editor.putFloat("preco_litro"+u, Float.parseFloat(preco_litro));
        editor.putFloat("valor_pago"+u, Float.parseFloat(valor_pago));
        editor.putInt("odometro"+u, Integer.parseInt(odometro));
        editor.putFloat("litros_gastos"+u, Float.parseFloat(litros_gastos));
        editor.commit();
    }

    public boolean registroAnteriorIdentico(String usuario, String peso, String gordura,
                                            String hidratacao, String musculo, String osso)
    {
        String u = usuario;
        if (!peso.equals(String.format("%.1f", shp.getFloat("peso"+u, 0)).replace(',', '.')))
            return false;
        else if (!gordura.equals(String.format("%.1f", shp.getFloat("gordura"+u, 0)).replace(',', '.')))
            return false;
        else if (!hidratacao.equals(String.format("%.1f", shp.getFloat("hidratacao"+u, 0)).replace(',', '.')))
            return false;
        else if (!musculo.equals(String.format("%.1f", shp.getFloat("musculo"+u, 0)).replace(',', '.')))
            return false;
        else if (!osso.equals(String.format("%.1f", shp.getFloat("osso"+u, 0)).replace(',', '.')))
            return false;

        return true;
    }

}
