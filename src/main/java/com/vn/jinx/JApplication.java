package com.vn.jinx;

import com.codahale.metrics.health.HealthCheck;
import com.vn.jinx.filter.RateLimitFilter;
import com.vn.jinx.resources.JokeResource;
import com.vn.jinx.service.JokeService;
import com.vn.jinx.service.RateLimitService;
import io.dropwizard.Application;
import io.dropwizard.client.HttpClientBuilder;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.apache.http.impl.client.CloseableHttpClient;

public class JApplication extends Application<JApplicationConfiguration> {

  public static void main(String[] args) throws Exception {
    if (args.length < 2) {
      new JApplication().run("server", "application.yml");
    } else {
      new JApplication().run(args);
    }
  }

  @Override
  public String getName() {
    return "jinx-joke-service";
  }

  @Override
  public void initialize(Bootstrap<JApplicationConfiguration> bootstrap) {
  }

  @Override
  public void run(JApplicationConfiguration jApplicationConfiguration, Environment environment) {
    environment.healthChecks().register("jinx-joke-service", new HealthCheck() {
      @Override
      protected Result check() {
        return Result.healthy();
      }
    });

    environment.jersey().register(new RateLimitFilter(new RateLimitService()));

    final CloseableHttpClient client = new HttpClientBuilder(environment).build(getName());

    environment.jersey().register(new JokeResource(new JokeService(client)));
  }
}
