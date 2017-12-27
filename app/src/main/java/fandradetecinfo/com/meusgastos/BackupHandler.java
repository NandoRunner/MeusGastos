package fandradetecinfo.com.meusgastos;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;

import fandradetecinfo.com.meusgastos.Models.Abastecimento;
import fandradetecinfo.com.meusgastos.Models.Consumo;


public class BackupHandler {

    private String bkpFile;

    public BackupHandler()
    {
        this.bkpFile = "mysdfile2.txt";
    }

    public void gravar(boolean arqExistente) {

        try {
            Log.i("LogX", "isExternalStorageAvailable() - >" + isExternalStorageAvailable());
            Log.i("LogX", "isExternalStorageReadOnly() - >" + isExternalStorageReadOnly());

            File myFile = new File(Environment.getExternalStorageDirectory(), this.bkpFile);
            OutputStreamWriter osw =
                    new OutputStreamWriter(new FileOutputStream(myFile, arqExistente));

            osw.append(" -> XPTO123\n");
            osw.close();

//            Toast.makeText(getBaseContext(),
//                    "Done writing SD 'mysdfile.txt'",
//                    Toast.LENGTH_SHORT).show();
            Log.i("LogX", "Done writing SD 'mysdfile.txt'");
        }
        catch (Exception e)
        {
            Log.i("LogX", e.getMessage());
        }
    }

    public void carregar()
    {
        String lstrlinha;
        try
        {
            File arq = new File(Environment.getExternalStorageDirectory(), this.bkpFile);
            BufferedReader br = new BufferedReader(new FileReader(arq));

            while ((lstrlinha = br.readLine()) != null)
            {
                Log.i("LogX", lstrlinha);
            }
        }
        catch (Exception e)
        {
            Log.i("LogX", e.getMessage());
        }
    }

    private static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    private static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            return true;
        }
        return false;
    }
}
