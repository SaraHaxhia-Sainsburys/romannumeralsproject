package com.saraspring.romannumeralsproject.service;

import com.saraspring.romannumeralsproject.error.InvalidInputException;
import org.springframework.stereotype.Service;

@Service
public class ConversionServiceImpl implements ConversionService {

  public int convertToNumber(String romanNumeral) {

    romanNumeral = initialValidationAndClean(romanNumeral);


    return 2;
  }



  private String initialValidationAndClean(String romanNumeral) {
    if(!(romanNumeral instanceof String)) {
      throw new InvalidInputException("Roman numeral input must be a non-null string");
    }

    romanNumeral = cleanString(romanNumeral);

    if (romanNumeral.isEmpty() ) {
      throw new InvalidInputException("Roman numeral input cannot be empty");
    }

    if(romanNumeral.length() > 15) {
      throw new InvalidInputException("Roman numeral input cannot be longer than 15 characters");
    }

  return romanNumeral;

  }


  private String cleanString(String romanNumeral) {
    if(romanNumeral.isEmpty()) {
      return romanNumeral;
    }

    if(romanNumeral.charAt(0)=='\"' && romanNumeral.charAt(romanNumeral.length()-1)=='\"') {
      romanNumeral = romanNumeral.substring(1, romanNumeral.length()-1);
    }

    romanNumeral = romanNumeral.trim();

    return romanNumeral;
  }



}
