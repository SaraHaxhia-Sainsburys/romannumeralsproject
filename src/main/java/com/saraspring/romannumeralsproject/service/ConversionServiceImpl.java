package com.saraspring.romannumeralsproject.service;

import com.saraspring.romannumeralsproject.error.InvalidInputException;
import org.springframework.stereotype.Service;

@Service
public class ConversionServiceImpl implements ConversionService {

  public int convertToNumber(String romanNumeral) {

    romanNumeral = initialValidationAndClean(romanNumeral);

    int total = 0;

    //process thousands
    if(romanNumeral.charAt(0) == 'M') {
        if(romanNumeral.length()>1 && romanNumeral.charAt(1) == 'M') {
          if(romanNumeral.length()>2 && romanNumeral.charAt(2) == 'M') {
            total += 3000;
            romanNumeral = romanNumeral.substring(3);
          } else {
            total += 2000;
            romanNumeral = romanNumeral.substring(2);
          }
        } else {
          total += 1000;
          romanNumeral = romanNumeral.substring(1);
        }
    }

    //process hundreds
    if(!(romanNumeral.isEmpty()) && (romanNumeral.charAt(0) == 'C' || romanNumeral.charAt(0) == 'D')) {
      CheckOptionsResult result = checkOptions(romanNumeral, 'C', 'D', 'M', 100);
      total += result.getValue();
      romanNumeral = result.getRemainingNumeral();
    }

    //process tens
    if(!(romanNumeral.isEmpty()) && (romanNumeral.charAt(0) == 'X' || romanNumeral.charAt(0) == 'L') ){
      CheckOptionsResult result = checkOptions(romanNumeral, 'X', 'L', 'C', 10);
      total += result.getValue();
      romanNumeral = result.getRemainingNumeral();
    }

    //process units
    if(!(romanNumeral.isEmpty()) && (romanNumeral.charAt(0)=='I' || romanNumeral.charAt(0)=='V')) {
      CheckOptionsResult result = checkOptions(romanNumeral, 'I', 'V', 'X', 1);
      total += result.getValue();
      romanNumeral = result.getRemainingNumeral();
    }

    if(romanNumeral.isEmpty()) {
      return total;
    }

    throw new InvalidInputException("Invalid Roman numeral input");



  }


  private CheckOptionsResult checkOptions(String romanNumeral, char firstChar, char secondChar, char thirdChar, int units) {
    int value = 0;
    String remainingNumeral;
    if (romanNumeral.charAt(0) == firstChar) { //e.g. starts with C
      if (romanNumeral.length() >1 && romanNumeral.charAt(1) == firstChar) { //e.g.  starts with CC
        if (romanNumeral.length() >2 && romanNumeral.charAt(2) == firstChar) { //e.g. CCC
          value += 3 * units;
          remainingNumeral = romanNumeral.substring(3);
          return new CheckOptionsResult(value, remainingNumeral);
        } else { //e.g. CC
          value += 2 * units;
          remainingNumeral = romanNumeral.substring(2);
          return new CheckOptionsResult(value, remainingNumeral);
        }
      } else if (romanNumeral.length() >1 && romanNumeral.charAt(1) == secondChar) { //e.g. CD
        value += 4 * units;
        remainingNumeral = romanNumeral.substring(2);
        return new CheckOptionsResult(value, remainingNumeral);
      } else if (romanNumeral.length() >1 && romanNumeral.charAt(1) == thirdChar) { //e.g. CM
        value += 9 * units;
        remainingNumeral = romanNumeral.substring(2);
        return new CheckOptionsResult(value, remainingNumeral);
      } else { //e.g. C
        value += units;
        remainingNumeral = romanNumeral.substring(1);
        return new CheckOptionsResult(value, remainingNumeral);
      }
    }

    if (romanNumeral.charAt(0) == secondChar) { //e.g. starts with D
      if (romanNumeral.length() >1 && romanNumeral.charAt(1) == firstChar) { //e.g.  starts with DC
        if (romanNumeral.length() >2 && romanNumeral.charAt(2) == firstChar) { //e.g. starts with DCC
          if (romanNumeral.length() >3 && romanNumeral.charAt(3) == firstChar) { //e.g.  DCCC
            value += 8 * units;
            remainingNumeral = romanNumeral.substring(4);
            return new CheckOptionsResult(value, remainingNumeral);
          } else { //e.g. DCC
            value += 7 * units;
            remainingNumeral = romanNumeral.substring(3);
            return new CheckOptionsResult(value, remainingNumeral);
          }
        } else { //e.g. DC
          value += 6 * units;
          remainingNumeral = romanNumeral.substring(2);
          return new CheckOptionsResult(value, remainingNumeral);
        }
      } else { //e.g. D
        value += 5 * units;
        remainingNumeral = romanNumeral.substring(1);
        return new CheckOptionsResult(value, remainingNumeral);
      }
    }

  throw new InvalidInputException("Invalid Roman numeral input");

    }




  private String initialValidationAndClean(String romanNumeral) {
    if(romanNumeral == null) {
      throw new InvalidInputException("Roman numeral input must be a non-null string");
    }

    romanNumeral = cleanString(romanNumeral);

    if (romanNumeral.isEmpty() ) {
      throw new InvalidInputException("Roman numeral input cannot be empty");
    }

    if(romanNumeral.length() > 15) {
      throw new InvalidInputException("Roman numeral input cannot be longer than 15 characters");
    }

    if(!(romanNumeral.matches("^[IVXLCDM]+$"))) {
      throw new InvalidInputException("Roman numeral input must contain only valid Roman numeral characters");
    }


  return romanNumeral;

  }


  private String cleanString(String romanNumeral) {
    if(romanNumeral.isEmpty()) {
      return romanNumeral;
    }

    romanNumeral = romanNumeral.trim();

    romanNumeral = romanNumeral.toUpperCase();

    return romanNumeral;
  }

}
