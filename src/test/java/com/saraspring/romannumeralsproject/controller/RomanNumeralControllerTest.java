package com.saraspring.romannumeralsproject.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.saraspring.romannumeralsproject.error.InvalidInputException;
import com.saraspring.romannumeralsproject.service.ConversionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(RomanNumeralController.class)
class RomanNumeralControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  ConversionService conversionService;

@Test
  void invalidInputShouldReturn400() throws Exception{
  when(conversionService.convertToNumber("MMMM")).thenThrow(new InvalidInputException("Invalid input"));
  mockMvc.perform((post("/romannumerals/convert-to-number")
          .param("romanNumeral", "MMMM")))
      .andExpect(status().isBadRequest());
}

@Test
void noInputParametersShouldReturn400() throws Exception {
  mockMvc.perform((post("/romannumerals/convert-to-number")))
      .andExpect(status().isBadRequest());
}

@Test
void unexpectedErrorShouldReturn500() throws Exception {
  when(conversionService.convertToNumber("X")).thenThrow(new RuntimeException("Unexpected error"));
  mockMvc.perform((post("/romannumerals/convert-to-number")
          .param("romanNumeral", "X")))
      .andExpect(status().isInternalServerError());
}


@Test
  void validInputShouldReturnNumber() throws Exception{
  when(conversionService.convertToNumber("X")).thenReturn(10);
  mockMvc.perform((post("/romannumerals/convert-to-number")
          .param("romanNumeral", "X")))
      .andExpect(status().isOk())
      .andExpect(content().string("10"));
}
}