package com.vn.jinx.service;

import com.google.gson.JsonObject;
import com.vn.jinx.service.dto.JokeDTO;
import com.vn.jinx.service.dto.JokeDTO.Result;
import com.vn.jinx.util.JsonUtils;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

@Slf4j
@RequiredArgsConstructor
public class JokeService {

  private final CloseableHttpClient client;

  public List<Object> fetchJoke(String search) throws IOException {
    int timeout = 30;
    RequestConfig config = RequestConfig.custom()
        .setConnectTimeout(timeout * 1000)
        .setConnectionRequestTimeout(timeout * 1000)
        .setSocketTimeout(timeout * 1000).build();

    HttpGet request = new HttpGet("https://api.chucknorris.io/jokes/search?query=" + search);
    request.setConfig(config);
    request.setHeader("content-type", MediaType.APPLICATION_JSON);

    CloseableHttpResponse response = client.execute(request);

    try {
      JokeDTO jokeDTO = JsonUtils.parse(EntityUtils.toString(response.getEntity()), JokeDTO.class);

      return jokeDTO.getResult().stream().map(Result::getValue).collect(Collectors.toList());
    } catch (Exception e) {
      log.error("Parsing jokes has an error ", e);
    }
    finally{
      response.close();
    }

    return Collections.emptyList();
  }
}
