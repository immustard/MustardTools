package cn.buli_home.utils.net.http;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

/**
 * HTTP 请求回调
 *
 * @author mustard
 * @version 1.0
 * Create by 2022/9/14
 */
class HttpCallback extends CompletableFuture<Response> implements Callback {

    @Override
    public void onFailure(@NotNull Call call, @NotNull IOException e) {
        super.completeExceptionally(e);
    }

    @Override
    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
        super.complete(response);
    }

}
