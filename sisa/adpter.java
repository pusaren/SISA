package com.example.sisa;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
private List<chatre>listItems;
private Context context;

    public Adapter(List<chatre> listItems, chats context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate your layout and create ViewHolder
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chatre, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Bind data to your ViewHolder
        chatre listitems=listItems.get(position);
        holder.estatename.setText(listitems.getEstatename());
        holder.Rnumber.setText(listitems.getRnumber());
        holder.Phone.setText(listitems.getPhone());
        holder.Email.setText(listitems.getEmail());
        holder.issue.setText(listitems.getIssue());
    }

    @Override
    public int getItemCount() {
        // Return the size of your data set
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView estatename,Rnumber,Phone,Email,issue;
        public ViewHolder(View itemView) {
            super(itemView);
            estatename=(TextView) itemView.findViewById(R.id.estatename);
            Rnumber=(TextView) itemView.findViewById(R.id.Rnumber);
            Phone=(TextView) itemView.findViewById(R.id.Phone);
            Email=(TextView) itemView.findViewById(R.id.Email);
            issue=(TextView) itemView.findViewById(R.id.issue);

        }
    }
}

