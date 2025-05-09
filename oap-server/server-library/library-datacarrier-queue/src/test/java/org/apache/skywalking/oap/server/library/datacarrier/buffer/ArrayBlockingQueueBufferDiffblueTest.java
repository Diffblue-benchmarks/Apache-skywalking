package org.apache.skywalking.oap.server.library.datacarrier.buffer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ArrayBlockingQueueBufferDiffblueTest {
  /**
   * Test {@link ArrayBlockingQueueBuffer#ArrayBlockingQueueBuffer(int, BufferStrategy)}.
   * <ul>
   *   <li>When three.</li>
   *   <li>Then return BufferSize is three.</li>
   * </ul>
   * <p>
   * Method under test: {@link ArrayBlockingQueueBuffer#ArrayBlockingQueueBuffer(int, BufferStrategy)}
   */
  @Test
  @DisplayName("Test new ArrayBlockingQueueBuffer(int, BufferStrategy); when three; then return BufferSize is three")
  @Tag("MaintainedByDiffblue")
  void testNewArrayBlockingQueueBuffer_whenThree_thenReturnBufferSizeIsThree() {
    // Arrange and Act
    ArrayBlockingQueueBuffer<Object> actualArrayBlockingQueueBuffer = new ArrayBlockingQueueBuffer<>(3,
        BufferStrategy.BLOCKING);

    // Assert
    assertEquals(3, actualArrayBlockingQueueBuffer.getBufferSize());
  }

  /**
   * Test {@link ArrayBlockingQueueBuffer#save(Object)}.
   * <p>
   * Method under test: {@link ArrayBlockingQueueBuffer#save(Object)}
   */
  @Test
  @DisplayName("Test save(Object)")
  @Tag("MaintainedByDiffblue")
  void testSave() {
    // Arrange
    ArrayBlockingQueueBuffer<Object> arrayBlockingQueueBuffer = new ArrayBlockingQueueBuffer<>(3,
        BufferStrategy.IF_POSSIBLE);

    // Act and Assert
    assertTrue(arrayBlockingQueueBuffer.save("Data"));
  }

  /**
   * Test {@link ArrayBlockingQueueBuffer#save(Object)}.
   * <ul>
   *   <li>Given {@link ArrayBlockingQueueBuffer#ArrayBlockingQueueBuffer(int, BufferStrategy)} with bufferSize is three and strategy is {@code BLOCKING}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ArrayBlockingQueueBuffer#save(Object)}
   */
  @Test
  @DisplayName("Test save(Object); given ArrayBlockingQueueBuffer(int, BufferStrategy) with bufferSize is three and strategy is 'BLOCKING'")
  @Tag("MaintainedByDiffblue")
  void testSave_givenArrayBlockingQueueBufferWithBufferSizeIsThreeAndStrategyIsBlocking() {
    // Arrange
    ArrayBlockingQueueBuffer<Object> arrayBlockingQueueBuffer = new ArrayBlockingQueueBuffer<>(3,
        BufferStrategy.BLOCKING);

    // Act and Assert
    assertTrue(arrayBlockingQueueBuffer.save("Data"));
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ArrayBlockingQueueBuffer#setStrategy(BufferStrategy)}
   *   <li>{@link ArrayBlockingQueueBuffer#getBufferSize()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  void testGettersAndSetters() {
    // Arrange
    ArrayBlockingQueueBuffer<Object> arrayBlockingQueueBuffer = new ArrayBlockingQueueBuffer<>(3,
        BufferStrategy.BLOCKING);

    // Act
    arrayBlockingQueueBuffer.setStrategy(BufferStrategy.BLOCKING);

    // Assert
    assertEquals(3, arrayBlockingQueueBuffer.getBufferSize());
  }
}
