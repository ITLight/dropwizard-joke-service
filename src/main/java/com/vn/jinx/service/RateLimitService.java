package com.vn.jinx.service;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RateLimitService {

  private static final Map<String, Bucket> bucketMap = new HashMap<>();

  public boolean isReachedQueryLimit(String search) {

    if(!Objects.isNull(search) && !search.isEmpty()) {
      Bucket searchingBucket = bucketMap.get(search);

      if(Objects.isNull(searchingBucket)) {
        Refill refill = Refill.intervally(5, Duration.ofMinutes(1));
        Bandwidth limit = Bandwidth.classic(5, refill);
        Bucket bucket = Bucket4j.builder()
            .addLimit(limit)
            .build();

        bucket.tryConsume(1L);

        bucketMap.put(search, bucket);

        return false;
      }

      return !searchingBucket.tryConsume(1L);
    }


    return true;
  }
}
