package com.btcsc.project;

import android.content.Context;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PlanningAdapterOverDue extends RecyclerView.Adapter<PlanningAdapterOverDue.PlanningVH> implements Filterable {
    Context context;
    ArrayList<Planning> data;
    setOnclick longListener;
    ArrayList<Planning> listFliter;
    Database database;

    public PlanningAdapterOverDue(Context context, ArrayList<Planning> data, setOnclick longListener) {
        this.context = context;
        this.data = data;
        this.listFliter =data;
        this.longListener =  longListener;
    }



    interface setOnclick{
        void onLongClickOverdue(Planning planning);
        void onDeleteOverdue(Planning planning);
    }
    @NonNull
    @Override
    public PlanningVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View convertView = layoutInflater.inflate(R.layout.layout_item,parent,false);
        Animation animation = new AnimationUtils().loadAnimation(context,R.anim.showitem);
        convertView.setAnimation(animation);
        return new PlanningVH(convertView);
    }

    @Override
    public void onBindViewHolder(@NonNull PlanningVH holder, final int position) {

       final Planning plan = listFliter.get(position);
        holder.txtDate.setText(plan.date);
        holder.txtTime.setText(plan.time);
        holder.txtSub.setText(plan.subject);
        holder.txtPlan.setText(plan.plan);
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                longListener.onLongClickOverdue(plan);
                return false;
            }
        });
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.checkBox.isChecked())
                {
                    longListener.onDeleteOverdue(plan);
                    notifyItemRemoved(position);
                }
            }
        });

    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if (charString.isEmpty()) {
                    listFliter = data;
                } else {
                    List<Planning> filteredList = new ArrayList<>();
                    for (Planning row : data) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getSubject().toLowerCase().contains(charString.toLowerCase()) ||
                                row.getPlan().contains(charString.toLowerCase())||
                                row.getDate().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    listFliter = (ArrayList<Planning>) filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = listFliter;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                listFliter = (ArrayList<Planning>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public int getItemCount() {
        return listFliter.size();
    }

    class PlanningVH extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        TextView txtSub,txtPlan,txtDate,txtTime;
        public PlanningVH(@NonNull View itemView) {
            super(itemView);
            checkBox=itemView.findViewById(R.id.checkBox);
            txtSub = itemView.findViewById(R.id.txtSub);
            txtPlan =itemView.findViewById(R.id.txtPlan);
            txtTime = itemView.findViewById(R.id.txtTime);
            txtDate=itemView.findViewById(R.id.txtDate);
            this.setIsRecyclable(false);
        }
    }

}
