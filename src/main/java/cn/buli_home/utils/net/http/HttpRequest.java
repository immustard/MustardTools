package cn.buli_home.utils.net.http;

import cn.buli_home.utils.common.StringUtils;
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

    public void addHeader(String key, String value) {
        if (Objects.isNull(header)) {
            header = new HashMap<>();
        }

        header.put(key, value);
    }

    public boolean hasHeader() {
        return Objects.nonNull(header) && !header.isEmpty();
    }

    public void addQueryParameter(String key, String value) {
        if (Objects.isNull(queryParameter)) {
            queryParameter = new HashMap<>();
        }

        queryParameter.put(key, value);
    }

    public boolean hasQueryParameter() {
        return Objects.nonNull(queryParameter) && !queryParameter.isEmpty();
    }

    public void addBodyParameter(String key, String value) {
        if (Objects.isNull(bodyParameter)) {
            bodyParameter = new HashMap<>();
        }

        bodyParameter.put(key, value);
    }

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
