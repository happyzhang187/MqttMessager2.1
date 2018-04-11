package pahodata;

import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;

/**
 * Created by Guston on 04/03/18.
 */

public class ConnectServer {
    private  String brokerUrl;
    private  String username;
    private  String password;
    private  String clientId;
    private MqttClient client;
    private MqttCallback mCallback;
    private MqttConnectOptions conOpt;


    public  ConnectServer(String brokerUrl , String userName , String password , String clientId){

        this.brokerUrl = brokerUrl;
        this.username = userName;
        this.password = password;
        this.clientId = clientId;


    }


    public void doConnect(){



    }
}
