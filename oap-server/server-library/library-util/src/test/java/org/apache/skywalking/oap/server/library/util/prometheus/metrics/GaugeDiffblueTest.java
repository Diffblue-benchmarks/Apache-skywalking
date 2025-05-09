package org.apache.skywalking.oap.server.library.util.prometheus.metrics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class GaugeDiffblueTest {
  /**
   * Test {@link Gauge#Gauge(String, Map, double, long)}.
   * <p>
   * Method under test: {@link Gauge#Gauge(String, Map, double, long)}
   */
  @Test
  @DisplayName("Test new Gauge(String, Map, double, long)")
  @Tag("MaintainedByDiffblue")
  void testNewGauge() {
    // Arrange and Act
    Gauge actualGauge = new Gauge("Name", new HashMap<>(), 10.0d, 10L);

    // Assert
    assertEquals("Name", actualGauge.getName());
    assertEquals(10.0d, actualGauge.getValue());
    assertEquals(10L, actualGauge.getTimestamp());
    assertTrue(actualGauge.getLabels().isEmpty());
  }

  /**
   * Test {@link Gauge#sum(Metric)}.
   * <ul>
   *   <li>When builder name {@code Name} timestamp ten value ten build.</li>
   * </ul>
   * <p>
   * Method under test: {@link Gauge#sum(Metric)}
   */
  @Test
  @DisplayName("Test sum(Metric); when builder name 'Name' timestamp ten value ten build")
  @Tag("MaintainedByDiffblue")
  void testSum_whenBuilderNameNameTimestampTenValueTenBuild() {
    // Arrange
    Gauge gauge = new Gauge("Name", new HashMap<>(), 10.0d, 10L);
    Gauge m = Gauge.builder().name("Name").timestamp(10L).value(10.0d).build();

    // Act
    Metric actualSumResult = gauge.sum(m);

    // Assert
    assertEquals(20.0d, gauge.getValue());
    assertSame(gauge, actualSumResult);
  }

  /**
   * Test {@link Gauge#sum(Metric)}.
   * <ul>
   *   <li>When {@link Counter#Counter(String, Map, double, long)} with {@code Name} and labels is {@link HashMap#HashMap()} and value is ten and timestamp is ten.</li>
   * </ul>
   * <p>
   * Method under test: {@link Gauge#sum(Metric)}
   */
  @Test
  @DisplayName("Test sum(Metric); when Counter(String, Map, double, long) with 'Name' and labels is HashMap() and value is ten and timestamp is ten")
  @Tag("MaintainedByDiffblue")
  void testSum_whenCounterWithNameAndLabelsIsHashMapAndValueIsTenAndTimestampIsTen() {
    // Arrange
    Gauge gauge = new Gauge("Name", new HashMap<>(), 10.0d, 10L);

    // Act
    Metric actualSumResult = gauge.sum(new Counter("Name", new HashMap<>(), 10.0d, 10L));

    // Assert
    assertEquals(20.0d, gauge.getValue());
    assertSame(gauge, actualSumResult);
  }

  /**
   * Test {@link Gauge#value()}.
   * <p>
   * Method under test: {@link Gauge#value()}
   */
  @Test
  @DisplayName("Test value()")
  @Tag("MaintainedByDiffblue")
  void testValue() {
    // Arrange, Act and Assert
    assertEquals(10.0d, (new Gauge("Name", new HashMap<>(), 10.0d, 10L)).value().doubleValue());
  }
}
