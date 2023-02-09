package com.shivakoshta.schat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder>{
    private Context context;
    private List<MessageModel> messageModelsList;
    private List<String> messageModelsID;

    public MessageAdapter(Context context) {
        this.context = context;
        messageModelsList = new ArrayList<>();
        messageModelsID = new ArrayList<>();

    }
    public void add(MessageModel messageModel)
    {
        if(!messageModelsID.contains(messageModel.getMsgID())) {
            messageModelsList.add(messageModel);
            messageModelsID.add(messageModel.getMsgID());
            notifyDataSetChanged();
        }
    }
    public void clear()
    {
        messageModelsList.clear();
    }

    public int getLength()
    {
        return messageModelsList.size();
    }


    public void sort()
    {
        Collections.sort(messageModelsList, new Comparator<MessageModel>() {
            @Override
            public int compare(MessageModel o1, MessageModel o2) {

                if((o1.getTime() - o2.getTime())>0)
                return 1;
                else if((o1.getTime() - o2.getTime())<0)
                    return -1;
                else return 0;
            }
        });
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
        holder.name.setText(messageModel.getName());
        if(messageModel.getSenderID().equals(FirebaseAuth.getInstance().getUid()))
        {
//            holder.main.setBackgroundColor(context.getResources().getColor(R.color.messenger_blue));
            holder.message.setBackgroundColor(context.getResources().getColor(R.color.white));
        }
        else
        {
//            holder.main.setBackgroundColor(context.getResources().getColor(R.color.black));
            holder.message.setBackgroundColor(context.getResources().getColor(R.color.white));
        }
    }

    @Override
    public int getItemCount() {
        return messageModelsList.size();
    }

    public class MyViewHolder extends  RecyclerView.ViewHolder{
        private TextView message;
        private TextView name;
//        private LinearLayout main;
        private CardView main;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.message);
            main = itemView.findViewById(R.id.card_message);
            name = itemView.findViewById(R.id.userName);
        }
    }

}
