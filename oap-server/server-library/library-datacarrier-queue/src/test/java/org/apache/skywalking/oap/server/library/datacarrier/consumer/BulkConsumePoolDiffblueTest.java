package org.apache.skywalking.oap.server.library.datacarrier.consumer;

import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class BulkConsumePoolDiffblueTest {
  /**
   * Test {@link BulkConsumePool#BulkConsumePool(String, int, long)}.
   * <ul>
   *   <li>When three.</li>
   *   <li>Then return not Running is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BulkConsumePool#BulkConsumePool(String, int, long)}
   */
  @Test
  @DisplayName("Test new BulkConsumePool(String, int, long); when three; then return not Running is 'null'")
  @Tag("MaintainedByDiffblue")
  void testNewBulkConsumePool_whenThree_thenReturnNotRunningIsNull() {
    // Arrange, Act and Assert
    assertFalse((new BulkConsumePool("Name", 3, 1L)).isRunning(null));
  }
}
