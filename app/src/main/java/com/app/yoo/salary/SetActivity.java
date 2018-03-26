package com.app.yoo.salary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SetActivity extends AppCompatActivity {

    private EditText et_sysIP,et_proxyIP,et_proxyUser,et_proxyPassword,et_proxyPort;
    private Button bt_saveSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        findView();
        bt_saveSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sysIp = et_sysIP.getText().toString();
                String proxyIp = et_proxyIP.getText().toString();
                String proxyUser = et_proxyUser.getText().toString();
                String proxyPassword = et_proxyPassword.getText().toString();
                String proxyPort = et_proxyPort.getText().toString();
                MyApp myApp = (MyApp)getApplication();
                myApp.setSetData(sysIp,proxyIp,proxyPort,proxyUser,proxyPassword);
                SetActivity.this.finish();
            }
        });
    }
    public void findView(){
        et_sysIP = findViewById(R.id.et_sysIP);
        et_proxyIP = findViewById(R.id.et_proxyIP);
        et_proxyUser = findViewById(R.id.et_proxyUser);
        et_proxyPassword = findViewById(R.id.et_proxyPassword);
        bt_saveSet = findViewById(R.id.bt_saveSet);
        et_proxyPort = findViewById(R.id.et_proxyPort);
    }
}
