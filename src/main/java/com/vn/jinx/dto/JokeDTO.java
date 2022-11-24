package com.vn.jinx.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JokeDTO {
  private long total;
  private List<Result> result;

  @Getter
  @Setter
  @AllArgsConstructor
  @NoArgsConstructor
  public class Result{
    private String value;
  }
}
