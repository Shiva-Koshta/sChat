package com.shivakoshta.schat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        MessageModel messageModel = messageModelsList.get(position);
        holder.messageLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context, ""+ position, Toast.LENGTH_SHORT).show();
            }
        });
        holder.message.setText(messageModel.getMessage());
//        holder.name.setText(messageModel.getName());
        if(messageModel.getSenderID().equals(FirebaseAuth.getInstance().getUid()))//self message is of different color
        {
            holder.messageLinear.setBackgroundColor(context.getResources().getColor(R.color.self_message_color));
            holder.message.setBackgroundColor(context.getResources().getColor(R.color.self_message_color));
            holder.message.setTextColor(context.getResources().getColor(R.color.white));
//            holder.userImage.setImageDrawable(R.drawable.transparent);
            holder.userImage.setColorFilter(context.getResources().getColor(R.color.self_message_color));
            holder.message.setGravity(Gravity.END);

        }
        else
        {
         holder.messageLinear.setBackgroundColor(context.getResources().getColor(R.color.white));
            holder.message.setBackgroundColor(context.getResources().getColor(R.color.white));
            holder.message.setTextColor(context.getResources().getColor(R.color.black));
        }
    }

    @Override
    public int getItemCount() {
        return messageModelsList.size();
    }

    public class MyViewHolder extends  RecyclerView.ViewHolder{
        private TextView message;
//        private TextView name;
        private LinearLayout messageLinear;
        private ImageView userImage;
//        private CardView main;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.message);
            userImage = itemView.findViewById(R.id.userImage);
//            main = itemView.findViewById(R.id.card_message);
//            name = itemView.findViewById(R.id.userName);
            messageLinear = itemView.findViewById(R.id.messageLinear);

        }
    }

}
