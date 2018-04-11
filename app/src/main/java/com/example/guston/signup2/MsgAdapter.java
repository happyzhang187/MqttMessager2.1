package com.example.guston.signup2;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import baseclasses.ChatMsg;

/**
 * Created by Guston on 05/03/18.
 */

public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.ViewHolder> {

    private List<ChatMsg> theMsgList;

    static class ViewHolder extends RecyclerView.ViewHolder{
   LinearLayout leftLayout;
   LinearLayout rightLayout;
   TextView leftMsg;
   TextView rightMsg;

        public ViewHolder(View itemView) {
            super(itemView);

            leftLayout = itemView.findViewById(R.id.left_layout);
            rightLayout = itemView.findViewById(R.id.right_layout);
            leftMsg = itemView.findViewById(R.id.left_msg);
            rightMsg = itemView.findViewById(R.id.right_msg);

        }
    }


    public  MsgAdapter(List<ChatMsg> theMsgList){

        this.theMsgList = theMsgList;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.msg_item,parent,false);
        return new ViewHolder(view);


    }





    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


        ChatMsg msg = theMsgList.get(position);
        if(msg.getType() == ChatMsg.TYPE_RECEVIED){

           // holder.

            holder.rightLayout.setVisibility(View.VISIBLE);
            holder.leftLayout.setVisibility(View.GONE);
            holder.rightMsg.setText(msg.getContent());


        }
        else if(msg.getType() == ChatMsg.TYPE_SEND){



            holder.leftLayout.setVisibility(View.VISIBLE);
            holder.rightLayout.setVisibility(View.GONE);
            holder.leftMsg.setText(msg.getContent());


        }


    }

    @Override
    public int getItemCount() {
        return theMsgList.size();
    }
}
