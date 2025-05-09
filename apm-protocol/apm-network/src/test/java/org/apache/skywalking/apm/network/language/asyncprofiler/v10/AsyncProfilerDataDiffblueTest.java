package org.apache.skywalking.apm.network.language.asyncprofiler.v10;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.apache.skywalking.apm.network.language.asyncprofiler.v10.AsyncProfilerData.ResultCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class AsyncProfilerDataDiffblueTest {
  /**
   * Test ResultCase {@link ResultCase#forNumber(int)}.
   * <ul>
   *   <li>When forty-two.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ResultCase#forNumber(int)}
   */
  @Test
  @DisplayName("Test ResultCase forNumber(int); when forty-two; then return 'null'")
  @Tag("MaintainedByDiffblue")
  void testResultCaseForNumber_whenFortyTwo_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(ResultCase.forNumber(42));
  }

  /**
   * Test ResultCase {@link ResultCase#forNumber(int)}.
   * <ul>
   *   <li>When three.</li>
   *   <li>Then return {@code CONTENT}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ResultCase#forNumber(int)}
   */
  @Test
  @DisplayName("Test ResultCase forNumber(int); when three; then return 'CONTENT'")
  @Tag("MaintainedByDiffblue")
  void testResultCaseForNumber_whenThree_thenReturnContent() {
    // Arrange, Act and Assert
    assertEquals(ResultCase.CONTENT, ResultCase.forNumber(3));
  }

  /**
   * Test ResultCase {@link ResultCase#forNumber(int)}.
   * <ul>
   *   <li>When two.</li>
   *   <li>Then return {@code ERRORMESSAGE}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ResultCase#forNumber(int)}
   */
  @Test
  @DisplayName("Test ResultCase forNumber(int); when two; then return 'ERRORMESSAGE'")
  @Tag("MaintainedByDiffblue")
  void testResultCaseForNumber_whenTwo_thenReturnErrormessage() {
    // Arrange, Act and Assert
    assertEquals(ResultCase.ERRORMESSAGE, ResultCase.forNumber(2));
  }

  /**
   * Test ResultCase {@link ResultCase#forNumber(int)}.
   * <ul>
   *   <li>When zero.</li>
   *   <li>Then return {@code RESULT_NOT_SET}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ResultCase#forNumber(int)}
   */
  @Test
  @DisplayName("Test ResultCase forNumber(int); when zero; then return 'RESULT_NOT_SET'")
  @Tag("MaintainedByDiffblue")
  void testResultCaseForNumber_whenZero_thenReturnResultNotSet() {
    // Arrange, Act and Assert
    assertEquals(ResultCase.RESULT_NOT_SET, ResultCase.forNumber(0));
  }

  /**
   * Test ResultCase {@link ResultCase#getNumber()}.
   * <p>
   * Method under test: {@link ResultCase#getNumber()}
   */
  @Test
  @DisplayName("Test ResultCase getNumber()")
  @Tag("MaintainedByDiffblue")
  void testResultCaseGetNumber() {
    // Arrange, Act and Assert
    assertEquals(2, ResultCase.valueOf("ERRORMESSAGE").getNumber());
  }

  /**
   * Test ResultCase {@link ResultCase#valueOf(int)} with {@code value}.
   * <ul>
   *   <li>When forty-two.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ResultCase#valueOf(int)}
   */
  @Test
  @DisplayName("Test ResultCase valueOf(int) with 'value'; when forty-two; then return 'null'")
  @Tag("MaintainedByDiffblue")
  void testResultCaseValueOfWithValue_whenFortyTwo_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(ResultCase.valueOf(42));
  }

  /**
   * Test ResultCase {@link ResultCase#valueOf(int)} with {@code value}.
   * <ul>
   *   <li>When three.</li>
   *   <li>Then return {@code CONTENT}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ResultCase#valueOf(int)}
   */
  @Test
  @DisplayName("Test ResultCase valueOf(int) with 'value'; when three; then return 'CONTENT'")
  @Tag("MaintainedByDiffblue")
  void testResultCaseValueOfWithValue_whenThree_thenReturnContent() {
    // Arrange, Act and Assert
    assertEquals(ResultCase.CONTENT, ResultCase.valueOf(3));
  }

  /**
   * Test ResultCase {@link ResultCase#valueOf(int)} with {@code value}.
   * <ul>
   *   <li>When two.</li>
   *   <li>Then return {@code ERRORMESSAGE}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ResultCase#valueOf(int)}
   */
  @Test
  @DisplayName("Test ResultCase valueOf(int) with 'value'; when two; then return 'ERRORMESSAGE'")
  @Tag("MaintainedByDiffblue")
  void testResultCaseValueOfWithValue_whenTwo_thenReturnErrormessage() {
    // Arrange, Act and Assert
    assertEquals(ResultCase.ERRORMESSAGE, ResultCase.valueOf(2));
  }

  /**
   * Test ResultCase {@link ResultCase#valueOf(int)} with {@code value}.
   * <ul>
   *   <li>When zero.</li>
   *   <li>Then return {@code RESULT_NOT_SET}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ResultCase#valueOf(int)}
   */
  @Test
  @DisplayName("Test ResultCase valueOf(int) with 'value'; when zero; then return 'RESULT_NOT_SET'")
  @Tag("MaintainedByDiffblue")
  void testResultCaseValueOfWithValue_whenZero_thenReturnResultNotSet() {
    // Arrange, Act and Assert
    assertEquals(ResultCase.RESULT_NOT_SET, ResultCase.valueOf(0));
  }
}
