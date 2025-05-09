package org.apache.skywalking.apm.network.servicemesh.v3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.apache.skywalking.apm.network.servicemesh.v3.ServiceMeshMetrics.MetricsCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ServiceMeshMetricsDiffblueTest {
  /**
   * Test MetricsCase {@link MetricsCase#forNumber(int)}.
   * <ul>
   *   <li>When forty-two.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MetricsCase#forNumber(int)}
   */
  @Test
  @DisplayName("Test MetricsCase forNumber(int); when forty-two; then return 'null'")
  @Tag("MaintainedByDiffblue")
  void testMetricsCaseForNumber_whenFortyTwo_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(MetricsCase.forNumber(42));
  }

  /**
   * Test MetricsCase {@link MetricsCase#forNumber(int)}.
   * <ul>
   *   <li>When one.</li>
   *   <li>Then return {@code HTTPMETRICS}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MetricsCase#forNumber(int)}
   */
  @Test
  @DisplayName("Test MetricsCase forNumber(int); when one; then return 'HTTPMETRICS'")
  @Tag("MaintainedByDiffblue")
  void testMetricsCaseForNumber_whenOne_thenReturnHttpmetrics() {
    // Arrange, Act and Assert
    assertEquals(MetricsCase.HTTPMETRICS, MetricsCase.forNumber(1));
  }

  /**
   * Test MetricsCase {@link MetricsCase#forNumber(int)}.
   * <ul>
   *   <li>When two.</li>
   *   <li>Then return {@code TCPMETRICS}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MetricsCase#forNumber(int)}
   */
  @Test
  @DisplayName("Test MetricsCase forNumber(int); when two; then return 'TCPMETRICS'")
  @Tag("MaintainedByDiffblue")
  void testMetricsCaseForNumber_whenTwo_thenReturnTcpmetrics() {
    // Arrange, Act and Assert
    assertEquals(MetricsCase.TCPMETRICS, MetricsCase.forNumber(2));
  }

  /**
   * Test MetricsCase {@link MetricsCase#forNumber(int)}.
   * <ul>
   *   <li>When zero.</li>
   *   <li>Then return {@code METRICS_NOT_SET}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MetricsCase#forNumber(int)}
   */
  @Test
  @DisplayName("Test MetricsCase forNumber(int); when zero; then return 'METRICS_NOT_SET'")
  @Tag("MaintainedByDiffblue")
  void testMetricsCaseForNumber_whenZero_thenReturnMetricsNotSet() {
    // Arrange, Act and Assert
    assertEquals(MetricsCase.METRICS_NOT_SET, MetricsCase.forNumber(0));
  }

  /**
   * Test MetricsCase {@link MetricsCase#getNumber()}.
   * <p>
   * Method under test: {@link MetricsCase#getNumber()}
   */
  @Test
  @DisplayName("Test MetricsCase getNumber()")
  @Tag("MaintainedByDiffblue")
  void testMetricsCaseGetNumber() {
    // Arrange, Act and Assert
    assertEquals(1, MetricsCase.valueOf("HTTPMETRICS").getNumber());
  }

  /**
   * Test MetricsCase {@link MetricsCase#valueOf(int)} with {@code value}.
   * <ul>
   *   <li>When forty-two.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MetricsCase#valueOf(int)}
   */
  @Test
  @DisplayName("Test MetricsCase valueOf(int) with 'value'; when forty-two; then return 'null'")
  @Tag("MaintainedByDiffblue")
  void testMetricsCaseValueOfWithValue_whenFortyTwo_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(MetricsCase.valueOf(42));
  }

  /**
   * Test MetricsCase {@link MetricsCase#valueOf(int)} with {@code value}.
   * <ul>
   *   <li>When one.</li>
   *   <li>Then return {@code HTTPMETRICS}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MetricsCase#valueOf(int)}
   */
  @Test
  @DisplayName("Test MetricsCase valueOf(int) with 'value'; when one; then return 'HTTPMETRICS'")
  @Tag("MaintainedByDiffblue")
  void testMetricsCaseValueOfWithValue_whenOne_thenReturnHttpmetrics() {
    // Arrange, Act and Assert
    assertEquals(MetricsCase.HTTPMETRICS, MetricsCase.valueOf(1));
  }

  /**
   * Test MetricsCase {@link MetricsCase#valueOf(int)} with {@code value}.
   * <ul>
   *   <li>When two.</li>
   *   <li>Then return {@code TCPMETRICS}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MetricsCase#valueOf(int)}
   */
  @Test
  @DisplayName("Test MetricsCase valueOf(int) with 'value'; when two; then return 'TCPMETRICS'")
  @Tag("MaintainedByDiffblue")
  void testMetricsCaseValueOfWithValue_whenTwo_thenReturnTcpmetrics() {
    // Arrange, Act and Assert
    assertEquals(MetricsCase.TCPMETRICS, MetricsCase.valueOf(2));
  }

  /**
   * Test MetricsCase {@link MetricsCase#valueOf(int)} with {@code value}.
   * <ul>
   *   <li>When zero.</li>
   *   <li>Then return {@code METRICS_NOT_SET}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MetricsCase#valueOf(int)}
   */
  @Test
  @DisplayName("Test MetricsCase valueOf(int) with 'value'; when zero; then return 'METRICS_NOT_SET'")
  @Tag("MaintainedByDiffblue")
  void testMetricsCaseValueOfWithValue_whenZero_thenReturnMetricsNotSet() {
    // Arrange, Act and Assert
    assertEquals(MetricsCase.METRICS_NOT_SET, MetricsCase.valueOf(0));
  }
}
