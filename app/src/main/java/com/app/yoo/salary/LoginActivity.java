package com.app.yoo.salary;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.Properties;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText et_username,et_password;
    private Button bt_login,bt_set;
    private WebView web;
    private MyApp myApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        myApp = (MyApp)getApplication();
        setProxy();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findView();

        String systemIP = myApp.getSystemIP();

        configWebView(web, false);
        synCookies(this, systemIP, "uid=11");
        synCookies(this, systemIP, "name=xyw");
        synCookies(this, systemIP, "agent=android");
        synCookies(this, systemIP, "age=20;sex=1;time=today");

        web.loadUrl(myApp.getSystemIP()+"/login.asp");
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = et_username.getText().toString();
                String password = et_password.getText().toString();

                new LoginTask(username,password).execute((Void) null);

            }
        });
        bt_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,SetActivity.class);
                startActivity(intent);
            }
        });
    }

    public class LoginTask extends AsyncTask<Void,Void,String>{

        private String username,password;
        private String result = null;
        LoginTask(String user,String pwd){
            this.username = user;
            this.password = pwd;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            if(s != null){
                if(s.contains("重新登陆")){
                    Toast.makeText(LoginActivity.this, "登录成功！！", Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        protected String doInBackground(Void... voids) {
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    //.proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(myApp.getProxyIP(), 808)))
                    .build();
            RequestBody requestBody = new FormBody.Builder()
                    .add("usrid", username)
                    .add("password", password)
                    .add("Submit", "%C8%B7%B6%A8")
                    .build();
            Request request = new Request.Builder()
                    .url(myApp.getSystemIP() + "/check.asp")
                    .post(requestBody)
                    .addHeader("Content-Type","application/x-www-form-urlencoded")
                    .addHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.108 Safari/537.36")
                    .addHeader("Referer","http://10.17.181.248/gz/login.asp")
                    .build();
            Call call = okHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    //Toast.makeText(LoginActivity.this, "连接不上工资系统服务器！", Toast.LENGTH_SHORT).show();
                    Log.i("Callback","连接不上工资系统服务器！");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    byte[] reByte = response.body().bytes();
                    result = new String(reByte,"gb2312");
                    Log.i("Callback",result);
                }
            });
            return result;
        }
    }
    public void setProxy(){
        Properties prop = System.getProperties();
        prop.setProperty("proxySet", "true");
        prop.setProperty("proxyHost", myApp.getProxyIP());
        prop.setProperty("proxyPort", myApp.getProxyPort());
//        prop.setProperty("proxyUser", myApp.getProxyUser());
//        prop.setProperty("proxyPassword", myApp.getProxyPassword());
    }
    public void findView(){
        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);
        bt_login = findViewById(R.id.bt_login);
        bt_set = findViewById(R.id.bt_set);
        web = findViewById(R.id.webview);
    }
    public void synCookies(Context context, String url, String cookie) {
        CookieSyncManager.createInstance(context);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.setCookie(url, cookie);//cookies是在HttpClient中获得的cookie
        CookieSyncManager.getInstance().sync();
    }
    protected void configWebView(WebView webView, boolean needCache) {

        if (!needCache) {
            webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        }

        webView.getSettings().setJavaScriptEnabled(true);
        //启用数据库
        webView.getSettings().setDatabaseEnabled(true);
        //设置定位的数据库路径
        String dir = this.getApplicationContext().getDir("database", Context.MODE_PRIVATE).getPath();
        webView.getSettings().setGeolocationDatabasePath(dir);
        //启用地理定位
        webView.getSettings().setGeolocationEnabled(true);
        //开启DomStorage缓存
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.setWebViewClient(new WebViewClient(){});
        webView.setWebChromeClient(new WebChromeClient());
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && web.canGoBack()) {
            web.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
