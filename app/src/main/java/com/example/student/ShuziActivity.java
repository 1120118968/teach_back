package com.example.student;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
public class ShuziActivity extends Activity {
    private boolean flag = true;
    ImageButton IMbuttn;
    String command;
    String Attribute;
    Handler handler;
    String  name;
    String id;
    String psd;
    ClientThread clientThread;
    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shuzi);
        new Thread(clientThread).start();//①android studio ctrl+alt+l 规范化文本格式 ECLIPSE CTRL+SHIFT+F
        handler = new Handler()//①
        {

            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0x12345) {
                    Toast.makeText(ShuziActivity.this,"签到成功！",Toast.LENGTH_SHORT).show();
                } else if(msg.what == 0x000){
                    Toast.makeText(ShuziActivity.this, "签到失败", Toast.LENGTH_SHORT).show();
                }
            }


        };
        Intent i = getIntent();
        name=i.getStringExtra("name");
        id=i.getStringExtra("id");
        psd=i.getStringExtra("psd");
        IMbuttn = (ImageButton)findViewById(R.id.myImg);
        handler = new Handler()//①
        {

            @Override
            public void
            handleMessage(Message msg) {
                if (msg.what == 0x12) {
                   flag = true;
                } else if(msg.what == 0x000){
                    flag = false;
                }
            }


        };
        if(flag){
            IMbuttn.setImageResource(R.mipmap.studying);
            IMbuttn.setVisibility(View.VISIBLE);
        }else{
            IMbuttn.setImageResource(R.drawable.bac);
            IMbuttn.setVisibility(View.INVISIBLE);
        }
                flag = !flag;
        IMbuttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message msg = new Message();
                msg.what = 0x345;//签到成功
                command ="签到";
                Attribute = "学生";
                msg.obj = name + " " + id + " " + psd + " " + command + " " + Attribute;
                clientThread = new ClientThread(handler);
                new Thread(clientThread).start();//
                clientThread.revHandler.sendMessage(msg);
            }
        });
    }
}
