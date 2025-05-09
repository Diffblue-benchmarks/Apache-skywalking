package org.apache.skywalking.oap.server.library.it;

import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ITVersionsDiffblueTest {
  /**
   * Test {@link ITVersions#get(String)}.
   * <p>
   * Method under test: {@link ITVersions#get(String)}
   */
  @Test
  @DisplayName("Test get(String)")
  @Tag("MaintainedByDiffblue")
  void testGet() {
    // Arrange, Act and Assert
    assertNull(ITVersions.get("Key"));
  }
}
