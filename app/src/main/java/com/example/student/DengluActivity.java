package com.example.student;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
public class DengluActivity extends Activity {
    Button btn;
    EditText edt_name;
    EditText edt_psd;
    EditText edt_id;
    TextView tvwj, tvzc;
    String name, psd, id;
    String command;
    String Attribute;
    Handler handler;
    ClientThread clientThread;
    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_denglu);
        //new Thread(clientThread).start();//①android studio ctrl+alt+l 规范化文本格式 ECLIPSE CTRL+SHIFT+F
        handler = new Handler()//①
        {

            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0x123) {
                    Intent i = new Intent(DengluActivity.this, APPMainActivity.class);
                    i.putExtra("name",name);
                    i.putExtra("id",id);
                    i.putExtra("psd",psd);
                    startActivity(i);//启动主界面
                    finish();
                    Toast.makeText(DengluActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                } else if(msg.what == 0x000){
                   Toast.makeText(DengluActivity.this, "密码或用户名错误", Toast.LENGTH_SHORT).show();
                }
            }



        };
        btn = (Button) findViewById(R.id.btn_login);
        tvwj = (TextView) findViewById(R.id.textView4);
        tvzc = (TextView) findViewById(R.id.textView5);
        edt_name = findViewById(R.id.edt_name);
        edt_psd = findViewById(R.id.edt_psd);
        edt_id = findViewById(R.id.edt_id);
        clientThread = new ClientThread(handler);
        new Thread(clientThread).start();//
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = edt_name.getText().toString();
                psd = edt_psd.getText().toString();
                id = edt_id.getText().toString();
                command = "登录";
                Attribute = "学生";
                Message msg = new Message();
                msg.what = 0x345;
                msg.obj = name + " " + id + " " + psd + " " + command + " " + Attribute;

                clientThread.revHandler.sendMessage(msg);
            }
        });
        tvwj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DengluActivity.this, WangjiActivity.class);
                startActivity(intent);
            }
        });
        tvzc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DengluActivity.this, ZhuceActivity.class);
                startActivity(intent);
            }
        });
    }
}
