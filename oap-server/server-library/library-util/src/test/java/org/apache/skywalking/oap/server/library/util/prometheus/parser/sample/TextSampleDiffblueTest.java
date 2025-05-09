package org.apache.skywalking.oap.server.library.util.prometheus.parser.sample;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class TextSampleDiffblueTest {
  /**
   * Test {@link TextSample#parse(String)}.
   * <p>
   * Method under test: {@link TextSample#parse(String)}
   */
  @Test
  @DisplayName("Test parse(String)")
  @Tag("MaintainedByDiffblue")
  void testParse() {
    // Arrange and Act
    TextSample actualParseResult = TextSample.parse("Line");

    // Assert
    assertEquals("", actualParseResult.getValue());
    assertEquals("Line", actualParseResult.getLine());
    assertEquals("Line", actualParseResult.getName());
    assertTrue(actualParseResult.getLabels().isEmpty());
  }
}
