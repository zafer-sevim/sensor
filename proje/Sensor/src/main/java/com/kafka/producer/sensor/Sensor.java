
package com.kafka.producer.sensor;

import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

public class Sensor {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        System.out.println("Sensör adını girin");
        String sensorad = in.next();

        System.out.println("Sensör x koordinatını girin");
        int sensor1x = Integer.parseInt(in.next());

        System.out.println("Sensör y koordinatını girin");
        int sensor1y = Integer.parseInt(in.next());

        int hedefx = -10;
        int hedefy = 50;

        int s1x = Math.abs(sensor1x - hedefx);
        int s1y = Math.abs(sensor1y - hedefy);

        double s1aci = Math.toDegrees(angle(s1x, s1y));

        if (hedefy >= 0) {
            if (sensor1x > hedefx) {
                s1aci = 360 - s1aci;
            }

        } else {
            if (sensor1x < hedefx) {
                s1aci = 90 + s1aci;
            }
        }
        System.out.println("kerteriz :" + s1aci);
        runProducer(s1aci, sensorad, sensor1x, sensor1y);
    }

    public static double angle(double b, double c) {
        double a = Math.sqrt(Math.pow(b, 2) + Math.pow(c, 2));
        return Math.acos((Math.pow(a, 2) + Math.pow(b, 2) - Math.pow(c, 2)) / (2 * a * b));

    }

    static void runProducer(double derece, String sensorad, int x, int y) {
        Producer<Long, String> producer = ProducerCreator.createProducer();

        try {
            ProducerRecord<Long, String> record = new ProducerRecord<Long, String>(IKafkaConstants.TOPIC_NAME,
                    sensorad + " sensörü x ekseninde " + x + " y ekseninde " + y + " kerteriz acisi: " + derece);
            RecordMetadata metadata = producer.send(record).get();
        } catch (ExecutionException e) {
            System.out.println("Error in sending record");
            System.out.println(e);
        } catch (InterruptedException e) {
            System.out.println("Error in sending record");
            System.out.println(e);
        }
    }
}
