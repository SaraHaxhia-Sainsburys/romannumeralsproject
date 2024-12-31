package com.saraspring.romannumeralsproject.service;

public class CheckOptionsResult {
  private int value;
  private String remainingNumeral;

  public CheckOptionsResult(int value, String remainingNumeral) {
    this.value = value;
    this.remainingNumeral = remainingNumeral;
  }

  public int getValue() {
    return value;
  }

  public String getRemainingNumeral() {
    return remainingNumeral;
  }
}
