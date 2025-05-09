package org.apache.skywalking.oap.server.library.jfr.type;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class FrameDiffblueTest {
  /**
   * Test {@link Frame#Frame(int, byte)}.
   * <p>
   * Method under test: {@link Frame#Frame(int, byte)}
   */
  @Test
  @DisplayName("Test new Frame(int, byte)")
  @Tag("MaintainedByDiffblue")
  void testNewFrame() {
    // Arrange, Act and Assert
    assertTrue((new Frame(1, (byte) 'A')).isEmpty());
  }
}
