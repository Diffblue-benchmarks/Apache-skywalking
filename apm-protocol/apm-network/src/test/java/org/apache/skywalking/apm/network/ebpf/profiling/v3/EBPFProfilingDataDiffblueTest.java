package org.apache.skywalking.apm.network.ebpf.profiling.v3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.apache.skywalking.apm.network.ebpf.profiling.v3.EBPFProfilingData.ProfilingCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class EBPFProfilingDataDiffblueTest {
  /**
   * Test ProfilingCase {@link ProfilingCase#forNumber(int)}.
   * <ul>
   *   <li>When forty-two.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProfilingCase#forNumber(int)}
   */
  @Test
  @DisplayName("Test ProfilingCase forNumber(int); when forty-two; then return 'null'")
  @Tag("MaintainedByDiffblue")
  void testProfilingCaseForNumber_whenFortyTwo_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(ProfilingCase.forNumber(42));
  }

  /**
   * Test ProfilingCase {@link ProfilingCase#forNumber(int)}.
   * <ul>
   *   <li>When three.</li>
   *   <li>Then return {@code OFFCPU}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProfilingCase#forNumber(int)}
   */
  @Test
  @DisplayName("Test ProfilingCase forNumber(int); when three; then return 'OFFCPU'")
  @Tag("MaintainedByDiffblue")
  void testProfilingCaseForNumber_whenThree_thenReturnOffcpu() {
    // Arrange, Act and Assert
    assertEquals(ProfilingCase.OFFCPU, ProfilingCase.forNumber(3));
  }

  /**
   * Test ProfilingCase {@link ProfilingCase#forNumber(int)}.
   * <ul>
   *   <li>When two.</li>
   *   <li>Then return {@code ONCPU}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProfilingCase#forNumber(int)}
   */
  @Test
  @DisplayName("Test ProfilingCase forNumber(int); when two; then return 'ONCPU'")
  @Tag("MaintainedByDiffblue")
  void testProfilingCaseForNumber_whenTwo_thenReturnOncpu() {
    // Arrange, Act and Assert
    assertEquals(ProfilingCase.ONCPU, ProfilingCase.forNumber(2));
  }

  /**
   * Test ProfilingCase {@link ProfilingCase#forNumber(int)}.
   * <ul>
   *   <li>When zero.</li>
   *   <li>Then return {@code PROFILING_NOT_SET}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProfilingCase#forNumber(int)}
   */
  @Test
  @DisplayName("Test ProfilingCase forNumber(int); when zero; then return 'PROFILING_NOT_SET'")
  @Tag("MaintainedByDiffblue")
  void testProfilingCaseForNumber_whenZero_thenReturnProfilingNotSet() {
    // Arrange, Act and Assert
    assertEquals(ProfilingCase.PROFILING_NOT_SET, ProfilingCase.forNumber(0));
  }

  /**
   * Test ProfilingCase {@link ProfilingCase#getNumber()}.
   * <p>
   * Method under test: {@link ProfilingCase#getNumber()}
   */
  @Test
  @DisplayName("Test ProfilingCase getNumber()")
  @Tag("MaintainedByDiffblue")
  void testProfilingCaseGetNumber() {
    // Arrange, Act and Assert
    assertEquals(2, ProfilingCase.valueOf("ONCPU").getNumber());
  }

  /**
   * Test ProfilingCase {@link ProfilingCase#valueOf(int)} with {@code value}.
   * <ul>
   *   <li>When forty-two.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProfilingCase#valueOf(int)}
   */
  @Test
  @DisplayName("Test ProfilingCase valueOf(int) with 'value'; when forty-two; then return 'null'")
  @Tag("MaintainedByDiffblue")
  void testProfilingCaseValueOfWithValue_whenFortyTwo_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(ProfilingCase.valueOf(42));
  }

  /**
   * Test ProfilingCase {@link ProfilingCase#valueOf(int)} with {@code value}.
   * <ul>
   *   <li>When three.</li>
   *   <li>Then return {@code OFFCPU}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProfilingCase#valueOf(int)}
   */
  @Test
  @DisplayName("Test ProfilingCase valueOf(int) with 'value'; when three; then return 'OFFCPU'")
  @Tag("MaintainedByDiffblue")
  void testProfilingCaseValueOfWithValue_whenThree_thenReturnOffcpu() {
    // Arrange, Act and Assert
    assertEquals(ProfilingCase.OFFCPU, ProfilingCase.valueOf(3));
  }

  /**
   * Test ProfilingCase {@link ProfilingCase#valueOf(int)} with {@code value}.
   * <ul>
   *   <li>When two.</li>
   *   <li>Then return {@code ONCPU}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProfilingCase#valueOf(int)}
   */
  @Test
  @DisplayName("Test ProfilingCase valueOf(int) with 'value'; when two; then return 'ONCPU'")
  @Tag("MaintainedByDiffblue")
  void testProfilingCaseValueOfWithValue_whenTwo_thenReturnOncpu() {
    // Arrange, Act and Assert
    assertEquals(ProfilingCase.ONCPU, ProfilingCase.valueOf(2));
  }

  /**
   * Test ProfilingCase {@link ProfilingCase#valueOf(int)} with {@code value}.
   * <ul>
   *   <li>When zero.</li>
   *   <li>Then return {@code PROFILING_NOT_SET}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProfilingCase#valueOf(int)}
   */
  @Test
  @DisplayName("Test ProfilingCase valueOf(int) with 'value'; when zero; then return 'PROFILING_NOT_SET'")
  @Tag("MaintainedByDiffblue")
  void testProfilingCaseValueOfWithValue_whenZero_thenReturnProfilingNotSet() {
    // Arrange, Act and Assert
    assertEquals(ProfilingCase.PROFILING_NOT_SET, ProfilingCase.valueOf(0));
  }
}
