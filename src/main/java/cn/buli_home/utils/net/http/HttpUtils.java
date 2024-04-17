package cn.buli_home.utils.net.http;

import cn.buli_home.utils.common.StringUtils;
import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * HTTP 请求工具类
 *
 * @author mustard
 * @version 1.0
 * Create by 2022/9/14
 */
@Slf4j
public class HttpUtils {

    private static OkHttpClient httpClient = new OkHttpClient.Builder().build();

    private static OkHttpClient sslHttpClient = new OkHttpClient.Builder()
            .sslSocketFactory(HttpClientConfig.sslSocketFactory(), HttpClientConfig.x509TrustManager())
            .hostnameVerifier(HttpClientConfig.hostnameVerifier())
            .build();

    private static List<Interceptor> interceptorList = new ArrayList<>();
    private static List<Interceptor> networkInterceptorList = new ArrayList<>();

    private static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
    private static final MediaType MEDIA_TYPE_GENERAL = MediaType.parse("application/octet-stream");
    private static final MediaType MEDIA_TYPE_IMAGE = MediaType.parse("image/png");

    /**
     * 发送http请求
     */
    public static HttpResponse request(HttpRequest httpRequest) {
        try {
            log.info("\uD83D\uDC4C\uD83D\uDC4C\uD83D\uDC4C okHttp send request: " + httpRequest);

            Request request = p_generateRequest(httpRequest);
            HttpResponse response = p_request(httpRequest, request);

            log.info("\uD83D\uDC4C\uD83D\uDC4C\uD83D\uDC4C okHttp receive response: " + response);

            return response;
        } catch (Exception e) {
            log.error("\uD83D\uDC4C\uD83D\uDC4C\uD83D\uDC4C " + e.getMessage());
            return p_errorResponse(httpRequest, e);
        }
    }

    /**
     * 添加拦截器 (根据`equals()`去重)
     * 添加之后需调用 `buildClient()` 生效
     */
    public static void addInterceptor(Interceptor... interceptors) {
        interceptorList = Stream.concat(
                interceptorList.stream(),
                Arrays.stream(interceptors)).distinct().collect(Collectors.toList()
        );

        log.info("\uD83D\uDC4C\uD83D\uDC4C\uD83D\uDC4C okHttp add interceptors: " + interceptorList);
    }

    /**
     * 添加 network 拦截器 (根据`equals()`去重)
     * 添加之后需调用 `buildClient()` 生效
     */
    public static void addNetworkInterceptor(Interceptor... interceptors) {
        networkInterceptorList = Stream.concat(
                networkInterceptorList.stream(),
                Arrays.stream(interceptors)).distinct().collect(Collectors.toList()
        );
        log.info("\uD83D\uDC4C\uD83D\uDC4C\uD83D\uDC4C okHttp add network interceptors: " + networkInterceptorList);
    }

    /**
     * 清空拦截器
     * 清空之后需调用 `buildClient()` 生效
     */
    public static void clearInterceptor() {
        interceptorList.clear();

        log.info("\uD83D\uDC4C\uD83D\uDC4C\uD83D\uDC4C okHttp clear interceptors.");
    }

    /**
     * 清空 network 拦截器
     * 清空之后需调用 `buildClient()` 生效
     */
    public static void clearNetworkInterceptor() {
        networkInterceptorList.clear();

        log.info("\uD83D\uDC4C\uD83D\uDC4C\uD83D\uDC4C okHttp clear network interceptors.");
    }

    /**
     * 构造 okHttp 请求客户端
     */
    public static void buildClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        OkHttpClient.Builder sslBuilder = new OkHttpClient.Builder()
                .sslSocketFactory(HttpClientConfig.sslSocketFactory(), HttpClientConfig.x509TrustManager())
                .hostnameVerifier(HttpClientConfig.hostnameVerifier());

        interceptorList.forEach(interceptor -> {
            builder.addInterceptor(interceptor);
            sslBuilder.addInterceptor(interceptor);
        });
        networkInterceptorList.forEach(interceptor -> {
            builder.addNetworkInterceptor(interceptor);
            sslBuilder.addNetworkInterceptor(interceptor);
        });

        httpClient = builder.build();
        sslHttpClient = sslBuilder.build();

        log.info("\uD83D\uDC4C\uD83D\uDC4C\uD83D\uDC4C okHttp build client finished: " + interceptorList.size() + "interceptors, " + networkInterceptorList.size() + "networkInterceptors");
    }

    // 请求底层方法
    private static HttpResponse p_request(HttpRequest httpRequest, Request request) {
        Call call = httpRequest.isIgnoreSSL()
                ? sslHttpClient.newCall(request)
                : httpClient.newCall(request);

        switch (httpRequest.getRequestMode()) {
            // 同步
            case SYNCHRONOUS: {
                try {
                    Response response = call.execute();

                    return p_convertResponse(response, httpRequest);
                } catch (IOException e) {
                    log.error("\uD83D\uDC4C\uD83D\uDC4C\uD83D\uDC4C " + e.getMessage());
                    return p_errorResponse(httpRequest, e);
                }
            }
            // 异步
            case ASYNCHRONOUS: {
                try {
                    HttpCallback callback = new HttpCallback();
                    call.enqueue(callback);

                    return p_convertResponse(callback.get(), httpRequest);
                } catch (Exception e) {
                    log.error("\uD83D\uDC4C\uD83D\uDC4C\uD83D\uDC4C " + e.getMessage());
                    return p_errorResponse(httpRequest, e);
                }
            }
            // 未知请求方式
            default:
                return p_errorResponse(httpRequest, new Exception("Unknown Request Mode!!!"));
        }
    }

    // okHttp.Response -> HttpResponse
    private static HttpResponse p_convertResponse(Response response, HttpRequest httpRequest) {
        HttpResponse httpResponse = new HttpResponse();

        httpResponse.setHttpRequest(httpRequest);
        httpResponse.setCode(response.code());
        httpResponse.setSuccess(response.code() / 100 == 2);

        try {
            String string = StringUtils.convert2String(response.body().string());
            httpResponse.setBodyString(string);
            httpResponse.setBody(JSON.parseObject(string));
        } catch (IOException e) {
            log.error("\uD83D\uDC4C\uD83D\uDC4C\uD83D\uDC4C " + e.getMessage());
        }

        return httpResponse;
    }

    // 解析url, 添加查询参数
    private static String p_parseUrl(HttpRequest httpRequest) {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(httpRequest.getUrl()).newBuilder();

        // 如果有 param, 则添加
        if (httpRequest.hasQueryParameter()) {
            httpRequest.getQueryParameter().forEach(urlBuilder::addQueryParameter);
        }

        return urlBuilder.toString();
    }

    private static void p_addHeader(HttpRequest httpRequest, Request.Builder builder) {
        // 如果有 header, 则添加
        if (httpRequest.hasHeader()) {
            httpRequest.getHeader().forEach(builder::addHeader);
        }
    }

    private static Request p_generateRequest(HttpRequest httpRequest) throws Exception {
        // 解析url
        String url = p_parseUrl(httpRequest);
        // 构造builder
        Request.Builder builder = new Request.Builder().url(url);
        // 添加header
        p_addHeader(httpRequest, builder);

        // 判断类型
        switch (httpRequest.getMethod()) {
            case GET:
                builder.get();
                break;
            case POST:
                builder.post(p_body(httpRequest));
                break;
            case PUT:
                builder.put(p_body(httpRequest));
                break;
            case DELETE:
                builder.delete();
                break;
            default:
                throw new Exception("Method not supported temporarily");
        }

        return builder.build();
    }

    // raw.json
    private static RequestBody p_jsonBody(HttpRequest httpRequest) {
        String json = "";
        if (httpRequest.hasBodyParameter()) {
            json = JSON.toJSONString(httpRequest.getBodyParameter());
        }

        return RequestBody.create(json, MEDIA_TYPE_JSON);
    }

    // form-data
    private static FormBody p_formBody(HttpRequest httpRequest) {
        FormBody.Builder bodyBuilder = new FormBody.Builder();
        if (httpRequest.hasBodyParameter()) {
            httpRequest.getBodyParameter().forEach(bodyBuilder::add);
        }

        return bodyBuilder.build();
    }

    private static RequestBody p_body(HttpRequest httpRequest) throws Exception {
        RequestBody body = null;
        switch (httpRequest.getRequestBodyMode()) {
            case RAW_JSON:
                body = p_jsonBody(httpRequest);
                break;
            case FORM_DATA:
                body = p_formBody(httpRequest);
                break;
            default:
                throw new Exception("Unknown Request Body Mode!!!");
        }

        return body;
    }

    private static HttpResponse p_errorResponse(HttpRequest httpRequest, Exception e) {
        HttpResponse response = new HttpResponse();

        response.setCode(-1);
        response.setHttpRequest(httpRequest);
        response.setBodyString(e.getMessage());
        response.setSuccess(false);

        return response;
    }
}
