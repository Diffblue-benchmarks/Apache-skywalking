package org.apache.skywalking.apm.network.ebpf.profiling.v3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.apache.skywalking.apm.network.ebpf.profiling.v3.ContinuousProfilingReport.TargetTaskCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ContinuousProfilingReportDiffblueTest {
  /**
   * Test TargetTaskCase {@link TargetTaskCase#forNumber(int)}.
   * <ul>
   *   <li>When eight.</li>
   *   <li>Then return {@code OFFCPU}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TargetTaskCase#forNumber(int)}
   */
  @Test
  @DisplayName("Test TargetTaskCase forNumber(int); when eight; then return 'OFFCPU'")
  @Tag("MaintainedByDiffblue")
  void testTargetTaskCaseForNumber_whenEight_thenReturnOffcpu() {
    // Arrange, Act and Assert
    assertEquals(TargetTaskCase.OFFCPU, TargetTaskCase.forNumber(8));
  }

  /**
   * Test TargetTaskCase {@link TargetTaskCase#forNumber(int)}.
   * <ul>
   *   <li>When forty-two.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TargetTaskCase#forNumber(int)}
   */
  @Test
  @DisplayName("Test TargetTaskCase forNumber(int); when forty-two; then return 'null'")
  @Tag("MaintainedByDiffblue")
  void testTargetTaskCaseForNumber_whenFortyTwo_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(TargetTaskCase.forNumber(42));
  }

  /**
   * Test TargetTaskCase {@link TargetTaskCase#forNumber(int)}.
   * <ul>
   *   <li>When {@link ContinuousProfilingReport#NETWORK_FIELD_NUMBER}.</li>
   *   <li>Then return {@code NETWORK}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TargetTaskCase#forNumber(int)}
   */
  @Test
  @DisplayName("Test TargetTaskCase forNumber(int); when NETWORK_FIELD_NUMBER; then return 'NETWORK'")
  @Tag("MaintainedByDiffblue")
  void testTargetTaskCaseForNumber_whenNetwork_field_number_thenReturnNetwork() {
    // Arrange, Act and Assert
    assertEquals(TargetTaskCase.NETWORK, TargetTaskCase.forNumber(ContinuousProfilingReport.NETWORK_FIELD_NUMBER));
  }

  /**
   * Test TargetTaskCase {@link TargetTaskCase#forNumber(int)}.
   * <ul>
   *   <li>When seven.</li>
   *   <li>Then return {@code ONCPU}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TargetTaskCase#forNumber(int)}
   */
  @Test
  @DisplayName("Test TargetTaskCase forNumber(int); when seven; then return 'ONCPU'")
  @Tag("MaintainedByDiffblue")
  void testTargetTaskCaseForNumber_whenSeven_thenReturnOncpu() {
    // Arrange, Act and Assert
    assertEquals(TargetTaskCase.ONCPU, TargetTaskCase.forNumber(7));
  }

  /**
   * Test TargetTaskCase {@link TargetTaskCase#forNumber(int)}.
   * <ul>
   *   <li>When zero.</li>
   *   <li>Then return {@code TARGETTASK_NOT_SET}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TargetTaskCase#forNumber(int)}
   */
  @Test
  @DisplayName("Test TargetTaskCase forNumber(int); when zero; then return 'TARGETTASK_NOT_SET'")
  @Tag("MaintainedByDiffblue")
  void testTargetTaskCaseForNumber_whenZero_thenReturnTargettaskNotSet() {
    // Arrange, Act and Assert
    assertEquals(TargetTaskCase.TARGETTASK_NOT_SET, TargetTaskCase.forNumber(0));
  }

  /**
   * Test TargetTaskCase {@link TargetTaskCase#getNumber()}.
   * <p>
   * Method under test: {@link TargetTaskCase#getNumber()}
   */
  @Test
  @DisplayName("Test TargetTaskCase getNumber()")
  @Tag("MaintainedByDiffblue")
  void testTargetTaskCaseGetNumber() {
    // Arrange, Act and Assert
    assertEquals(7, TargetTaskCase.valueOf("ONCPU").getNumber());
  }

  /**
   * Test TargetTaskCase {@link TargetTaskCase#valueOf(int)} with {@code value}.
   * <ul>
   *   <li>When eight.</li>
   *   <li>Then return {@code OFFCPU}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TargetTaskCase#valueOf(int)}
   */
  @Test
  @DisplayName("Test TargetTaskCase valueOf(int) with 'value'; when eight; then return 'OFFCPU'")
  @Tag("MaintainedByDiffblue")
  void testTargetTaskCaseValueOfWithValue_whenEight_thenReturnOffcpu() {
    // Arrange, Act and Assert
    assertEquals(TargetTaskCase.OFFCPU, TargetTaskCase.valueOf(8));
  }

  /**
   * Test TargetTaskCase {@link TargetTaskCase#valueOf(int)} with {@code value}.
   * <ul>
   *   <li>When forty-two.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TargetTaskCase#valueOf(int)}
   */
  @Test
  @DisplayName("Test TargetTaskCase valueOf(int) with 'value'; when forty-two; then return 'null'")
  @Tag("MaintainedByDiffblue")
  void testTargetTaskCaseValueOfWithValue_whenFortyTwo_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(TargetTaskCase.valueOf(42));
  }

  /**
   * Test TargetTaskCase {@link TargetTaskCase#valueOf(int)} with {@code value}.
   * <ul>
   *   <li>When {@link ContinuousProfilingReport#NETWORK_FIELD_NUMBER}.</li>
   *   <li>Then return {@code NETWORK}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TargetTaskCase#valueOf(int)}
   */
  @Test
  @DisplayName("Test TargetTaskCase valueOf(int) with 'value'; when NETWORK_FIELD_NUMBER; then return 'NETWORK'")
  @Tag("MaintainedByDiffblue")
  void testTargetTaskCaseValueOfWithValue_whenNetwork_field_number_thenReturnNetwork() {
    // Arrange, Act and Assert
    assertEquals(TargetTaskCase.NETWORK, TargetTaskCase.valueOf(ContinuousProfilingReport.NETWORK_FIELD_NUMBER));
  }

  /**
   * Test TargetTaskCase {@link TargetTaskCase#valueOf(int)} with {@code value}.
   * <ul>
   *   <li>When seven.</li>
   *   <li>Then return {@code ONCPU}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TargetTaskCase#valueOf(int)}
   */
  @Test
  @DisplayName("Test TargetTaskCase valueOf(int) with 'value'; when seven; then return 'ONCPU'")
  @Tag("MaintainedByDiffblue")
  void testTargetTaskCaseValueOfWithValue_whenSeven_thenReturnOncpu() {
    // Arrange, Act and Assert
    assertEquals(TargetTaskCase.ONCPU, TargetTaskCase.valueOf(7));
  }

  /**
   * Test TargetTaskCase {@link TargetTaskCase#valueOf(int)} with {@code value}.
   * <ul>
   *   <li>When zero.</li>
   *   <li>Then return {@code TARGETTASK_NOT_SET}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TargetTaskCase#valueOf(int)}
   */
  @Test
  @DisplayName("Test TargetTaskCase valueOf(int) with 'value'; when zero; then return 'TARGETTASK_NOT_SET'")
  @Tag("MaintainedByDiffblue")
  void testTargetTaskCaseValueOfWithValue_whenZero_thenReturnTargettaskNotSet() {
    // Arrange, Act and Assert
    assertEquals(TargetTaskCase.TARGETTASK_NOT_SET, TargetTaskCase.valueOf(0));
  }
}
