package com.vn.jinx.filter;

import com.vn.jinx.api.JokeAPI;
import com.vn.jinx.api.enumeration.ResponseEnum;
import com.vn.jinx.service.RateLimitService;
import com.vn.jinx.util.JsonUtils;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.lang.reflect.Method;
import javax.ws.rs.core.Response.Status;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class RateLimitFilter implements ContainerRequestFilter {

  private final RateLimitService rateLimitService;

  @Context private ResourceInfo resourceInfo;

  @Context private HttpServletRequest request;

  @Override
  public void filter(ContainerRequestContext context) {
    String search = request.getParameter("search");

    if (search.length() >= 3 && rateLimitService.isReachedQueryLimit(JsonUtils.encodeURI(search))) {
      context.abortWith(
          Response.status(Status.TOO_MANY_REQUESTS)
              .entity(new JokeAPI(ResponseEnum.TOO_MANY_REQUEST, search))
              .type(MediaType.APPLICATION_JSON)
              .build());
    }

  }
}
