package com.vn.jinx.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RateLimitConfigDTO {

  private int requests;
  private int timeLimitMinute;
}
