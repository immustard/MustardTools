package org.buli.apps;

import org.buli.utils.net.http.*;

public class HttpApp {

    public static void main(String[] args) {
        HttpRequest request = new HttpRequest.Builder()
                .url("http://10.133.115.12:12345/dolphinscheduler/resources/list")
                .requestMode(HttpRequestMode.ASYNCHRONOUS)
                .build();
        request.addQueryParameter("type", "FILE");
        request.addHeader("token", "d24f33949594c44f18870d5e024d183e");

        HttpUtils.request(request);

    }

}
