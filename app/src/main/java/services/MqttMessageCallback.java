package services;

import android.content.Context;
import android.content.Intent;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import pahodata.MqttManager;

/**
 * Created by Guston on 05/03/18.
 */

public class MqttMessageCallback implements MqttCallback {

    private Context context;
    private MqttManager mqttManager;

    public  MqttMessageCallback(Context context){
        this.context = context;

    }
    @Override
    public void connectionLost(Throwable cause) {
        mqttManager = MqttManager.getInstance();
        mqttManager.doConnect();

    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {

        Intent intent1 = new Intent("com.sendValue.value");
        intent1.putExtra(topic,message.getPayload());
        context.sendBroadcast(intent1);



    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {





    }
}
