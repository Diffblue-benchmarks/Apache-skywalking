package org.apache.skywalking.apm.network.ebpf.accesslog.v3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.apache.skywalking.apm.network.ebpf.accesslog.v3.ConnectionAttachment.EnvironmentCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ConnectionAttachmentDiffblueTest {
  /**
   * Test EnvironmentCase {@link EnvironmentCase#forNumber(int)}.
   * <ul>
   *   <li>When forty-two.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EnvironmentCase#forNumber(int)}
   */
  @Test
  @DisplayName("Test EnvironmentCase forNumber(int); when forty-two; then return 'null'")
  @Tag("MaintainedByDiffblue")
  void testEnvironmentCaseForNumber_whenFortyTwo_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(EnvironmentCase.forNumber(42));
  }

  /**
   * Test EnvironmentCase {@link EnvironmentCase#forNumber(int)}.
   * <ul>
   *   <li>When one.</li>
   *   <li>Then return {@code ZTUNNEL}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EnvironmentCase#forNumber(int)}
   */
  @Test
  @DisplayName("Test EnvironmentCase forNumber(int); when one; then return 'ZTUNNEL'")
  @Tag("MaintainedByDiffblue")
  void testEnvironmentCaseForNumber_whenOne_thenReturnZtunnel() {
    // Arrange, Act and Assert
    assertEquals(EnvironmentCase.ZTUNNEL, EnvironmentCase.forNumber(1));
  }

  /**
   * Test EnvironmentCase {@link EnvironmentCase#forNumber(int)}.
   * <ul>
   *   <li>When zero.</li>
   *   <li>Then return {@code ENVIRONMENT_NOT_SET}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EnvironmentCase#forNumber(int)}
   */
  @Test
  @DisplayName("Test EnvironmentCase forNumber(int); when zero; then return 'ENVIRONMENT_NOT_SET'")
  @Tag("MaintainedByDiffblue")
  void testEnvironmentCaseForNumber_whenZero_thenReturnEnvironmentNotSet() {
    // Arrange, Act and Assert
    assertEquals(EnvironmentCase.ENVIRONMENT_NOT_SET, EnvironmentCase.forNumber(0));
  }

  /**
   * Test EnvironmentCase {@link EnvironmentCase#getNumber()}.
   * <p>
   * Method under test: {@link EnvironmentCase#getNumber()}
   */
  @Test
  @DisplayName("Test EnvironmentCase getNumber()")
  @Tag("MaintainedByDiffblue")
  void testEnvironmentCaseGetNumber() {
    // Arrange, Act and Assert
    assertEquals(1, EnvironmentCase.valueOf("ZTUNNEL").getNumber());
  }

  /**
   * Test EnvironmentCase {@link EnvironmentCase#valueOf(int)} with {@code value}.
   * <ul>
   *   <li>When forty-two.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EnvironmentCase#valueOf(int)}
   */
  @Test
  @DisplayName("Test EnvironmentCase valueOf(int) with 'value'; when forty-two; then return 'null'")
  @Tag("MaintainedByDiffblue")
  void testEnvironmentCaseValueOfWithValue_whenFortyTwo_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(EnvironmentCase.valueOf(42));
  }

  /**
   * Test EnvironmentCase {@link EnvironmentCase#valueOf(int)} with {@code value}.
   * <ul>
   *   <li>When one.</li>
   *   <li>Then return {@code ZTUNNEL}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EnvironmentCase#valueOf(int)}
   */
  @Test
  @DisplayName("Test EnvironmentCase valueOf(int) with 'value'; when one; then return 'ZTUNNEL'")
  @Tag("MaintainedByDiffblue")
  void testEnvironmentCaseValueOfWithValue_whenOne_thenReturnZtunnel() {
    // Arrange, Act and Assert
    assertEquals(EnvironmentCase.ZTUNNEL, EnvironmentCase.valueOf(1));
  }

  /**
   * Test EnvironmentCase {@link EnvironmentCase#valueOf(int)} with {@code value}.
   * <ul>
   *   <li>When zero.</li>
   *   <li>Then return {@code ENVIRONMENT_NOT_SET}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EnvironmentCase#valueOf(int)}
   */
  @Test
  @DisplayName("Test EnvironmentCase valueOf(int) with 'value'; when zero; then return 'ENVIRONMENT_NOT_SET'")
  @Tag("MaintainedByDiffblue")
  void testEnvironmentCaseValueOfWithValue_whenZero_thenReturnEnvironmentNotSet() {
    // Arrange, Act and Assert
    assertEquals(EnvironmentCase.ENVIRONMENT_NOT_SET, EnvironmentCase.valueOf(0));
  }
}
