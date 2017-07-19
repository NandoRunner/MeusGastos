package fandradetecinfo.com.meusgastos;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class Fragment04 extends Fragment {

    private View view;
    private Map<Integer, String> mapCombustivel = new Hashtable<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mapCombustivel.put(1, "Gasolina");
        mapCombustivel.put(2, "Etanol");
        mapCombustivel.put(3, "GNV");

        view = inflater.inflate(R.layout.frag_04, container, false);
        return view;
    }

    public static Fragment04 newInstance(String text) {

        Fragment04 f = new Fragment04();
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
        listHeader.add("Período");
        listHeader.add("Veículo");
        listHeader.add("Combustivel");
        listHeader.add("Pago");
        listHeader.add("Litros");
        listHeader.add("KM");

        GridView gridViewHeader=(GridView) view.findViewById(R.id.grvTotaisHeader);
        gridViewHeader.setAdapter(null);
        gridViewHeader.setAdapter(new ArrayAdapter<String>(getActivity().getBaseContext(), R.layout.cell, listHeader));
    }

    private void carregarGrid()
    {
        GridView gridView=(GridView) view.findViewById(R.id.grvTotais);
        gridView.setAdapter(null);
        ArrayList<String> list=new ArrayList<String>();
        ArrayAdapter adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,list);

        DBMeusGastos handler=new DBMeusGastos(getActivity().getBaseContext());//getting the context object
        try
        {
            //Cursor c=handler.UpdateData();
            Cursor c = handler.exibirTotais();

            if(c.moveToFirst())
            {
                do
                {
                    list.add(c.getString(0));
                    list.add(c.getString(1));
                    list.add(mapCombustivel.get(c.getInt(2)));
                    list.add(String.format("%.2f", c.getFloat(3)));
                    list.add(String.format("%.1f", c.getFloat(4)));
                    list.add(String.format("%.0f", c.getFloat(5)));
                    gridView.setAdapter(adapter);
                }while(c.moveToNext());
            }
            else
            {
                Toast.makeText(getActivity(), "No data found", Toast.LENGTH_LONG).show();
            }
        }catch(Exception e)
        {
            Toast.makeText(getActivity(), "No data found"+e.getMessage(), Toast.LENGTH_LONG).show();
        }
   }
}
