package cn.buli_home.utils.net.http;

import javax.net.ssl.*;
import java.security.cert.X509Certificate;


public class HttpClientConfig {

    // 获取SSLSocketFactory
    public static SSLSocketFactory sslSocketFactory() {
        try {
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustManager(), new java.security.SecureRandom());
            return sslContext.getSocketFactory();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // 获取TrustManager
    private static TrustManager[] trustManager() {
        return new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                    }

                    @Override
                    public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new java.security.cert.X509Certificate[]{};
                    }
                }
        };
    }

    // 获取HostnameVerifier
    public static HostnameVerifier hostnameVerifier() {
        return (s, sslSession) -> true;
    }

    public static X509TrustManager x509TrustManager() {
        return ((X509TrustManager) trustManager()[0]);
    }

}
