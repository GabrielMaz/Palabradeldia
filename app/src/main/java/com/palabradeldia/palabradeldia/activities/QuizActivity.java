package com.palabradeldia.palabradeldia.activities;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
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
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import butterknife.BindView;

public class QuizActivity extends AppCompatActivity {

    TextView description;

    private Word word;
    private String quizUrl = "http://nandorak.esy.es/palabraDelDia/app/quiz.php";
    ArrayList opciones= new ArrayList();
    Random ran = new Random();
    int colorVerde = Color.parseColor("#26CE16");
    int colorRojo = Color.parseColor("#FF0000");
    int colorNaranja=Color.parseColor("#FE9A2E");


    Button btn1, btn2, btn3, btn4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        description = (TextView) findViewById(R.id.descriptionQuiz);
        btn1 =(Button) findViewById(R.id.answer1);
        btn2 =(Button) findViewById(R.id.answer2);
        btn3 =(Button) findViewById(R.id.answer3);
        btn4 =(Button) findViewById(R.id.answer4);
        btn1.setBackgroundColor(colorNaranja);
        btn2.setBackgroundColor(colorNaranja);
        btn3.setBackgroundColor(colorNaranja);
        btn4.setBackgroundColor(colorNaranja);

        GetQuizService downloadTask = new GetQuizService(QuizActivity.this);
        downloadTask.execute(quizUrl);
    }

    public void setWord(Word newWord) {
        word =newWord;
        description.setText(word.getDescription());
        opciones.add(word.getResp1());
        opciones.add(word.getResp2());
        opciones.add(word.getResp3());
        opciones.add(posicion(),word.getWord());
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
        return ran.nextInt(3);
    }

    public void verificar(View v){
        Button b = (Button)findViewById(v.getId());
        if(b.getText()==word.getWord()){
            b.setBackgroundColor(colorVerde);
        }else{
            b.setBackgroundColor(colorRojo);
        }



        try {
            //set time in mili
            Thread.sleep(3000);
            GetQuizService downloadTask = new GetQuizService(QuizActivity.this);
            downloadTask.execute(quizUrl);

        }catch (Exception e){
            e.printStackTrace();
        }
        //b.setBackgroundColor(colorNaranja);
        //GetQuizService downloadTask = new GetQuizService(QuizActivity.this);
        //downloadTask.execute(quizUrl);

    }
}
