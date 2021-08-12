package com.btcsc.project;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IntroduceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IntroduceFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    int pos;
    View indicator1,indicator2,indicator3;
    Button btn0,btn1,btn2,btnYes;
    EditText edtJob,edtName;
    CheckBox checkBox;
    static String file ="information";
    public IntroduceFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment IntroduceFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static IntroduceFragment newInstance(int pos) {
        IntroduceFragment fragment = new IntroduceFragment();
        Bundle args = new Bundle();
        args.putInt("pos",pos);
        fragment.setArguments(args);
        return fragment;
    }
    public static IntroduceFragment newInstance(String param1, String param2) {
        IntroduceFragment fragment = new IntroduceFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        pos = getArguments().getInt("pos");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View  view = null;
        switch (pos)
        {
            case 0:
            {
                view=inflater.inflate(R.layout.fragment_introduce,container,false);
                indicator1= view.findViewById(R.id.indicator1);
                indicator1.setBackground(getContext().getDrawable(R.drawable.indicator_ok));
                btn0=view.findViewById(R.id.btnIntroduce0);
                btn0.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog();
                    }
                });
              break;
            }
            case 1:
            {
                view=inflater.inflate(R.layout.fragment_introduce_1,container,false);
                indicator2= view.findViewById(R.id.indicator2);
                indicator2.setBackground(getContext().getDrawable(R.drawable.indicator_ok));
                btn1=view.findViewById(R.id.btnIntroduce1);
                btn1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog();
                    }
                });
               break;
            }
            case 2:
            {
                view=inflater.inflate(R.layout.fragment_introduce_2,container,false);
                indicator3= view.findViewById(R.id.indicator3);
                indicator3.setBackground(getContext().getDrawable(R.drawable.indicator_ok));
                btn2=view.findViewById(R.id.btnIntroduce2);
                btn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                            dialog();
                    }
                });
                break;
            }
        }
            return  view;
    }
    public  void dialog ()
    {
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_viewpager);
         btnYes =  dialog.findViewById(R.id.btnYesAddList);
         edtName = dialog.findViewById(R.id.edtName);
         edtJob = dialog.findViewById(R.id.edtaddList);
         checkBox = dialog.findViewById(R.id.checkSaveInfornamtion);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveInfor();
                Intent intent = new Intent(getContext(),MainActivity2.class);
                startActivity(intent);
            }
        });
        dialog.show();
    }
    void saveInfor()
    {
        SharedPreferences sharedPreferences =this.getActivity().
                getSharedPreferences(file,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if(checkBox.isChecked())
        {
            editor.putString("name",edtName.getText().toString());
            editor.putString("job",edtJob.getText().toString());
            editor.putBoolean("checked",checkBox.isChecked());
        }
        else
        {
            editor.clear();
        }
        editor.commit();
    }

    @Override
    public void onResume() {
        super.onResume();
        chekUser();
    }
    void chekUser()
    {
        SharedPreferences sharedPreferences =this.getActivity().getSharedPreferences(file,Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("name","");
        String job = sharedPreferences.getString("job","");
        if(!name.isEmpty()&&!job.isEmpty())
        {
            Intent intent = new Intent(getContext(),MainActivity2.class);
            startActivity(intent);
        }
    }
}