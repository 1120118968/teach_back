package com.example.student;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
public class ZhuceActivity extends AppCompatActivity {
    private static final int PORT = 8888;
    private static final String HOST = "192.168.191.1";
    public String Attribute;
    public String name;
    public String id;
    public String password1;
    public String password2;
    public String command;
    EditText edt_name;
    EditText edt_id;
    EditText edt_password1;
    EditText edt_password2;
    Button Registe;
    Handler handler;
    ClientThread clientThread;
    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhuce);
        edt_id = findViewById(R.id.id);
        edt_name = findViewById(R.id.name);
        edt_password1 = findViewById(R.id.password1);
        edt_password2 = findViewById(R.id.password2);
        Registe = findViewById(R.id.register);
        new Thread(clientThread).start();//①
        handler = new Handler()//①
        {

            @Override
            public void
            handleMessage(Message msg) {
                if (msg.what == 0x1234) {
                    Intent i = new Intent(ZhuceActivity.this, DengluActivity.class);
                    Toast.makeText(ZhuceActivity.this, "注册成功，请登录", Toast.LENGTH_SHORT).show();
                    startActivity(i);//start LoginActivity
                    finish();
                } else if (msg.what == 0x000){
                    Toast.makeText(ZhuceActivity.this, "注册失败,密码或用户名错误", Toast.LENGTH_SHORT).show();
                }
            }


        };
        clientThread = new ClientThread(handler);
        new Thread(clientThread).start();//
        Registe.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("HandlerLeak")
            @Override
            public void onClick(View v) {
                name = edt_name.getText().toString();
                System.out.print(name);
                id = edt_id.getText().toString();
                System.out.print(id);
                password1 = edt_password1.getText().toString();
                System.out.print(password1);
                password2 = edt_password2.getText().toString();
                System.out.print(password2);
                command = "注册";
                Attribute = "学生";
                Message msg = new Message();
                msg.what = 0x345;
                msg.obj = name + " " + id + " " + password1 + " " + command + " " + Attribute;
                clientThread.revHandler.sendMessage(msg);
            }


        });
    }
    @SuppressLint("HandlerLeak")
    public void getServerMessage() {
        handler = new
                Handler()//①


                {

                    @Override
                    public void
                    handleMessage(Message msg) {
                        if (msg.what == 0x123) {
                            String message = msg.obj.toString();
                            Toast.makeText(ZhuceActivity.this, " " + message, Toast.LENGTH_SHORT).show();
                        }
                    }


                };
    }
}
