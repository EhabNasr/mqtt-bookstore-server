package com.mapr.demo.mqtt.simple;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class Subscriber {

  public static void main(String[] args) throws MqttException {

    System.out.println("== START SUBSCRIBER ==");

    MqttClient client=new MqttClient("tcp://m12.cloudmqtt.com:17492", MqttClient.generateClientId(),new MemoryPersistence());
    client.setCallback( new SimpleMqttCallBack() );

    MqttConnectOptions conOpt = new MqttConnectOptions();
    conOpt.setCleanSession(true);
    conOpt.setUserName("qldkjxzp");
    conOpt.setPassword("gK5_OW6wcgAd".toCharArray());

    client.connect(conOpt );

    client.subscribe("alfred/walk");

    /*String topic        = "alfred/walk";
    int qos             = 0;
    String broker       = "tcp://m12.cloudmqtt.com:17492";
    String clientId     = "234";
    MemoryPersistence persistence = new MemoryPersistence(); */



  }

}
