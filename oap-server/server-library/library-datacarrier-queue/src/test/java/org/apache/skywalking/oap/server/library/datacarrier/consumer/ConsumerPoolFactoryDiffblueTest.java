package org.apache.skywalking.oap.server.library.datacarrier.consumer;

import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ConsumerPoolFactoryDiffblueTest {
  /**
   * Test {@link ConsumerPoolFactory#get(String)}.
   * <p>
   * Method under test: {@link ConsumerPoolFactory#get(String)}
   */
  @Test
  @DisplayName("Test get(String)")
  @Tag("MaintainedByDiffblue")
  void testGet() {
    // Arrange, Act and Assert
    assertNull(ConsumerPoolFactory.INSTANCE.get("Pool Name"));
  }
}
