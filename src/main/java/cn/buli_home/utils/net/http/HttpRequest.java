package cn.buli_home.utils.net.http;

import cn.buli_home.utils.common.StringUtils;
import com.alibaba.fastjson2.JSONObject;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * HTTP Request DTO
 *
 * @author mustard
 * @version 1.0
 * Create by 2022/9/14
 */
@Data
public class HttpRequest {

    private String url;

    private HttpMethod method;

    private HttpRequestMode requestMode;

    private HttpRequestBodyMode requestBodyMode;

    private Map<String, String> header;

    private Map<String, String> queryParameter;

    private Map<String, String> bodyParameter;

    private boolean ignoreSSL;

    /**
     * 添加请求头 (键值对)
     * @param key 键
     * @param value 值
     */
    public void addHeader(String key, String value) {
        if (Objects.isNull(header)) {
            header = new HashMap<>();
        }

        header.put(key, value);
    }

    /**
     * 添加请求头 (字典)
     * @param jsonObject 字典
     */
    public void addHeader(JSONObject jsonObject) {
        if (Objects.isNull(jsonObject)) {
            return;
        }

        jsonObject.forEach( (k, v) -> {
            addHeader(k, StringUtils.convert2String(v));
        });
    }

    /**
     * 是否有请求头
     */
    public boolean hasHeader() {
        return Objects.nonNull(header) && !header.isEmpty();
    }

    /**
     * 添加查询参数 (键值对)
     * @param key 键
     * @param value 值
     */
    public void addQueryParameter(String key, String value) {
        if (Objects.isNull(queryParameter)) {
            queryParameter = new HashMap<>();
        }

        queryParameter.put(key, value);
    }

    /**
     * 添加查询参数 (字典)
     * @param jsonObject 字典
     */
    public void addQueryParameter(JSONObject jsonObject) {
        if (Objects.isNull(jsonObject)) {
            return;
        }

        jsonObject.forEach( (k, v) -> {
            addQueryParameter(k, StringUtils.convert2String(v));
        });
    }

    /**
     * 是否有查询参数
     */
    public boolean hasQueryParameter() {
        return Objects.nonNull(queryParameter) && !queryParameter.isEmpty();
    }

    /**
     * 添加请求体 (键值对)
     * @param key 键
     * @param value 值
     */
    public void addBodyParameter(String key, String value) {
        if (Objects.isNull(bodyParameter)) {
            bodyParameter = new HashMap<>();
        }

        bodyParameter.put(key, value);
    }

    /**
     * 添加请求体 (字典)
     * @param jsonObject 字典
     */
    public void addBodyParameter(JSONObject jsonObject) {
        if (Objects.isNull(jsonObject)) {
            return;
        }

        jsonObject.forEach( (k, v) -> {
            addBodyParameter(k, StringUtils.convert2String(v));
        });
    }

    /**
     * 是否有请求体
     */
    public boolean hasBodyParameter() {
        return Objects.nonNull(bodyParameter) && !bodyParameter.isEmpty();
    }

    private HttpRequest() {
    }

    private HttpRequest(Builder builder) {
        if (StringUtils.isEmptyWithoutBlank(builder.url)) {
            throw new NullPointerException(builder.url);
        }

        this.url = builder.url;
        this.method = Objects.isNull(builder.method) ? HttpMethod.GET : builder.method;
        this.requestMode = Objects.isNull(builder.requestMode) ? HttpRequestMode.SYNCHRONOUS : builder.requestMode;
        this.requestBodyMode = Objects.isNull(builder.requestBodyMode) ? HttpRequestBodyMode.FORM_DATA : builder.requestBodyMode;
    }

    public static class Builder {
        private String url;
        private HttpMethod method;
        private HttpRequestMode requestMode;
        private HttpRequestBodyMode requestBodyMode;

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder method(HttpMethod method) {
            this.method = method;
            return this;
        }

        public Builder requestMode(HttpRequestMode requestMode) {
            this.requestMode = requestMode;
            return this;
        }

        public Builder requestBodyMode(HttpRequestBodyMode requestBodyMode) {
            this.requestBodyMode = requestBodyMode;
            return this;
        }

        public HttpRequest build() {
            return new HttpRequest(this);
        }
    }
}
