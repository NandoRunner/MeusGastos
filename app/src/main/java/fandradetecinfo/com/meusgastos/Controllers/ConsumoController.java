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

//    private EditText etData;
//    private Spinner spVeiculo;
    private EditText etLitrosGastos;
    private EditText etKmHora;

    public ConsumoController(Activity activity) {

        this.activity = activity;
        this.model = new Consumo(activity.getBaseContext());

//        this.etData = (EditText) activity.findViewById(R.id.txtData);
//        this.spVeiculo = (Spinner) activity.findViewById(R.id.spinnerVeiculo);
        this.etLitrosGastos = (EditText) activity.findViewById(R.id.txtLitrosGastos);
        this.etKmHora = (EditText) activity.findViewById(R.id.txtKmHora);

    }

    public Consumo getModel() {
        return model;
    }

    public void setModel(Consumo model) {
        this.model = model;
    }

    public void pegarDoFormulario()
    {
//        model.setDataTratada(etData.getText().toString());
//        model.setId_veiculo(String.valueOf(spVeiculo.getSelectedItemPosition()));
        model.setLitros_gastos(etLitrosGastos.getText().toString());
        model.setKm_hora(etKmHora.getText().toString());
    }

    public boolean validarDados()
    {
//        if (!validarCampo(etData)) return false;
//        if (!validarLista(spVeiculo, "Veículo", 1)) return false;
        if (!validarCampo(etLitrosGastos)) return false;
        return validarCampo(etKmHora);
    }

    public void calcularKmPercorridos(String odometro, String ultimoOdometro)
    {
        int dif = Integer.parseInt(odometro) - Integer.parseInt(ultimoOdometro);
        model.setKm_percorridos(String.valueOf(dif));
    }

    public void inserir()
    {
        ContentValues content = new ContentValues();
//        content.put("data", model.getData());
//        content.put("id_veiculo", model.getId_veiculo());
//        content.put("combustivel", model.getCombustivel());
        content.put("id_abastecimento", model.getId_abastecimento());
        content.put("km_percorridos", model.getKm_percorridos());
        content.put("litros_gastos", model.getLitros_gastos());
        content.put("km_hora", model.getKm_hora());

        model.open();

        model.insert(content);

        model.close();
    }

}
