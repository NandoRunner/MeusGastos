package fandradetecinfo.com.meusgastos.Controllers;

import android.app.Activity;
import android.database.Cursor;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import fandradetecinfo.com.meusgastos.MainActivity;
import fandradetecinfo.com.meusgastos.Models._Relatorio;
import fandradetecinfo.com.meusgastos.R;
import fandradetecinfo.com.meusgastos.Relatorio;

public class _RelatorioController extends _BaseController {

    private _Relatorio model;

    public _RelatorioController(Activity activity) {

        this.activity = activity;
        this.model = new _Relatorio(activity.getBaseContext());
    }

    public void carregarGrid(List<String> listHeader, GridView gridViewHeader, GridView gridView, Relatorio rel)
    {
        gridViewHeader.setAdapter(null);
        gridViewHeader.setAdapter(new ArrayAdapter<String>(activity.getBaseContext(), R.layout.cell, listHeader));

        gridView.setAdapter(null);
        ArrayList<String> list=new ArrayList<String>();
        ArrayAdapter adapter=new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item,list);

        model.open();
        try
        {
            Cursor c;
            int maxcol = 5;

            switch (rel)
            {
                case Abastecimentos:
                    c = model.exibirAbastecimento(MainActivity.veiculo);
                    break;

                case Consumos:
                    c = model.exibirConsumo(MainActivity.veiculo);
                    break;

                default:
                    c = model.exibirTotais();
                    maxcol = 6;
                    break;
            }

            if(c.moveToFirst())
            {
                do
                {
                    for (int i = 0; i < maxcol; i++) {

						if (c.getColumnName(i).equals("combustivel"))
							list.add(mapCombustivel.get(Integer.valueOf(c.getString(i))));
                        else if (c.getColumnName(i).equals("id_veiculo"))
                            list.add(mapVeiculo.get(Integer.valueOf(c.getString(i))));
						else
                             list.add(c.getString(i));
                    }
                    gridView.setAdapter(adapter);
                }while(c.moveToNext());
            }
            else
            {
                Toast.makeText(activity, "Nenhum registro encontrado", Toast.LENGTH_SHORT).show();
                Log.i("LogX", "Nenhum registro encontrado");
            }
        }catch(Exception e)
        {
            Toast.makeText(activity, e.getMessage(), Toast.LENGTH_LONG).show();
            Log.i("LogX", e.getMessage());
        }
        model.close();
    }


}

