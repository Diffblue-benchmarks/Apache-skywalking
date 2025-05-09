package org.apache.skywalking.oap.server.library.util;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class GRPCStreamStatusDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link GRPCStreamStatus}
   *   <li>{@link GRPCStreamStatus#done()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  void testGettersAndSetters() {
    // Arrange and Act
    GRPCStreamStatus actualGrpcStreamStatus = new GRPCStreamStatus();
    actualGrpcStreamStatus.done();

    // Assert
    assertTrue(actualGrpcStreamStatus.isDone());
  }
}
