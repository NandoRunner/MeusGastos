package fandradetecinfo.com.meusgastos.Views;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import fandradetecinfo.com.meusgastos.R;

public class Fragment03 extends Fragment {

    private View view;
    private Map<Integer, String> mapCombustivel = new Hashtable<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mapCombustivel.put(1, "Gasolina");
        mapCombustivel.put(2, "Etanol");
        mapCombustivel.put(3, "GNV");

        view = inflater.inflate(R.layout.frag_03, container, false);
        return view;
    }

    public static Fragment03 newInstance(String text) {

        Fragment03 f = new Fragment03();
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
                this.criarHeader();
                this.carregarGrid();
            }
        }
    }

    private void criarHeader()
    {
        List<String> listHeader = new ArrayList<String>();
        listHeader.add("Data");
        listHeader.add("Veículo");
        listHeader.add("Combustivel");
        listHeader.add("Km");
        listHeader.add("Litros");
        listHeader.add("Autonomia");

        GridView gridViewHeader=(GridView) view.findViewById(R.id.grvConsumoHeader);
        gridViewHeader.setAdapter(null);
        gridViewHeader.setAdapter(new ArrayAdapter<String>(getActivity().getBaseContext(), R.layout.cell, listHeader));
    }

    private void carregarGrid()
    {
        GridView gridView=(GridView) view.findViewById(R.id.grvConsumo);
        gridView.setAdapter(null);
        ArrayList<String> list=new ArrayList<String>();
        ArrayAdapter adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,list);

//        DBMeusGastos handler=new DBMeusGastos(getActivity().getBaseContext());//getting the context object
//        try
//        {
//            //Cursor c=handler.UpdateData();
//            Cursor c = handler.exibirConsumo();
//
//            if(c.moveToFirst())
//            {
//                do
//                {
//                    list.add(c.getString(1));
//                    list.add(c.getString(2));
//                    list.add(mapCombustivel.get(c.getInt(c.getColumnIndex("combustivel"))));
//                    list.add(c.getString(c.getColumnIndex("km_percorridos")));
//                    list.add(String.format("%.1f", c.getFloat(c.getColumnIndex("litros_gastos"))));
//                    list.add(String.format("%.1f", c.getFloat(c.getColumnIndex("autonomia"))));
//                    gridView.setAdapter(adapter);
//                }while(c.moveToNext());
//            }
//            else
//            {
//                Toast.makeText(getActivity(), "No data found", Toast.LENGTH_LONG).show();
//            }
//        }catch(Exception e)
//        {
//            Toast.makeText(getActivity(), "No data found"+e.getMessage(), Toast.LENGTH_LONG).show();
//        }
   }
}
