package com.shivakoshta.schat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shivakoshta.schat.databinding.ActivityChatBinding;

import java.util.UUID;

public class ChatActivity extends AppCompatActivity {
    ActivityChatBinding binding;
    String recieverID;
    String senderRoom,recieverRoom;
    DatabaseReference databaseReferenceSender,databaseReferenceReciever;
    MessageAdapter messageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        recieverID = getIntent().getStringExtra("id");

        senderRoom = FirebaseAuth.getInstance().getUid() + recieverID;
        recieverRoom = recieverID + FirebaseAuth.getInstance().getUid() ;

        messageAdapter = new MessageAdapter(this);
        binding.recyclerChat.setAdapter(messageAdapter);
        binding.recyclerChat.setLayoutManager(new LinearLayoutManager(this));


        databaseReferenceSender = FirebaseDatabase.getInstance().getReference("chats").child(senderRoom);
        databaseReferenceReciever = FirebaseDatabase.getInstance().getReference("chats").child(recieverRoom);

        databaseReferenceSender.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                messageAdapter.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    MessageModel messageModel = dataSnapshot.getValue(MessageModel.class);
                    messageAdapter.add(messageModel);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        binding.sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageToSend = binding.messageEd.getText().toString();
                if(messageToSend.trim().length()>0)
                {
                    sendMessege(messageToSend);
                    binding.messageEd.setText("");
                }
            }
        });

    }

    private void sendMessege(String messageToSend) {
        String messageUid = UUID.randomUUID().toString();
        long time = System.currentTimeMillis();
        MessageModel messageModel = new MessageModel(messageUid,FirebaseAuth.getInstance().getUid(),messageToSend,FirebaseAuth.getInstance().getCurrentUser().getDisplayName(),time);
        messageAdapter.add(messageModel);
        databaseReferenceSender
                .child(messageUid)
                .setValue(messageModel);
        databaseReferenceReciever
                .child(messageUid)
                .setValue(messageModel);

    }
}