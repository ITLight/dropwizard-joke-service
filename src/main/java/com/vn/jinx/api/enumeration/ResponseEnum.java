package com.vn.jinx.api.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseEnum {

  SUCCESS(1,"The request is handled successfully"),
  EXCEPTION(0, "The system has an error")
  ;

  private final int code;
  private final String message;
}
