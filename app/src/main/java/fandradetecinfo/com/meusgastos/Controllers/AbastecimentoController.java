package fandradetecinfo.com.meusgastos.Controllers;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import fandradetecinfo.com.meusgastos.MainActivity;
import fandradetecinfo.com.meusgastos.Models.Abastecimento;
import fandradetecinfo.com.meusgastos.R;


public class AbastecimentoController extends _BaseController {

    private Abastecimento model;

    private EditText etData;
    private Spinner spVeiculo;
    private Spinner spPosto;
    private Spinner spCombustivel;
    private EditText etPreco;
    private EditText etPago;
    private EditText etOdometro;
    public AbastecimentoController(Activity activity) {

        this.activity = activity;
        this.model = new Abastecimento(activity.getBaseContext());

        this.etData = (EditText) activity.findViewById(R.id.txtData);
        this.spVeiculo = (Spinner) activity.findViewById(R.id.spinnerVeiculo);
        this.spPosto = (Spinner) activity.findViewById(R.id.spinnerPosto);
        this.spCombustivel = (Spinner) activity.findViewById(R.id.spinnerCombustivel);
        this.etPreco = (EditText) activity.findViewById(R.id.txtPreco);
        this.etPago = (EditText) activity.findViewById(R.id.txtPago);
        this.etOdometro = (EditText) activity.findViewById(R.id.txtOdometro);
    }

    public Abastecimento getModel() {
        return model;
    }

    public void setModel(Abastecimento model) {
        this.model = model;
    }

    public void pegarDoFormulario()
    {
        model.setDataTratada(etData.getText().toString());
        model.setPreco_litro(etPreco.getText().toString());
        model.setValor_pago(etPago.getText().toString());
        model.setOdometro(etOdometro.getText().toString());
        model.setId_veiculo(String.valueOf(spVeiculo.getSelectedItemPosition()));
        model.setId_posto(String.valueOf(spPosto.getSelectedItemPosition()));
        model.setCombustivel(String.valueOf(spCombustivel.getSelectedItemPosition()));
    }

    public boolean validarDados()
    {
        if (!validarCampo(etData)) return false;
        if (!validarLista(spVeiculo, "Veículo", 1)) return false;
        if (!validarLista(spPosto, "Posto", 1)) return false;
        if (!validarLista(spCombustivel, "Combustível", 1)) return false;
        if (!validarCampo(etPreco)) return false;
        if (!validarCampo(etPago)) return false;
        return validarCampo(etOdometro);
    }

    public boolean registroExistente()
    {
        String sql = "SELECT count(*) FROM abastecimento "
                + " WHERE id_veiculo = ? "
                + " AND id_posto = ? "
                + " AND data = ?";
        String args[] = { model.getId_veiculo(), model.getId_posto(), model.getData() };

        model.open();

        boolean ret = model.exists(sql, args);

        model.close();

        return ret;
    }

    public void alertarRegistroAnteriorIdentico()
    {
        montarAlerta("Meu Peso Diário ->  Gravar", "Usuário com registro anterior idêntico!");
    }

    public void alertarRegistroExistente()
    {
        montarAlerta("Meu Peso Diário ->  Gravar", "Usuário/Data já possui registro!");
    }

    public void inserir()
    {

        ContentValues content = new ContentValues();
        content.put("data", model.getData());
        content.put("id_veiculo", model.getId_veiculo());
        content.put("id_posto", model.getId_posto());
        content.put("combustivel", model.getCombustivel());
        content.put("preco_litro", model.getPreco_litro());
        content.put("valor_pago", model.getValor_pago());
        content.put("odometro", model.getOdometro());

        model.open();

        model.insert(content);

        model.close();
    }

    public List<Abastecimento> getLista(Context ctx){

        List<Abastecimento> lstRegistro = new ArrayList<Abastecimento>();
        model.open();
        int maxRegistros = 6;
        int curRegistro = 1;
        try
        {
            Cursor c = model.exibirRegistros();

            while ((c.moveToNext())) {

                Abastecimento reg = new Abastecimento(ctx);

                reg.setId_veiculo(mapVeiculo.get(c.getInt(c.getColumnIndex("id_veiculo"))));
                reg.setId_posto(mapPosto.get(c.getInt(c.getColumnIndex("id_posto"))));
                reg.setData(c.getString(c.getColumnIndex("data")));
                reg.setCombustivel(mapCombustivel.get(c.getInt(c.getColumnIndex("combustivel"))));
                reg.setPreco_litro(c.getString(c.getColumnIndex("preco_litro")));
                reg.setValor_pago(c.getString(c.getColumnIndex("valor_pago")));
                reg.setOdometro(c.getString(c.getColumnIndex("odometro")));
                lstRegistro.add(reg);

                if (++curRegistro > maxRegistros) break;
            }
            c.close();

        }catch(Exception e)
        {
            Toast.makeText(activity, e.getMessage(), Toast.LENGTH_LONG).show();
            Log.i("LogX", e.getMessage());
        }
        model.close();

        return lstRegistro;
    }

    public void buscarUltimosDados()
    {
        model.open();
        Cursor c = model.buscarUltimosDados(MainActivity.veiculo);

        if(c.moveToFirst())
        {
            model.setId(c.getLong(0));
            model.setUltimoOdometro(c.getString(1));
        }
        else
        {
            model.setId(0);
            model.setUltimoOdometro("0");
        }
        c.close();
        model.close();
    }

//    public void buscarUltimoCombustivel()
//    {
//        model.open();
//        Cursor c = model.buscarUltimoCombustivel(MainActivity.veiculo);
//
//        if(c.moveToFirst())
//        {
//            model.setUltimoCombustivel(c.getString(0));
//        }
//        else
//        {
//            model.setUltimoCombustivel("0");
//        }
//        c.close();
//        model.close();
//    }

}
