package com.pc.webview_js;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.GeolocationPermissions;
import android.webkit.JavascriptInterface;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private WebView mWv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        //支持js交互
        WebSettings settings = mWv.getSettings();
        settings.setJavaScriptEnabled(true);

        mWv.addJavascriptInterface(this,"android");

        //设置弹出Alert
        mWv.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return false;
            }

            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
                return super.onJsPrompt(view, url, message, defaultValue, result);
            }

            @Override
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                callback.invoke(origin,true,false);
                super.onGeolocationPermissionsShowPrompt(origin, callback);
            }
        });

        //加载js网页
        mWv.loadUrl("file:///android_asset/js.html");

        //设置webView长按点击事件，Android调用js
        mWv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                //Android调用js
                mWv.loadUrl("javascript:test1()");
                //Toast.makeText(MainActivity.this, "123456", Toast.LENGTH_SHORT).show();

                return false;
            }
        });

    }

    //js调用android
    @JavascriptInterface
    public void jump(){
        Toast.makeText(this, "js调用了Android的方法", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity.this,Main2Activity.class);
        startActivity(intent);
    }
    //js调用android
    @JavascriptInterface
    public void show(){
        Toast.makeText(this, "Android调用js之后，js又调用Android的方法", Toast.LENGTH_SHORT).show();
    }

    private void initView() {
        mWv = (WebView) findViewById(R.id.wv);
    }
}
