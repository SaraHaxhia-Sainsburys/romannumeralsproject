package com.saraspring.romannumeralsproject.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.saraspring.romannumeralsproject.error.InvalidInputException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class ConversionServiceImplTest {

  private final ConversionServiceImpl conversionService = new ConversionServiceImpl();

  @ParameterizedTest
  @CsvSource({"I, 1", "XXX, 30", "DC, 600", "MM, 2000", "LXXXVII, 87", "MMCCCXLIX, 2349",
      "LIV, 54"})
  void validInputShouldReturnNumber(String input, int expected) {
    assertEquals(expected, conversionService.convertToNumber(input));
  }
  @Test
  void validInputShouldNotBeCaseSensitive() {
    assertEquals(30, conversionService.convertToNumber("xxx"));
  }

  @ParameterizedTest
  @ValueSource(strings = {" XXX", "  XXX "})
  void validInputShouldIgnoreTrailingSpaces(String input) {
    assertEquals(30, conversionService.convertToNumber(input));
  }

  @Test
  void nullInputShouldReturnException() {
    InvalidInputException exception = assertThrows(InvalidInputException.class, () -> conversionService.convertToNumber(null));
    assertEquals("Roman numeral input must be a non-null string", exception.getMessage());
  }

  @ParameterizedTest
  @ValueSource(strings = {"", " ", "  "})
  void emptyInputShouldReturnException(String input) {
    InvalidInputException exception = assertThrows(InvalidInputException.class, () -> conversionService.convertToNumber(input));
    assertEquals("Roman numeral input cannot be empty", exception.getMessage());
  }

  @Test
  void tooLongInputShouldReturnException() {
    InvalidInputException exception = assertThrows(InvalidInputException.class, () -> conversionService.convertToNumber("MMMMDCCCLXXXVIII"));
    assertEquals("Roman numeral input cannot be longer than 15 characters", exception.getMessage());
  }

  @Test
  void invalidCharactersInInputShouldReturnException() {
    InvalidInputException exception = assertThrows(InvalidInputException.class, () -> conversionService.convertToNumber("Mmab"));
    assertEquals("Roman numeral input must contain only valid Roman numeral characters", exception.getMessage());
  }

  @ParameterizedTest
  @ValueSource(strings = {"MMMM", "IXX ", "IIV", "XXXX", "DCCCM", "LL", "CMXCIIV"})
  void invalidInputShouldReturnException(String input) {
    assertThrows(InvalidInputException.class, () -> conversionService.convertToNumber(input));
  }

}