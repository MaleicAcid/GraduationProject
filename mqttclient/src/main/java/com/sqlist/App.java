package com.sqlist;

import com.alibaba.fastjson.JSON;
import com.sqlist.mqtt.Client;
import com.sqlist.mqtt.DeviceInfo;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.io.*;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
//        String deviceInfoStr = readDeviceInfo(args[0]);
//        DeviceInfo deviceInfo = JSON.parseObject(deviceInfoStr, DeviceInfo.class);

        DeviceInfo deviceInfo = new DeviceInfo();
        deviceInfo.setProductKey("a73a00b320794a66b15eb64486168f1e");
        deviceInfo.setDeviceKey("0eec30b5528348ef8ef70a8bbcd00f75");
        deviceInfo.setDeviceSecret("25013b9a8db14a2b8382ffab0f802b9e");

        try {
            Client.init(deviceInfo);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    private static String readDeviceInfo(String path) {
        InputStreamReader reader = null;
        BufferedReader bufReader = null;
        try {
            File filename = new File(path);
            reader = new InputStreamReader(new FileInputStream(filename));
            bufReader = new BufferedReader(reader);
            String line = "";
            StringBuilder result = new StringBuilder();
            while ((line = bufReader.readLine()) != null) {
                result.append(line);
            }
            return result.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufReader != null){
                    bufReader.close();
                }
                if (bufReader != null){
                    bufReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }
}
