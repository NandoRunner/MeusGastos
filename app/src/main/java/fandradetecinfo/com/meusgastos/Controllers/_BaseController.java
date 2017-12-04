package fandradetecinfo.com.meusgastos.Controllers;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Hashtable;
import java.util.Map;

import fandradetecinfo.com.meusgastos.R;

public class _BaseController {

    protected Activity activity;

    protected Map<Integer, String> mapPosto = new Hashtable<>();
    protected Map<Integer, String> mapVeiculo = new Hashtable<>();
    protected Map<Integer, String> mapCombustivel = new Hashtable<>();

    public _BaseController()
    {
        mapVeiculo.put(1, "S");

        mapPosto.put(1, "W");
        mapPosto.put(2, "O");

        mapCombustivel.put(0, "TUDO");
        mapCombustivel.put(1, "G");
        mapCombustivel.put(2, "E");
        mapCombustivel.put(3, "GNV");
        mapCombustivel.put(4, "GA");
    }

    protected boolean validarCampo(EditText edt) {
        if (edt.getText().toString().length() == 0) {
            Toast.makeText(activity, edt.getHint().toString() + " em branco",
                    Toast.LENGTH_LONG).show();
            edt.setError(edt.getHint().toString() + " em branco");
            return false;
        }
        return true;
    }

    protected boolean validarLista(Spinner spn, String nome, int minSeletion) {
        if(spn.getSelectedItemPosition() < minSeletion)
        {
            Toast.makeText(activity, nome + " em branco", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    protected void montarAlerta(String titulo, String mensagem)
    {
        AlertDialog.Builder builderAlerta = new AlertDialog.Builder(activity, R.style.MyDialogTheme);
        builderAlerta.setTitle(titulo);
        builderAlerta.setMessage(mensagem);
        builderAlerta.setIcon(R.drawable.heart_monitor_48px);

        builderAlerta.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.i("LogX","Clicou no Ok!");
            }
        });

        AlertDialog meuAlerta = builderAlerta.create();
        meuAlerta.show();
    }



}
