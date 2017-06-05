package com.palabradeldia.palabradeldia.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.palabradeldia.palabradeldia.R;
import com.palabradeldia.palabradeldia.services.GetWordService;
import com.palabradeldia.palabradeldia.services.SendVoteService;
import com.palabradeldia.palabradeldia.services.SendWordService;
import com.palabradeldia.palabradeldia.services.Word;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.word)
    TextView word;

    @BindView(R.id.description)
    TextView description;

    @BindView(R.id.change_word)
    Button changeWord;

    @BindView(R.id.nolike)
    ImageButton nolike;

    @BindView(R.id.txtlikes)
    TextView cantlikes;

    @BindView(R.id.like)
    ImageButton like;

    ProgressDialog progress;
    private String wordUrl = "http://nandorak.esy.es/palabraDelDia/app/mostrar.php";

    Word palabra =null;
    private boolean votarLike = true;
    private boolean votarNoLike = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setTitle(getString(R.string.title_main));

        progress = new ProgressDialog(MainActivity.this);
        progress.setTitle(getString(R.string.loadign_title));
        progress.setMessage(getString(R.string.loading_message));
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();

        GetWordService downloadTask = new GetWordService(MainActivity.this);
        downloadTask.execute(wordUrl);

        changeWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progress.show();
                GetWordService downloadTask = new GetWordService(MainActivity.this);
                downloadTask.execute(wordUrl);
            }
        });

        like.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                if(puedoVotarLike()){
                    SendVoteService send = new SendVoteService(MainActivity.this, String.valueOf(word.getId()), "votar");
                    send.execute();
                    int cant = palabra.getLike();
                    cantlikes.setText(String.valueOf(cant+1));
                }

            }
        });
        nolike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(puedoVotarNoLike()){
                    SendVoteService send = new SendVoteService(MainActivity.this, String.valueOf(word.getId()), "nomegusta");
                    send.execute();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_title_add_word:
                startActivity(new Intent(MainActivity.this, AddWordActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void setWord(Word newWord) {
        palabra=newWord;
        votarLike=true;
        votarNoLike=true;
        nolike.setEnabled(true);
        like.setEnabled(true);
        word.setText(newWord.getWord());
        description.setText(newWord.getDescription());
        cantlikes.setText(String.valueOf(newWord.getLike()));
        word.setId(newWord.getId());
        progress.dismiss();
    }

    private boolean puedoVotarLike(){
        if (votarLike){
            votarLike = false;
            like.setEnabled(false);
            return true;
        }else{
            return false;
        }
    }

    private boolean puedoVotarNoLike(){
        if (votarNoLike){
            votarNoLike = false;
            nolike.setEnabled(false);
            return true;
        }else{
            return false;
        }
    }


}
