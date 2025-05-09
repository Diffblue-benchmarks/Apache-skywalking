package org.apache.skywalking.apm.network.ebpf.accesslog.v3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.apache.skywalking.apm.network.ebpf.accesslog.v3.AccessLogProtocolLogs.ProtocolCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class AccessLogProtocolLogsDiffblueTest {
  /**
   * Test ProtocolCase {@link ProtocolCase#forNumber(int)}.
   * <ul>
   *   <li>When forty-two.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProtocolCase#forNumber(int)}
   */
  @Test
  @DisplayName("Test ProtocolCase forNumber(int); when forty-two; then return 'null'")
  @Tag("MaintainedByDiffblue")
  void testProtocolCaseForNumber_whenFortyTwo_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(ProtocolCase.forNumber(42));
  }

  /**
   * Test ProtocolCase {@link ProtocolCase#forNumber(int)}.
   * <ul>
   *   <li>When one.</li>
   *   <li>Then return {@code HTTP}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProtocolCase#forNumber(int)}
   */
  @Test
  @DisplayName("Test ProtocolCase forNumber(int); when one; then return 'HTTP'")
  @Tag("MaintainedByDiffblue")
  void testProtocolCaseForNumber_whenOne_thenReturnHttp() {
    // Arrange, Act and Assert
    assertEquals(ProtocolCase.HTTP, ProtocolCase.forNumber(1));
  }

  /**
   * Test ProtocolCase {@link ProtocolCase#forNumber(int)}.
   * <ul>
   *   <li>When zero.</li>
   *   <li>Then return {@code PROTOCOL_NOT_SET}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProtocolCase#forNumber(int)}
   */
  @Test
  @DisplayName("Test ProtocolCase forNumber(int); when zero; then return 'PROTOCOL_NOT_SET'")
  @Tag("MaintainedByDiffblue")
  void testProtocolCaseForNumber_whenZero_thenReturnProtocolNotSet() {
    // Arrange, Act and Assert
    assertEquals(ProtocolCase.PROTOCOL_NOT_SET, ProtocolCase.forNumber(0));
  }

  /**
   * Test ProtocolCase {@link ProtocolCase#getNumber()}.
   * <p>
   * Method under test: {@link ProtocolCase#getNumber()}
   */
  @Test
  @DisplayName("Test ProtocolCase getNumber()")
  @Tag("MaintainedByDiffblue")
  void testProtocolCaseGetNumber() {
    // Arrange, Act and Assert
    assertEquals(1, ProtocolCase.valueOf("HTTP").getNumber());
  }

  /**
   * Test ProtocolCase {@link ProtocolCase#valueOf(int)} with {@code value}.
   * <ul>
   *   <li>When forty-two.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProtocolCase#valueOf(int)}
   */
  @Test
  @DisplayName("Test ProtocolCase valueOf(int) with 'value'; when forty-two; then return 'null'")
  @Tag("MaintainedByDiffblue")
  void testProtocolCaseValueOfWithValue_whenFortyTwo_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(ProtocolCase.valueOf(42));
  }

  /**
   * Test ProtocolCase {@link ProtocolCase#valueOf(int)} with {@code value}.
   * <ul>
   *   <li>When one.</li>
   *   <li>Then return {@code HTTP}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProtocolCase#valueOf(int)}
   */
  @Test
  @DisplayName("Test ProtocolCase valueOf(int) with 'value'; when one; then return 'HTTP'")
  @Tag("MaintainedByDiffblue")
  void testProtocolCaseValueOfWithValue_whenOne_thenReturnHttp() {
    // Arrange, Act and Assert
    assertEquals(ProtocolCase.HTTP, ProtocolCase.valueOf(1));
  }

  /**
   * Test ProtocolCase {@link ProtocolCase#valueOf(int)} with {@code value}.
   * <ul>
   *   <li>When zero.</li>
   *   <li>Then return {@code PROTOCOL_NOT_SET}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProtocolCase#valueOf(int)}
   */
  @Test
  @DisplayName("Test ProtocolCase valueOf(int) with 'value'; when zero; then return 'PROTOCOL_NOT_SET'")
  @Tag("MaintainedByDiffblue")
  void testProtocolCaseValueOfWithValue_whenZero_thenReturnProtocolNotSet() {
    // Arrange, Act and Assert
    assertEquals(ProtocolCase.PROTOCOL_NOT_SET, ProtocolCase.valueOf(0));
  }
}
