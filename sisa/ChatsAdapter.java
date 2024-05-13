package com.example.sisa;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ChatsAdapter extends RecyclerView.Adapter<ChatsAdapter.ChatsViewHolder> {

   public List<nonmodel> nonmodelList;

    public ChatsAdapter(List<nonmodel> chatsList) {
        this.nonmodelList = chatsList;
    }

    @NonNull
    @Override
    public ChatsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chatre, parent, false);
        return new ChatsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatsViewHolder holder, int position) {
        nonmodel non = nonmodelList.get(position);
        holder.bind(non);
    }

    @Override
    public int getItemCount() {
        return nonmodelList.size();
    }

    public class ChatsViewHolder extends RecyclerView.ViewHolder {
         TextView textViewEstateName,textViewRoomNumber,textViewphone,textViewemail,textViewissue;
        // Add other views based on your chatsmodel fields

        public ChatsViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewEstateName = itemView.findViewById(R.id.estatename);
            textViewRoomNumber = itemView.findViewById(R.id.Rnumber);
           textViewphone = itemView.findViewById(R.id.phone);
            textViewemail = itemView.findViewById(R.id.Email);
            textViewissue = itemView.findViewById(R.id.issue);
            // Initialize other views based on your item layout
        }

        public void bind(nonmodel chatsModel) {
            textViewEstateName.setText(chatsModel.getEstatename());
            textViewRoomNumber.setText(chatsModel.getRnumber());
           textViewphone.setText(chatsModel.getPhone());
            textViewemail.setText(chatsModel.getEmail());
            textViewissue.setText(chatsModel.getIssue());
            // Bind other data to views
        }
    }
}
