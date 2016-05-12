package com.example.android.dailyexpense;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;

public class AddTransaction extends AppCompatActivity {

    DBHelper myDb;
    EditText spend_amt,category,date;
    Button btnsave, btnshow;

    private String save_Spend;
    private String textAtView_spend;

    private String save_Date;
    private String textAtView_date;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Log.i("onCreate", "Add Transaction");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


//        if(savedInstanceState!=null){
//            Log.i("savedInstanceState","Not Null");
//            savedInstanceState.get(textAtView_spend);
//            spend_amt.setText(save_Spend);
//
//        }
//        else

//            Log.i("savedInstanceState","Null");

            EditText ed = (EditText) findViewById(R.id.editText_Category);

            Intent intent = getIntent();
            String str = intent.getStringExtra("mytext");
            ed.setText(str);

            myDb = new DBHelper(this);

            spend_amt = (EditText) findViewById(R.id.editText_spend);
            category = (EditText) findViewById(R.id.editText_Category);
            date = (EditText) findViewById(R.id.editText_Date);

            btnsave = (Button) findViewById(R.id.button_save);
            btnshow = (Button) findViewById(R.id.button_show);


            AddData();
            ViewData();


            category.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(hasFocus) {
                        Log.i("OnFocusChangeListener", "Category Focus");
//                    Intent intent = new Intent(AddTransaction.this, Category.class);
//                    startActivity(intent);

                        Intent i = new Intent(AddTransaction.this, Category.class);
                        startActivityForResult(i, 1);
                        category.clearFocus();
                    }
                }
            });

            final EditText txtDate = (EditText) findViewById(R.id.editText_Date);

            txtDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                      Log.i("OnFocusChangeDate","date focus");
                    if (hasFocus) {
                        DateDialog dialog = new DateDialog(v);
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        dialog.show(ft, "DatePicker");
                        txtDate.clearFocus();
                    }
                }
            });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                String result = data.getStringExtra("result");
                category.setText(result);

            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceStat) {
        super.onSaveInstanceState(savedInstanceStat);
        Log.i("onSaveInstanceState", "savedInstance");

        //save spend amount
        save_Spend = spend_amt.getText().toString();

        savedInstanceStat.putString("spendAmount", save_Spend);


    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceStat) {
        super.onRestoreInstanceState(savedInstanceStat);

        spend_amt.setText(savedInstanceStat.getString("spendAmount"));

    }


    public void onStart(){
        Log.i("AddTransaction", "onStart");
        super.onStart();

//        category.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                Intent intent = new Intent(AddTransaction.this, Category.class);
//                startActivity(intent);
//
//            }
//        });
//
//
//        EditText txtDate = (EditText)findViewById(R.id.editText_Date);
//        txtDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if(hasFocus){
//                    DateDialog dialog = new DateDialog(v);
//                    FragmentTransaction ft = getFragmentManager().beginTransaction();
//                    dialog.show(ft,"DatePicker");
//                }
//            }
//        });


    }

    public void onResume(){
        Log.i("AddTransaction", "onResume");
        super.onResume();

    }

    public void onPause(){
        Log.i("AddTransaction", "onPause");
        super.onPause();
    }

    public void onStop(){
        Log.i("AddTransaction", "onStop");
        super.onStop();

    }

    public void onRestart(){
        Log.i("AddTransaction", "onRestart");
        super.onRestart();
    }

    public void onDestroy(){
        Log.i("AddTransaction", "onDestroy");
        super.onDestroy();

        // Stop method tracing that the activity started during onCreate()
        android.os.Debug.stopMethodTracing();
    }





    public void AddData(){
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted = myDb.insertData(spend_amt.getText().toString(), category.getText().toString(), date.getText().toString());

                if (isInserted = true) {
                    Toast.makeText(AddTransaction.this, "Data Inserted", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(AddTransaction.this, "Data Not Inserted", Toast.LENGTH_LONG).show();
                }
                finish();
            }
        });
    }

    public void ViewData(){
        btnshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = myDb.getAllData();
                if (res.getCount() == 0) {
                    //show message
                    ShowMessage("Error", "Nothing Found");
                    return;
                }

                StringBuffer buffer = new StringBuffer();

                while (res.moveToNext()) {
                    buffer.append("ID :" + res.getString(0) + "\n");
                    buffer.append("AMOUNT :" + res.getString(1) + "\n");
                    buffer.append("CATEGORY :" + res.getString(2) + "\n");
                    buffer.append("DATE :" + res.getString(3) + "\n\n");
                }

                //show all data
                ShowMessage("Data", buffer.toString());
            }
        });
    }

    public void ShowMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }







}
