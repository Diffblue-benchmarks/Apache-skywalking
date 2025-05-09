package org.apache.skywalking.oap.server.library.util.prometheus.metrics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class CounterDiffblueTest {
  /**
   * Test {@link Counter#Counter(String, Map, double, long)}.
   * <p>
   * Method under test: {@link Counter#Counter(String, Map, double, long)}
   */
  @Test
  @DisplayName("Test new Counter(String, Map, double, long)")
  @Tag("MaintainedByDiffblue")
  void testNewCounter() {
    // Arrange and Act
    Counter actualCounter = new Counter("Name", new HashMap<>(), 10.0d, 10L);

    // Assert
    assertEquals("Name", actualCounter.getName());
    assertEquals(10.0d, actualCounter.getValue());
    assertEquals(10L, actualCounter.getTimestamp());
    assertTrue(actualCounter.getLabels().isEmpty());
  }

  /**
   * Test {@link Counter#sum(Metric)}.
   * <p>
   * Method under test: {@link Counter#sum(Metric)}
   */
  @Test
  @DisplayName("Test sum(Metric)")
  @Tag("MaintainedByDiffblue")
  void testSum() {
    // Arrange
    Counter counter = new Counter("Name", new HashMap<>(), 10.0d, 10L);
    Counter m = new Counter("Name", new HashMap<>(), 10.0d, 10L);

    // Act
    Metric actualSumResult = counter.sum(m);

    // Assert
    assertEquals(10.0d, m.getValue());
    assertSame(counter, actualSumResult);
  }

  /**
   * Test {@link Counter#sum(Metric)}.
   * <p>
   * Method under test: {@link Counter#sum(Metric)}
   */
  @Test
  @DisplayName("Test sum(Metric)")
  @Tag("MaintainedByDiffblue")
  void testSum2() {
    // Arrange
    Counter counter = new Counter("Name", new HashMap<>(), 10.0d, 10L);
    Gauge m = Gauge.builder().name("Name").timestamp(10L).value(10.0d).build();

    // Act
    Metric actualSumResult = counter.sum(m);

    // Assert
    assertEquals(20.0d, counter.getValue());
    assertSame(counter, actualSumResult);
  }

  /**
   * Test {@link Counter#value()}.
   * <p>
   * Method under test: {@link Counter#value()}
   */
  @Test
  @DisplayName("Test value()")
  @Tag("MaintainedByDiffblue")
  void testValue() {
    // Arrange, Act and Assert
    assertEquals(10.0d, (new Counter("Name", new HashMap<>(), 10.0d, 10L)).value().doubleValue());
  }
}
