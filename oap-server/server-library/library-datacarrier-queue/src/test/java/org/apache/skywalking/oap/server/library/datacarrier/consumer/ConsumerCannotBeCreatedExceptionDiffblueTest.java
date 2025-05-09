package org.apache.skywalking.oap.server.library.datacarrier.consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ConsumerCannotBeCreatedExceptionDiffblueTest {
  /**
   * Test {@link ConsumerCannotBeCreatedException#ConsumerCannotBeCreatedException(Throwable)}.
   * <p>
   * Method under test: {@link ConsumerCannotBeCreatedException#ConsumerCannotBeCreatedException(Throwable)}
   */
  @Test
  @DisplayName("Test new ConsumerCannotBeCreatedException(Throwable)")
  @Tag("MaintainedByDiffblue")
  void testNewConsumerCannotBeCreatedException() {
    // Arrange
    Throwable t = new Throwable();

    // Act
    ConsumerCannotBeCreatedException actualConsumerCannotBeCreatedException = new ConsumerCannotBeCreatedException(t);

    // Assert
    assertEquals("java.lang.Throwable", actualConsumerCannotBeCreatedException.getMessage());
    assertEquals(0, actualConsumerCannotBeCreatedException.getSuppressed().length);
    assertSame(t, actualConsumerCannotBeCreatedException.getCause());
  }
}
