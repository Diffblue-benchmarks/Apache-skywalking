package org.apache.skywalking.oap.server.library.util.prometheus.metrics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class HistogramDiffblueTest {
  /**
   * Test {@link Histogram#Histogram(String, Map, long, double, Map, long)}.
   * <p>
   * Method under test: {@link Histogram#Histogram(String, Map, long, double, Map, long)}
   */
  @Test
  @DisplayName("Test new Histogram(String, Map, long, double, Map, long)")
  @Tag("MaintainedByDiffblue")
  void testNewHistogram() {
    // Arrange
    HashMap<String, String> labels = new HashMap<>();

    // Act
    Histogram actualHistogram = new Histogram("Name", labels, 3L, 10.0d, new HashMap<>(), 10L);

    // Assert
    assertEquals("Name", actualHistogram.getName());
    assertEquals(10.0d, actualHistogram.getSampleSum());
    assertEquals(10L, actualHistogram.getTimestamp());
    assertEquals(3L, actualHistogram.getSampleCount());
    assertTrue(actualHistogram.getBuckets().isEmpty());
    assertTrue(actualHistogram.getLabels().isEmpty());
  }

  /**
   * Test {@link Histogram#sum(Metric)}.
   * <p>
   * Method under test: {@link Histogram#sum(Metric)}
   */
  @Test
  @DisplayName("Test sum(Metric)")
  @Tag("MaintainedByDiffblue")
  void testSum() {
    // Arrange
    HashMap<String, String> labels = new HashMap<>();
    Histogram histogram = new Histogram("Name", labels, 3L, 10.0d, new HashMap<>(), 10L);
    HashMap<String, String> labels2 = new HashMap<>();

    // Act and Assert
    assertSame(histogram, histogram.sum(new Histogram("Name", labels2, 3L, 10.0d, new HashMap<>(), 10L)));
  }

  /**
   * Test {@link Histogram#value()}.
   * <p>
   * Method under test: {@link Histogram#value()}
   */
  @Test
  @DisplayName("Test value()")
  @Tag("MaintainedByDiffblue")
  void testValue() {
    // Arrange
    HashMap<String, String> labels = new HashMap<>();

    // Act and Assert
    assertEquals(3333.3333333333335d,
        (new Histogram("Name", labels, 3L, 10.0d, new HashMap<>(), 10L)).value().doubleValue());
  }
}
