package com.vn.jinx.test;

import com.vn.jinx.JApplication;
import com.vn.jinx.JApplicationConfiguration;
import com.vn.jinx.api.JokeAPI;
import com.vn.jinx.enumeration.ResponseEnum;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit5.DropwizardAppExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@ExtendWith(DropwizardExtensionsSupport.class)
class JApplicationTest {

  private final DropwizardAppExtension<JApplicationConfiguration> EXT = new DropwizardAppExtension<>(
      JApplication.class,
      ResourceHelpers.resourceFilePath("application.yml")
  );

  @Test
  void testRateLimit() {
    Client client = EXT.client();

    for (int i = 0; i < 6; i++) {
      Response result = client.target(
          String.format("http://localhost:%d/api/v1/joke", EXT.getLocalPort())
      ).queryParam("search", "abc").request().get(Response.class);

      int status = result.getStatus();

      Map<String, Object> response = result.readEntity(HashMap.class);

      int code = Integer.parseInt(response.get("code").toString());

      if(i < 5) {
        assertThat(status).isEqualTo(HttpStatus.SC_OK);
        assertThat(code).isEqualTo(ResponseEnum.SUCCESS.getCode());
      } else {
        assertThat(status).isEqualTo(HttpStatus.SC_TOO_MANY_REQUESTS);
        assertThat(code).isEqualTo(ResponseEnum.TOO_MANY_REQUEST.getCode());
      }
    }
  }

  @Test
  void invalidSearchText() {
    Client client = EXT.client();

    Response result = client.target(
        String.format("http://localhost:%d/api/v1/joke", EXT.getLocalPort())
    ).queryParam("search", "ab").request().get(Response.class);

    int status = result.getStatus();

    Map<String, Object> response = result.readEntity(HashMap.class);

    int code = Integer.parseInt(response.get("code").toString());

    assertThat(status).isEqualTo(HttpStatus.SC_OK);
    assertThat(code).isEqualTo(ResponseEnum.INVALID_SEARCH_TEXT.getCode());
  }

//  private static final ResourceExtension EXT = ResourceExtension.builder()
////      .addResource(new JokeResource())
//      .build();
//
//  @Test
//  public void fetchJoke() {
//    Response response = EXT.target("/api/v1/joke").queryParam("search", "abc").request().get();
//
//    log.info("response: {}", response);
//  }
//

}
