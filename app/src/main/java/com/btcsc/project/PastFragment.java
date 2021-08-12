package com.btcsc.project;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.appcompat.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PastFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PastFragment extends Fragment implements PlanningAdapterOverDue.setOnclick {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ArrayList<Planning>  dataOverdue;
    PlanningAdapterOverDue planningAdapterOverDue;
    RecyclerView recyclerView;
    ImageView imageAdd;
    SearchView searchView;
    Database database;
    Context context;
    Planning positon,planning;

    public PastFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PastFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PastFragment newInstance(String param1, String param2) {
        PastFragment fragment = new PastFragment();
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
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_past,container,false);

    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.rvFragmentPast);
        imageAdd = view.findViewById(R.id.imageAddMain5Overdue);
        imageAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),MainActivity3.class);
                intent.putExtra("Main5",5);
                startActivity(intent);
            }
        });
        searchView=view.findViewById(R.id.searchViewOverdue);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                planningAdapterOverDue.getFilter().filter(newText);
                Toast.makeText(getActivity(), newText, Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        database = new Database(getActivity());
        database.createTable();
        dataOverdue= new ArrayList<>();
        dataOverdue.addAll(database.getAllPlanningOverDue());
        planningAdapterOverDue = new PlanningAdapterOverDue((MainActivity5)getActivity(),dataOverdue, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(planningAdapterOverDue);
        registerForContextMenu(recyclerView);
        setHasOptionsMenu(true);

    }
    @Override
    public void onLongClickOverdue(Planning planning) {
        positon = new Planning();
        positon = planning;
    }

    @Override
    public void onDeleteOverdue(Planning planning) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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
                database.deletePlanning(planning);
                dataOverdue.clear();
                dataOverdue.addAll(database.getAllPlanningOverDue());
                planningAdapterOverDue.notifyDataSetChanged();

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }



    @Override
    public void onCreateContextMenu(ContextMenu menu,  View v,  ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.menu_long_click,menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.edit:
                onEditPlanning(positon);
                onResume();
                break;
            case R.id.delete:
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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
                        planningAdapterOverDue.notifyDataSetChanged();

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                break;
             }
        return super.onContextItemSelected(item);
    }
    void onEditPlanning (Planning planning)
    {
        Intent intent = new Intent(getContext(),MainActivity4.class);
        intent.putExtra("id",planning.id);
        intent.putExtra("Main5",5);
        startActivity(intent);
    }
}