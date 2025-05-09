package org.apache.skywalking.oap.server.library.datacarrier.partition;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ProducerThreadPartitionerDiffblueTest {
  /**
   * Test {@link ProducerThreadPartitioner#partition(int, Object)}.
   * <ul>
   *   <li>When one.</li>
   *   <li>Then return zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProducerThreadPartitioner#partition(int, Object)}
   */
  @Test
  @DisplayName("Test partition(int, Object); when one; then return zero")
  @Tag("MaintainedByDiffblue")
  void testPartition_whenOne_thenReturnZero() {
    // Arrange
    ProducerThreadPartitioner<Object> producerThreadPartitioner = new ProducerThreadPartitioner<>();

    // Act and Assert
    assertEquals(0, producerThreadPartitioner.partition(1, "Data"));
  }

  /**
   * Test {@link ProducerThreadPartitioner#maxRetryCount()}.
   * <p>
   * Method under test: {@link ProducerThreadPartitioner#maxRetryCount()}
   */
  @Test
  @DisplayName("Test maxRetryCount()")
  @Tag("MaintainedByDiffblue")
  void testMaxRetryCount() {
    // Arrange
    ProducerThreadPartitioner<Object> producerThreadPartitioner = new ProducerThreadPartitioner<>();

    // Act and Assert
    assertEquals(1, producerThreadPartitioner.maxRetryCount());
  }
}
