package com.example.lab06;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.lab06.databinding.ActivityChatRoomBinding;

public class ChatRoom extends AppCompatActivity {

    ActivityChatRoomBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_chat_room);




    }
}