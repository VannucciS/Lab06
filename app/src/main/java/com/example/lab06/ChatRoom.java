package com.example.lab06;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lab06.databinding.ActivityChatRoomBinding;
import com.example.lab06.databinding.ReceiveMessageBinding;
import com.example.lab06.databinding.SentMessageBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ChatRoom extends AppCompatActivity {

    ActivityChatRoomBinding binding;

    ChatRoomViewModel chatModel;

    ArrayList<ChatMessage> messages;

    private RecyclerView.Adapter myAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        chatModel = new ViewModelProvider(this).get(ChatRoomViewModel.class);
        messages = chatModel.messages.getValue();
        if(messages == null){
            chatModel.messages.postValue(messages = new ArrayList<ChatMessage>());
        }

        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));

        binding.sendButton.setOnClickListener(click -> {
            String msg = binding.textInput.getText().toString();
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MM-yyyy hh-mm-ss a");
            String currentDateAndTime = sdf.format(new Date());
            ChatMessage newMessage = new ChatMessage(msg,currentDateAndTime, true);
            messages.add(newMessage );

            // to know that something was inserted
            myAdapter.notifyItemInserted(messages.size()-1);

            //clear the previous text;
            binding.textInput.setText("");
        });

        binding.receiveButton.setOnClickListener(click -> {
            String msg = binding.textInput.getText().toString();
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MM-yyyy hh-mm-ss a");
            String currentDateAndTime = sdf.format(new Date());
            ChatMessage newMessage = new ChatMessage(msg,currentDateAndTime, false);
            messages.add(newMessage );

            // to know that something was inserted
            myAdapter.notifyItemInserted(messages.size()-1);

            //clear the previous text;
            binding.textInput.setText("");
        });

        binding.recycleView.setAdapter(myAdapter = new RecyclerView.Adapter<MyRowHolder>() {
            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());

                if(viewType == 0){
                    SentMessageBinding sentBinding = SentMessageBinding.inflate(inflater, parent, false);
                    return new MyRowHolder(sentBinding.getRoot());
                } else{
                    ReceiveMessageBinding receiveBinding = ReceiveMessageBinding.inflate(inflater, parent, false);
                    return new MyRowHolder(receiveBinding.getRoot());
                }

            }

            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
                holder.messageText.setText("");
                holder.timeText.setText("");
                ChatMessage obj = messages.get(position);
                holder.messageText.setText(obj.getMessage());
                holder.timeText.setText(obj.getTimeSent());
            }

            @Override
            public int getItemCount() {
                return messages.size();
            }

            public int getItemViewType(int position){
                ChatMessage obj = messages.get(position);
                return obj.isSentButton() ? 0 : 1; }
        });
    }

    class MyRowHolder extends RecyclerView.ViewHolder{
        TextView messageText;
        TextView timeText;

        public MyRowHolder(@NonNull View itemView){
            super(itemView);
            messageText = itemView.findViewById(R.id.messageText);
            timeText = itemView.findViewById(R.id.timeText);
        }
    }
}