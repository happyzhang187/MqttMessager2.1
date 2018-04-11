package com.example.guston.signup2;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guston.signup2.databinding.ActivityMainChatBinding;

import java.util.List;

import baseclasses.MainChatInfo;
import dao.infosDao;
import Interface.OnItemClickListener;

/**
 * Created by Guston on 15/12/17.
 */

public class MainChatActivity extends AppCompatActivity {
     //private


    private ActivityMainChatBinding ChatBinding;
     ListView list1;
     RecyclerView InfoList;


     private  InfoListAdapter adapter;
     private LinearLayoutManager mLayoutManager ;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        ChatBinding = DataBindingUtil.setContentView(MainChatActivity.this,R.layout.activity_main_chat);
       // list1 = ChatBinding.listviewChat;

        InfoList = ChatBinding.listInfo;


        infosDao.getTheList().add(new MainChatInfo("Test",1));
        infosDao.getTheList().add(new MainChatInfo("Test1",2));
        infosDao.getTheList().add(new MainChatInfo("Test2",3));
        infosDao.getTheList().add(new MainChatInfo("Test3",4));

        Toast.makeText(MainChatActivity.this,"The info is"+infosDao.getTheList().get(2).getFriend_name(),Toast.LENGTH_LONG).show();



        // SET UP THE RECYCLERVIEW Adapter and the view
    adapter = new InfoListAdapter(infosDao.getTheList());
    adapter.setMyItemClickListener(new OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            startrTransition(view,position);
        }

        @Override
        public void onItemLongClick(View view, int position) {

        }
    });

mLayoutManager = new LinearLayoutManager(this);
InfoList.setLayoutManager(mLayoutManager);
InfoList.setAdapter(adapter);
InfoList.setItemAnimator(new DefaultItemAnimator());
InfoList.addItemDecoration(new RecyclerView.ItemDecoration() {
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        outRect.set(0,10,0,10);
    }
});











}








private void startrTransition(View view , int position){


    Intent intent = new Intent(MainChatActivity.this , ChatInterfaceActivity.class);
    intent.putExtra("Position",position);


    Bundle b = ActivityOptionsCompat.makeSceneTransitionAnimation(MainChatActivity.this , view ,"content_area").toBundle();

    ActivityCompat.startActivity(MainChatActivity.this,intent,b);
}






















/*



class CustomizeAdapter extends ArrayAdapter<String>{

    List<MainChatInfo> theInfolist;



    Context context;
    int[] Images;
    String[] texts;
CustomizeAdapter(Context c , String[] texts , int[] imgs){
    super(c,R.layout.single_row,R.id.textView_text,texts);
    this.context = c;
    this.Images = imgs;
    this.texts = texts;

}

class  MyViewHolder
{
ImageView ProfileImage;
TextView TheTexts;
MyViewHolder(View v){
    ProfileImage = v.findViewById(R.id.imageView2);
    TheTexts = v.findViewById(R.id.textView_text);




}



}

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
       View row = convertView;
       MyViewHolder holder = null;
       if (row == null) {
           LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.single_row, parent, false);
            holder = new MyViewHolder(row);
            row.setTag(holder);


       }

       else {
           holder = (MyViewHolder) row.getTag();


       }


        holder.ProfileImage.setImageResource(Images[position]);
        holder.TheTexts.setText(texts[position]);




        return row;





    }


    @Nullable
    @Override
    public String getItem(int position) {
        return super.getItem(position);
    }



}}



*/


public class InfoListAdapter extends RecyclerView.Adapter<InfoListAdapter.ViewHolder> {


    private OnItemClickListener myItemClickListener;



     private  List<MainChatInfo> infoList ;

    class ViewHolder extends RecyclerView.ViewHolder{
    // View infoView;

     TextView TheName;
     ImageView TheprofileImage;


        public ViewHolder(View itemView) {
            super(itemView);
           TheName = itemView.findViewById(R.id.info_name);
           TheprofileImage = itemView.findViewById(R.id.info_image);
            //infoView = itemView;

        }
    }

     public InfoListAdapter(List<MainChatInfo> infoList) {
         this.infoList = infoList;
     }


     public void setMyItemClickListener(OnItemClickListener myItemClickListener) {
         this.myItemClickListener = myItemClickListener;
     }

     @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

         Log.e("Adapter","Here we come !!!! FOOOOOOOOOOOOOOOOOOOK");

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row, parent,false);


        return new ViewHolder(view);



    }




    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


  MainChatInfo theInfo = infoList.get(position);

  holder.TheName.setText(theInfo.getFriend_name());

        Log.e("Adapter","Here we come !!!! LOOOOOOOOOOOOOOOOOOOK");

        if(myItemClickListener!= null) {


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    myItemClickListener.onItemClick(view,position);


                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    myItemClickListener.onItemLongClick(view,position);
                    return true;
                }
            });


        }






        Toast.makeText(MainChatActivity.this,position+"The info is"+infosDao.getTheList().get(position).getFriend_name(),Toast.LENGTH_LONG).show();
















        holder.itemView.setTag(position);





    }

    @Override
    public int getItemCount() {
        return infoList.size();
    }



     public MainChatInfo getItem(int position){
        return this.infoList.get(position);



     }

}




}
