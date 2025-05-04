package com.vectras.vm;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class QuoteFetcher {
    private static final String QUOTE_API_URL = "https://v1.hitokoto.cn/";

    public interface QuoteCallback {
        void onSuccess(String quote);
        void onFailure(String error);
    }

    public static void fetchQuote(QuoteCallback callback) {
        OkHttpClient client = new OkHttpClient();

        // 构建请求
        Request request = new Request.Builder()
                .url(QUOTE_API_URL)
                .build();

        // 异步请求
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFailure("网络请求失败: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    callback.onFailure("服务器错误: " + response.code());
                    return;
                }

                // 解析 JSON（这里使用 Gson）
                try {
                    String json = response.body().string();
                    Gson gson = new Gson();
                    QuoteResponse quoteResponse = gson.fromJson(json, QuoteResponse.class);
                    callback.onSuccess(quoteResponse.getHitokoto()+"  ——"+quoteResponse.getFrom());
                } catch (Exception e) {
                    callback.onFailure("解析失败: " + e.getMessage());
                }
            }
        });
    }

    // 数据模型（对应 API 返回的 JSON 结构）
    private static class QuoteResponse {
        String hitokoto;
        String from;

        public String getHitokoto(){
            return hitokoto;
        }

        public String getFrom(){
            return from;
        }
    }
}
