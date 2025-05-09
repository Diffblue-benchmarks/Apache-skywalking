package org.apache.skywalking.library.kubernetes;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class IstioServiceEntriesDiffblueTest {
  /**
   * Test {@link IstioServiceEntries#list()}.
   * <p>
   * Method under test: {@link IstioServiceEntries#list()}
   */
  @Test
  @DisplayName("Test list()")
  @Tag("MaintainedByDiffblue")
  void testList() {
    // Arrange, Act and Assert
    assertTrue(IstioServiceEntries.INSTANCE.list().isEmpty());
  }
}
