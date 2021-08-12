package com.btcsc.project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.StringTokenizer;

public class MainActivity4 extends AppCompatActivity {
    EditText txtSub,txtPlan,txtTime,txtDate;
    ImageView imagClose,imagDone,imagCalender,imagTime,imagMicSub,imagMicPlan;
    Button btnYes;
    Calendar calendar = Calendar.getInstance() ,calendar1 = Calendar.getInstance(),calendar2 = Calendar.getInstance() ;
    AlarmManager alarmManager1 ,alarmManager2;
    PendingIntent pendingIntent1 ,pendingIntent2;
    String Tag = "MainActivity";
    Database data ;
    Toolbar toolbar;
    int day, month,year,hour,minute;
    String s1,s2,s3;
    int id;
    int main2,main5;
    String list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        anhxa();
        Intent intent= getIntent();
        id=intent.getIntExtra("id",0);
        main2=intent.getIntExtra("Main2",0);
        main5=intent.getIntExtra("Main5",0);
        Planning planning = data.getOnePlanning(id);
        list = planning.list;
        txtTime.setText(planning.time);
        txtDate.setText(planning.date);
        txtPlan.setText(planning.plan);
        txtSub.setText(planning.subject);
        StringTokenizer stringTokenizer = new StringTokenizer(planning.date,"/");
        s1= stringTokenizer.nextToken();
        s2=stringTokenizer.nextToken();
        s3=stringTokenizer.nextToken();
        month=Integer.parseInt(s2)-1;
        day = Integer.parseInt(s1);
        year = Integer.parseInt(s3);


        imagTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTime();
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
                onUpdatePlanning();
            }
        });
        imagDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onUpdatePlanning();
            }
        });
        imagClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    private void anhxa() {
        txtSub=findViewById(R.id.edtSubjectMain3);
        txtPlan=findViewById(R.id.edtPlanMain3);
        txtDate=findViewById(R.id.edtDateMain3);
        txtTime=findViewById(R.id.edtTimeMain3);
        toolbar=findViewById(R.id.toolbarMain3);
        data = new Database(MainActivity4.this);
        data.createTable();
        imagClose=toolbar.findViewById(R.id.imgCLoseMain3);
        imagDone=toolbar.findViewById(R.id.imgDoneMain3);
        imagCalender=findViewById(R.id.imgDateMain3);
        imagTime = findViewById(R.id.imaTimeMain3);
        imagMicPlan = findViewById(R.id.imgPlanMic);
        imagMicSub= findViewById(R.id.imageMicSub);
        btnYes = findViewById(R.id.btnYesMain3);
    }
    void setDate()
    {

        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DATE);
        DatePickerDialog date = new DatePickerDialog(MainActivity4.this, new DatePickerDialog.OnDateSetListener() {
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
        hour = calendar.get(Calendar.HOUR);
        minute = calendar.get(Calendar.MINUTE);
        TimePickerDialog time = new TimePickerDialog(MainActivity4.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minutes) {
                txtTime.setText(String.format("%02d:%02d",hourOfDay,minutes));
                hour=hourOfDay;
                minute=minutes;
            }
        }, hour, minute, true);
        time.show();
    }
    void onUpdatePlanning()
    {
        String subject , plan , time , date;
        int status;
        subject = txtSub.getText().toString();
        plan=txtPlan.getText().toString();
        time=txtTime.getText().toString();
        date=txtDate.getText().toString();


        if(plan.isEmpty()||subject.isEmpty()||time.isEmpty()||date.isEmpty())
        {
            Toast.makeText(MainActivity4.this,"Thông tin của bạn đang trống",Toast.LENGTH_LONG).show();
        }
        else
        {
            if(month==calendar.get(Calendar.MONTH))
            {
                if(day-calendar.get(Calendar.DAY_OF_MONTH)>1)
                {
                    alarmManager1 = (AlarmManager) getSystemService(ALARM_SERVICE);
                    alarmManager2 =(AlarmManager) getSystemService(ALARM_SERVICE);

                    Intent intent1  = new Intent(MainActivity4.this,AlarmReceiver1.class);
                    Intent intent2  = new Intent(MainActivity4.this,AlarmReceiver2.class);

                    status = 1;
                    Planning planning = new Planning(subject, plan, date, time,id,status,list);
                    Log.d("ABC",planning.toString());
                    data.updatePlanning(planning);

                    intent1.putExtra("ID",id);
                    intent2.putExtra("ID",id);

                    calendar1.set(Calendar.YEAR,year);
                    calendar2.set(Calendar.MONTH,month);
                    calendar1.set(Calendar.DAY_OF_MONTH,day);

                    calendar2.set(Calendar.YEAR,year);
                    calendar2.set(Calendar.MONTH,month);
                    calendar2.set(Calendar.DAY_OF_MONTH,day);
                    calendar2.set(Calendar.HOUR,hour);
                    calendar2.set(Calendar.MINUTE,minute);

                    int requsetcode = id;

                    Log.d("ABC",String.format("%d/%d/%d ",day,month,year));
                    Log.d("ABC",String.format("%d/%d/%d + %d:%d ",day,month,year,hour,minute));

                    pendingIntent1 = PendingIntent.getBroadcast(MainActivity4.this,
                            requsetcode,intent1,PendingIntent.FLAG_UPDATE_CURRENT);
                    pendingIntent2 = PendingIntent.getBroadcast(MainActivity4.this,
                            requsetcode,intent2,PendingIntent.FLAG_UPDATE_CURRENT);


                    alarmManager1.set(AlarmManager.RTC_WAKEUP,calendar1.getTimeInMillis(),pendingIntent1);

                    alarmManager2.set(AlarmManager.RTC_WAKEUP,calendar2.getTimeInMillis(),pendingIntent2);

                    Toast.makeText(MainActivity4.this, "Đã cập nhật", Toast.LENGTH_LONG).show();
                    if(main2==2)
                    {
                        Intent intent = new Intent(MainActivity4.this, MainActivity2.class);
                        startActivity(intent);
                        finish();
                    }
                    if(main5==5)
                    {
                        Intent intent = new Intent(MainActivity4.this, MainActivity5.class);
                        startActivity(intent);

                    }
                }
                if(day<calendar.get(Calendar.DAY_OF_MONTH))
                {
                    status = -1 ;
                    Planning planning = new Planning(subject, plan, date, time,id,status,list);
                    Log.d("ABC",planning.toString());
                    data.updatePlanning(planning);
                    Toast.makeText(MainActivity4.this, "Đã cập nhật", Toast.LENGTH_LONG).show();
                    if(main2==2)
                    {
                        Intent intent = new Intent(MainActivity4.this, MainActivity2.class);
                        startActivity(intent);
                        finish();
                    }
                    if(main5==5)
                    {
                        Intent intent = new Intent(MainActivity4.this, MainActivity5.class);
                        startActivity(intent);

                    }
                }
                if(day==calendar.get(Calendar.DAY_OF_MONTH))
                {

                    alarmManager2 =(AlarmManager) getSystemService(ALARM_SERVICE);


                    Intent intent2  = new Intent(MainActivity4.this,AlarmReceiver2.class);

                    status = 0;
                    Planning planning = new Planning(subject, plan, date, time,id,status,list);
                    Log.d("ABC",planning.toString());
                    data.updatePlanning(planning);

                    intent2.putExtra("ID",id);
                    calendar2.set(Calendar.YEAR,year);
                    calendar2.set(Calendar.MONTH,month);
                    calendar2.set(Calendar.DAY_OF_MONTH,day);
                    calendar2.set(Calendar.HOUR,hour);
                    calendar2.set(Calendar.MINUTE,minute);

                    int requsetcode = id;

                    Log.d("ABC",String.format("%d/%d/%d +update ",day,month,year));
                    Log.d("ABC",String.format("%d/%d/%d + %d:%d +update",day,month,year,hour,minute));


                    pendingIntent2 = PendingIntent.getBroadcast(MainActivity4.this,
                            requsetcode,intent2,PendingIntent.FLAG_UPDATE_CURRENT);



                    alarmManager2.set(AlarmManager.RTC_WAKEUP,calendar2.getTimeInMillis(),pendingIntent2);

                    Toast.makeText(MainActivity4.this, "Đã cập nhật", Toast.LENGTH_LONG).show();

                    if(main2==2)
                    {
                        Intent intent = new Intent(MainActivity4.this, MainActivity2.class);
                        startActivity(intent);
                        finish();
                    }
                    if(main5==5)
                    {
                        Intent intent = new Intent(MainActivity4.this, MainActivity5.class);
                        startActivity(intent);

                    }
                }

            }
            if(month<calendar.get(Calendar.MONTH))
            {
                status = -1 ;
                Planning planning = new Planning(subject, plan, date, time,id,status,list);
                Log.d("ABC",planning.toString());
                data.updatePlanning(planning);
                Toast.makeText(MainActivity4.this, "Đã cập nhật", Toast.LENGTH_LONG).show();
                if(main2==2)
                {
                    Intent intent = new Intent(MainActivity4.this, MainActivity2.class);
                    startActivity(intent);
                    finish();
                }
                if(main5==5)
                {
                    Intent intent = new Intent(MainActivity4.this, MainActivity5.class);
                    startActivity(intent);

                }
            }
            if(month>calendar.get(Calendar.MONTH))
            {
                alarmManager1 = (AlarmManager) getSystemService(ALARM_SERVICE);
                alarmManager2 =(AlarmManager) getSystemService(ALARM_SERVICE);

                Intent intent1  = new Intent(MainActivity4.this,AlarmReceiver1.class);
                Intent intent2  = new Intent(MainActivity4.this,AlarmReceiver2.class);

                status = 1;
                Planning planning = new Planning(subject, plan, date, time,id,status,list);
                Log.d("ABC",planning.toString());
                data.updatePlanning(planning);

                intent1.putExtra("ID",id);
                intent2.putExtra("ID",id);

                calendar1.set(Calendar.YEAR,year);
                calendar2.set(Calendar.MONTH,month);
                calendar1.set(Calendar.DAY_OF_MONTH,day);

                calendar2.set(Calendar.YEAR,year);
                calendar2.set(Calendar.MONTH,month);
                calendar2.set(Calendar.DAY_OF_MONTH,day);
                calendar2.set(Calendar.HOUR,hour);
                calendar2.set(Calendar.MINUTE,minute);

                int requsetcode = id;

                Log.d("ABC",String.format("%d/%d/%d ",day,month,year));
                Log.d("ABC",String.format("%d/%d/%d + %d:%d ",day,month,year,hour,minute));

                pendingIntent1 = PendingIntent.getBroadcast(MainActivity4.this,
                        requsetcode,intent1,PendingIntent.FLAG_UPDATE_CURRENT);
                pendingIntent2 = PendingIntent.getBroadcast(MainActivity4.this,
                        requsetcode,intent2,PendingIntent.FLAG_UPDATE_CURRENT);


                alarmManager1.set(AlarmManager.RTC_WAKEUP,calendar1.getTimeInMillis(),pendingIntent1);

                alarmManager2.set(AlarmManager.RTC_WAKEUP,calendar2.getTimeInMillis(),pendingIntent2);

                Toast.makeText(MainActivity4.this, "Đã cập nhật", Toast.LENGTH_LONG).show();
            }

        }

    }

}