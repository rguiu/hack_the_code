package com.aluzine.randory.hackthecode.user;

import static java.net.URLEncoder.encode;
import java.io.IOException;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import com.google.gson.Gson;

public class FetchUserResponse {
    private static final Gson gson = new Gson();
    private final static int CONNECTION_TIMEOUT = 5 * 1000; // timeout in millis
    private final static RequestConfig requestConfig = RequestConfig.custom()
                                                                    .setConnectionRequestTimeout(CONNECTION_TIMEOUT)
                                                                    .setConnectTimeout(CONNECTION_TIMEOUT)
                                                                    .setSocketTimeout(CONNECTION_TIMEOUT)
                                                                    .build();

    public static String callRemote(String url, String magicWord) {
        try {
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpGet getRequest = new HttpGet(url + "/solve/" + encode(magicWord, "UTF-8"));
            getRequest.setConfig(requestConfig);
            getRequest.addHeader("accept", "application/json");

            HttpResponse response = httpClient.execute(getRequest);

            if (response.getStatusLine().getStatusCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
            }

            return EntityUtils.toString(response.getEntity());

        } catch (ClientProtocolException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String sendStats(String url, StatResponse statResponse) {
        try {
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost postRequest = new HttpPost(url + "/stats");
            postRequest.setConfig(requestConfig);
            postRequest.addHeader("accept", "application/json");
            StringEntity params = new StringEntity(gson.toJson(statResponse));
            postRequest.setEntity(params);
            //HttpResponse response =

            httpClient.execute(postRequest);

//            if (response.getStatusLine().getStatusCode() != 200) {
//                throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
//            }
//
//            return EntityUtils.toString(response.getEntity());

        } catch (ClientProtocolException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
