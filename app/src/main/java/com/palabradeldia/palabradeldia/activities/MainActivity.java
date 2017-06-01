package com.palabradeldia.palabradeldia.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.DownloadListener;
import android.widget.Button;
import android.widget.TextView;

import com.palabradeldia.palabradeldia.R;
import com.palabradeldia.palabradeldia.services.DownloadTask;
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

    ProgressDialog progress;
    private String wordUrl = "http://nandorak.esy.es/palabraDelDia/app/mostrar.php";

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

        DownloadTask downloadTask = new DownloadTask(MainActivity.this);
        downloadTask.execute(wordUrl);

        changeWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progress.show();
                DownloadTask downloadTask = new DownloadTask(MainActivity.this);
                downloadTask.execute(wordUrl);
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
        word.setText(newWord.getWord());
        description.setText(newWord.getDescription());
        progress.dismiss();
    }
}
