package com.vn.jinx.resources;

import com.vn.jinx.api.JokeAPI;
import com.vn.jinx.enumeration.ResponseEnum;
import com.vn.jinx.service.JokeService;
import com.vn.jinx.util.JsonUtils;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
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
  public Response fetchJoke(@QueryParam("search") String search) {

    JokeAPI res;

    try {
      if (search.length() < 3)
        return Response.status(Status.OK)
            .entity(new JokeAPI(ResponseEnum.INVALID_SEARCH_TEXT, search))
            .build();

      List<String> data = jokeService.fetchJoke(JsonUtils.encodeURI(search));
      res = new JokeAPI(search, data);
    } catch (Exception e) {
      log.error("Error while fetching jokes from {}, ex:", search, e);
      res = new JokeAPI(ResponseEnum.EXCEPTION, search);
    }

    return Response.status(Status.OK).entity(res).build();
  }
}
