package org.apache.skywalking.oap.server.library.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class BooleanUtilsDiffblueTest {
  /**
   * Test {@link BooleanUtils#valueToBoolean(int)}.
   * <ul>
   *   <li>When forty-two.</li>
   *   <li>Then throw {@link RuntimeException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BooleanUtils#valueToBoolean(int)}
   */
  @Test
  @DisplayName("Test valueToBoolean(int); when forty-two; then throw RuntimeException")
  @Tag("MaintainedByDiffblue")
  void testValueToBoolean_whenFortyTwo_thenThrowRuntimeException() {
    // Arrange, Act and Assert
    assertThrows(RuntimeException.class, () -> BooleanUtils.valueToBoolean(42));
  }

  /**
   * Test {@link BooleanUtils#valueToBoolean(int)}.
   * <ul>
   *   <li>When one.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BooleanUtils#valueToBoolean(int)}
   */
  @Test
  @DisplayName("Test valueToBoolean(int); when one; then return 'true'")
  @Tag("MaintainedByDiffblue")
  void testValueToBoolean_whenOne_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue(BooleanUtils.valueToBoolean(1));
  }

  /**
   * Test {@link BooleanUtils#valueToBoolean(int)}.
   * <ul>
   *   <li>When zero.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BooleanUtils#valueToBoolean(int)}
   */
  @Test
  @DisplayName("Test valueToBoolean(int); when zero; then return 'false'")
  @Tag("MaintainedByDiffblue")
  void testValueToBoolean_whenZero_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(BooleanUtils.valueToBoolean(0));
  }

  /**
   * Test {@link BooleanUtils#booleanToValue(Boolean)}.
   * <ul>
   *   <li>When {@code false}.</li>
   *   <li>Then return zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link BooleanUtils#booleanToValue(Boolean)}
   */
  @Test
  @DisplayName("Test booleanToValue(Boolean); when 'false'; then return zero")
  @Tag("MaintainedByDiffblue")
  void testBooleanToValue_whenFalse_thenReturnZero() {
    // Arrange, Act and Assert
    assertEquals(0, BooleanUtils.booleanToValue(false));
  }

  /**
   * Test {@link BooleanUtils#booleanToValue(Boolean)}.
   * <ul>
   *   <li>When {@code true}.</li>
   *   <li>Then return one.</li>
   * </ul>
   * <p>
   * Method under test: {@link BooleanUtils#booleanToValue(Boolean)}
   */
  @Test
  @DisplayName("Test booleanToValue(Boolean); when 'true'; then return one")
  @Tag("MaintainedByDiffblue")
  void testBooleanToValue_whenTrue_thenReturnOne() {
    // Arrange, Act and Assert
    assertEquals(1, BooleanUtils.booleanToValue(true));
  }
}
