package fandradetecinfo.com.meusgastos;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Fernando on 22/03/2018.
 */

public class BackgroundWorker extends AsyncTask<String, Void, String> {

    Context context;
    AlertDialog alertDialog;

    BackgroundWorker(Context ctx)
    {
        context = ctx;
    }
    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
        String login_url = "http://nandorunner1-001-site1.htempurl.com/login.php";
        boolean tipoValido = false;
        String post_data = "";
        if (type.equals("veiculo"))
        {
            tipoValido = true;
            String vei_veiculo_id = params[1];
            String vei_modelo = params[2];
            String vei_fabricante = params[3];
            String vei_ano = params[4];
            String vei_ativo = params[5];
            String vei_usu_usuario_id = params[6];

            try {
                post_data = URLEncoder.encode("vei_veiculo_id", "UTF-8")+"="+URLEncoder.encode(vei_veiculo_id, "UTF-8")+"&"
                    +URLEncoder.encode("vei_modelo", "UTF-8")+"="+URLEncoder.encode(vei_modelo, "UTF-8")+"&"
                    +URLEncoder.encode("vei_fabricante", "UTF-8")+"="+URLEncoder.encode(vei_fabricante, "UTF-8")+"&"
                    +URLEncoder.encode("vei_ano", "UTF-8")+"="+URLEncoder.encode(vei_ano, "UTF-8")+"&"
                    +URLEncoder.encode("vei_ativo", "UTF-8")+"="+URLEncoder.encode(vei_ativo, "UTF-8")+"&"
                    +URLEncoder.encode("vei_usu_usuario_id", "UTF-8")+"="+URLEncoder.encode(vei_usu_usuario_id, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
       }

        if (tipoValido)
        {
            try {
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter  bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result="";
                String line;
                while ((line = bufferedReader.readLine())!=null)
                {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Login Status");
    }

    @Override
    protected void onPostExecute(String result) {
        alertDialog.setMessage(result);
        alertDialog.show();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
