package org.apache.skywalking.apm.network.ebpf.accesslog.v3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.apache.skywalking.apm.network.ebpf.accesslog.v3.EBPFTimestamp.TimestampCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class EBPFTimestampDiffblueTest {
  /**
   * Test TimestampCase {@link TimestampCase#forNumber(int)}.
   * <ul>
   *   <li>When forty-two.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TimestampCase#forNumber(int)}
   */
  @Test
  @DisplayName("Test TimestampCase forNumber(int); when forty-two; then return 'null'")
  @Tag("MaintainedByDiffblue")
  void testTimestampCaseForNumber_whenFortyTwo_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(TimestampCase.forNumber(42));
  }

  /**
   * Test TimestampCase {@link TimestampCase#forNumber(int)}.
   * <ul>
   *   <li>When one.</li>
   *   <li>Then return {@code OFFSET}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TimestampCase#forNumber(int)}
   */
  @Test
  @DisplayName("Test TimestampCase forNumber(int); when one; then return 'OFFSET'")
  @Tag("MaintainedByDiffblue")
  void testTimestampCaseForNumber_whenOne_thenReturnOffset() {
    // Arrange, Act and Assert
    assertEquals(TimestampCase.OFFSET, TimestampCase.forNumber(1));
  }

  /**
   * Test TimestampCase {@link TimestampCase#forNumber(int)}.
   * <ul>
   *   <li>When zero.</li>
   *   <li>Then return {@code TIMESTAMP_NOT_SET}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TimestampCase#forNumber(int)}
   */
  @Test
  @DisplayName("Test TimestampCase forNumber(int); when zero; then return 'TIMESTAMP_NOT_SET'")
  @Tag("MaintainedByDiffblue")
  void testTimestampCaseForNumber_whenZero_thenReturnTimestampNotSet() {
    // Arrange, Act and Assert
    assertEquals(TimestampCase.TIMESTAMP_NOT_SET, TimestampCase.forNumber(0));
  }

  /**
   * Test TimestampCase {@link TimestampCase#getNumber()}.
   * <p>
   * Method under test: {@link TimestampCase#getNumber()}
   */
  @Test
  @DisplayName("Test TimestampCase getNumber()")
  @Tag("MaintainedByDiffblue")
  void testTimestampCaseGetNumber() {
    // Arrange, Act and Assert
    assertEquals(1, TimestampCase.valueOf("OFFSET").getNumber());
  }

  /**
   * Test TimestampCase {@link TimestampCase#valueOf(int)} with {@code value}.
   * <ul>
   *   <li>When forty-two.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TimestampCase#valueOf(int)}
   */
  @Test
  @DisplayName("Test TimestampCase valueOf(int) with 'value'; when forty-two; then return 'null'")
  @Tag("MaintainedByDiffblue")
  void testTimestampCaseValueOfWithValue_whenFortyTwo_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(TimestampCase.valueOf(42));
  }

  /**
   * Test TimestampCase {@link TimestampCase#valueOf(int)} with {@code value}.
   * <ul>
   *   <li>When one.</li>
   *   <li>Then return {@code OFFSET}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TimestampCase#valueOf(int)}
   */
  @Test
  @DisplayName("Test TimestampCase valueOf(int) with 'value'; when one; then return 'OFFSET'")
  @Tag("MaintainedByDiffblue")
  void testTimestampCaseValueOfWithValue_whenOne_thenReturnOffset() {
    // Arrange, Act and Assert
    assertEquals(TimestampCase.OFFSET, TimestampCase.valueOf(1));
  }

  /**
   * Test TimestampCase {@link TimestampCase#valueOf(int)} with {@code value}.
   * <ul>
   *   <li>When zero.</li>
   *   <li>Then return {@code TIMESTAMP_NOT_SET}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TimestampCase#valueOf(int)}
   */
  @Test
  @DisplayName("Test TimestampCase valueOf(int) with 'value'; when zero; then return 'TIMESTAMP_NOT_SET'")
  @Tag("MaintainedByDiffblue")
  void testTimestampCaseValueOfWithValue_whenZero_thenReturnTimestampNotSet() {
    // Arrange, Act and Assert
    assertEquals(TimestampCase.TIMESTAMP_NOT_SET, TimestampCase.valueOf(0));
  }
}
