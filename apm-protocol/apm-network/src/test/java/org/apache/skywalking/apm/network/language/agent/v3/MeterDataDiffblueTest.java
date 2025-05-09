package org.apache.skywalking.apm.network.language.agent.v3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.apache.skywalking.apm.network.language.agent.v3.MeterData.MetricCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class MeterDataDiffblueTest {
  /**
   * Test MetricCase {@link MetricCase#forNumber(int)}.
   * <ul>
   *   <li>When forty-two.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MetricCase#forNumber(int)}
   */
  @Test
  @DisplayName("Test MetricCase forNumber(int); when forty-two; then return 'null'")
  @Tag("MaintainedByDiffblue")
  void testMetricCaseForNumber_whenFortyTwo_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(MetricCase.forNumber(42));
  }

  /**
   * Test MetricCase {@link MetricCase#forNumber(int)}.
   * <ul>
   *   <li>When one.</li>
   *   <li>Then return {@code SINGLEVALUE}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MetricCase#forNumber(int)}
   */
  @Test
  @DisplayName("Test MetricCase forNumber(int); when one; then return 'SINGLEVALUE'")
  @Tag("MaintainedByDiffblue")
  void testMetricCaseForNumber_whenOne_thenReturnSinglevalue() {
    // Arrange, Act and Assert
    assertEquals(MetricCase.SINGLEVALUE, MetricCase.forNumber(1));
  }

  /**
   * Test MetricCase {@link MetricCase#forNumber(int)}.
   * <ul>
   *   <li>When two.</li>
   *   <li>Then return {@code HISTOGRAM}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MetricCase#forNumber(int)}
   */
  @Test
  @DisplayName("Test MetricCase forNumber(int); when two; then return 'HISTOGRAM'")
  @Tag("MaintainedByDiffblue")
  void testMetricCaseForNumber_whenTwo_thenReturnHistogram() {
    // Arrange, Act and Assert
    assertEquals(MetricCase.HISTOGRAM, MetricCase.forNumber(2));
  }

  /**
   * Test MetricCase {@link MetricCase#forNumber(int)}.
   * <ul>
   *   <li>When zero.</li>
   *   <li>Then return {@code METRIC_NOT_SET}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MetricCase#forNumber(int)}
   */
  @Test
  @DisplayName("Test MetricCase forNumber(int); when zero; then return 'METRIC_NOT_SET'")
  @Tag("MaintainedByDiffblue")
  void testMetricCaseForNumber_whenZero_thenReturnMetricNotSet() {
    // Arrange, Act and Assert
    assertEquals(MetricCase.METRIC_NOT_SET, MetricCase.forNumber(0));
  }

  /**
   * Test MetricCase {@link MetricCase#getNumber()}.
   * <p>
   * Method under test: {@link MetricCase#getNumber()}
   */
  @Test
  @DisplayName("Test MetricCase getNumber()")
  @Tag("MaintainedByDiffblue")
  void testMetricCaseGetNumber() {
    // Arrange, Act and Assert
    assertEquals(1, MetricCase.valueOf("SINGLEVALUE").getNumber());
  }

  /**
   * Test MetricCase {@link MetricCase#valueOf(int)} with {@code value}.
   * <ul>
   *   <li>When forty-two.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MetricCase#valueOf(int)}
   */
  @Test
  @DisplayName("Test MetricCase valueOf(int) with 'value'; when forty-two; then return 'null'")
  @Tag("MaintainedByDiffblue")
  void testMetricCaseValueOfWithValue_whenFortyTwo_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(MetricCase.valueOf(42));
  }

  /**
   * Test MetricCase {@link MetricCase#valueOf(int)} with {@code value}.
   * <ul>
   *   <li>When one.</li>
   *   <li>Then return {@code SINGLEVALUE}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MetricCase#valueOf(int)}
   */
  @Test
  @DisplayName("Test MetricCase valueOf(int) with 'value'; when one; then return 'SINGLEVALUE'")
  @Tag("MaintainedByDiffblue")
  void testMetricCaseValueOfWithValue_whenOne_thenReturnSinglevalue() {
    // Arrange, Act and Assert
    assertEquals(MetricCase.SINGLEVALUE, MetricCase.valueOf(1));
  }

  /**
   * Test MetricCase {@link MetricCase#valueOf(int)} with {@code value}.
   * <ul>
   *   <li>When two.</li>
   *   <li>Then return {@code HISTOGRAM}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MetricCase#valueOf(int)}
   */
  @Test
  @DisplayName("Test MetricCase valueOf(int) with 'value'; when two; then return 'HISTOGRAM'")
  @Tag("MaintainedByDiffblue")
  void testMetricCaseValueOfWithValue_whenTwo_thenReturnHistogram() {
    // Arrange, Act and Assert
    assertEquals(MetricCase.HISTOGRAM, MetricCase.valueOf(2));
  }

  /**
   * Test MetricCase {@link MetricCase#valueOf(int)} with {@code value}.
   * <ul>
   *   <li>When zero.</li>
   *   <li>Then return {@code METRIC_NOT_SET}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MetricCase#valueOf(int)}
   */
  @Test
  @DisplayName("Test MetricCase valueOf(int) with 'value'; when zero; then return 'METRIC_NOT_SET'")
  @Tag("MaintainedByDiffblue")
  void testMetricCaseValueOfWithValue_whenZero_thenReturnMetricNotSet() {
    // Arrange, Act and Assert
    assertEquals(MetricCase.METRIC_NOT_SET, MetricCase.valueOf(0));
  }
}
