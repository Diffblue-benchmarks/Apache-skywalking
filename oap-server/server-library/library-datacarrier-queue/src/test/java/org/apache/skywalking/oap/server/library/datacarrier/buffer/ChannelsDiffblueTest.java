package org.apache.skywalking.oap.server.library.datacarrier.buffer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.apache.skywalking.oap.server.library.datacarrier.partition.IDataPartitioner;
import org.apache.skywalking.oap.server.library.datacarrier.partition.ProducerThreadPartitioner;
import org.apache.skywalking.oap.server.library.datacarrier.partition.SimpleRollingPartitioner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ChannelsDiffblueTest {
  /**
   * Test {@link Channels#Channels(int, int, IDataPartitioner, BufferStrategy)}.
   * <ul>
   *   <li>When {@link ProducerThreadPartitioner} (default constructor).</li>
   *   <li>Then return ChannelSize is three.</li>
   * </ul>
   * <p>
   * Method under test: {@link Channels#Channels(int, int, IDataPartitioner, BufferStrategy)}
   */
  @Test
  @DisplayName("Test new Channels(int, int, IDataPartitioner, BufferStrategy); when ProducerThreadPartitioner (default constructor); then return ChannelSize is three")
  @Tag("MaintainedByDiffblue")
  void testNewChannels_whenProducerThreadPartitioner_thenReturnChannelSizeIsThree() {
    // Arrange and Act
    Channels<Object> actualChannels = new Channels<>(3, 3, new ProducerThreadPartitioner<>(), BufferStrategy.BLOCKING);

    // Assert
    assertEquals(3, actualChannels.getChannelSize());
    assertEquals(9L, actualChannels.size());
  }

  /**
   * Test {@link Channels#save(Object)}.
   * <p>
   * Method under test: {@link Channels#save(Object)}
   */
  @Test
  @DisplayName("Test save(Object)")
  @Tag("MaintainedByDiffblue")
  void testSave() {
    // Arrange
    Channels<Object> channels = new Channels<>(3, 3, new ProducerThreadPartitioner<>(), BufferStrategy.BLOCKING);

    // Act and Assert
    assertTrue(channels.save("Data"));
  }

  /**
   * Test {@link Channels#save(Object)}.
   * <p>
   * Method under test: {@link Channels#save(Object)}
   */
  @Test
  @DisplayName("Test save(Object)")
  @Tag("MaintainedByDiffblue")
  void testSave2() {
    // Arrange
    Channels<Object> channels = new Channels<>(3, 3, new ProducerThreadPartitioner<>(), BufferStrategy.IF_POSSIBLE);

    // Act and Assert
    assertTrue(channels.save("Data"));
  }

  /**
   * Test {@link Channels#save(Object)}.
   * <p>
   * Method under test: {@link Channels#save(Object)}
   */
  @Test
  @DisplayName("Test save(Object)")
  @Tag("MaintainedByDiffblue")
  void testSave3() {
    // Arrange
    Channels<Object> channels = new Channels<>(3, 3, new SimpleRollingPartitioner<>(), BufferStrategy.IF_POSSIBLE);

    // Act and Assert
    assertTrue(channels.save("Data"));
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Channels#setPartitioner(IDataPartitioner)}
   *   <li>{@link Channels#size()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  void testGettersAndSetters() {
    // Arrange
    Channels<Object> channels = new Channels<>(3, 3, new ProducerThreadPartitioner<>(), BufferStrategy.BLOCKING);

    // Act
    channels.setPartitioner(new ProducerThreadPartitioner<>());

    // Assert
    assertEquals(9L, channels.size());
  }

  /**
   * Test {@link Channels#getChannelSize()}.
   * <p>
   * Method under test: {@link Channels#getChannelSize()}
   */
  @Test
  @DisplayName("Test getChannelSize()")
  @Tag("MaintainedByDiffblue")
  void testGetChannelSize() {
    // Arrange
    Channels<Object> channels = new Channels<>(3, 3, new ProducerThreadPartitioner<>(), BufferStrategy.BLOCKING);

    // Act and Assert
    assertEquals(3, channels.getChannelSize());
  }

  /**
   * Test {@link Channels#getBuffer(int)}.
   * <ul>
   *   <li>Then return {@link ArrayBlockingQueueBuffer}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Channels#getBuffer(int)}
   */
  @Test
  @DisplayName("Test getBuffer(int); then return ArrayBlockingQueueBuffer")
  @Tag("MaintainedByDiffblue")
  void testGetBuffer_thenReturnArrayBlockingQueueBuffer() {
    // Arrange
    Channels<Object> channels = new Channels<>(3, 3, new ProducerThreadPartitioner<>(), BufferStrategy.BLOCKING);

    // Act
    QueueBuffer<Object> actualBuffer = channels.getBuffer(1);

    // Assert
    assertTrue(actualBuffer instanceof ArrayBlockingQueueBuffer);
    assertEquals(3, actualBuffer.getBufferSize());
  }
}
