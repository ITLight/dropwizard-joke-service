package com.vn.jinx.resources;

import com.vn.jinx.api.JokeAPI;
import com.vn.jinx.api.enumeration.ResponseEnum;
import com.vn.jinx.service.JokeService;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Path("/v1")
@Produces(MediaType.APPLICATION_JSON)
@RequiredArgsConstructor
public class JokeResource {

  private final JokeService jokeService;

  @GET
  @Path("/joke")
  public JokeAPI fetchJoke(@QueryParam("search")String search) {

    JokeAPI res;

    try {
      List<Object> data = jokeService.fetchJoke(search);
      res = new JokeAPI(search, data);
    } catch (Exception e) {
      log.error("Error while fetching jokes from {}, ex:", search, e);
      res = new JokeAPI(ResponseEnum.EXCEPTION, search);
    }

    return res;
  }

}
