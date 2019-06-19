package com.example.acer.jm_pos.utility;

import com.android.volley.toolbox.HurlStack;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;

public class ProxyHurlStack extends HurlStack {

    @Override
    protected HttpURLConnection createConnection(URL url) throws IOException {
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("192.168.42.111", 80));
        HttpURLConnection conn = (HttpURLConnection) url.openConnection(proxy);
        return conn;
    }
}