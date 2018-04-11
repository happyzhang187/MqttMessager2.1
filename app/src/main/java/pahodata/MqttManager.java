package pahodata;

import android.widget.Toast;

import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;

import java.util.logging.Logger;

/**
 * Created by Guston on 04/03/18.
 */

public class MqttManager {

    private static  MqttManager mInstance = null;
    private MqttCallback mqttCallback;
    public static MqttClient client ;
    private MqttConnectOptions connectOptions;
    private  boolean clean = true;



    public  static  MqttManager getInstance(){

        if(null == mInstance){
            mInstance = new MqttManager();
        }
        return  mInstance;

    }

    //Realease Singleton


    public MqttClient getClient(){




        return  client;

    }

    public static  void  release(){
        try{
            if(mInstance != null){

                mInstance.disconnect();
                mInstance = null;
                //logic to disconnect

            }

        }catch (Exception e){

        }




    }








    //Ready to connect to the Broker

    public  boolean createConnect(String brokerUrl , String username ,String password , String clientId){
        boolean flag = false;


        String tmpDir = System.getProperty("java.io.tmpdir");
        MqttDefaultFilePersistence dataStore = new MqttDefaultFilePersistence(tmpDir);



        try{
            connectOptions = new MqttConnectOptions();
            connectOptions.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1_1);
            connectOptions.setCleanSession(clean);
            if(password != null){
                connectOptions.setPassword(password.toCharArray());

            }
            if(username != null){
                connectOptions.setUserName(username);

            }

            client = new MqttClient(brokerUrl,clientId,dataStore);

            client.setCallback(mqttCallback);

            flag = doConnect();
        }
        catch (Exception e){

           // Logger.(e.getMessage());

        }


        return flag;

    }

    //Connect to The Broker
    public Boolean doConnect(){
        boolean flag = false;
        if(client != null){

            try {
                client.connect(connectOptions);

                flag = true;
            }
            catch (Exception e){

            }

        }
        return  flag;
    }







    //Publish message

    public  boolean publish (String topic , int qos , byte[] payload){
        boolean falg = false;

        if(client != null && client.isConnected()){


            MqttMessage message = new MqttMessage(payload);
            message.setQos(qos);

            try {
                client.publish(topic,message);
                falg = true;
            }catch (Exception e){


            }


        }

        return  falg;

    }

    //Subscribe to broker


    public Boolean Subscribe(String topic , int qos){
        Boolean flag = false;
        if(client != null && client.isConnected()){


            try {
                client.subscribe(topic,qos);

                flag = true;
            }catch (Exception e){}





        }

        return flag;
    }

    public void  disconnect() throws MqttException{
        if(client != null && client.isConnected()){

            client.disconnect();
        }

    }




}
