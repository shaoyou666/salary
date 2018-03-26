package com.app.yoo.salary;

import android.app.Application;

/**
 * Created by csyoo on 2018/3/23.
 */

public class MyApp extends Application {
    private String systemIP = "http://10.17.181.248/gz",
            proxyIP = "119.146.233.94",
            proxyPort = "808",
            proxyUser = "gz",
            proxyPassword = "swdx123!";


    public void setSetData(String sip,String pip,String pport,String puser,String ppwd){
        this.systemIP = sip;
        this.proxyIP = pip;
        this.proxyUser = puser;
        this.proxyPassword = ppwd;
        this.proxyPort = pport;
    }
    public String getSystemIP(){
        return this.systemIP;
    }
    public String getProxyIP(){
        return this.proxyIP;
    }
    public String getProxyUser(){
        return this.proxyUser;
    }
    public String getProxyPassword(){
        return this.proxyPassword;
    }
    public String getProxyPort(){
        return this.proxyPort;
    }
}
