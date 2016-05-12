package com.example.android.dailyexpense;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Category extends AppCompatActivity {

    // Array of strings...
    String[] mobileArray = {"Android","IPhone","WindowsMobile","Blackberry","WebOS","Ubuntu","Windows7","Max OS X"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.category_items, mobileArray);

        ListView listView = (ListView) findViewById(R.id.mobile_list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String ListItem = ((TextView)view).getText().toString();

//                Intent intent = new Intent(Category.this, AddTransaction.class);
//                intent.putExtra("mytext",ListItem);
//                startActivity(intent);

                Intent returnIntent = new Intent();
                returnIntent.putExtra("result",ListItem);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();

            }
        });
    }


}
