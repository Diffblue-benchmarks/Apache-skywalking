package org.apache.skywalking.oap.server.library.datacarrier.partition;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class SimpleRollingPartitionerDiffblueTest {
  /**
   * Test {@link SimpleRollingPartitioner#partition(int, Object)}.
   * <ul>
   *   <li>When one.</li>
   *   <li>Then return zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link SimpleRollingPartitioner#partition(int, Object)}
   */
  @Test
  @DisplayName("Test partition(int, Object); when one; then return zero")
  @Tag("MaintainedByDiffblue")
  void testPartition_whenOne_thenReturnZero() {
    // Arrange
    SimpleRollingPartitioner<Object> simpleRollingPartitioner = new SimpleRollingPartitioner<>();

    // Act and Assert
    assertEquals(0, simpleRollingPartitioner.partition(1, "Data"));
  }

  /**
   * Test {@link SimpleRollingPartitioner#maxRetryCount()}.
   * <p>
   * Method under test: {@link SimpleRollingPartitioner#maxRetryCount()}
   */
  @Test
  @DisplayName("Test maxRetryCount()")
  @Tag("MaintainedByDiffblue")
  void testMaxRetryCount() {
    // Arrange
    SimpleRollingPartitioner<Object> simpleRollingPartitioner = new SimpleRollingPartitioner<>();

    // Act and Assert
    assertEquals(3, simpleRollingPartitioner.maxRetryCount());
  }
}
