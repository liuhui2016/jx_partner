package com.game.util.ip.sina;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SinaIpUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(SinaIpUtils.class);
    public static final String SINA_IP_API = "http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=json&ip=";

    public static JsonIPInfo getAddress(String ip) {
        if (ip == null || ip.equals("127.0.0.1") || ip.equals("0:0:0:0:0:0:0:1"))
            ip = "";
        HttpGet httpGet = new HttpGet(SINA_IP_API + ip);
        try {
            HttpResponse httpResponse = HttpClients.createDefault().execute(httpGet);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
                mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
                HttpEntity httpEntity = httpResponse.getEntity();
                String result = EntityUtils.toString(httpEntity);
                if (StringUtils.isNotBlank(result) && result.contains("country")) {
                    JsonIPInfo ipInfo = mapper.readValue(result, JsonIPInfo.class);
                    return ipInfo;
                }
            }
        } catch (IOException e) {
            LOGGER.info("IO异常：{}", e);
        } catch (Exception e) {
            LOGGER.info("异常：{}", e);
        }
        return null;
    }

    public static void main(String[] args) {
        String s = "008613800591500";
        System.out.println(s.substring(s.length() - 11));
        System.out.println(NumberUtils.toLong("|100|".replaceAll("\\|", "")));
        System.out.println("|100|".replaceAll("\\|", ""));
        System.out.println(getAddress("127.0.0.1").toString());
        System.out.println(getAddress("183.207.208.241").toString());
        System.out.println(getAddress("89.199.127.91").toString());
        System.out.println(getAddress("181.140.103.213").toString());
        System.out.println(getAddress("195.142.122.222").toString());
        System.out.println(getAddress("157.61.112.9").toString());
        System.out.println(getAddress("125.72.128.42").toString());
        System.out.println(getAddress("222.168.146.61").toString());
        System.out.println(getAddress("60.194.130.6").toString());
        System.out.println(getAddress("124.119.86.30").toString());
    }
}
