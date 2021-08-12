package com.btcsc.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.Toolbar;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity3 extends AppCompatActivity {
    SpinnerAdapter  spinnerAdapter ;
    ArrayList<ListPlanning>  listPlannings;
    AppCompatSpinner  spinner;
    EditText txtSub,txtPlan,txtTime,txtDate;
    TextView txtList;
    ImageView imagClose,imagDone,imagCalender,imagTime,imagMicSub,imagMicPlan,imageAddList;
    Button btnYes;
    Calendar calendar ,calendar1, calendar2;
    String Tag = "MainActivity";
    Database data ;
    Toolbar toolbar;
    AlarmManager alarmManager1 , alarmManager2;
    PendingIntent pendingIntent1 ,pendingIntent2;
    int day, month,year,hour,minute;
    int s2,s5;
    String list;
    DatabaseList  databaseList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        databaseList = new DatabaseList(MainActivity3.this);
        databaseList.createTable();
        
        setContentView(R.layout.activity_main3);
        Intent intent = getIntent();
        s2=intent.getIntExtra("Main2",0);
        s5=intent.getIntExtra("Main5",0);

        anhxa();
        listPlannings = new ArrayList<>();
        listPlannings.addAll(ListPlanning.getData());
        listPlannings.addAll(databaseList.getAllList());
        spinnerAdapter =  new SpinnerAdapter(MainActivity3.this,listPlannings);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        list = listPlannings.get(position).toString();
                        Toast.makeText(MainActivity3.this,list,Toast.LENGTH_LONG).show();
                        Log.d("ABC",list);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        txtTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTime();
            }
        });
        imagTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTime();
            }
        });
        txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate();
            }
        });
        imagCalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate();
            }
        });
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertPlanning();
            }
        });
        imagDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertPlanning();
            }
        });
        imagClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        imageAddList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addList();
            }
        });


    }

    private void addList() {
        Dialog dialog = new Dialog(MainActivity3.this);
        dialog.setContentView(R.layout.dialog_add_list);
        Button btnYes, btnNo;
        EditText editText;
        editText=dialog.findViewById(R.id.edtaddList);
        btnYes=dialog.findViewById(R.id.btnYesAddList);
        btnNo=dialog.findViewById(R.id.btnNoAddList);


        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = editText.getText().toString();
                if(!text.isEmpty())
                {
                    ListPlanning listPlanning = new ListPlanning(text);
                    databaseList.insertList(listPlanning);
                    Toast.makeText(MainActivity3.this, "Đã thêm danh sách mới",Toast.LENGTH_LONG).show();
                    listPlannings.clear();
                    listPlannings.addAll(ListPlanning.getData());
                    listPlannings.addAll(databaseList.getAllList());
                    spinnerAdapter.notifyDataSetChanged();
                    dialog.dismiss();

                }
                else
                {
                    Toast.makeText(MainActivity3.this, "Tên danh sách đang trống",Toast.LENGTH_LONG).show();
                }
            }

        });
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    @Override
    protected void onResume() {
        super.onResume();
        listPlannings.clear();
        listPlannings.addAll(ListPlanning.getData());
        listPlannings.addAll(databaseList.getAllList());
        spinnerAdapter.notifyDataSetChanged();
    }

    private void anhxa() {

        spinner = findViewById(R.id.spinner);
//        txtList = findViewById(R.id.txtList);
        txtSub=findViewById(R.id.edtSubjectMain3);
        txtPlan=findViewById(R.id.edtPlanMain3);
        txtDate=findViewById(R.id.edtDateMain3);
        txtTime=findViewById(R.id.edtTimeMain3);
        toolbar=findViewById(R.id.toolbarMain3);
        data = new Database(MainActivity3.this);
        data.createTable();
        imagClose=toolbar.findViewById(R.id.imgCLoseMain3);
        imagDone=toolbar.findViewById(R.id.imgDoneMain3);
        imagCalender=findViewById(R.id.imgDateMain3);
        imagTime = findViewById(R.id.imaTimeMain3);
        imagMicPlan = findViewById(R.id.imgPlanMic);
        imagMicSub= findViewById(R.id.imageMicSub);
        imageAddList = findViewById(R.id.imageAddList);
        btnYes = findViewById(R.id.btnYesMain3);
        calendar1 = Calendar.getInstance();
        calendar2 = Calendar.getInstance();

    }
     void insertPlanning()
    {
        String subject , plan , time , date;
        int status;
        subject = txtSub.getText().toString();
        plan=txtPlan.getText().toString();
        time=txtTime.getText().toString();
        date=txtDate.getText().toString();

                if(plan.isEmpty()||subject.isEmpty()||time.isEmpty()||date.isEmpty())
                {
                    Toast.makeText(MainActivity3.this,"Thông tin của bạn đang trống",Toast.LENGTH_LONG).show();
                }
                else
                {
                    if(month==calendar.get(Calendar.MONTH))

                    {
                        if(day-calendar.get(Calendar.DAY_OF_MONTH)>=1)
                        {
                            alarmManager1 = (AlarmManager) getSystemService(ALARM_SERVICE);
                            alarmManager2 =(AlarmManager) getSystemService(ALARM_SERVICE);

                            Intent intent1  = new Intent(MainActivity3.this,AlarmReceiver1.class);
                            Intent intent2  = new Intent(MainActivity3.this,AlarmReceiver2.class);

                            status = 1;

                            Planning planning = new Planning(subject, plan, date, time,status,list);
                            Log.d("ABC",planning.toString());
                            data.insertPlanning(planning);
                            int ID  = data.getID(planning);
                            Log.d("ABC", String.valueOf(ID));

                            intent1.putExtra("ID",ID);
                            intent2.putExtra("ID",ID);

                            calendar1.set(Calendar.YEAR,year);
                            calendar2.set(Calendar.MONTH,month);
                            calendar1.set(Calendar.DAY_OF_MONTH,day);

                            calendar2.set(Calendar.YEAR,year);
                            calendar2.set(Calendar.MONTH,month);
                            calendar2.set(Calendar.DAY_OF_MONTH,day);
                            calendar2.set(Calendar.HOUR,hour);
                            calendar2.set(Calendar.MINUTE,minute);

                            int requsetcode = ID;

                            Log.d("ABC",String.format("%d/%d/%d ",day,month+1,year));
                            Log.d("ABC",String.format("%d/%d/%d + %d:%d ",day,month+1,year,hour,minute));

                            pendingIntent1 = PendingIntent.getBroadcast(MainActivity3.this,
                                    requsetcode,intent1,PendingIntent.FLAG_UPDATE_CURRENT);
                            pendingIntent2 = PendingIntent.getBroadcast(MainActivity3.this,
                                    requsetcode,intent2,PendingIntent.FLAG_UPDATE_CURRENT);


                            alarmManager1.set(AlarmManager.RTC_WAKEUP,calendar1.getTimeInMillis(),pendingIntent1);

                            alarmManager2.set(AlarmManager.RTC_WAKEUP,calendar2.getTimeInMillis(),pendingIntent2);

                            Toast.makeText(MainActivity3.this, "Đã thêm vào", Toast.LENGTH_LONG).show();

                            if(s2==2)
                            {
                            Intent intent = new Intent(MainActivity3.this, MainActivity2.class);
                            startActivity(intent);
                            finish();
                            }
                            if(s5==5)
                            {
                                Intent intent = new Intent(MainActivity3.this, MainActivity5.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                        if(day<calendar.get(Calendar.DAY_OF_MONTH))
                        {
                            status = -1 ;
                            Planning planning = new Planning(subject, plan, date, time,status,list);
                            Log.d("ABC",planning.toString());
                            data.insertPlanning(planning);
                            int ID  = data.getID(planning);
                            Log.d("ABC", String.valueOf(ID));
                            Toast.makeText(MainActivity3.this, "Đã thêm vào", Toast.LENGTH_LONG).show();
                            if(s2==2)
                            {
                                Intent intent = new Intent(MainActivity3.this, MainActivity2.class);
                                startActivity(intent);
                                finish();
                            }
                            if(s5==5)
                            {
                                Intent intent = new Intent(MainActivity3.this, MainActivity5.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                        if(day==calendar.get(Calendar.DAY_OF_MONTH))
                        {

                            alarmManager2 = (AlarmManager) getSystemService(ALARM_SERVICE);

                            Intent intent2  = new Intent(MainActivity3.this,AlarmReceiver2.class);

                            status = 0;

                            Planning planning = new Planning(subject, plan, date, time,status,list);
                            Log.d("ABC",planning.toString());
                            data.insertPlanning(planning);
                            int ID  = data.getID(planning);
                            Log.d("ABC", String.valueOf(ID));

                            intent2.putExtra("ID",ID);

                            calendar2.set(Calendar.YEAR,year);
                            calendar2.set(Calendar.MONTH,month);
                            calendar2.set(Calendar.DAY_OF_MONTH,day);
                            calendar2.set(Calendar.HOUR,hour);
                            calendar2.set(Calendar.MINUTE,minute);

                            int requsetcode = ID;

                            Log.d("ABC",String.format("%d/%d/%d ",day,month+1,year));
                            Log.d("ABC",String.format("%d/%d/%d + %d:%d ",day,month+1,year,hour,minute));

                            pendingIntent2 = PendingIntent.getBroadcast(MainActivity3.this,
                                    requsetcode,intent2,PendingIntent.FLAG_UPDATE_CURRENT);


                            alarmManager2.set(AlarmManager.RTC_WAKEUP,calendar2.getTimeInMillis(),pendingIntent2);

                            Toast.makeText(MainActivity3.this, "Đã thêm vào", Toast.LENGTH_LONG).show();

                            if(s2==2)
                            {
                                Intent intent = new Intent(MainActivity3.this, MainActivity2.class);
                                startActivity(intent);
                                finish();
                            }
                            if(s5==5)
                            {
                                Intent intent = new Intent(MainActivity3.this, MainActivity5.class);
                                startActivity(intent);
                                finish();
                            }
                        }

                    }
                    if(month<calendar.get(Calendar.MONTH))
                    {
                        status = -1 ;
                        Planning planning = new Planning(subject, plan, date, time,status,list);
                        Log.d("ABC",planning.toString());
                        data.insertPlanning(planning);
                        int ID  = data.getID(planning);
                        Log.d("ABC", String.valueOf(ID));;
                        Toast.makeText(MainActivity3.this, "Đã thêm vào", Toast.LENGTH_LONG).show();
                        if(s2==2)
                        {
                            Intent intent = new Intent(MainActivity3.this, MainActivity2.class);
                            startActivity(intent);
                            finish();
                        }
                        if(s5==5)
                        {
                            Intent intent = new Intent(MainActivity3.this, MainActivity5.class);
                            startActivity(intent);

                        }
                    }
                    if(month>calendar.get(Calendar.MONTH))
                    {
                        alarmManager1 = (AlarmManager) getSystemService(ALARM_SERVICE);
                        alarmManager2 =(AlarmManager) getSystemService(ALARM_SERVICE);

                        Intent intent1  = new Intent(MainActivity3.this,AlarmReceiver1.class);
                        Intent intent2  = new Intent(MainActivity3.this,AlarmReceiver2.class);

                        status = 1;

                        Planning planning = new Planning(subject, plan, date, time,status,list);
                        Log.d("ABC",planning.toString());
                        data.insertPlanning(planning);
                        int ID  = data.getID(planning);
                        Log.d("ABC", String.valueOf(ID));

                        intent1.putExtra("ID",ID);
                        intent2.putExtra("ID",ID);

                        calendar1.set(Calendar.YEAR,year);
                        calendar2.set(Calendar.MONTH,month);
                        calendar1.set(Calendar.DAY_OF_MONTH,day);

                        calendar2.set(Calendar.YEAR,year);
                        calendar2.set(Calendar.MONTH,month);
                        calendar2.set(Calendar.DAY_OF_MONTH,day);
                        calendar2.set(Calendar.HOUR,hour);
                        calendar2.set(Calendar.MINUTE,minute);

                        int requsetcode = ID;

                        Log.d("ABC",String.format("%d/%d/%d ",day,month+1,year));
                        Log.d("ABC",String.format("%d/%d/%d + %d:%d ",day,month+1,year,hour,minute));

                        pendingIntent1 = PendingIntent.getBroadcast(MainActivity3.this,
                                requsetcode,intent1,PendingIntent.FLAG_UPDATE_CURRENT);
                        pendingIntent2 = PendingIntent.getBroadcast(MainActivity3.this,
                                requsetcode,intent2,PendingIntent.FLAG_UPDATE_CURRENT);


                        alarmManager1.set(AlarmManager.RTC_WAKEUP,calendar1.getTimeInMillis(),pendingIntent1);

                        alarmManager2.set(AlarmManager.RTC_WAKEUP,calendar2.getTimeInMillis(),pendingIntent2);

                        Toast.makeText(MainActivity3.this, "Đã thêm vào", Toast.LENGTH_LONG).show();

                        if(s2==2)
                        {
                            Intent intent = new Intent(MainActivity3.this, MainActivity2.class);
                            startActivity(intent);
                            finish();
                        }
                        if(s5==5)
                        {
                            Intent intent = new Intent(MainActivity3.this, MainActivity5.class);
                            startActivity(intent);
                            finish();
                        }
                    }

                }



    }
    void setDate()
    {

        calendar= Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DATE);
        DatePickerDialog date = new DatePickerDialog(MainActivity3.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int years, int months, int dayOfMonth) {
                txtDate.setText(String.format("%02d/%02d/%d",dayOfMonth,months+1,years));
                year=years;
                month=months;
                day=dayOfMonth;
            }
        }, year, month, day);
        date.show();

    }
    void setTime()
    {
        calendar = Calendar.getInstance();
        hour = calendar.get(Calendar.HOUR);
        minute = calendar.get(Calendar.MINUTE);
        TimePickerDialog time = new TimePickerDialog(MainActivity3.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minutes) {
                txtTime.setText(String.format("%02d:%02d",hourOfDay,minutes));
                hour=hourOfDay;
                minute=minutes;
            }
        }, hour, minute, true);
        time.show();
    }

}