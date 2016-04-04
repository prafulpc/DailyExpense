package com.example.android.dailyexpense;

import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddTransaction extends AppCompatActivity {

    DBHelper myDb;
    EditText spend_amt,category,date;
    Button btnsave, btnshow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        EditText ed = (EditText)findViewById(R.id.editText_Category);

        Intent intent = getIntent();
        String str = intent.getStringExtra("mytext");
        ed.setText(str);

        myDb = new DBHelper(this);

        spend_amt = (EditText)findViewById(R.id.editText_spend);
        category = (EditText)findViewById(R.id.editText_Category);
        date = (EditText)findViewById(R.id.editText_Date);

        btnsave = (Button)findViewById(R.id.button_save);
        btnshow= (Button)findViewById(R.id.button_show);

        AddData();
        ViewData();

    }

    public void onStart(){
        super.onStart();
        EditText txtDate = (EditText)findViewById(R.id.editText_Date);
        txtDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    DateDialog dialog = new DateDialog(v);
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    dialog.show(ft,"DatePicker");
                }
            }
        });

        category = (EditText)findViewById(R.id.editText_Category);

        category.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Intent intent = new Intent(AddTransaction.this,Category.class);
                startActivity(intent);

            }
        });
    }

    public void AddData(){
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted = myDb.insertData(spend_amt.getText().toString(), category.getText().toString(), date.getText().toString());

                if (isInserted = true)
                    Toast.makeText(AddTransaction.this, "Data Inserted", Toast.LENGTH_LONG).show();

                else
                    Toast.makeText(AddTransaction.this, "Data Not Inserted", Toast.LENGTH_LONG).show();

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
