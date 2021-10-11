package com.example.chatapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageHolder> {

    private ArrayList<Message> messages;
    private String senderImg, receiverImg;
    private Context context;

    public MessageAdapter(ArrayList<Message> messages, String senderImg, String receiver, Context context) {
        this.messages = messages;
        this.senderImg = senderImg;
        this.receiverImg = receiver;
        this.context = context;
    }

    @NonNull
    @Override
    public MessageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.message_holder,parent,false);
        return new MessageHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageHolder holder, int position) {
        holder.txtMessage.setText(messages.get(position).getContent());

        ConstraintLayout constraintLayout = holder.ccll;

            if (messages.get(position).getSender().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())){
                Glide.with(context).load(senderImg).error(R.drawable.ic_account_img).placeholder(R.drawable.ic_account_img).into(holder.profImage);
                ConstraintSet constraintSet = new ConstraintSet();
                constraintSet.clone(constraintLayout);
                constraintSet.clear(R.id.profile_cardView, ConstraintSet.LEFT);
                constraintSet.clear(R.id.txt_messageContent, ConstraintSet.LEFT);;
                constraintSet.connect(R.id.profile_cardView, ConstraintSet.RIGHT, R.id.cc_layout, ConstraintSet.RIGHT, 0);
                constraintSet.connect(R.id.txt_messageContent, ConstraintSet.RIGHT, R.id.profile_cardView, ConstraintSet.LEFT, 0);;
                constraintSet.applyTo(constraintLayout);
            }else{
                Glide.with(context).load(receiverImg).error(R.drawable.ic_account_img).placeholder(R.drawable.ic_account_img).into(holder.profImage);
                ConstraintSet constraintSet = new ConstraintSet();
                constraintSet.clone(constraintLayout);
                constraintSet.clear(R.id.profile_cardView, ConstraintSet.RIGHT);
                constraintSet.clear(R.id.txt_messageContent, ConstraintSet.RIGHT);;
                constraintSet.connect(R.id.profile_cardView, ConstraintSet.LEFT, R.id.cc_layout, ConstraintSet.LEFT, 0);
                constraintSet.connect(R.id.txt_messageContent, ConstraintSet.LEFT, R.id.profile_cardView, ConstraintSet.RIGHT, 0);;
                constraintSet.applyTo(constraintLayout);
            }
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class MessageHolder extends RecyclerView.ViewHolder{

        ConstraintLayout ccll;
        TextView txtMessage;
        ImageView profImage;

        public MessageHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
