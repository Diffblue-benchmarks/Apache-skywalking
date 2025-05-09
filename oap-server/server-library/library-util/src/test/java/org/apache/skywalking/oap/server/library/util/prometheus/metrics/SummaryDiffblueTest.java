package org.apache.skywalking.oap.server.library.util.prometheus.metrics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class SummaryDiffblueTest {
  /**
   * Test {@link Summary#Summary(String, Map, long, double, Map, long)}.
   * <p>
   * Method under test: {@link Summary#Summary(String, Map, long, double, Map, long)}
   */
  @Test
  @DisplayName("Test new Summary(String, Map, long, double, Map, long)")
  @Tag("MaintainedByDiffblue")
  void testNewSummary() {
    // Arrange
    HashMap<String, String> labels = new HashMap<>();

    // Act
    Summary actualSummary = new Summary("Name", labels, 3L, 10.0d, new HashMap<>(), 10L);

    // Assert
    assertEquals("Name", actualSummary.getName());
    assertEquals(10.0d, actualSummary.getSampleSum());
    assertEquals(10L, actualSummary.getTimestamp());
    assertEquals(3L, actualSummary.getSampleCount());
    assertTrue(actualSummary.getLabels().isEmpty());
    assertTrue(actualSummary.getQuantiles().isEmpty());
  }

  /**
   * Test {@link Summary#sum(Metric)}.
   * <p>
   * Method under test: {@link Summary#sum(Metric)}
   */
  @Test
  @DisplayName("Test sum(Metric)")
  @Tag("MaintainedByDiffblue")
  void testSum() {
    // Arrange
    HashMap<String, String> labels = new HashMap<>();
    Summary summary = new Summary("Name", labels, 3L, 10.0d, new HashMap<>(), 10L);
    HashMap<String, String> labels2 = new HashMap<>();

    // Act and Assert
    assertSame(summary, summary.sum(new Summary("Name", labels2, 3L, 10.0d, new HashMap<>(), 10L)));
  }

  /**
   * Test {@link Summary#value()}.
   * <p>
   * Method under test: {@link Summary#value()}
   */
  @Test
  @DisplayName("Test value()")
  @Tag("MaintainedByDiffblue")
  void testValue() {
    // Arrange
    HashMap<String, String> labels = new HashMap<>();

    // Act and Assert
    assertEquals(3333.3333333333335d,
        (new Summary("Name", labels, 3L, 10.0d, new HashMap<>(), 10L)).value().doubleValue());
  }
}
