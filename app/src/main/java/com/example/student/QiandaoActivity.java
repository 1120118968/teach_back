package com.example.student;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
public class QiandaoActivity extends AppCompatActivity {
    TextView tv_shuziqiandao;
    String name;
    String id;
    String psd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qiandao);
        Intent i = getIntent();
        name = i.getStringExtra("name");
        id = i.getStringExtra("id");
        psd = i.getStringExtra("psd");
        tv_shuziqiandao = (TextView)findViewById(R.id.tv_shuziqiandao);
        tv_shuziqiandao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent szqd= new Intent(QiandaoActivity.this,ShuziActivity.class);
                szqd.putExtra("name",name);
                szqd.putExtra("id",id);
                szqd.putExtra("psd",psd);
                startActivity(szqd);
            }
        });
    }
}
