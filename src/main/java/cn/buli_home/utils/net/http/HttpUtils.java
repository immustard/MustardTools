package cn.buli_home.utils.net.http;

import cn.buli_home.utils.common.StringUtils;
import com.alibaba.fastjson2.JSON;
import okhttp3.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * HTTP 请求工具类
 *
 * @author mustard
 * @version 1.0
 * Create by 2022/9/14
 */
public class HttpUtils {

    private static final Logger log = LogManager.getLogger(HttpUtils.class);

    private static final OkHttpClient httpClient = new OkHttpClient();

    private static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
    private static final MediaType MEDIA_TYPE_GENERAL = MediaType.parse("application/octet-stream");
    private static final MediaType MEDIA_TYPE_IMAGE = MediaType.parse("image/png");

    /**
     * 发送http请求
     */
    public static HttpResponse request(HttpRequest httpRequest) {
        try {
            Request request = p_generateRequest(httpRequest);

            return p_request(httpRequest, request);
        } catch (Exception e) {
            return p_errorResponse(httpRequest, e);
        }
    }

    // 请求底层方法
    private static HttpResponse p_request(HttpRequest httpRequest, Request request) {
        Call call = httpClient.newCall(request);

        switch (httpRequest.getRequestMode()) {
            // 同步
            case SYNCHRONOUS: {
                try {
                    Response response = call.execute();

                    return p_convertResponse(response, httpRequest);
                } catch (IOException e) {
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

        try {
            String string = StringUtils.convert2String(response.body().string());
            httpResponse.setBodyString(string);
            httpResponse.setBody(JSON.parseObject(string));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return httpResponse;
    }

    // 解析url, 添加查询参数
    private static String p_parseUrl(HttpRequest httpRequest) {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(httpRequest.getUrl()).newBuilder();

        // 如果有 param, 则添加
        if (httpRequest.hasQueryParameter()) {
            for (String key : httpRequest.getQueryParameter().keySet()) {
                String value = httpRequest.getQueryParameter().get(key);
                urlBuilder.addQueryParameter(key, value);
            }
        }

        return urlBuilder.toString();
    }

    private static void p_addHeader(HttpRequest httpRequest, Request.Builder builder) {
        // 如果有 header, 则添加
        if (httpRequest.hasHeader()) {
            for (String key : httpRequest.getHeader().keySet()) {
                String value = httpRequest.getHeader().get(key);
                builder.addHeader(key, value);
            }
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
            for (String key : httpRequest.getBodyParameter().keySet()) {
                String value = httpRequest.getBodyParameter().get(key);
                bodyBuilder.add(key, value);
            }
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

        return response;
    }
}
