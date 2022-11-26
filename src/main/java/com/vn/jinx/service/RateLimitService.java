package com.vn.jinx.service;

import com.vn.jinx.dto.RateLimitConfigDTO;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class RateLimitService {

  private static final Map<String, Bucket> bucketMap = new HashMap<>();

  private final RateLimitConfigDTO rateLimitConfig;

  public boolean isReachedQueryLimit(String search) {
    String key = "empty_search";

    if (!Objects.isNull(search) && !search.isEmpty()) {
      key = search;
    }

    Bucket searchingBucket = bucketMap.get(key);

    if (Objects.isNull(searchingBucket)) {
      Refill refill =
          Refill.intervally(
              rateLimitConfig.getRequests(),
              Duration.ofMinutes(rateLimitConfig.getTimeLimitMinute()));
      Bandwidth limit = Bandwidth.classic(rateLimitConfig.getRequests(), refill);
      Bucket bucket = Bucket4j.builder().addLimit(limit).build();

      bucket.tryConsume(1L);

      bucketMap.put(key, bucket);

      return false;
    }

    return !searchingBucket.tryConsume(1L);
  }
}
