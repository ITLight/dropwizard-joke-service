package com.vn.jinx.api;

import com.vn.jinx.enumeration.ResponseEnum;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class JokeAPI {

  private final int code;
  private final String message;
  private String searchText;
  private Object data;

  public JokeAPI(String searchText, Object data) {
    this.code = ResponseEnum.SUCCESS.getCode();
    this.message = ResponseEnum.SUCCESS.getMessage();
    this.searchText = searchText;
    this.data = data;
  }

  public JokeAPI(ResponseEnum responseEnum, String searchText) {
    this.code = responseEnum.getCode();
    this.message = responseEnum.getMessage();
    this.searchText = searchText;
  }
}
