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

        databaseReferenceSender.orderByKey().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                messageAdapter.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    MessageModel messageModel = dataSnapshot.getValue(MessageModel.class);
                    messageAdapter.add(messageModel);
                }
                binding.recyclerChat.scrollToPosition(messageAdapter.getLength()-1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ChatActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        binding.sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageToSend = binding.messageEd.getText().toString();
                binding.recyclerChat.scrollToPosition(messageAdapter.getLength());
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
        String key = String.valueOf(time);
        MessageModel messageModel = new MessageModel(messageUid,FirebaseAuth.getInstance().getUid(),messageToSend,FirebaseAuth.getInstance().getCurrentUser().getDisplayName(),time);
        messageAdapter.add(messageModel);
        databaseReferenceSender.child(key).setValue(messageModel);
        databaseReferenceReciever.child(key).setValue(messageModel);

    }
}
/*
public class MainActivity {

  private DatabaseReference databaseReference;

  // Method to store data in the Firebase Realtime Database
  public void storeDataInFirebase(String data) {
    databaseReference = FirebaseDatabase.getInstance().getReference("Data");
    // Generate a unique key based on the current time
    String key = String.valueOf(System.currentTimeMillis());
    databaseReference.child(key).setValue(data);
  }

  // Method to retrieve data from the Firebase Realtime Database
  public void retrieveDataFromFirebase() {
    databaseReference = FirebaseDatabase.getInstance().getReference("Data");
    databaseReference.orderByKey().addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
          String data = snapshot.getValue(String.class);
          // Use the data as needed
        }
      }

      @Override
      public void onCancelled(DatabaseError databaseError) {
        // Handle errors here
      }
    });
  }
}

 */