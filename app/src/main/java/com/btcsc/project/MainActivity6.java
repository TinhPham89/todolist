package com.btcsc.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity6 extends AppCompatActivity implements  ShowListAdapter.setOnclick {

    Toolbar toolbar ;
    Spinner  spinner;
    TextView textView;
    SpinnerAdapter spinnerAdapter;
    RecyclerView  recyclerView;
    String  list ;
    ShowListAdapter showListAdapter;
    ArrayList<Planning> plannings;
    ArrayList<ListPlanning> listPlannings;
    Database database;
    DatabaseList databaseList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);

        setSupportActionBar(toolbar);

        anhxa();

        listPlannings= new ArrayList<>();
        listPlannings.addAll(ListPlanning.getData());
        listPlannings.addAll(databaseList.getAllList());

        spinnerAdapter= new SpinnerAdapter(MainActivity6.this,listPlannings);
        spinner.setAdapter(spinnerAdapter);




        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                list = listPlannings.get(position).toString();
                textView.setText("Danh sách "+list);
                Toast.makeText(MainActivity6.this,list,Toast.LENGTH_LONG).show();
                plannings = new ArrayList<>();
                plannings.addAll(database.getList(list));
                showListAdapter = new ShowListAdapter(MainActivity6.this,plannings,MainActivity6.this);
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity6.this,
                        RecyclerView.VERTICAL,false));
                recyclerView.setAdapter(showListAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    void anhxa()
    {
        toolbar = findViewById(R.id.toolbarMain6);
        spinner = toolbar.findViewById(R.id.spinnerMain6);
        recyclerView = findViewById(R.id.rvmain6);
        textView = findViewById(R.id.txtMain6);

        database = new Database(MainActivity6.this);
        database.createTable();

        databaseList = new DatabaseList(MainActivity6.this);
        databaseList.createTable();


    }

    @Override
    public void onLongClickOverdue(Planning planning) {

    }

    @Override
    public void onDeleteOverdue(Planning planning) {

    }
}