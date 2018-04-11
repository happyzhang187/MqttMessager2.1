package com.example.guston.signup2;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.guston.signup2.databinding.ActivityChatInterfaceBinding;

import java.util.ArrayList;
import java.util.List;

import baseclasses.ChatMsg;
import baseclasses.MainChatInfo;
import pahodata.MqttManager;
import services.MqttConnectionService;
import dao.infosDao;
import transition.transitionHelper;

/**
 * Created by Guston on 04/03/18.
 */

public class ChatInterfaceActivity extends AppCompatActivity {

    private ActivityChatInterfaceBinding chatInterfaceBinding;

    private IntentFilter intentFilter;
    private MyBroadCast myBroadCast;



    private Button writeButton;
    private RecyclerView msgRecyclerView;
    private MsgAdapter adapter;
    private List<ChatMsg> theList = new ArrayList<>();


    private MainChatInfo info ;

    private TextView infoText;

    private EditText EditMsg;

    private Boolean ifFriendsConfirmedMessage = true;
    private  String confirmedString;
    private  Boolean ifReadyToAddLine = false;
    private Boolean ArrowedToUpdateFriendsMsg = true;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

chatInterfaceBinding = DataBindingUtil.setContentView(this,R.layout.activity_chat_interface);
        infoText = chatInterfaceBinding.infoText;
        writeButton = chatInterfaceBinding.button;
        msgRecyclerView = chatInterfaceBinding.chatsList;
        EditMsg = chatInterfaceBinding.inputMsg;
        //start the service


        if(!this.ifServiceExisted("services.MqttConnectionService")) {

            Intent intent = new Intent(ChatInterfaceActivity.this, MqttConnectionService.class);


            startService(intent);


        }
        else {

            Log.i("info","The service has started!!!!!");
        }











        //transition enter action

        int position = getIntent().getIntExtra("position",0);
        info = infosDao.getTheList().get(position);

         infoText.setText(info.getFriend_name());



         transitionHelper.setSharedElementEnterTransition(this,R.transition.detail_shared_element_enter_transition);






        myBroadCast = new MyBroadCast();

        //
        intentFilter = new IntentFilter();
        intentFilter.addAction("com.sendValue.value");


        //RegisterBroadCast


        registerReceiver(myBroadCast,intentFilter);












        //SET LAYOUT

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        msgRecyclerView.setLayoutManager(layoutManager);



        adapter = new MsgAdapter(theList); //Create the instance of the adapter and upload the data

        msgRecyclerView.setAdapter(adapter);
        msgRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(10,10,10,10);
            }
        });





        // The button to start to write something

        writeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);

                writeButton.setVisibility(View.GONE);


            }
        });



       // PrintTheServices();






        //Listener of sending message
        //EditMsg.
        EditMsg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {



            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

              Boolean ifPublished =  MqttManager.getInstance().publish("tyler",1,charSequence.toString().getBytes());


            /*    if(ifPublished){

                    Log.i("info","Published");
                }



*/
                confirmedString = charSequence.toString();

            }

            @Override
            public void afterTextChanged(Editable editable) {



            }
        });



// logic to confirm my message


        writeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ifFriendsConfirmedMessage){
                    theList.add(new ChatMsg(confirmedString,ChatMsg.TYPE_SEND));
                    adapter.notifyItemInserted(theList.size() - 1);
                    msgRecyclerView.scrollToPosition(theList.size() - 1);
                    ifReadyToAddLine = true;
                    EditMsg.setText("");

                }

                 else {

                    theList.add(theList.get(theList.size()-1));
                    theList.set(theList.size() - 2 ,new ChatMsg(confirmedString,ChatMsg.TYPE_SEND));
                    adapter.notifyDataSetChanged();
                    msgRecyclerView.scrollToPosition(theList.size() - 1);
                    EditMsg.setText("");



                }





            }
        });



    }



































































//After you recieve the broadcast
    class  MyBroadCast extends BroadcastReceiver{


        @Override
        public void onReceive(Context context, Intent intent) {




            Log.i("info","I have recieved the BRODCAST");
        byte[] bytes = intent.getByteArrayExtra("value");

            Log.i("info","I have recieved the BRODCAST+++++" + new String(bytes));

            try {


                //If the messages is empty , create the new item of the recyclerview , or just change the text of the item

                if (theList.size() <= 0 | ifReadyToAddLine ) {
                    theList.add(new ChatMsg(new String(bytes), ChatMsg.TYPE_RECEVIED));
                    adapter.notifyItemInserted(theList.size() - 1);
                    ifReadyToAddLine = false;
                }
                else {

                    theList.set(theList.size() - 1, new ChatMsg(new String(bytes), ChatMsg.TYPE_RECEVIED));
                   // theList.
                    adapter.notifyItemChanged(theList.size() - 1);
                }



                //adapter.notifyItemInserted(theList.size() - 1);
                msgRecyclerView.scrollToPosition(theList.size() - 1);

            }catch (Exception E){
                Log.i("info","I have recieved the BRODCAST------"+E);

            }

        }
    }

    //Intent intent = new Intent(ChatInterfaceActivity.this, MqttConnectionService.class);


//To check if the service exist
    private boolean ifServiceExisted(String className){
        ActivityManager myMannger = (ActivityManager) ChatInterfaceActivity.this
                .getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);

        ArrayList<ActivityManager.RunningServiceInfo> runningServices =(ArrayList<ActivityManager.RunningServiceInfo>) myMannger.getRunningServices(300);

        for (int i = 0 ; i< runningServices.size();i++){

            if(runningServices.get(i).service.getClassName().equals(className))
            {
                return true;


            }

        }


        return false;

    }






//Print out the name of the services


    private void PrintTheServices(){
        ActivityManager myMannger = (ActivityManager) ChatInterfaceActivity.this
                .getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);

        ArrayList<ActivityManager.RunningServiceInfo> runningServices =(ArrayList<ActivityManager.RunningServiceInfo>) myMannger.getRunningServices(3000);


        for (int i = 0 ; i< runningServices.size();i++){

         Log.i("services", "MMMMMMM"+runningServices.get(i).service.getClassName() + "TTTTTTTT");


        }






    }


    @Override
    protected void onPause() {
        super.onPause();

        unregisterReceiver(myBroadCast);
    }
}
