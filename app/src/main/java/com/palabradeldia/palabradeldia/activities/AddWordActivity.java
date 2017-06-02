package com.palabradeldia.palabradeldia.activities;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.palabradeldia.palabradeldia.R;
import com.palabradeldia.palabradeldia.services.SendWordService;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddWordActivity extends AppCompatActivity{

    @BindView(R.id.word)
    EditText word;

    @BindView(R.id.description)
    EditText description;

    @BindView(R.id.add_word)
    Button addWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_word);
        ButterKnife.bind(this);
        setTitle(getString(R.string.title_add_word));

        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        addWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (word.getText().toString().equals("")) {
                    Toast.makeText(AddWordActivity.this, R.string.activity_add_word_empty_word, Toast.LENGTH_LONG).show();

                } else if (description.getText().toString().equals("")) {
                    Toast.makeText(AddWordActivity.this, R.string.activity_add_word_empty_description, Toast.LENGTH_LONG).show();

                } else {
                    SendWordService send = new SendWordService(AddWordActivity.this, word.getText().toString(), description.getText().toString());
                    send.execute();
                    word.setText("");
                    description.setText("");

                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
