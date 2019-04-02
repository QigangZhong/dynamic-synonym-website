package com.example.test.controller;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@RestController
public class IndexController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/synonym")
    public ResponseEntity<String> synonym(HttpServletResponse response){

        String synonyms = jdbcTemplate.queryForObject("select GROUP_CONCAT(synonyms separator '\\r\\n') from synonym_config",String.class);
        System.out.println(synonyms);

        String md5 = DigestUtils.md5Hex(synonyms);
        System.out.println(md5);

        HttpHeaders headers = new HttpHeaders();
        headers.add("ETag", md5);
        headers.add("Last-Modified", new Date().toString());
        headers.add("name", "zhangsan");
        headers.add("Content-Type", "text/plain");
        return ResponseEntity.status(200).headers(headers).body(synonyms);
    }

    @GetMapping("/index")
    public String index() throws IOException {
        RequestConfig rc = RequestConfig.custom()
                .setConnectionRequestTimeout(10 * 1000)
                .setConnectTimeout(10 * 1000).setSocketTimeout(15 * 1000)
                .build();
        HttpHead head = new HttpHead("http://localhost:8080/synonym");

        CloseableHttpClient httpClient = HttpClientBuilder
                .create()
                .setDefaultRequestConfig(rc)
                .build();

        HttpResponse response = httpClient.execute(head);

        System.out.println(response.getStatusLine());

        for (Header h : response.getAllHeaders()) {
            System.out.println("key:" + h.getName() + " value:" + h.getValue());
        }

        head.releaseConnection();

        return "ok";
    }
}
