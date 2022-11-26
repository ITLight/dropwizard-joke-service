package com.vn.jinx.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseEnum {

  SUCCESS(1,"The request is handled successfully"),
  EXCEPTION(0, "The system has an error"),
  TOO_MANY_REQUEST(-1, "Too many requests. Only 5 request per one minute"),
  INVALID_SEARCH_TEXT(-2, "Size must be between 3 and 120")
  ;

  private final int code;
  private final String message;
}
