package org.apache.skywalking.apm.network.ebpf.profiling.v3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.apache.skywalking.apm.network.ebpf.profiling.v3.ContinuousProfilingCause.CauseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ContinuousProfilingCauseDiffblueTest {
  /**
   * Test CauseCase {@link CauseCase#forNumber(int)}.
   * <ul>
   *   <li>When forty-two.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CauseCase#forNumber(int)}
   */
  @Test
  @DisplayName("Test CauseCase forNumber(int); when forty-two; then return 'null'")
  @Tag("MaintainedByDiffblue")
  void testCauseCaseForNumber_whenFortyTwo_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(CauseCase.forNumber(42));
  }

  /**
   * Test CauseCase {@link CauseCase#forNumber(int)}.
   * <ul>
   *   <li>When three.</li>
   *   <li>Then return {@code URI}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CauseCase#forNumber(int)}
   */
  @Test
  @DisplayName("Test CauseCase forNumber(int); when three; then return 'URI'")
  @Tag("MaintainedByDiffblue")
  void testCauseCaseForNumber_whenThree_thenReturnUri() {
    // Arrange, Act and Assert
    assertEquals(CauseCase.URI, CauseCase.forNumber(3));
  }

  /**
   * Test CauseCase {@link CauseCase#forNumber(int)}.
   * <ul>
   *   <li>When two.</li>
   *   <li>Then return {@code SINGLEVALUE}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CauseCase#forNumber(int)}
   */
  @Test
  @DisplayName("Test CauseCase forNumber(int); when two; then return 'SINGLEVALUE'")
  @Tag("MaintainedByDiffblue")
  void testCauseCaseForNumber_whenTwo_thenReturnSinglevalue() {
    // Arrange, Act and Assert
    assertEquals(CauseCase.SINGLEVALUE, CauseCase.forNumber(2));
  }

  /**
   * Test CauseCase {@link CauseCase#forNumber(int)}.
   * <ul>
   *   <li>When zero.</li>
   *   <li>Then return {@code CAUSE_NOT_SET}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CauseCase#forNumber(int)}
   */
  @Test
  @DisplayName("Test CauseCase forNumber(int); when zero; then return 'CAUSE_NOT_SET'")
  @Tag("MaintainedByDiffblue")
  void testCauseCaseForNumber_whenZero_thenReturnCauseNotSet() {
    // Arrange, Act and Assert
    assertEquals(CauseCase.CAUSE_NOT_SET, CauseCase.forNumber(0));
  }

  /**
   * Test CauseCase {@link CauseCase#getNumber()}.
   * <p>
   * Method under test: {@link CauseCase#getNumber()}
   */
  @Test
  @DisplayName("Test CauseCase getNumber()")
  @Tag("MaintainedByDiffblue")
  void testCauseCaseGetNumber() {
    // Arrange, Act and Assert
    assertEquals(2, CauseCase.valueOf("SINGLEVALUE").getNumber());
  }

  /**
   * Test CauseCase {@link CauseCase#valueOf(int)} with {@code value}.
   * <ul>
   *   <li>When forty-two.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CauseCase#valueOf(int)}
   */
  @Test
  @DisplayName("Test CauseCase valueOf(int) with 'value'; when forty-two; then return 'null'")
  @Tag("MaintainedByDiffblue")
  void testCauseCaseValueOfWithValue_whenFortyTwo_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(CauseCase.valueOf(42));
  }

  /**
   * Test CauseCase {@link CauseCase#valueOf(int)} with {@code value}.
   * <ul>
   *   <li>When three.</li>
   *   <li>Then return {@code URI}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CauseCase#valueOf(int)}
   */
  @Test
  @DisplayName("Test CauseCase valueOf(int) with 'value'; when three; then return 'URI'")
  @Tag("MaintainedByDiffblue")
  void testCauseCaseValueOfWithValue_whenThree_thenReturnUri() {
    // Arrange, Act and Assert
    assertEquals(CauseCase.URI, CauseCase.valueOf(3));
  }

  /**
   * Test CauseCase {@link CauseCase#valueOf(int)} with {@code value}.
   * <ul>
   *   <li>When two.</li>
   *   <li>Then return {@code SINGLEVALUE}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CauseCase#valueOf(int)}
   */
  @Test
  @DisplayName("Test CauseCase valueOf(int) with 'value'; when two; then return 'SINGLEVALUE'")
  @Tag("MaintainedByDiffblue")
  void testCauseCaseValueOfWithValue_whenTwo_thenReturnSinglevalue() {
    // Arrange, Act and Assert
    assertEquals(CauseCase.SINGLEVALUE, CauseCase.valueOf(2));
  }

  /**
   * Test CauseCase {@link CauseCase#valueOf(int)} with {@code value}.
   * <ul>
   *   <li>When zero.</li>
   *   <li>Then return {@code CAUSE_NOT_SET}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CauseCase#valueOf(int)}
   */
  @Test
  @DisplayName("Test CauseCase valueOf(int) with 'value'; when zero; then return 'CAUSE_NOT_SET'")
  @Tag("MaintainedByDiffblue")
  void testCauseCaseValueOfWithValue_whenZero_thenReturnCauseNotSet() {
    // Arrange, Act and Assert
    assertEquals(CauseCase.CAUSE_NOT_SET, CauseCase.valueOf(0));
  }
}
