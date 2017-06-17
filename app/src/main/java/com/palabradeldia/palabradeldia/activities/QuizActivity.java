package com.palabradeldia.palabradeldia.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.palabradeldia.palabradeldia.R;
import com.palabradeldia.palabradeldia.services.GetQuizService;
import com.palabradeldia.palabradeldia.services.Word;

import java.util.ArrayList;
import java.util.Random;

public class QuizActivity extends AppCompatActivity {

    TextView description;

    SharedPreferences prefs;
    private Word word;
    private String quizUrl = "http://nandorak.esy.es/palabraDelDia/app/quiz.php";
    ArrayList opciones;
    Random ran = new Random();
    TextView correctas;
    TextView record;
    int colorVerde = Color.parseColor("#26CE16");
    int colorRojo = Color.parseColor("#FF0000");
    int colorNaranja=Color.parseColor("#FE9A2E");
    int acuCorrectas=0;
    int recordActual=0;
    Context context;


    Button btn1, btn2, btn3, btn4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        context=this;
        description = (TextView) findViewById(R.id.descriptionQuiz);
        correctas=(TextView)findViewById(R.id.correctas);
        record = (TextView) findViewById(R.id.record);
        btn1 =(Button) findViewById(R.id.answer1);
        btn2 =(Button) findViewById(R.id.answer2);
        btn3 =(Button) findViewById(R.id.answer3);
        btn4 =(Button) findViewById(R.id.answer4);
        btn1.setBackgroundColor(colorNaranja);
        btn2.setBackgroundColor(colorNaranja);
        btn3.setBackgroundColor(colorNaranja);
        btn4.setBackgroundColor(colorNaranja);

        prefs = getSharedPreferences("MisRecords", Context.MODE_PRIVATE);

        // use a default value using new Date()
        recordActual = prefs.getInt("record",0);
        record.setText(String.valueOf(recordActual));

        GetQuizService downloadTask = new GetQuizService(QuizActivity.this);
        downloadTask.execute(quizUrl);
    }

    public void setWord(Word newWord) {
        opciones = new ArrayList();
        word =newWord;
        description.setText(word.getDescription());
        opciones.add(word.getResp1());
        opciones.add(word.getResp2());
        opciones.add(word.getResp3());
        if(posicion()==3){
            opciones.add(word.getWord());
        }else{
            opciones.add(posicion(),word.getWord());
        }
        btn1.setText(opciones.get(0).toString());
        btn2.setText(opciones.get(1).toString());
        btn3.setText(opciones.get(2).toString());
        btn4.setText(opciones.get(3).toString());
        btn1.setBackgroundColor(colorNaranja);
        btn2.setBackgroundColor(colorNaranja);
        btn3.setBackgroundColor(colorNaranja);
        btn4.setBackgroundColor(colorNaranja);
    }

    public int posicion(){
        return ran.nextInt(4);
    }

    public void verificar(View v){
        Button b = (Button)findViewById(v.getId());
        if(b.getText()==word.getWord()){
            b.setBackgroundColor(colorVerde);
            acuCorrectas++;
            correctas.setText("Correctas: "+String.valueOf(acuCorrectas));
        }else{
            Toast.makeText(context,"Fallaste",Toast.LENGTH_SHORT).show();
            b.setBackgroundColor(colorRojo);
            if(acuCorrectas>recordActual){
                prefs.edit().putInt("record",acuCorrectas).apply();
                record.setText(String.valueOf(acuCorrectas));
                //******************
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(QuizActivity.this);
                // Setting Dialog Message
                alertDialog.setTitle("Nuevo record!!");
                alertDialog.setMessage("Si deseas puedes compartir tu puntaje con tus amigos e insitarlos a que te superen...si pueden");

                alertDialog.setCancelable(false);
                alertDialog.setPositiveButton("Compartir", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        share();
                    }
                });
                alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialog.show();
                //**********************
                recordActual=acuCorrectas;
            }
            correctas.setText("Correctas: 0");

            acuCorrectas=0;
        }
        refrescar();
    }

    public void refrescar(){
        try {
            //set time in mili
            Thread.sleep(500);
            GetQuizService downloadTask = new GetQuizService(QuizActivity.this);
            downloadTask.execute(quizUrl);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void share(){
                    /***** COMPARTIR IMAGEN *****/
            try {
                final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                String mensaje= "Mi nuevo record en WordUpdate: "+recordActual+". Intenta superarme, si puedes! https://urlapk.com";
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, mensaje);


                startActivity(Intent.createChooser(intent, "Compartir con.."));
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(context,e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

    }
