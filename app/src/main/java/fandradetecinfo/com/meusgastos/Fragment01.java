package fandradetecinfo.com.meusgastos;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Fragment01 extends Fragment 
{
	private String combustivel;
	private String preco_litro;
	private String valor_pago;
	private String odometro;
	private String litros_gastos;
	
    private DBMeusGastos handler;
    private View vw;
    private Context ctx;

    PrefsHandler prefs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vw = inflater.inflate(R.layout.frag_01, container, false);
        ctx = getActivity().getBaseContext();
        prefs = new PrefsHandler(ctx);



        Spinner mySpn = (Spinner) vw.findViewById(R.id.spinnerVeiculo);
        mySpn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
                MainActivity.veiculo = String.valueOf(position);
                prefs.carregar(vw, MainActivity.veiculo);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // sometimes you need nothing here
            }
        });

        EditText myTxt = (EditText) vw.findViewById(R.id.txtData);
        android.text.format.DateFormat df = new android.text.format.DateFormat();
        myTxt.setText(df.format("dd/MM/yyyy", new Date()));
        myTxt.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
             @Override
             public void onFocusChange(View v1, boolean hasFocus)
             {
                 tratarData(vw, hasFocus);
             }
        });

        Button myBtn = (Button) vw.findViewById(R.id.btnGravar);
        myBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v1)
            {
                gravarRegistro(vw);
            }
        });
        myBtn.setFocusableInTouchMode(true);
        myBtn.requestFocus();
        return vw;
    }
    private void tratarData(View v1, boolean hasFocus)
    {
        if (hasFocus)
        {
            Calendar cal = Calendar.getInstance(TimeZone.getDefault()); // Get current date

            DatePickerDialog datePicker = new DatePickerDialog(this.getActivity(),
                    R.style.AppTheme, datePickerListener,
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH));

            datePicker.setCancelable(false);
            datePicker.setTitle("Selecione a data");
            datePicker.getWindow().setLayout(600, 1000);
            datePicker.show();
        }
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener()
    {
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            String year1 = String.valueOf(selectedYear);
            String month1 = String.valueOf(selectedMonth + 1);
            String day1 = String.valueOf(selectedDay);
            EditText tvDt = (EditText) vw.findViewById(R.id.txtData);
            tvDt.setText(day1 + "/" + month1 + "/" + year1);
        }
    };

	private String tratarData(String data)
    {
        return data.substring(6, 10) + "-" + data.substring(3, 5) + "-" + data.substring(0, 2);
    }
	
    private void gravarRegistro(View v1) 
	{

        try
        {
	        String data = ((EditText) v1.findViewById(R.id.txtData)).getText().toString();
			data = tratarData(data);
	        String veiculo = String.valueOf(((Spinner) v1.findViewById(R.id.spinnerVeiculo)).getSelectedItemPosition());
	        String posto = String.valueOf(((Spinner) v1.findViewById(R.id.spinnerPosto)).getSelectedItemPosition());
            
			if(registroExistente(veiculo, posto, data)) {
                alertarRegistroExistente();
                return;
            }

            prepararDados(v1);

	        /*
			if(prefs.registroAnteriorIdentico(veiculo, posto, combustivel, preco_litro, valor_pago, odometro, litros_gastos)) {
                alertarRegistroAnteriorIdentico();
                return;
            }
			*/
			
			prefs.salvar(veiculo, posto, combustivel, preco_litro, valor_pago, odometro, litros_gastos);

	        handler = new DBMeusGastos(getActivity().getBaseContext());//getting the context object
	        long id = handler.inserirDados(data, veiculo, posto, combustivel, preco_litro, valor_pago,
                    odometro, litros_gastos);

	        Toast.makeText(getActivity(), "Registro gravado!", Toast.LENGTH_SHORT).show();
	        Log.i("LogX", "Registro gravado!");
		}
		catch (Exception e)
        {
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
            Log.i("LogX", e.getMessage());
        }
    }


    public static Fragment01 newInstance(String text) {

        Fragment01 f = new Fragment01();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }

    private void prepararDados(View v1)
    {
		combustivel = String.valueOf(((Spinner) v1.findViewById(R.id.spinnerCombustivel)).getSelectedItemPosition());
        preco_litro = ((EditText) v1.findViewById(R.id.txtPreco)).getText().toString().replace(',', '.');
        valor_pago = ((EditText) v1.findViewById(R.id.txtPago)).getText().toString().replace(',', '.');
        odometro = ((EditText) v1.findViewById(R.id.txtOdometro)).getText().toString();
        litros_gastos = ((EditText) v1.findViewById(R.id.txtLitrosGastos)).getText().toString().replace(',', '.');
	}

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser) {
            Activity a = getActivity();
            if(a != null) a.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    private void alertarRegistroAnteriorIdentico()
    {
        montarAlerta("Meus Gastos ->  Gravar", "Veículo com registro anterior idêntico!");
    }
	
    private void alertarRegistroExistente()
    {
        montarAlerta("Meus Gastos ->  Gravar", "Veículo/Posto/Data já possui registro!");
    }

    private void montarAlerta(String titulo, String mensagem)
    {
        AlertDialog.Builder builderAlerta = new AlertDialog.Builder(getActivity());
        builderAlerta.setTitle(titulo);
        builderAlerta.setMessage(mensagem);
        //builderAlerta.setIcon(R.drawable.heart_monitor_48px);

        builderAlerta.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.i("LogX","Clicou no Ok!");
            }
        });

        AlertDialog meuAlerta = builderAlerta.create();
        meuAlerta.show();
    }
	
    private boolean registroExistente(String veiculo, String posto, String data)
    {
        boolean ret = false;
        try {
            handler=new DBMeusGastos(getActivity().getBaseContext());//getting the context object
            if (!handler.buscarRegistro(veiculo, posto, data).equals("0")) {
                ret = true;
            }
        }
        catch (Exception e)
        {
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
            Log.i("LogX", e.getMessage());
        }

        return ret;
    }

}
