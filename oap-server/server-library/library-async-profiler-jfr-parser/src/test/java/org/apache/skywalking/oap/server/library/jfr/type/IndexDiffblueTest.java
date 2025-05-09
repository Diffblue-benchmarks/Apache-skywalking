package org.apache.skywalking.oap.server.library.jfr.type;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class IndexDiffblueTest {
  /**
   * Test {@link Index#Index(Class, Object)}.
   * <p>
   * Method under test: {@link Index#Index(Class, Object)}
   */
  @Test
  @DisplayName("Test new Index(Class, Object)")
  @Tag("MaintainedByDiffblue")
  void testNewIndex() {
    // Arrange
    Class<Object> cls = Object.class;

    // Act
    Index<Object> actualObjectMap = new Index<>(cls, "Empty");

    // Assert
    assertEquals(1, actualObjectMap.size());
    assertEquals(0, actualObjectMap.get("Empty").intValue());
  }
}
