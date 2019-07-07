package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar;
    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progressBar1);
        mTextView = findViewById(R.id.textViewResult);
    }

    public void handleClick(View view) {

        MyTask myTask = new MyTask(progressBar,mTextView);
        myTask.execute("http://google.com");//input type

       /* EditText nameEditText = findViewById(R.id.editTextName);
       String name = nameEditText.getText().toString();

        TextView textView = findViewById(R.id.textViewResult);
        textView.setText(name);*/

    }
}
