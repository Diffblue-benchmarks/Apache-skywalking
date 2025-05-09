package org.apache.skywalking.oap.server.library.util.prometheus.metrics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.HashMap;
import java.util.List;
import org.apache.skywalking.oap.server.library.util.prometheus.metrics.MetricFamily.Builder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class MetricFamilyDiffblueTest {
  /**
   * Test Builder {@link Builder#addMetric(Metric)}.
   * <p>
   * Method under test: {@link Builder#addMetric(Metric)}
   */
  @Test
  @DisplayName("Test Builder addMetric(Metric)")
  @Tag("MaintainedByDiffblue")
  void testBuilderAddMetric() {
    // Arrange
    Builder builder = new Builder();
    builder.addMetric(new Counter("Name", new HashMap<>(), 10.0d, 10L));

    // Act and Assert
    assertSame(builder, builder.addMetric(new Counter("Name", new HashMap<>(), 10.0d, 10L)));
  }

  /**
   * Test Builder {@link Builder#addMetric(Metric)}.
   * <ul>
   *   <li>Given {@link Builder} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link Builder#addMetric(Metric)}
   */
  @Test
  @DisplayName("Test Builder addMetric(Metric); given Builder (default constructor)")
  @Tag("MaintainedByDiffblue")
  void testBuilderAddMetric_givenBuilder() {
    // Arrange
    Builder builder = new Builder();

    // Act and Assert
    assertSame(builder, builder.addMetric(new Counter("Name", new HashMap<>(), 10.0d, 10L)));
  }

  /**
   * Test Builder {@link Builder#build()}.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Builder#build()}
   *   <li>default or parameterless constructor of {@link Builder}
   *   <li>{@link Builder#setHelp(String)}
   *   <li>{@link Builder#setName(String)}
   *   <li>{@link Builder#setType(MetricType)}
   * </ul>
   */
  @Test
  @DisplayName("Test Builder build()")
  @Tag("MaintainedByDiffblue")
  void testBuilderBuild() {
    // Arrange and Act
    MetricFamily actualBuildResult = (new Builder()).setHelp("Help")
        .setName("Name")
        .setType(MetricType.COUNTER)
        .build();

    // Assert
    assertEquals("Help", actualBuildResult.getHelp());
    assertEquals("Name", actualBuildResult.getName());
    assertEquals(MetricType.COUNTER, actualBuildResult.getType());
    assertTrue(actualBuildResult.getMetrics().isEmpty());
  }

  /**
   * Test {@link MetricFamily#MetricFamily(Builder)}.
   * <p>
   * Method under test: {@link MetricFamily#MetricFamily(Builder)}
   */
  @Test
  @DisplayName("Test new MetricFamily(Builder)")
  @Tag("MaintainedByDiffblue")
  void testNewMetricFamily() {
    // Arrange
    Builder builder = new Builder();
    builder.addMetric(new Counter("Name", new HashMap<>(), 10.0d, 10L));
    builder.setType(MetricType.GAUGE);
    builder.setName("Need to set name");

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> new MetricFamily(builder));
  }

  /**
   * Test {@link MetricFamily#MetricFamily(Builder)}.
   * <ul>
   *   <li>Given {@code COUNTER}.</li>
   *   <li>Then return Type is {@code COUNTER}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MetricFamily#MetricFamily(Builder)}
   */
  @Test
  @DisplayName("Test new MetricFamily(Builder); given 'COUNTER'; then return Type is 'COUNTER'")
  @Tag("MaintainedByDiffblue")
  void testNewMetricFamily_givenCounter_thenReturnTypeIsCounter() {
    // Arrange
    Builder builder = new Builder();
    builder.setType(MetricType.COUNTER);
    builder.setName("Need to set name");

    // Act
    MetricFamily actualMetricFamily = new MetricFamily(builder);

    // Assert
    assertEquals("Need to set name", actualMetricFamily.getName());
    assertNull(actualMetricFamily.getHelp());
    assertEquals(MetricType.COUNTER, actualMetricFamily.getType());
    assertTrue(actualMetricFamily.getMetrics().isEmpty());
  }

  /**
   * Test {@link MetricFamily#MetricFamily(Builder)}.
   * <ul>
   *   <li>Given {@code GAUGE}.</li>
   *   <li>When {@link Builder} (default constructor) Type is {@code GAUGE}.</li>
   *   <li>Then return Type is {@code GAUGE}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MetricFamily#MetricFamily(Builder)}
   */
  @Test
  @DisplayName("Test new MetricFamily(Builder); given 'GAUGE'; when Builder (default constructor) Type is 'GAUGE'; then return Type is 'GAUGE'")
  @Tag("MaintainedByDiffblue")
  void testNewMetricFamily_givenGauge_whenBuilderTypeIsGauge_thenReturnTypeIsGauge() {
    // Arrange
    Builder builder = new Builder();
    builder.setType(MetricType.GAUGE);
    builder.setName("Need to set name");

    // Act
    MetricFamily actualMetricFamily = new MetricFamily(builder);

    // Assert
    assertEquals("Need to set name", actualMetricFamily.getName());
    assertNull(actualMetricFamily.getHelp());
    assertEquals(MetricType.GAUGE, actualMetricFamily.getType());
    assertTrue(actualMetricFamily.getMetrics().isEmpty());
  }

  /**
   * Test {@link MetricFamily#MetricFamily(Builder)}.
   * <ul>
   *   <li>Given {@code HISTOGRAM}.</li>
   *   <li>Then return Type is {@code HISTOGRAM}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MetricFamily#MetricFamily(Builder)}
   */
  @Test
  @DisplayName("Test new MetricFamily(Builder); given 'HISTOGRAM'; then return Type is 'HISTOGRAM'")
  @Tag("MaintainedByDiffblue")
  void testNewMetricFamily_givenHistogram_thenReturnTypeIsHistogram() {
    // Arrange
    Builder builder = new Builder();
    builder.setType(MetricType.HISTOGRAM);
    builder.setName("Need to set name");

    // Act
    MetricFamily actualMetricFamily = new MetricFamily(builder);

    // Assert
    assertEquals("Need to set name", actualMetricFamily.getName());
    assertNull(actualMetricFamily.getHelp());
    assertEquals(MetricType.HISTOGRAM, actualMetricFamily.getType());
    assertTrue(actualMetricFamily.getMetrics().isEmpty());
  }

  /**
   * Test {@link MetricFamily#MetricFamily(Builder)}.
   * <ul>
   *   <li>Given {@code Need to set name}.</li>
   *   <li>Then throw {@link IllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MetricFamily#MetricFamily(Builder)}
   */
  @Test
  @DisplayName("Test new MetricFamily(Builder); given 'Need to set name'; then throw IllegalArgumentException")
  @Tag("MaintainedByDiffblue")
  void testNewMetricFamily_givenNeedToSetName_thenThrowIllegalArgumentException() {
    // Arrange
    Builder builder = new Builder();
    builder.setName("Need to set name");

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> new MetricFamily(builder));
  }

  /**
   * Test {@link MetricFamily#MetricFamily(Builder)}.
   * <ul>
   *   <li>Given {@code SUMMARY}.</li>
   *   <li>Then return Type is {@code SUMMARY}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MetricFamily#MetricFamily(Builder)}
   */
  @Test
  @DisplayName("Test new MetricFamily(Builder); given 'SUMMARY'; then return Type is 'SUMMARY'")
  @Tag("MaintainedByDiffblue")
  void testNewMetricFamily_givenSummary_thenReturnTypeIsSummary() {
    // Arrange
    Builder builder = new Builder();
    builder.setType(MetricType.SUMMARY);
    builder.setName("Need to set name");

    // Act
    MetricFamily actualMetricFamily = new MetricFamily(builder);

    // Assert
    assertEquals("Need to set name", actualMetricFamily.getName());
    assertNull(actualMetricFamily.getHelp());
    assertEquals(MetricType.SUMMARY, actualMetricFamily.getType());
    assertTrue(actualMetricFamily.getMetrics().isEmpty());
  }

  /**
   * Test {@link MetricFamily#MetricFamily(Builder)}.
   * <ul>
   *   <li>Then return Metrics size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link MetricFamily#MetricFamily(Builder)}
   */
  @Test
  @DisplayName("Test new MetricFamily(Builder); then return Metrics size is one")
  @Tag("MaintainedByDiffblue")
  void testNewMetricFamily_thenReturnMetricsSizeIsOne() {
    // Arrange
    Builder builder = new Builder();
    Gauge metric = Gauge.builder().name("Name").timestamp(10L).value(10.0d).build();
    builder.addMetric(metric);
    builder.setType(MetricType.GAUGE);
    builder.setName("Need to set name");

    // Act and Assert
    List<Metric> metrics = (new MetricFamily(builder)).getMetrics();
    assertEquals(1, metrics.size());
    Metric getResult = metrics.get(0);
    assertTrue(getResult instanceof Gauge);
    assertEquals("Name", getResult.getName());
    assertEquals(10.0d, ((Gauge) getResult).getValue());
    assertEquals(10L, getResult.getTimestamp());
    assertTrue(getResult.getLabels().isEmpty());
  }

  /**
   * Test {@link MetricFamily#MetricFamily(Builder)}.
   * <ul>
   *   <li>When {@link Builder} (default constructor).</li>
   *   <li>Then throw {@link IllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MetricFamily#MetricFamily(Builder)}
   */
  @Test
  @DisplayName("Test new MetricFamily(Builder); when Builder (default constructor); then throw IllegalArgumentException")
  @Tag("MaintainedByDiffblue")
  void testNewMetricFamily_whenBuilder_thenThrowIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(IllegalArgumentException.class, () -> new MetricFamily(new Builder()));
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link MetricFamily#getHelp()}
   *   <li>{@link MetricFamily#getName()}
   *   <li>{@link MetricFamily#getType()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  void testGettersAndSetters() {
    // Arrange
    MetricFamily buildResult = (new Builder()).setHelp("Help").setName("Name").setType(MetricType.COUNTER).build();

    // Act
    String actualHelp = buildResult.getHelp();
    String actualName = buildResult.getName();

    // Assert
    assertEquals("Help", actualHelp);
    assertEquals("Name", actualName);
    assertEquals(MetricType.COUNTER, buildResult.getType());
  }

  /**
   * Test {@link MetricFamily#getMetrics()}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link MetricFamily#getMetrics()}
   */
  @Test
  @DisplayName("Test getMetrics(); then return Empty")
  @Tag("MaintainedByDiffblue")
  void testGetMetrics_thenReturnEmpty() {
    // Arrange
    MetricFamily buildResult = (new Builder()).setHelp("Help").setName("Name").setType(MetricType.COUNTER).build();

    // Act and Assert
    assertTrue(buildResult.getMetrics().isEmpty());
  }
}
