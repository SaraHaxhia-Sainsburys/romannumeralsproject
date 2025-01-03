package com.saraspring.romannumeralsproject.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class RomanNumeralIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @ParameterizedTest
  @CsvSource({"I, 1", "XXX, 30", "DC, 600", "MM, 2000", "LXXXVII, 87", "MMCCCXLIX, 2349",
      "LIV, 54"})
  void validInputShouldReturnNumberIntegrationTest(String input, String expected) throws Exception {
    mockMvc.perform((post("/romannumerals/convert-to-number")
            .param("romanNumeral", input)))
        .andExpect(status().isOk())
        .andExpect(content().string(expected));
  }

  @ParameterizedTest
  @ValueSource(strings = {"MMMM", "IXX ", "IIV", "XXXX", "DCCCM", "LL", "CMXCIIV", " ", "aaa",
      "MMMMDCCCLXXXVIII"})
  void invalidInputShouldReturnBadRequestIntegrationTest(String input) throws Exception {
    mockMvc.perform((post("/romannumerals/convert-to-number")
            .param("romanNumeral", input)))
        .andExpect(status().isBadRequest());

  }
}
