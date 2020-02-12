package com.example.chatapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.chatapp.MessageActivity;
import com.example.chatapp.Model.Chat;
import com.example.chatapp.Model.User;
import com.example.chatapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder>
{
    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;

    private Context mContext;
    private List<Chat> mChat;
    //private List<Chat> sendChats;
    private String imageurl;



    FirebaseUser fuser;

    public MessageAdapter(Context mContext, List<Chat> mChat, String imageurl)
    {
        this.mChat = mChat;
        //this.sendChats = sendChats;
        this.mContext = mContext;
        this.imageurl = imageurl;
    }

    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {

        if (viewType == MSG_TYPE_RIGHT)
        {
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_right, parent, false);
            return new MessageAdapter.ViewHolder(view);
        }
        else
        {
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_left, parent, false);
            return new MessageAdapter.ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final MessageAdapter.ViewHolder holder, final int position)
    {
        Chat chat = mChat.get(position);

        holder.show_message.setText(chat.getMessage());

        if (imageurl.equals("default"))
        {
            holder.profile_image.setImageResource(R.mipmap.ic_launcher);
        }
        else
        {
            Glide.with(mContext).load(imageurl).into(holder.profile_image);
        }

        if (position == mChat.size()-1)
        {
            if (chat.isIsseen())
            {
                holder.txt_seen.setText("Seen");
            }
            else
            {
                holder.txt_seen.setText("Delivered");
            }
        }
        else
        {

            holder.txt_seen.setVisibility(View.GONE);
        }

//        holder.emotionbtn.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//        Chat sentChats = sendChats.get(position);
        fuser = FirebaseAuth.getInstance().getCurrentUser();

            // move forward if not the last element of the list
            if (position != mChat.size() - 1)
            {
                // initialized variables to get the next index of the message list.
                int numPosistion = position+1;
                int correctPosition = 0;
                boolean found = false;

                // continue until to check all the next messages in the list.
                while (! (numPosistion >= mChat.size() ) )
                {

                    // check if the messeage reciever is equal to the user.
                    if(mChat.get(numPosistion).getReceiver().equals(fuser.getUid()))
                    {
                        found =true;
                        correctPosition = numPosistion;

                        break;
                    }
                    else
                    {
                        numPosistion+= 1;
                    }
                }

                if (found)
                {
                    Chat prechat = mChat.get(correctPosition);
                    holder.emote.setText(prechat.getEmote());
                }
                else
                {
                    holder.emote.setText("Hold on, keep texting");
                }
            }
            else
            {
                holder.emote.setText("Hold on, keep texting");
            }

//        }
//        });


    }

    @Override
    public int getItemCount()
    {
        return mChat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView show_message;
        public ImageView profile_image;
        public TextView txt_seen;
        public TextView emote;
        public TextView emotion;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            show_message = itemView.findViewById(R.id.show_message);
            profile_image = itemView.findViewById(R.id.profile_image);
            txt_seen = itemView.findViewById(R.id.txt_seen);
            emote  =  itemView.findViewById(R.id.emote);
        }
    }

    @Override
    public int getItemViewType(int position) {
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        if (mChat.get(position).getSender().equals(fuser.getUid()))
        {
            return MSG_TYPE_RIGHT;
        }
        else
        {
            return MSG_TYPE_LEFT;
        }
    }
}