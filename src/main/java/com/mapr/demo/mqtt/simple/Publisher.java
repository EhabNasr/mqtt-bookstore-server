package com.mapr.demo.mqtt.simple;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.Scanner;

public class Publisher {
    public static boolean acknowledge = false;

  public static void main(String[] args) {
    sendToRobot("r", 1);
      sendToRobot("l", 1);

  }

    private static boolean sendToRobot (String move, int robot_id){
        String topic = "alfred/walk/" + Integer.toString(robot_id);
        String broker       = "tcp://m12.cloudmqtt.com:17492";
        String clientId     = "234";
        int qos             = 0;
//        MemoryPersistence persistence = new MemoryPersistence();
        try {

            MqttConnectOptions conOpt = new MqttConnectOptions();
            conOpt.setCleanSession(true);
            conOpt.setUserName("qldkjxzp");
            conOpt.setPassword("gK5_OW6wcgAd".toCharArray());

            MqttClient subscriber_client=new MqttClient(broker, MqttClient.generateClientId(),new MemoryPersistence());
            SimpleMqttCallBack myCallBack = new SimpleMqttCallBack();
            subscriber_client.setCallback( myCallBack );

            MqttClient publisher_client = new MqttClient(broker, clientId, new MemoryPersistence());

            System.out.println("Connecting to broker: "+broker);
            publisher_client.connect(conOpt);
            subscriber_client.connect(conOpt);
            subscriber_client.subscribe("acc/here/" + Integer.toString(robot_id));
            System.out.println("Connected");
            System.out.println("Publishing message: " + move);
            MqttMessage message = new MqttMessage(move.getBytes());
            message.setQos(qos);
            publisher_client.publish(topic, message);
            System.out.println("Message published");
            while (Publisher.acknowledge == false){

            }
            Publisher.acknowledge = false;
            System.out.println("I'm here 1");
            publisher_client.disconnect();
            subscriber_client.disconnect();

            return true;

        }catch(MqttException me) {
            System.out.println("reason "+me.getReasonCode());
            System.out.println("msg "+me.getMessage());
            System.out.println("loc "+me.getLocalizedMessage());
            System.out.println("cause "+me.getCause());
            System.out.println("excep "+me);
            me.printStackTrace();
        }

        return false;
    }
}