package org.apache.skywalking.apm.network.ebpf.accesslog.v3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.apache.skywalking.apm.network.ebpf.accesslog.v3.AccessLogKernelLog.OperationCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class AccessLogKernelLogDiffblueTest {
  /**
   * Test OperationCase {@link OperationCase#forNumber(int)}.
   * <ul>
   *   <li>When five.</li>
   *   <li>Then return {@code WRITE}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OperationCase#forNumber(int)}
   */
  @Test
  @DisplayName("Test OperationCase forNumber(int); when five; then return 'WRITE'")
  @Tag("MaintainedByDiffblue")
  void testOperationCaseForNumber_whenFive_thenReturnWrite() {
    // Arrange, Act and Assert
    assertEquals(OperationCase.WRITE, OperationCase.forNumber(5));
  }

  /**
   * Test OperationCase {@link OperationCase#forNumber(int)}.
   * <ul>
   *   <li>When forty-two.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OperationCase#forNumber(int)}
   */
  @Test
  @DisplayName("Test OperationCase forNumber(int); when forty-two; then return 'null'")
  @Tag("MaintainedByDiffblue")
  void testOperationCaseForNumber_whenFortyTwo_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(OperationCase.forNumber(42));
  }

  /**
   * Test OperationCase {@link OperationCase#forNumber(int)}.
   * <ul>
   *   <li>When four.</li>
   *   <li>Then return {@code READ}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OperationCase#forNumber(int)}
   */
  @Test
  @DisplayName("Test OperationCase forNumber(int); when four; then return 'READ'")
  @Tag("MaintainedByDiffblue")
  void testOperationCaseForNumber_whenFour_thenReturnRead() {
    // Arrange, Act and Assert
    assertEquals(OperationCase.READ, OperationCase.forNumber(4));
  }

  /**
   * Test OperationCase {@link OperationCase#forNumber(int)}.
   * <ul>
   *   <li>When one.</li>
   *   <li>Then return {@code CONNECT}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OperationCase#forNumber(int)}
   */
  @Test
  @DisplayName("Test OperationCase forNumber(int); when one; then return 'CONNECT'")
  @Tag("MaintainedByDiffblue")
  void testOperationCaseForNumber_whenOne_thenReturnConnect() {
    // Arrange, Act and Assert
    assertEquals(OperationCase.CONNECT, OperationCase.forNumber(1));
  }

  /**
   * Test OperationCase {@link OperationCase#forNumber(int)}.
   * <ul>
   *   <li>When three.</li>
   *   <li>Then return {@code CLOSE}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OperationCase#forNumber(int)}
   */
  @Test
  @DisplayName("Test OperationCase forNumber(int); when three; then return 'CLOSE'")
  @Tag("MaintainedByDiffblue")
  void testOperationCaseForNumber_whenThree_thenReturnClose() {
    // Arrange, Act and Assert
    assertEquals(OperationCase.CLOSE, OperationCase.forNumber(3));
  }

  /**
   * Test OperationCase {@link OperationCase#forNumber(int)}.
   * <ul>
   *   <li>When two.</li>
   *   <li>Then return {@code ACCEPT}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OperationCase#forNumber(int)}
   */
  @Test
  @DisplayName("Test OperationCase forNumber(int); when two; then return 'ACCEPT'")
  @Tag("MaintainedByDiffblue")
  void testOperationCaseForNumber_whenTwo_thenReturnAccept() {
    // Arrange, Act and Assert
    assertEquals(OperationCase.ACCEPT, OperationCase.forNumber(2));
  }

  /**
   * Test OperationCase {@link OperationCase#forNumber(int)}.
   * <ul>
   *   <li>When zero.</li>
   *   <li>Then return {@code OPERATION_NOT_SET}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OperationCase#forNumber(int)}
   */
  @Test
  @DisplayName("Test OperationCase forNumber(int); when zero; then return 'OPERATION_NOT_SET'")
  @Tag("MaintainedByDiffblue")
  void testOperationCaseForNumber_whenZero_thenReturnOperationNotSet() {
    // Arrange, Act and Assert
    assertEquals(OperationCase.OPERATION_NOT_SET, OperationCase.forNumber(0));
  }

  /**
   * Test OperationCase {@link OperationCase#getNumber()}.
   * <p>
   * Method under test: {@link OperationCase#getNumber()}
   */
  @Test
  @DisplayName("Test OperationCase getNumber()")
  @Tag("MaintainedByDiffblue")
  void testOperationCaseGetNumber() {
    // Arrange, Act and Assert
    assertEquals(1, OperationCase.valueOf("CONNECT").getNumber());
  }

  /**
   * Test OperationCase {@link OperationCase#valueOf(int)} with {@code value}.
   * <ul>
   *   <li>When five.</li>
   *   <li>Then return {@code WRITE}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OperationCase#valueOf(int)}
   */
  @Test
  @DisplayName("Test OperationCase valueOf(int) with 'value'; when five; then return 'WRITE'")
  @Tag("MaintainedByDiffblue")
  void testOperationCaseValueOfWithValue_whenFive_thenReturnWrite() {
    // Arrange, Act and Assert
    assertEquals(OperationCase.WRITE, OperationCase.valueOf(5));
  }

  /**
   * Test OperationCase {@link OperationCase#valueOf(int)} with {@code value}.
   * <ul>
   *   <li>When forty-two.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OperationCase#valueOf(int)}
   */
  @Test
  @DisplayName("Test OperationCase valueOf(int) with 'value'; when forty-two; then return 'null'")
  @Tag("MaintainedByDiffblue")
  void testOperationCaseValueOfWithValue_whenFortyTwo_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(OperationCase.valueOf(42));
  }

  /**
   * Test OperationCase {@link OperationCase#valueOf(int)} with {@code value}.
   * <ul>
   *   <li>When four.</li>
   *   <li>Then return {@code READ}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OperationCase#valueOf(int)}
   */
  @Test
  @DisplayName("Test OperationCase valueOf(int) with 'value'; when four; then return 'READ'")
  @Tag("MaintainedByDiffblue")
  void testOperationCaseValueOfWithValue_whenFour_thenReturnRead() {
    // Arrange, Act and Assert
    assertEquals(OperationCase.READ, OperationCase.valueOf(4));
  }

  /**
   * Test OperationCase {@link OperationCase#valueOf(int)} with {@code value}.
   * <ul>
   *   <li>When one.</li>
   *   <li>Then return {@code CONNECT}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OperationCase#valueOf(int)}
   */
  @Test
  @DisplayName("Test OperationCase valueOf(int) with 'value'; when one; then return 'CONNECT'")
  @Tag("MaintainedByDiffblue")
  void testOperationCaseValueOfWithValue_whenOne_thenReturnConnect() {
    // Arrange, Act and Assert
    assertEquals(OperationCase.CONNECT, OperationCase.valueOf(1));
  }

  /**
   * Test OperationCase {@link OperationCase#valueOf(int)} with {@code value}.
   * <ul>
   *   <li>When three.</li>
   *   <li>Then return {@code CLOSE}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OperationCase#valueOf(int)}
   */
  @Test
  @DisplayName("Test OperationCase valueOf(int) with 'value'; when three; then return 'CLOSE'")
  @Tag("MaintainedByDiffblue")
  void testOperationCaseValueOfWithValue_whenThree_thenReturnClose() {
    // Arrange, Act and Assert
    assertEquals(OperationCase.CLOSE, OperationCase.valueOf(3));
  }

  /**
   * Test OperationCase {@link OperationCase#valueOf(int)} with {@code value}.
   * <ul>
   *   <li>When two.</li>
   *   <li>Then return {@code ACCEPT}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OperationCase#valueOf(int)}
   */
  @Test
  @DisplayName("Test OperationCase valueOf(int) with 'value'; when two; then return 'ACCEPT'")
  @Tag("MaintainedByDiffblue")
  void testOperationCaseValueOfWithValue_whenTwo_thenReturnAccept() {
    // Arrange, Act and Assert
    assertEquals(OperationCase.ACCEPT, OperationCase.valueOf(2));
  }

  /**
   * Test OperationCase {@link OperationCase#valueOf(int)} with {@code value}.
   * <ul>
   *   <li>When zero.</li>
   *   <li>Then return {@code OPERATION_NOT_SET}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OperationCase#valueOf(int)}
   */
  @Test
  @DisplayName("Test OperationCase valueOf(int) with 'value'; when zero; then return 'OPERATION_NOT_SET'")
  @Tag("MaintainedByDiffblue")
  void testOperationCaseValueOfWithValue_whenZero_thenReturnOperationNotSet() {
    // Arrange, Act and Assert
    assertEquals(OperationCase.OPERATION_NOT_SET, OperationCase.valueOf(0));
  }
}
