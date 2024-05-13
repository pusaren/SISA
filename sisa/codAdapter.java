package com.example.sisa;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

class codAdpter extends RecyclerView.Adapter<codAdpter.ViewHolder> {
    List<codmodel> codmodelList;
    public codAdpter(List<codmodel> chatmisList) {
        this.codmodelList = chatmisList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate your layout and create ViewHolder
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.codre, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        codmodel listitems = codmodelList.get(position);
        holder.school.setText(listitems.getSchool());
        holder.department.setText(listitems.getDepartment());
        holder.studentname.setText(listitems.getStudentname());
        holder.regno.setText(listitems.getRegno());
        holder.phonenumber.setText(listitems.getPhonenumber());
        holder.unitcode.setText(listitems.getUnitcode());
        holder.cat.setText(listitems.getCat());
        // holder.main.setText(listitems.getMain());
        // holder.special.setText(listitems.getSpecial());
        //holder.sup.setText(listitems.getSup());
        //holder.retake.setText(listitems.getRetake());
    }

    @Override
    public int getItemCount() {
        // Return the size of your data set
        return codmodelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView school, department, studentname, regno, phonenumber,unitcode,cat,main,sup,special,retake;

        public ViewHolder(View itemView) {
            super(itemView);
            school = itemView.findViewById(R.id.school);
            department = itemView.findViewById(R.id.department);
            studentname = itemView.findViewById(R.id.studentname);
            regno = itemView.findViewById(R.id.registrationnumber);
            phonenumber = itemView.findViewById(R.id.phonenumber);
            unitcode = itemView.findViewById(R.id.unitcode);
            cat = itemView.findViewById(R.id.exam);
//           main = itemView.findViewById(R.id.main);
//            sup = itemView.findViewById(R.id.sup);
//            special = itemView.findViewById(R.id.special);
//            retake = itemView.findViewById(R.id.retake);
        }
    }
}
