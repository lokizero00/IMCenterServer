package com.loki.server.utils;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import java.io.*;
import java.security.*;
import java.security.cert.CertificateException;

public class ForwardRequest {

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(ForwardRequest.class);

    /**
     * 发送微信转账的ssl请求
     *
     * @param url      请求URL
     * @param data     请求内容
     * @param charset  编码格式
     * @param certPath 证书地址
     * @param mchId    证书密码
     * @return
     * @author scott
     * https://www.jianshu.com/p/beafe38428c7
     */
    public static String httpPostSsl(String url, String data, String charset, String certPath, String mchId) {
        String responseData = null;
        try {
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            //P12文件目录
            InputStream inputStream = new FileInputStream(new File(certPath));
            try {
                //密码,默认是MCHID
                keyStore.load(inputStream, mchId.toCharArray());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
            }
            SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, mchId.toCharArray()).build();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext,
                    new String[]{"TLSv1"}, null, SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
            CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();

            try {
                // 设置响应头信息
                HttpPost httpPost = new HttpPost(url);
                httpPost.addHeader("Content-Type", "application/xml");
                httpPost.addHeader("Host", "api.mch.weixin.qq.com");
                httpPost.addHeader("Cache-Control", "max-age=0");
                httpPost.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0) ");
                httpPost.addHeader("Connection", "keep-alive");
                httpPost.addHeader("Accept", "*/*");
                httpPost.setEntity(new StringEntity(data, "UTF-8"));
                logger.info("request url: {}, charset:{}, data:{}", httpPost.getURI(), charset, data);

                CloseableHttpResponse response = httpclient.execute(httpPost);
                try {
                    HttpEntity entity = response.getEntity();
                    responseData = EntityUtils.toString(entity, charset);
                    EntityUtils.consume(entity);
                    logger.info("Response content:{} ", responseData);
                } finally {
                    if (response != null) {
                        response.close();
                    }
                }
            } finally {
                // 关闭连接,释放资源
                try {
                    if (httpclient != null) {
                        httpclient.close();
                    }
                } catch (IOException e) {
                    logger.error("Error:{}", e);
                }
            }
        } catch (Exception e) {
            logger.error("Error:{}", e);
        }
        return responseData;
    }


    /**
     * 这个版本是基于httpcomponents 4.3.1.pom
     * @param url
     * @param data
     * @return
     */
    private String ssl(String url, String data,String certPath,String mchId) {
        //HttpsRequest
        StringBuffer message = new StringBuffer();
        try {
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            FileInputStream instream = new FileInputStream(new File(certPath));
            keyStore.load(instream, mchId.toCharArray());
            SSLContext sslcontext = SSLContexts.custom()
                    .loadKeyMaterial(keyStore, mchId.toCharArray())
                    .build();
            // Allow TLSv1 protocol only
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                    sslcontext,
                    new String[]{"TLSv1"},
                    null,
                    SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
            CloseableHttpClient httpclient = HttpClients.custom()
                    .setSSLSocketFactory(sslsf)
                    .build();

            HttpPost httpost = new HttpPost(url);
            httpost.addHeader("Connection", "keep-alive");
            httpost.addHeader("Accept", "*/*");
            httpost.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            httpost.addHeader("Host", "api.mch.weixin.qq.com");
            httpost.addHeader("X-Requested-With", "XMLHttpRequest");
            httpost.addHeader("Cache-Control", "max-age=0");
            httpost.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0) ");
            httpost.setEntity(new StringEntity(data, "UTF-8"));

            CloseableHttpResponse response = httpclient.execute(httpost);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
                    String text;
                    while ((text = bufferedReader.readLine()) != null) {
                        message.append(text);
                    }
                }
                EntityUtils.consume(entity);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                response.close();
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return message.toString();
    }

    /**
     * 基于4.5.1版本的
     * @param url
     * @param data
     * @param certPath
     * @param mchId
     * @return
     * @throws KeyStoreException
     * @throws IOException
     * @throws CertificateException
     * @throws NoSuchAlgorithmException
     * @throws UnrecoverableKeyException
     * @throws KeyManagementException
     * @throws UnrecoverableKeyException
     * @throws KeyManagementException
     */
    public String TransferRestTemplate(String url,String data,String certPath,String mchId) throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException, UnrecoverableKeyException, KeyManagementException, UnrecoverableKeyException, KeyManagementException {
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        FileInputStream instream = new FileInputStream(new File(certPath));
        keyStore.load(instream, mchId.toCharArray());
        // Trust own CA and all self-signed certs
        SSLContext sslcontext = SSLContextBuilder.create()
                .loadKeyMaterial(keyStore, mchId.toCharArray())
                .build();
        // Allow TLSv1 protocol only
        HostnameVerifier hostnameVerifier = NoopHostnameVerifier.INSTANCE;
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext,  new String[]{"TLSv1"},
                null,hostnameVerifier);

        CloseableHttpClient httpclient = HttpClients.custom()
                .setSSLSocketFactory(sslsf)
                .build();

        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(httpclient);
        RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory);
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Connection", "keep-alive");
        requestHeaders.add("Accept", "*/*");
        requestHeaders.add("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        requestHeaders.add("Host", "api.mch.weixin.qq.com");
        requestHeaders.add("X-Requested-With", "XMLHttpRequest");
        requestHeaders.add("Cache-Control", "max-age=0");
        requestHeaders.add("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0) ");

        org.springframework.http.HttpEntity<String> requestEntity =
                new org.springframework.http.HttpEntity(new StringEntity(data, "UTF-8"),requestHeaders);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        String str = response.getBody();
        return str;
    }


}
