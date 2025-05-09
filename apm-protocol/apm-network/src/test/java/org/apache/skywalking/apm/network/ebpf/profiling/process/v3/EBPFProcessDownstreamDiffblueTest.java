package org.apache.skywalking.apm.network.ebpf.profiling.process.v3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.apache.skywalking.apm.network.ebpf.profiling.process.v3.EBPFProcessDownstream.ProcessCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class EBPFProcessDownstreamDiffblueTest {
  /**
   * Test ProcessCase {@link ProcessCase#forNumber(int)}.
   * <ul>
   *   <li>When forty-two.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessCase#forNumber(int)}
   */
  @Test
  @DisplayName("Test ProcessCase forNumber(int); when forty-two; then return 'null'")
  @Tag("MaintainedByDiffblue")
  void testProcessCaseForNumber_whenFortyTwo_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(ProcessCase.forNumber(42));
  }

  /**
   * Test ProcessCase {@link ProcessCase#forNumber(int)}.
   * <ul>
   *   <li>When three.</li>
   *   <li>Then return {@code K8SPROCESS}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessCase#forNumber(int)}
   */
  @Test
  @DisplayName("Test ProcessCase forNumber(int); when three; then return 'K8SPROCESS'")
  @Tag("MaintainedByDiffblue")
  void testProcessCaseForNumber_whenThree_thenReturnK8sprocess() {
    // Arrange, Act and Assert
    assertEquals(ProcessCase.K8SPROCESS, ProcessCase.forNumber(3));
  }

  /**
   * Test ProcessCase {@link ProcessCase#forNumber(int)}.
   * <ul>
   *   <li>When two.</li>
   *   <li>Then return {@code HOSTPROCESS}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessCase#forNumber(int)}
   */
  @Test
  @DisplayName("Test ProcessCase forNumber(int); when two; then return 'HOSTPROCESS'")
  @Tag("MaintainedByDiffblue")
  void testProcessCaseForNumber_whenTwo_thenReturnHostprocess() {
    // Arrange, Act and Assert
    assertEquals(ProcessCase.HOSTPROCESS, ProcessCase.forNumber(2));
  }

  /**
   * Test ProcessCase {@link ProcessCase#forNumber(int)}.
   * <ul>
   *   <li>When zero.</li>
   *   <li>Then return {@code PROCESS_NOT_SET}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessCase#forNumber(int)}
   */
  @Test
  @DisplayName("Test ProcessCase forNumber(int); when zero; then return 'PROCESS_NOT_SET'")
  @Tag("MaintainedByDiffblue")
  void testProcessCaseForNumber_whenZero_thenReturnProcessNotSet() {
    // Arrange, Act and Assert
    assertEquals(ProcessCase.PROCESS_NOT_SET, ProcessCase.forNumber(0));
  }

  /**
   * Test ProcessCase {@link ProcessCase#getNumber()}.
   * <p>
   * Method under test: {@link ProcessCase#getNumber()}
   */
  @Test
  @DisplayName("Test ProcessCase getNumber()")
  @Tag("MaintainedByDiffblue")
  void testProcessCaseGetNumber() {
    // Arrange, Act and Assert
    assertEquals(2, ProcessCase.valueOf("HOSTPROCESS").getNumber());
  }

  /**
   * Test ProcessCase {@link ProcessCase#valueOf(int)} with {@code value}.
   * <ul>
   *   <li>When forty-two.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessCase#valueOf(int)}
   */
  @Test
  @DisplayName("Test ProcessCase valueOf(int) with 'value'; when forty-two; then return 'null'")
  @Tag("MaintainedByDiffblue")
  void testProcessCaseValueOfWithValue_whenFortyTwo_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(ProcessCase.valueOf(42));
  }

  /**
   * Test ProcessCase {@link ProcessCase#valueOf(int)} with {@code value}.
   * <ul>
   *   <li>When three.</li>
   *   <li>Then return {@code K8SPROCESS}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessCase#valueOf(int)}
   */
  @Test
  @DisplayName("Test ProcessCase valueOf(int) with 'value'; when three; then return 'K8SPROCESS'")
  @Tag("MaintainedByDiffblue")
  void testProcessCaseValueOfWithValue_whenThree_thenReturnK8sprocess() {
    // Arrange, Act and Assert
    assertEquals(ProcessCase.K8SPROCESS, ProcessCase.valueOf(3));
  }

  /**
   * Test ProcessCase {@link ProcessCase#valueOf(int)} with {@code value}.
   * <ul>
   *   <li>When two.</li>
   *   <li>Then return {@code HOSTPROCESS}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessCase#valueOf(int)}
   */
  @Test
  @DisplayName("Test ProcessCase valueOf(int) with 'value'; when two; then return 'HOSTPROCESS'")
  @Tag("MaintainedByDiffblue")
  void testProcessCaseValueOfWithValue_whenTwo_thenReturnHostprocess() {
    // Arrange, Act and Assert
    assertEquals(ProcessCase.HOSTPROCESS, ProcessCase.valueOf(2));
  }

  /**
   * Test ProcessCase {@link ProcessCase#valueOf(int)} with {@code value}.
   * <ul>
   *   <li>When zero.</li>
   *   <li>Then return {@code PROCESS_NOT_SET}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessCase#valueOf(int)}
   */
  @Test
  @DisplayName("Test ProcessCase valueOf(int) with 'value'; when zero; then return 'PROCESS_NOT_SET'")
  @Tag("MaintainedByDiffblue")
  void testProcessCaseValueOfWithValue_whenZero_thenReturnProcessNotSet() {
    // Arrange, Act and Assert
    assertEquals(ProcessCase.PROCESS_NOT_SET, ProcessCase.valueOf(0));
  }
}
