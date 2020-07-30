package com.example.student;
import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
public class ClientThread implements Runnable {
    private Socket s;
    public String Attribute;
    public String content = null;
    String name;
    String password;
    String id;
    String commend;
    private Handler handler;
    public Handler revHandler;
    BufferedReader br = null;
    OutputStream os = null;
    @SuppressLint("HandlerLeak")
    public ClientThread(Handler handler) {
         this.handler = handler;

    }
    @SuppressLint("HandlerLeak")
    public void run() {
        try {
            //192.168.191.2为本机的ip地址，30000为与MultiThreadServer服务器通信的端口
            s = new Socket("47.93.221.25", 8888);
            br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            os = s.getOutputStream();
            new Thread() {
                @Override
                public void run() {
                    try {
                        while ((content = br.readLine()) != null) {
                            Message msg = new Message();
                            msg.obj = content;
                            String[] strarray = content.split(" ");
                            for (int i = 0; i < strarray.length; i++)
                                System.out.println(strarray[i] + " ");
                            System.out.println(content);
                            String command = strarray[2];
                            if (command.equals("Ys")) {//login
                                //if psd=s psd
                                msg.what = 0x123;
                                msg.obj = content;
                                if(handler!=null){
                                    handler.sendMessage(msg);}
                            } else if (command.equals("YRs")){//register
                                msg.what = 0x1234;
                                msg.obj = content;
                                if(handler!=null){
                                    handler.sendMessage(msg);}
                            } else if ( command.equals("SQ")){//qd
                                msg.what = 0x12;
                                msg.obj = content;
                                if(handler!=null){
                                    handler.sendMessage(msg);}
                            }else if (command.equals("SQY")){//签到成功
                                msg.what = 0x12345;
                                msg.obj = content;
                                if(handler!=null){
                                    handler.sendMessage(msg);}
                            }else if (command.equals("SR")){//得到题目成功
                                msg.what = 0x111;
                                msg.obj = content;
                                if(handler!=null){
                                handler.sendMessage(msg);}
                            }
                            else{
                                //warring
                                msg.what = 0x000;
                                msg.obj = content;
                                if(handler!=null){
                                    handler.sendMessage(msg);}
                            }
                        }
                    } catch (IOException
                            e) {

                        e.printStackTrace();
                    }
                }
            }.start();
            Looper.prepare();

            revHandler = new Handler() {

                @Override
                public void handleMessage(Message msg) {
                    if (msg.what == 0x345) {
                        String ip = s.getInetAddress().getHostAddress();
                        try {
                            os.write((msg.obj.toString()+" "+ip+ "\r\n").getBytes("utf-8"));
                        } catch (Exception
                                e) {
                            e.printStackTrace();
                        }
                    }
                }

            };Looper.loop();
        } catch (SocketTimeoutException e1) {
            System.out.println("网络连接超时！！");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}

