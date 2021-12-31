package hu.gemesi.taskmaker.rest.apache;

import com.google.gson.Gson;
import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import javax.enterprise.context.Dependent;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

@Dependent
public class BaseApacheClient {

    public static final int CONNECTION_TIMEOUT_MILLIS = (int) TimeUnit.MINUTES.toMillis(1);

    public static final String APPLICATION_XML = "application/xml";
    public static final String TEXT_XML = "text/xml";
    public static final String APPLICATION_JSON = "application/json";

    public RequestConfig createRequestConfig() {
        return RequestConfig.custom().setConnectionRequestTimeout(CONNECTION_TIMEOUT_MILLIS).setConnectTimeout(CONNECTION_TIMEOUT_MILLIS).setSocketTimeout(CONNECTION_TIMEOUT_MILLIS).build();
    }


    public HttpResponse sendClientBasePost(String url, Object entityObject) throws Exception {
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("content-type", APPLICATION_JSON);
        CloseableHttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(createRequestConfig()).build();
        try {
            String entity = toJsonString(entityObject);
            httpPost.setEntity(new StringEntity(entity));
            return client.execute(httpPost);
        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception(e.getLocalizedMessage());
        }

    }

    public HttpResponse sendClientBaseGet(String url) throws Exception {
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(createRequestConfig()).build();
        try {
            return client.execute(httpGet);
        } catch (ClientProtocolException e) {
            throw new InterruptedException("HTTP protocol exception: " + e.getLocalizedMessage());
        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception(e.getLocalizedMessage());
        }

    }

    public HttpResponse sendClientBasePut(String url, Object entityObject) throws Exception {
        HttpPut httpPut = new HttpPut(url);
        CloseableHttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(createRequestConfig()).build();
        try {
            String entity = toJsonString(entityObject);
            httpPut.setEntity(new StringEntity(entity));
            return client.execute(httpPut);
        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception(e.getLocalizedMessage());
        }

    }

    public HttpResponse sendClientBaseDelete(String url) throws Exception {
        HttpDelete httpDelete = new HttpDelete(url);
        CloseableHttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(createRequestConfig()).build();
        try {
            return client.execute(httpDelete);
        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception(e.getLocalizedMessage());
        }

    }

    public String encodeURL(String url) throws UnsupportedEncodingException {
        return URLEncoder.encode(url, StandardCharsets.UTF_8.name());
    }

    protected String toJsonString(Object object) {
        String entityString = "";
        if (object instanceof String) {
            entityString = (String) object;
        } else {
            var gson = new Gson();
            entityString = gson.toJson(object);
        }
        return entityString;
    }
}
