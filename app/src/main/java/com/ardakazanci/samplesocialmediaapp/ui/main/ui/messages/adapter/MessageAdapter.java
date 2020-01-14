package com.ardakazanci.samplesocialmediaapp.ui.main.ui.messages.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ardakazanci.samplesocialmediaapp.R;
import com.ardakazanci.samplesocialmediaapp.data.model.DataModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {

    private List<DataModel.MessageValue> MessageList;

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nickname;
        TextView message;


        MyViewHolder(View view) {
            super(view);

            nickname = (TextView) view.findViewById(R.id.nicname);
            message = (TextView) view.findViewById(R.id.message);


        }
    }

    public MessageAdapter(List<DataModel.MessageValue> MessagesList) {

        this.MessageList = MessagesList;


    }

    @Override
    public int getItemCount() {
        return MessageList.size();
    }

    @NotNull
    @Override
    public MessageAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_chat, parent, false);


        return new MessageAdapter.MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final MessageAdapter.MyViewHolder holder, final int position) {
        final DataModel.MessageValue m = MessageList.get(position);
        holder.nickname.setText(m.getNickname() + " : ");
        holder.message.setText(m.getMessage());
    }


}
