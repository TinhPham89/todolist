package com.btcsc.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import static com.google.android.material.navigation.NavigationView.*;

public class MainActivity2 extends AppCompatActivity implements
        PlanningAdapterOverDue.setOnclick,
        PlanningAdapterTomorrow.setOnclick,
        PlanningAdapterToday.setOnclick{
    DrawerLayout drawerLayout;
    View headerview;
    SwipeRefreshLayout swipeRefreshLayout;
    NavigationView nav;
    ImageView imageCloseArrow,imageMenu,btnAdd,imageAvatar;
    Toolbar toolbar;
    RecyclerView RVlist1,RVlist2,RVlist3;
    PlanningAdapterOverDue adapterOverDue;
    PlanningAdapterToday adapterToday;
    PlanningAdapterTomorrow adapterTomorrow;
    ArrayList<Planning> dataOverdue,dataToday,dataTomorrow;
    TextView txtName ,txtJob;
    Database database;
    Planning positon ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        link();
        setSupportActionBar(toolbar);
        sharedPreferences();
        dataOverdue = new ArrayList<>();
        dataOverdue.addAll(database.getAllPlanningOverDue());
        dataToday = new ArrayList<>();
        dataToday.addAll(database.getAllPlanningToday());
        dataTomorrow = new ArrayList<>();
        dataTomorrow.addAll(database.getAllPlanningTomorrow());
        imageMenu.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.START);
            }
        });

        adapterOverDue = new PlanningAdapterOverDue(MainActivity2.this,dataOverdue,MainActivity2.this);
        adapterToday = new PlanningAdapterToday(MainActivity2.this,dataToday,MainActivity2.this);
        adapterTomorrow = new PlanningAdapterTomorrow(MainActivity2.this,dataTomorrow,MainActivity2.this);
        RVlist1.setLayoutManager(new LinearLayoutManager(MainActivity2.this,
                LinearLayoutManager.VERTICAL,false));
        RVlist1.setAdapter(adapterOverDue);
        RVlist1.setItemAnimator(new DefaultItemAnimator());
        RVlist2.setLayoutManager(new LinearLayoutManager(MainActivity2.this,
                LinearLayoutManager.VERTICAL,false));
        RVlist2.setAdapter(adapterToday);
        RVlist2.setItemAnimator(new DefaultItemAnimator());
        RVlist3.setLayoutManager(new LinearLayoutManager(MainActivity2.this,
                LinearLayoutManager.VERTICAL,false));
        RVlist3.setAdapter(adapterTomorrow);
        RVlist3.setItemAnimator(new DefaultItemAnimator());

        registerForContextMenu(RVlist1);
        registerForContextMenu(RVlist2);
        registerForContextMenu(RVlist3);
        nav.setNavigationItemSelectedListener(new OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem itemId) {
                showToast(itemId);
                return true;
            }
        });

    }

    private void showToast(MenuItem itemId) {
        switch (itemId.getItemId())
        {
            case R.id.home:
            {
                Toast.makeText(MainActivity2.this,"HOME",Toast.LENGTH_LONG).show();
                break;
            }
            case R.id.settings:
            {
                startActivity(new Intent(MainActivity2.this,MainActivity6.class));
                Toast.makeText(MainActivity2.this,"SETTINGS",Toast.LENGTH_LONG).show();
                break;
            }
            case R.id.info:
            {
                Toast.makeText(MainActivity2.this,"INFO",Toast.LENGTH_LONG).show();
                break;
            }
            case R.id.planning:
            {
                startActivity(new Intent(MainActivity2.this,MainActivity5.class));
                Toast.makeText(MainActivity2.this,"PLANNING",Toast.LENGTH_LONG).show();
                break;
            }
        }
    }

    public void link()
    {
        database = new Database(MainActivity2.this);
        database.createTable();
        drawerLayout = findViewById(R.id.layoutDrawer);
        nav = findViewById(R.id.nav);
        headerview = nav.getHeaderView(0);
        txtJob=(TextView)headerview.findViewById(R.id.txtJob);
        txtName=(TextView)headerview.findViewById(R.id.txtName);
        txtName.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                editSharePerence();
            }
        });
        txtJob.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                editSharePerence();
            }
        });
        imageAvatar = headerview.findViewById(R.id.imgAvatarMain2);
        imageAvatar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        imageCloseArrow = headerview.findViewById(R.id.imagCloseArrow);
        imageCloseArrow.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawers();
            }
        });
        toolbar = findViewById(R.id.toolbar);
        imageMenu = toolbar.findViewById(R.id.btnMenu);
        swipeRefreshLayout = findViewById(R.id.refereshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                dataOverdue.clear();
                dataOverdue.addAll(database.getAllPlanningOverDue());
                adapterOverDue.notifyDataSetChanged();
                dataToday.clear();
                dataToday.addAll(database.getAllPlanningToday());
                adapterToday.notifyDataSetChanged();
                dataTomorrow.clear();
                dataTomorrow.addAll(database.getAllPlanningTomorrow());
                adapterTomorrow.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        RVlist1=findViewById(R.id.recyclerview1);
        RVlist2=findViewById(R.id.recyclerview2);
        RVlist3=findViewById(R.id.recyclerview3);
        btnAdd=findViewById(R.id.imageAddMain2);
        btnAdd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this,MainActivity3.class);
                intent.putExtra("Main2",2);
                startActivity(intent);

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        dataOverdue.clear();
        dataOverdue.addAll(database.getAllPlanningOverDue());
        adapterOverDue.notifyDataSetChanged();
        dataToday.clear();
        dataToday.addAll(database.getAllPlanningToday());
        adapterToday.notifyDataSetChanged();
        dataTomorrow.clear();
        dataTomorrow.addAll(database.getAllPlanningTomorrow());
        adapterTomorrow.notifyDataSetChanged();
    }



    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_long_click,menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.edit:
                onEditPlanning(positon);
                dataOverdue.clear();
                dataOverdue.addAll(database.getAllPlanningOverDue());
                adapterOverDue.notifyDataSetChanged();
                dataToday.clear();
                dataToday.addAll(database.getAllPlanningToday());
                adapterToday.notifyDataSetChanged();
                dataTomorrow.clear();
                dataTomorrow.addAll(database.getAllPlanningTomorrow());
                adapterTomorrow.notifyDataSetChanged();

                break;
            case R.id.delete:
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity2.this);
                builder.setTitle("Delete");
                builder.setMessage("Do you want to delete");
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        database.deletePlanning(positon);
                        dataOverdue.clear();
                        dataOverdue.addAll(database.getAllPlanningOverDue());
                        adapterOverDue.notifyDataSetChanged();
                        dataToday.clear();
                        dataToday.addAll(database.getAllPlanningToday());
                        adapterToday.notifyDataSetChanged();
                        dataTomorrow.clear();
                        dataTomorrow.addAll(database.getAllPlanningTomorrow());
                        adapterTomorrow.notifyDataSetChanged();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                break;
        }

        return super.onContextItemSelected(item);
    }


    void onEditPlanning(Planning planning)
    {
        Intent intent = new Intent(MainActivity2.this,MainActivity4.class);
        intent.putExtra("id",planning.id);
        intent.putExtra("Main2",2);
        startActivity(intent);
    }

    @Override
    public void onLongClickOverdue(Planning planning) {
        positon = new Planning();
        positon = planning;
    }

    @Override
    public void onDeleteOverdue(Planning planning) {
                database.deletePlanning(planning);
                dataOverdue.clear();
                dataOverdue.addAll(database.getAllPlanningOverDue());
//                adapterOverDue.notifyDataSetChanged();
            }


    @Override
    public void onLongClickToday(Planning planning) {
        positon = new Planning();
        positon = planning;
    }

    @Override
    public void onDeleteToday(Planning planning) {

                database.deletePlanning(planning);
                dataToday.clear();
                dataToday.addAll(database.getAllPlanningOverDue());
//                adapterToday.notifyItemRemoved(planning.id);

    }

    @Override
    public void onLongClickTomorrow(Planning planning) {
        positon = new Planning();
        positon = planning;


    }

    @Override
    public void onDeleteTomorrow(Planning planning) {
                database.deletePlanning(planning);
                dataTomorrow.clear();
                dataTomorrow.addAll(database.getAllPlanningOverDue());
//                adapterTomorrow.notifyDataSetChanged();
    }

    void sharedPreferences ()
    {
        SharedPreferences sharedPreferences = getSharedPreferences("information",MODE_PRIVATE);
        String name = sharedPreferences.getString("name","");
        String job = sharedPreferences.getString("job","");
        txtJob.setText(job);
        txtName.setText(name);
    }
    void editSharePerence()
    {
        Dialog  dialog = new Dialog(MainActivity2.this);
        dialog.setContentView(R.layout.dialog_edit_information);
        Button btnYes = dialog.findViewById(R.id.btnYesMain2);
        Button btnNo = dialog.findViewById(R.id.btnNoMain2);
        EditText edtxtName = dialog.findViewById(R.id.edtNameMain2);
        EditText edtxtJob = dialog.findViewById(R.id.edtJobMain2);
        btnYes.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("information",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                String nameUpdate = edtxtName.getText().toString();
                String jobUpdate = edtxtJob.getText().toString();
                if(!nameUpdate.isEmpty()&&!jobUpdate.isEmpty())
                {
                    editor.putString("name",nameUpdate);
                    editor.putString("job",jobUpdate);
                    txtJob.setText(jobUpdate);
                    txtName.setText(nameUpdate);
                    editor.commit();
                    dialog.dismiss();
                }
                else
                {
                    Toast.makeText(MainActivity2.this,"Thông tin của bạn vẫn đang còn trống",Toast.LENGTH_LONG).show();
                }
            }
        });
        btnNo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}