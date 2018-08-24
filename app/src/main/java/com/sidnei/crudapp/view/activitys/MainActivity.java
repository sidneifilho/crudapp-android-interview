package com.sidnei.crudapp.view.activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.sidnei.crudapp.R;

public class MainActivity extends AppCompatActivity {


    private Button btnNewPersonRecord;
    private Button btnSearchPerson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Tela principal");

        btnNewPersonRecord = findViewById(R.id.btnNewPersonRecord);
        btnNewPersonRecord.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
            Intent intent = new Intent();
            intent.setClass(getApplicationContext(), PersonSaveOrUpdateActivity.class);
            intent.putExtra("idPerson", 0);
            startActivity(intent);
            }
        });

        btnSearchPerson = findViewById(R.id.btnSearchPerson);
        btnSearchPerson.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), PersonSearchActivity.class);
                startActivity(intent);
            }
        });

    }


    @Override
    protected void onDestroy(){
        super.onDestroy();
    }
}
