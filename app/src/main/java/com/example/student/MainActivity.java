package com.example.student;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
public class MainActivity extends AppCompatActivity {
ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.a);
        Thread myThread = new Thread() {
            public void run() {
                try
                {
                    Thread.sleep(3000);
                    Intent intent = new Intent(MainActivity.this, DengluActivity.class);
                    startActivity(intent);
                    finish();
                } catch (
                        InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        };
        myThread.start();
    }
}
