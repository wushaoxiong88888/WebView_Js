package com.pc.webview_js;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {

    private WebView mWv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initView();

        //支持js交互
        mWv.getSettings().setJavaScriptEnabled(true);

        mWv.loadUrl("file:///android_asset/infos.html");

        mWv.addJavascriptInterface(this,"android");

    }

    @JavascriptInterface
    public void toastMessage(String name,String pwd){
        Toast.makeText(this, "姓名:"+name+",密码:"+pwd, Toast.LENGTH_SHORT).show();
    }

    private void initView() {
        mWv = (WebView) findViewById(R.id.wv);
    }
}
