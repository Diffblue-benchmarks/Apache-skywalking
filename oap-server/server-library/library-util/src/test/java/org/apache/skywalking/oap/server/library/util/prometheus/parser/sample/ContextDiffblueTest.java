package org.apache.skywalking.oap.server.library.util.prometheus.parser.sample;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ContextDiffblueTest {
  /**
   * Test new {@link Context} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of {@link Context}
   */
  @Test
  @DisplayName("Test new Context (default constructor)")
  @Tag("MaintainedByDiffblue")
  void testNewContext() {
    // Arrange and Act
    Context actualContext = new Context();

    // Assert
    assertEquals("", actualContext.labelname.toString());
    assertEquals("", actualContext.labelvalue.toString());
    assertEquals("", actualContext.name.toString());
    assertEquals("", actualContext.value.toString());
    assertTrue(actualContext.labels.isEmpty());
  }
}
