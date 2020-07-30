package com.example.student;
import android.annotation.SuppressLint;
import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TabHost;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;
import com.youth.banner.loader.ImageLoaderInterface;

import java.util.ArrayList;
public class APPMainActivity extends Activity  {
    ClientThread clientThread;
    Handler handler;
    private ViewPager mTabPager;
    private View layout;
    private boolean menu_display = false;
    private PopupWindow menuWindow;
    private LayoutInflater inflater;
    private ImageButton watchTitle;//
    private ImageButton qiandao;//签到
    private ImageButton luntan;
    String name;
    String id;
    String psd;
    private TabHost tab;
    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_main);
        Intent i = getIntent();
        name = i.getStringExtra("name");
        id = i.getStringExtra("id");
        psd = i.getStringExtra("psd");
        watchTitle = (ImageButton) findViewById(R.id.btn_teacher_title);
        qiandao = findViewById(R.id.ib_commonandcommunication);
        luntan = findViewById(R.id.ib_redPackageBtn);
        qiandao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(APPMainActivity.this,QiandaoActivity.class);
                i.putExtra("name",name);//数据从登录页面传至签到页面
                i.putExtra("id",id);
                i.putExtra("psd",psd);
                startActivity(i);
                finish();
            }
        });
        luntan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(APPMainActivity.this, com.example.student.Activity.class);
                startActivity(i);
                finish();
            }
        });
        clientThread = new ClientThread(handler);
        new Thread(clientThread).start();//
        watchTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(APPMainActivity.this, TestActivity.class);
                i.putExtra("name",name);//数据从登录页面传至签到页面
                i.putExtra("id",id);
                i.putExtra("psd",psd);
                startActivity(i);
                Toast.makeText(APPMainActivity.this, "开始课堂互动", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        tab = (TabHost) this.findViewById(android.R.id.tabhost);
        tab.setup();
        tab.addTab(tab.newTabSpec("tab1").setIndicator("首页", null).setContent(R.id.tab1));
        tab.addTab(tab.newTabSpec("tab2").setIndicator("好友", null).setContent(R.id.tab2));
        tab.addTab(tab.newTabSpec("tab3").setIndicator("动态", null).setContent(R.id.tab3));
        mTabPager = (ViewPager) this.findViewById(R.id.tabPager);
    }
}

