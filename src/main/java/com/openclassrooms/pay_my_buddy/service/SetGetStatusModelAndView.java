package com.openclassrooms.pay_my_buddy.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class SetGetStatusModelAndView {

  private int setStatus;
  private HttpStatus httpStatus;

  public SetGetStatusModelAndView() {}

  public SetGetStatusModelAndView(int setStatus, HttpStatus httpStatus) {
    this.setStatus = setStatus;
    this.httpStatus = httpStatus;
  }

  public int getSetStatus() {
    return this.setStatus;
  }

  public void setSetStatus(int setStatus) {
    this.setStatus = setStatus;
  }

  public HttpStatus getHttpStatus() {
    return this.httpStatus;
  }

  public void setHttpStatus(HttpStatus httpStatus) {
    this.httpStatus = httpStatus;
  }
}
