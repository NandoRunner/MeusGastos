package fandradetecinfo.com.meusgastos;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fandradetecinfo.com.meusgastos.Models.Abastecimento;


public class RegistroAdapter extends BaseAdapter {

    private final List<Abastecimento> registros;
    private final Activity activity;

    public RegistroAdapter(List<Abastecimento> registros, Activity activity) {
        this.registros = registros;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return this.registros.size();
    }

    @Override
    public Object getItem(int position) {
        return this.registros.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.registros.get(position).getId();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View linha = convertView;

        Abastecimento registro = registros.get(position);

        if (linha == null)
        {
            linha = this.activity.getLayoutInflater().inflate(R.layout.cell_frag_01, parent, false);
        }

        TextView veiculo = (TextView) linha.findViewById(R.id.txtRegVeiculo);
        TextView posto = (TextView) linha.findViewById(R.id.txtRegPosto);
        TextView data = (TextView) linha.findViewById(R.id.txtRegData);
        TextView combustivel = (TextView) linha.findViewById(R.id.txtRegCombustivel);
        TextView preco = (TextView) linha.findViewById(R.id.txtRegPreco);
        TextView litros = (TextView) linha.findViewById(R.id.txtRegLitros);
        TextView pago = (TextView) linha.findViewById(R.id.txtRegPago);
        TextView odometro = (TextView) linha.findViewById(R.id.txtRegOdometro);

        ArrayList<TextView> lstTV = new ArrayList<>();

        lstTV.add(veiculo);
        lstTV.add(posto);
        lstTV.add(data);
        lstTV.add(combustivel);
        lstTV.add(preco);
        lstTV.add(litros);
        lstTV.add(pago);
        lstTV.add(odometro);

        int color = position % 2 != 0 ? R.color.colorWhite : R.color.colorBlack;

        for( TextView aux : lstTV )
        {
            aux.setTextColor(activity.getResources().getColor(color));
        }
        linha.setBackgroundColor(activity.getResources().getColor((position % 2 != 0 ?
                    R.color.colorPrimary : R.color.actionbar_fg_color)));

        veiculo.setText(registro.getId_veiculo());
        posto.setText(registro.getId_posto());
        data.setText(String.valueOf(registro.getData()));
        combustivel.setText(registro.getCombustivel());
        preco.setText(registro.getPreco_litro());
        litros.setText(registro.getLitros());
        pago.setText(registro.getValor_pago());
        odometro.setText(registro.getOdometro());

        return linha;
    }

}
