package com.shivakoshta.schat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder>{
    private Context context;
    private List<MessageModel> messageModelsList;

    public MessageAdapter(Context context) {
        this.context = context;
        messageModelsList = new ArrayList<>();
    }
    public void add(MessageModel userModel)
    {
        messageModelsList.add(userModel);
        notifyDataSetChanged();
    }
    public void clear()
    {
        messageModelsList.clear();
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.messagelayout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MessageModel messageModel = messageModelsList.get(position);
        holder.message.setText(messageModel.getMessage());
        if(messageModel.getSenderID().equals(FirebaseAuth.getInstance().getUid()))
        {
            holder.main.setBackgroundColor(context.getResources().getColor(R.color.messenger_blue));
            holder.message.setBackgroundColor(context.getResources().getColor(R.color.white));
        }
        else
        {
            holder.main.setBackgroundColor(context.getResources().getColor(R.color.black));
            holder.message.setBackgroundColor(context.getResources().getColor(R.color.white));
        }
    }

    @Override
    public int getItemCount() {
        return messageModelsList.size();
    }

    public class MyViewHolder extends  RecyclerView.ViewHolder{
        private TextView message;
        private LinearLayout main;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.message);
            main = itemView.findViewById(R.id.messageLinear);
        }
    }

}
