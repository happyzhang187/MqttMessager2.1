package services;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.guston.signup2.ChatInterfaceActivity;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.ArrayList;

import pahodata.MqttManager;

/**
 * Created by Guston on 04/03/18.
 */

public class MqttConnectionService extends Service {


    //new thread setting
    HandlerThread thread = new HandlerThread("MyHandlerThread");





    private MqttManager mqttManager;
    private String brokerUrl = "tcp://192.168.43.37:1883";
    private String username = "test";
    private  String password = "test";
    private  String clientId = "server";



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {



        super.onCreate();
    }


    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("Info","Services here we run");



        thread.start();
        Handler handlerelse = new Handler(thread.getLooper());
        handlerelse.post(new Runnable() {
            @Override
            public void run() {





                mqttManager = MqttManager.getInstance();
                mqttManager.createConnect(brokerUrl,username,password,clientId);

                if(mqttManager.getClient() != null) {


                    if (!mqttManager.getClient().isConnected()) {
                        //mqttManager.createConnect(brokerUrl, username, password, clientId);
                        mqttManager.doConnect();







                        mqttManager.getClient().setCallback(new MqttCallback() {
                            @Override
                            public void connectionLost(Throwable cause) {

                                mqttManager.createConnect(brokerUrl, username, password, clientId);
                                mqttManager.doConnect();




                            }

                            @Override
                            public void messageArrived(String topic, MqttMessage message) throws Exception {


                                Intent intent1 = new Intent("com.sendValue.value");
                                intent1.putExtra(topic, message.getPayload());
                                intent1.putExtra("topic", topic);
                                sendBroadcast(intent1);


                                Log.i("info","YOU HAVE recieved message" + message.getPayload().toString());

                            }

                            @Override
                            public void deliveryComplete(IMqttDeliveryToken token) {

                            }
                        });

                        if(!mqttManager.getClient().isConnected()){

                            Log.i("Info","You haven't connected the server22222222");

                        }
                        else {

                            Log.i("Info","You have connected the server.YES!!!!!!!");

                        }






                    }
                    else {


                        mqttManager.getClient().setCallback(new MqttCallback() {
                            @Override
                            public void connectionLost(Throwable cause) {

                                mqttManager.createConnect(brokerUrl, username, password, clientId);
                                mqttManager.doConnect();




                            }

                            @Override
                            public void messageArrived(String topic, MqttMessage message) throws Exception {


                                Intent intent1 = new Intent("com.sendValue.value");
                                intent1.putExtra("value", message.getPayload());
                                intent1.putExtra("topic", topic);
                                sendBroadcast(intent1);


                                Log.i("info","YOU HAVE recieved message" + new String(message.getPayload()));

                            }

                            @Override
                            public void deliveryComplete(IMqttDeliveryToken token) {

                            }
                        });


























                        Log.i("Info","You have connected the server yet!1111111");




                    //Subscribe to te broker
                     Boolean isSubcribed =   mqttManager.Subscribe("tyler",1);
                     if(isSubcribed){

                         Log.i("info","You have subscribed it successfully");

                     }
                     else {

                         Log.i("info","You haven't  subscribed it yet! ");


                     }








                    }



                }

                else {

                    Log.i("Info","The MqttClient is null");


                }




























            }
        });


































        return START_STICKY;
    }










}
