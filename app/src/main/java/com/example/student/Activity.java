package com.example.student;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
public class Activity extends AppCompatActivity {
    WebView mWebView;
    EditText src;
    String strSrc;
    Button enter;
        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_);
        mWebView = (WebView) findViewById(R.id.web_view);
        src = (EditText)findViewById(R.id.src);
        enter = findViewById(R.id.enter);
        strSrc = src.getText().toString();
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWebView.loadUrl(strSrc);
            }
        });
    }
}
