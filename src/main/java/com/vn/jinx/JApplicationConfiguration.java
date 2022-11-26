package com.vn.jinx;

import com.vn.jinx.dto.RateLimitConfigDTO;
import io.dropwizard.Configuration;
import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JApplicationConfiguration extends Configuration {

  private String queryURL;

  private RateLimitConfigDTO rateLimit;

}
