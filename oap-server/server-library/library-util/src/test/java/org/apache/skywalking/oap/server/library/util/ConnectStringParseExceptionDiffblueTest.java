package org.apache.skywalking.oap.server.library.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ConnectStringParseExceptionDiffblueTest {
  /**
   * Test {@link ConnectStringParseException#ConnectStringParseException(String)}.
   * <p>
   * Method under test: {@link ConnectStringParseException#ConnectStringParseException(String)}
   */
  @Test
  @DisplayName("Test new ConnectStringParseException(String)")
  @Tag("MaintainedByDiffblue")
  void testNewConnectStringParseException() {
    // Arrange and Act
    ConnectStringParseException actualConnectStringParseException = new ConnectStringParseException(
        "An error occurred");

    // Assert
    assertEquals("An error occurred", actualConnectStringParseException.getMessage());
    assertNull(actualConnectStringParseException.getCause());
    assertEquals(0, actualConnectStringParseException.getSuppressed().length);
  }
}
