package fandradetecinfo.com.meusgastos.Views;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


import fandradetecinfo.com.meusgastos.Controllers._RelatorioController;
import fandradetecinfo.com.meusgastos.MainActivity;
import fandradetecinfo.com.meusgastos.R;
import fandradetecinfo.com.meusgastos.Relatorio;

public class Fragment02 extends Fragment {

    private _RelatorioController controller;
    private View vw;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        vw = inflater.inflate(R.layout.frag_02, container, false);
		
		this.controller = new _RelatorioController(getActivity());
				
        return vw;
    }

    public static Fragment02 newInstance(String text) {

        Fragment02 f = new Fragment02();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser) {
            Activity a = getActivity();
            if(a != null)
            {
                a.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                this.montarGrid();
                Log.i("LogX", "Frag 02 - Carregou grid");
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void montarGrid()
    {
        List<String> listHeader = new ArrayList<String>();
        listHeader.add("Data");
        //listHeader.add("Posto");
        listHeader.add("Tipo");
        listHeader.add("(R$) / Litro");
        listHeader.add("(R$)");
        listHeader.add("Litros");

        GridView gridViewHeader=(GridView) vw.findViewById(R.id.grvAbastecimentoHeader);

        GridView gridView=(GridView) vw.findViewById(R.id.grvAbastecimento);
        controller.carregarGrid(listHeader, gridViewHeader, gridView, Relatorio.Abastecimentos);
	}
}
