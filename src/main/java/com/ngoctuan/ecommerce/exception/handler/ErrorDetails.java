package com.ngoctuan.ecommerce.exception.handler;

import lombok.Data;

import java.time.Instant;

@Data
public class ErrorDetails {

  private Instant timestamp;
  private String message;
  private String details;

  public ErrorDetails(String message, String details) {
    this.timestamp = Instant.now();
    this.message = message;
    this.details = details;
  }
}
