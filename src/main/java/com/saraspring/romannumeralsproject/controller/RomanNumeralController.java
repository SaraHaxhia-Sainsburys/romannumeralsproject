package com.saraspring.romannumeralsproject.controller;

import com.saraspring.romannumeralsproject.service.ConversionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/romannumerals")
public class RomanNumeralController {

private ConversionService conversionService;

public RomanNumeralController(ConversionService conversionService) {
  this.conversionService = conversionService;
}

  @PostMapping("/convert-to-number")
  public int convertToNumber(@RequestParam String romanNumeral) {
    return conversionService.convertToNumber(romanNumeral);
  }


}
