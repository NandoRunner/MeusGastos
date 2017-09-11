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
import java.util.List;

import fandradetecinfo.com.meusgastos.Models.Abastecimento;
import fandradetecinfo.com.meusgastos.Models.Consumo;
import fandradetecinfo.com.meusgastos.R;


public class ConsumoController extends _BaseController {

    private Consumo model;

    private EditText etData;
    private Spinner spVeiculo;
    private Spinner spCombustivel;
    private EditText etLitrosGastos;

    public ConsumoController(Activity activity) {

        this.activity = activity;
        this.model = new Consumo(activity.getBaseContext());

        this.etData = (EditText) activity.findViewById(R.id.txtData);
        this.spVeiculo = (Spinner) activity.findViewById(R.id.spinnerVeiculo);
        //this.spCombustivel = (Spinner) activity.findViewById(R.id.spinnerCombustivel);
        this.etLitrosGastos = (EditText) activity.findViewById(R.id.txtLitrosGastos);

    }

    public Consumo getModel() {
        return model;
    }

    public void setModel(Consumo model) {
        this.model = model;
    }

    public void pegarDoFormulario()
    {
        model.setDataTratada(etData.getText().toString());
        model.setId_veiculo(String.valueOf(spVeiculo.getSelectedItemPosition()));
        //model.setCombustivel(String.valueOf(spCombustivel.getSelectedItemPosition()));
        model.setLitros_gastos(etLitrosGastos.getText().toString());
    }

    public boolean validarDados()
    {
        if (!validarCampo(etData)) return false;
        if (!validarLista(spVeiculo, "Veículo", 1)) return false;
        //if (!validarLista(spCombustivel, "Combustível", 1)) return false;
        return validarCampo(etLitrosGastos);
    }

    public void calcularKmPercorridos(String odometro, String ultimoOdometro)
    {
        int dif = Integer.parseInt(odometro) - Integer.parseInt(ultimoOdometro);
        model.setKm_percorridos(String.valueOf(dif));
    }

    public void buscarUltimoCombustivel(String ultimoCombustivel)
    {
        model.setCombustivel(ultimoCombustivel);
    }

    public void inserir()
    {

        ContentValues content = new ContentValues();
        content.put("data", model.getData());
        content.put("id_veiculo", model.getId_veiculo());
        content.put("combustivel", model.getCombustivel());
        content.put("km_percorridos", model.getKm_percorridos());
        content.put("litros_gastos", model.getLitros_gastos());

        model.open();

        model.insert(content);

        model.close();
    }

}
