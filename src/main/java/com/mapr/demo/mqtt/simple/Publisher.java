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

  public static void main(String[] args) {

      String topic        = "alfred/walk";
    int qos             = 0;
    String broker       = "tcp://m12.cloudmqtt.com:17492";
    String clientId     = "234";
    MemoryPersistence persistence = new MemoryPersistence();

    try {
        MqttConnectOptions conOpt = new MqttConnectOptions();
        conOpt.setCleanSession(true);
        conOpt.setUserName("qldkjxzp");
        conOpt.setPassword("gK5_OW6wcgAd".toCharArray());

        MqttClient subscriber_client=new MqttClient(broker, MqttClient.generateClientId(),new MemoryPersistence());
        subscriber_client.setCallback( new SimpleMqttCallBack() );

        MqttClient publisher_client = new MqttClient(broker, clientId, new MemoryPersistence());

      System.out.println("Connecting to broker: "+broker);
      publisher_client.connect(conOpt);
      subscriber_client.connect(conOpt);
      subscriber_client.subscribe(topic);

        System.out.println("Connected");
          while (true) {
              System.out.print("Enter your command: " );
              Scanner s = new Scanner(System.in);
              String str = s.nextLine();
              if(str == "e\n") break;
              System.out.println("Publishing message: " + str);
              MqttMessage message = new MqttMessage(str.getBytes());
              message.setQos(qos);
              publisher_client.publish(topic, message);
              System.out.println("Message published");
          }
      publisher_client.disconnect();
      System.out.println("Disconnected");
      System.exit(0);
    } catch(MqttException me) {
      System.out.println("reason "+me.getReasonCode());
      System.out.println("msg "+me.getMessage());
      System.out.println("loc "+me.getLocalizedMessage());
      System.out.println("cause "+me.getCause());
      System.out.println("excep "+me);
      me.printStackTrace();
    }
  }
}