package com.example.elo14.ficherotexto2;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    private EditText et1;
    private EditText et2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et1=(EditText)findViewById(R.id.et1);
        et2=(EditText)findViewById(R.id.et2);
    }

    public void guardar(View v){
        String nomArchivo=et1.getText().toString();
        nomArchivo=nomArchivo.replace('/', '-');
        try{
            OutputStreamWriter archivo=new OutputStreamWriter(openFileOutput(nomArchivo, Activity.MODE_PRIVATE));
            archivo.write(et2.getText().toString());
            archivo.flush();
            archivo.close();
        } catch (Exception e){
            Toast.makeText(getApplicationContext(), "No se puede abrir archivo", Toast.LENGTH_LONG).show();
        }

        et1.setText("");
        et2.setText("");
    }

    public void recuperar(View v){
        String nomArchivo=et1.getText().toString();
        nomArchivo=nomArchivo.replace('/', '-');

        boolean encontrado=false;
        // fileList() devuelve array con nombre ficheros de la app
        String[] archivos=fileList();
        for(int i=0; i<archivos.length; i++){
            if(nomArchivo.equals(archivos[i])){
                encontrado=true;
            }
        }

        if(encontrado){
            try {
                InputStreamReader archivo=new InputStreamReader(openFileInput(nomArchivo));
                BufferedReader br=new BufferedReader(archivo);
                String linea=br.readLine();
                String todo="";
                while(linea!=null){
                    todo+=linea+"\n";
                    linea=br.readLine();
                }

                br.close();
                archivo.close();
                et2.setText(todo);

            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "No se puede abrir archivo", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void mostrarFicheros(View v){
        String[] archivos=fileList();
        String listado="";
        for(int i=0;i<archivos.length;i++){
            listado += archivos[i]+"\n";
        }

        et2.setText(listado);
    }

    public void borrar(View v){
        et1.setText("");
        et2.setText("");
    }
}
