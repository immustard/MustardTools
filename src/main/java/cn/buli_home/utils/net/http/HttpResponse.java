package cn.buli_home.utils.net.http;

import com.alibaba.fastjson2.JSONObject;
import lombok.Data;

/**
 * HTTP Response DTO
 *
 * @author mustard
 * @version 1.0
 * Create by 2022/9/14
 */
@Data
public final class HttpResponse {

    private HttpRequest httpRequest;

    private int code;

    private JSONObject body;

    private String bodyString;

}
