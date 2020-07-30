package com.example.student;
import android.annotation.SuppressLint;
import android.app.Activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
public class TestActivity extends Activity {
    ClientThread clientThread;
    Handler handler;
    private List<CheckBox> checkBoxList = new ArrayList<CheckBox>();
    String timu;//题目
    String titleid;//题号
    String title;//题目标题
    String answer;//答案
    String style;//题目类型
    String zs;//章数
    String js;//节数
    String blankAnswer;//填空题答案
    String sAnswer;//单选题答案
    StringBuffer sb;//多选题答案
    boolean flag = false;//答题情况
    CheckBox a;//a选项
    CheckBox b;
    CheckBox c;
    CheckBox d;//d选项
    TextView tihao;//显示题号
    TextView aneirong;//显示a选项
    TextView bneirong;
    TextView cneirong;
    TextView dneirong;//显示d选项
    EditText fillBlank;//填空答案
    Button next;
    String name, psd, id;
    String command;
    String Attribute;
    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ceshi);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        handler = new Handler()//①
        {
            //实例化Handler
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0x111) {
                    timu = msg.obj.toString();
                    System.out.print("接收到子线程消息" + timu);
                    String[] strarray1 = timu.split(",");
                    for (int i = 0; i < strarray1.length; i++)
                        System.out.println(strarray1[i] + " ");
                    String[] strarray2= strarray1[5].split(" ");
                    for (int i = 0; i < strarray2.length; i++)
                        System.out.println(strarray2[i] + " ");
                    timu = strarray1[1]+","+strarray1[2]+","+strarray1[3]+","+strarray1[4]+","+strarray1[5]+","+strarray2[0];
                    view(timu);
                }
            }
        };
        clientThread = new ClientThread(handler);//初始化线程
        new Thread(clientThread).start();//Executors.newCachedThreadPool();
        Intent i = getIntent();
        name = i.getStringExtra("name");
        id = i.getStringExtra("id");
        psd = i.getStringExtra("psd");
        command = "查询题目信息";
        Attribute = "学生";
        Message msg = new Message();//创建发送的消息
        msg.what = 0x345;//发送数据给子线程，并转发给服务器，开始搜索题目，反馈给学生
        msg.obj = name + " " + id + " " + psd + " " + command + " " + Attribute;
        //clientThread.revHandler.sendMessage(msg);
        dbHelper = new DatabaseHelper(TestActivity.this, "student", null, 1);
        db = dbHelper.getWritableDatabase();
        a = findViewById(R.id.c_a);
        b = findViewById(R.id.c_b);
        c = findViewById(R.id.c_c);
        d = findViewById(R.id.c_d);
        tihao = findViewById(R.id.tihao);
        aneirong = findViewById(R.id.aneirong);
        bneirong = findViewById(R.id.bneirong);
        cneirong = findViewById(R.id.cneirong);
        dneirong = findViewById(R.id.dneirong);
        next = findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             timuzhengquexing();
            }
        });
    }
    public void view(String timu) {
        String[] strarray1 = timu.split(",");
        for (int i = 0; i < strarray1.length; i++)
            System.out.println(strarray1[i] + " ");
        System.out.println(timu);
        titleid = strarray1[0];
        title = strarray1[1];
        answer = strarray1[2];
        style = strarray1[3];
        zs = strarray1[4];
        js = strarray1[5];
        if (style.equals("single")) {//单选
            String[] strarray2 = titleid.split(":");
            for (int i = 0; i < strarray2.length; i++)
                System.out.println(strarray1[i] + " ");
            System.out.println(title);
            String ax = strarray2[0];
            String bx = strarray2[1];
            String cx = strarray2[2];
            String dx = strarray2[3];
            aneirong.setText(ax);
            bneirong.setText(bx);
            cneirong.setText(cx);
            dneirong.setText(dx);
            tihao.setText(title);
        } else if (style.equals("double")) {//多选
            String[] strarray3 = title.split(";");//选项
            for (int i = 0; i < strarray3.length; i++)
                System.out.println(strarray3[i] + " ");
            System.out.println(title);
            String ax = strarray3[0];
            String bx = strarray3[1];
            String cx = strarray3[2];
            String dx = strarray3[3];
            aneirong.setText(ax);
            bneirong.setText(bx);
            cneirong.setText(cx);
            dneirong.setText(dx);
            checkBoxList.add(a);
            checkBoxList.add(b);
            checkBoxList.add(c);
            checkBoxList.add(d);
            StringBuffer sb = new StringBuffer();
            for (CheckBox checkbox : checkBoxList) {
                if (checkbox.isChecked()){
                    sb.append(checkbox.getText().toString() + " ");
                }
            }
            if (sb!=null && "".equals(sb.toString())){
                Toast.makeText(getApplicationContext(), "请至少选择一个", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getApplicationContext(), sb.toString(), Toast.LENGTH_SHORT).show();
            }
        } else {
       }
    }
    public void timuzhengquexing() {
        if (style.equals("single")) {
            a.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    a.setSelected(true);
                    b.setSelected(false);
                    c.setSelected(false);
                    d.setSelected(false);
                    sAnswer = "a";
                }
            });
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    b.setSelected(true);
                    c.setSelected(false);
                    d.setSelected(false);
                    a.setSelected(false);
                    sAnswer = "b";
                }
            });
            c.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    c.setSelected(true);
                    a.setSelected(false);
                    b.setSelected(false);
                    d.setSelected(false);
                    sAnswer = "c";
                }
            });
            d.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    d.setSelected(true);
                    b.setSelected(false);
                    c.setSelected(false);
                    a.setSelected(false);
                    sAnswer = "d";
                }
            });
            if (sAnswer == answer) {
                flag = true;
            } else {
                flag = false;
            }
            Toast.makeText(TestActivity.this, "当前为单选题，请选择正确答案", Toast.LENGTH_LONG).show();
        }else if(style.equals("double")){
        if (sb.equals(answer)) {
            flag = true;
        } else {

            flag = false;
        }
        Toast.makeText(TestActivity.this, "当前为多选题，请选择正确答案", Toast.LENGTH_LONG).show();
    }
        command = "答题结束反馈答题结果";
        Attribute = "学生";
        Message msg = new Message();
        msg.what = 0x345;
        psd = timu + ":" + zs + ":" + js + ":" + titleid + ":" + flag;
        msg.obj = name + " " + id + " " + psd + " " + command + " " + Attribute;
        clientThread.revHandler.sendMessage(msg);
    }
}


