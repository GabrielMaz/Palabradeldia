package com.palabradeldia.palabradeldia.services;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.Toast;

import com.palabradeldia.palabradeldia.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Usuario on 04/06/2017.
 */

public class SendVoteService extends AsyncTask<String,String,String> {

    private Activity activity;
    private HttpURLConnection conn;
    private URL url = null;
    private String id;
    private String accion;

    public SendVoteService(Activity activity, String id, String accion) {
        this.activity = activity;
        this.id = id;
        this.accion = accion;
    }

    @Override
    protected void onPreExecute(){
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params){
        try{
            url = new URL("http://nandorak.esy.es/palabraDelDia/app/votar.php");
        }catch (MalformedURLException e){
            e.printStackTrace();
            return "exception";
        }
        try{
            //SETUP HTTPURLCONENNCTION CLASS TO SEND AND RECEIVE DATA FROM PHP AND MYSQL
            conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("POST");

            conn.setDoInput(true);
            conn.setDoOutput(true);

            //Agregar parametros al URL

            Uri.Builder builder = new Uri.Builder()
            .appendQueryParameter("id",id)
            .appendQueryParameter("accion",accion);
            String query = builder.build().getEncodedQuery();

            //open connection for sending data
            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
            writer.write(query);
            writer.flush();
            writer.close();
            os.close();
            conn.connect();
        }catch (IOException e1){
            e1.printStackTrace();
            return "exception";
        }

        try{
            int response_code = conn.getResponseCode();

            //chequear conexion satisfactoria
            if(response_code == HttpURLConnection.HTTP_OK){
            //Read data sent from server
            InputStream input = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            StringBuilder result = new StringBuilder();
            String line;

            while ((line = reader.readLine())!=null){
            result.append(line);
            }

            //pass data to onPostExecute method
            return result.toString();
        }else{
            return "unsuccessful";
        }
        }catch (IOException e){
            e.printStackTrace();
            return "exception";
        }finally {
            conn.disconnect();
        }
    }

    @Override
    protected void onPostExecute(String result){
        if(result.equalsIgnoreCase("success")){
        Toast.makeText(activity, R.string.service_send_success_message, Toast.LENGTH_SHORT).show();
        }else if(result.equalsIgnoreCase("fail")){
        Toast.makeText(activity, R.string.service_send_fail_message,Toast.LENGTH_SHORT).show();
        }
    }
}
