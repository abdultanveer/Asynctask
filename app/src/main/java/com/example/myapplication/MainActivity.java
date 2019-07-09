package com.example.myapplication;

import android.app.Activity;
import com.example.myapplication.data.TodoContract.TodoEntry;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.data.DAO;
import com.example.myapplication.data.TodoNote;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar;
    TextView mTextView;
    CheckBox checkBox;
    EditText nameEditText,passwordEditText;
    DAO dao;
    public static String FILE_NAME = "sageit";
    public static String NAME_KEY = "name";
    public static String PASSWORD_KEY = "pwd";
    public static String RM_PASSWORD_KEY = "rem";
    private SimpleCursorAdapter adapter;


    public  static  int MODE = Activity.MODE_PRIVATE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progressBar1);
        mTextView = findViewById(R.id.textViewResult);
        Toast.makeText(this, "mainactivity is awake", Toast.LENGTH_SHORT).show();
        nameEditText = findViewById(R.id.editTextName);
        passwordEditText = findViewById(R.id.editText2);
        checkBox = findViewById(R.id.checkBox);
        dao = new DAO(this);
        dao.openDb();
        Cursor dataCursor = dao.readRows();
         adapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_2,
                dataCursor,
                new String[]{TodoEntry.COLUMN_NAME_TITLE,TodoEntry.COLUMN_NAME_SUBTITLE},
                new int[]{android.R.id.text1,android.R.id.text2}, 0 );


        ListView dbListView = findViewById(R.id.dbList);
        dbListView.setAdapter(adapter);


        //sendSms();
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveData();
    }

    private void saveData() {
        String name = nameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        boolean isChecked = checkBox.isChecked();
        //create a file
        SharedPreferences sharedPreferences = getSharedPreferences(FILE_NAME,MODE_PRIVATE);
        //open file
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //write to file
        editor.putString(NAME_KEY, name);
        editor.putString(PASSWORD_KEY,password);
        editor.putBoolean(RM_PASSWORD_KEY,isChecked);
        //save file
        editor.commit();

    }

    @Override
    protected void onResume() {
        super.onResume();
        restoreData();
    }

    private void restoreData(){
        //open the file
        SharedPreferences sharedPreferences = getSharedPreferences(FILE_NAME,MODE_PRIVATE);
        //read the file
        String name = sharedPreferences.getString(NAME_KEY,"");
        String pass = sharedPreferences.getString(PASSWORD_KEY,"");
        //put the data in edittexts
        nameEditText.setText(name);
        passwordEditText.setText(pass);
    }

    public void handleClick(View view) {

        MyTask myTask = new MyTask(progressBar,mTextView);
        myTask.execute("http://google.com");//input type

       /* EditText nameEditText = findViewById(R.id.editTextName);
       String name = nameEditText.getText().toString();

        TextView textView = findViewById(R.id.textViewResult);
        textView.setText(name);*/

    }

    public void sendSms(){
        SmsManager manager = SmsManager.getDefault();
        manager.sendTextMessage("5554",null,"happy birthday",null,null);
    }

    public void dbHandler(View view) {
        switch (view.getId()){
            case R.id.buttonput:
                String title = nameEditText.getText().toString();
                String subtitle = passwordEditText.getText().toString();

                dao.createRow(new TodoNote(title,subtitle));
                adapter.notifyDataSetChanged();//hey mr adapter new data has been added, plz update listview

                nameEditText.setText("");
                passwordEditText.setText("");
                break;
            case R.id.buttonget:
               String result = dao.readRow();
               mTextView.setText(result);
                break;
        }
    }
}
