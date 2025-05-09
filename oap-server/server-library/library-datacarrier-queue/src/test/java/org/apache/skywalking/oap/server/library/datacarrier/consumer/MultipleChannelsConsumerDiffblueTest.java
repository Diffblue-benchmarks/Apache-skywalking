package org.apache.skywalking.oap.server.library.datacarrier.consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.apache.skywalking.oap.server.library.datacarrier.buffer.BufferStrategy;
import org.apache.skywalking.oap.server.library.datacarrier.buffer.Channels;
import org.apache.skywalking.oap.server.library.datacarrier.partition.ProducerThreadPartitioner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class MultipleChannelsConsumerDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link MultipleChannelsConsumer#MultipleChannelsConsumer(String, long)}
   *   <li>{@link MultipleChannelsConsumer#shutdown()}
   *   <li>{@link MultipleChannelsConsumer#size()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  void testGettersAndSetters() {
    // Arrange and Act
    MultipleChannelsConsumer actualMultipleChannelsConsumer = new MultipleChannelsConsumer("Thread Name", 1L);
    actualMultipleChannelsConsumer.shutdown();

    // Assert
    assertEquals(0L, actualMultipleChannelsConsumer.size());
  }

  /**
   * Test {@link MultipleChannelsConsumer#addNewTarget(Channels, IConsumer)}.
   * <p>
   * Method under test: {@link MultipleChannelsConsumer#addNewTarget(Channels, IConsumer)}
   */
  @Test
  @DisplayName("Test addNewTarget(Channels, IConsumer)")
  @Tag("MaintainedByDiffblue")
  void testAddNewTarget() {
    // Arrange
    MultipleChannelsConsumer multipleChannelsConsumer = new MultipleChannelsConsumer("Thread Name", 1L);
    Channels channels = new Channels(3, 3, new ProducerThreadPartitioner(), BufferStrategy.BLOCKING);

    // Act
    multipleChannelsConsumer.addNewTarget(channels, new SampleConsumer());

    // Assert
    assertEquals(9L, multipleChannelsConsumer.size());
  }

  /**
   * Test {@link MultipleChannelsConsumer#addNewTarget(Channels, IConsumer)}.
   * <p>
   * Method under test: {@link MultipleChannelsConsumer#addNewTarget(Channels, IConsumer)}
   */
  @Test
  @DisplayName("Test addNewTarget(Channels, IConsumer)")
  @Tag("MaintainedByDiffblue")
  void testAddNewTarget2() {
    // Arrange
    MultipleChannelsConsumer multipleChannelsConsumer = new MultipleChannelsConsumer("Thread Name", 1L);
    Channels channels = new Channels(3, 3, new ProducerThreadPartitioner(), BufferStrategy.BLOCKING);

    multipleChannelsConsumer.addNewTarget(channels, new SampleConsumer());
    Channels channels2 = new Channels(3, 3, new ProducerThreadPartitioner(), BufferStrategy.BLOCKING);

    // Act
    multipleChannelsConsumer.addNewTarget(channels2, new SampleConsumer());

    // Assert
    assertEquals(18L, multipleChannelsConsumer.size());
  }
}
