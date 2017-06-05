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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.palabradeldia.palabradeldia.Calculate;
import com.palabradeldia.palabradeldia.R;
import com.palabradeldia.palabradeldia.services.GetWordService;
import com.palabradeldia.palabradeldia.services.SendVoteService;
import com.palabradeldia.palabradeldia.services.Word;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.word)
    TextView textWord;

    @BindView(R.id.description)
    TextView description;

    @BindView(R.id.change_word)
    Button changeWord;

    @BindView(R.id.like)
    ImageView like;

    @BindView(R.id.dislike)
    ImageView dislikes;

    @BindView(R.id.txtlLkes)
    TextView cantLikes;

    @BindView(R.id.txtDislikes)
    TextView cantDislikes;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private ProgressDialog progress;
    private String wordUrl = "http://nandorak.esy.es/palabraDelDia/app/mostrar.php";
    private Word word =null;
    private boolean voted;

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

                if (!voted) {
                    progressBar.setProgress(Calculate.calculatePercentage(word.getLike() + 1, word.getUnlike()));
                    SendVoteService send = new SendVoteService(MainActivity.this, String.valueOf(word.getId()), "votar");
                    send.execute();
                    cantLikes.setText(String.valueOf(word.getLike() + 1));
                    voted = true;
                }

            }
        });
        dislikes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!voted) {
                    progressBar.setProgress(Calculate.calculatePercentage(word.getLike(), word.getUnlike() + 1));
                    SendVoteService send = new SendVoteService(MainActivity.this, String.valueOf(word.getId()), "nomegusta");
                    send.execute();
                    cantDislikes.setText(String.valueOf(word.getUnlike() + 1));
                    voted = true;
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
        word =newWord;
        dislikes.setEnabled(true);
        like.setEnabled(true);
        voted = false;
        textWord.setText(word.getWord());
        description.setText(word.getDescription());
        cantLikes.setText(String.valueOf(word.getLike()));
        cantDislikes.setText(String.valueOf(word.getUnlike()));
        word.setId(word.getId());
        progressBar.setProgress(Calculate.calculatePercentage(word.getLike(), word.getUnlike()));
        progress.dismiss();
    }
}
