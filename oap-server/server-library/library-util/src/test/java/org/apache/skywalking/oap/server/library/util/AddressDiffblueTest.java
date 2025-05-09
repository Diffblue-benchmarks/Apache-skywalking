package org.apache.skywalking.oap.server.library.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class AddressDiffblueTest {
  /**
   * Test new {@link Address} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of {@link Address}
   */
  @Test
  @DisplayName("Test new Address (default constructor)")
  @Tag("MaintainedByDiffblue")
  void testNewAddress() {
    // Arrange and Act
    Address actualAddress = new Address();

    // Assert
    assertNull(actualAddress.getHost());
    assertEquals(0, actualAddress.getPort());
  }
}
