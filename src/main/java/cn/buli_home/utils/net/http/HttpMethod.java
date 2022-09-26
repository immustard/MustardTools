package cn.buli_home.utils.net.http;

import java.util.HashMap;
import java.util.Map;

/**
 * HTTP 请求类型
 *
 * @author mustard
 * @version 1.0
 * Create by 2022/9/14
 */
public enum HttpMethod {

    GET,
    HEAD,
    POST,
    PUT,
    PATCH,
    DELETE,
    OPTIONS,
    TRACE;

    private static final Map<String, HttpMethod> mappings = new HashMap<>(16);

    private HttpMethod() {}

    public static HttpMethod resolve(String method) {
        return method != null ? mappings.get(method) : null;
    }

    static {
        HttpMethod[] methods = values();
        int length1 = methods.length;

        for (HttpMethod method : methods) {
            mappings.put(method.name(), method);
        }
    }
}
