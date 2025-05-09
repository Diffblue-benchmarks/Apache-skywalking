package org.apache.skywalking.oap.server.library.datacarrier;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import java.util.Properties;
import org.apache.skywalking.oap.server.library.datacarrier.buffer.BufferStrategy;
import org.apache.skywalking.oap.server.library.datacarrier.buffer.Channels;
import org.apache.skywalking.oap.server.library.datacarrier.consumer.BulkConsumePool;
import org.apache.skywalking.oap.server.library.datacarrier.consumer.ConsumerPool;
import org.apache.skywalking.oap.server.library.datacarrier.consumer.IConsumer;
import org.apache.skywalking.oap.server.library.datacarrier.consumer.SampleConsumer;
import org.apache.skywalking.oap.server.library.datacarrier.partition.ProducerThreadPartitioner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class DataCarrierDiffblueTest {
  /**
   * Test {@link DataCarrier#DataCarrier(int, int, BufferStrategy)}.
   * <ul>
   *   <li>When {@code BLOCKING}.</li>
   *   <li>Then return produce {@code Data}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DataCarrier#DataCarrier(int, int, BufferStrategy)}
   */
  @Test
  @DisplayName("Test new DataCarrier(int, int, BufferStrategy); when 'BLOCKING'; then return produce 'Data'")
  @Tag("MaintainedByDiffblue")
  void testNewDataCarrier_whenBlocking_thenReturnProduceData() {
    // Arrange and Act
    DataCarrier<Object> actualDataCarrier = new DataCarrier<>(3, 3, BufferStrategy.BLOCKING);

    // Assert
    assertTrue(actualDataCarrier.produce("Data"));
  }

  /**
   * Test {@link DataCarrier#DataCarrier(int, int, BufferStrategy)}.
   * <ul>
   *   <li>When {@code BLOCKING}.</li>
   *   <li>Then return produce {@code Data}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DataCarrier#DataCarrier(int, int, BufferStrategy)}
   */
  @Test
  @DisplayName("Test new DataCarrier(int, int, BufferStrategy); when 'BLOCKING'; then return produce 'Data'")
  @Tag("MaintainedByDiffblue")
  void testNewDataCarrier_whenBlocking_thenReturnProduceData2() {
    // Arrange and Act
    DataCarrier<Object> actualDataCarrier = new DataCarrier<>(3, 3, BufferStrategy.BLOCKING);

    // Assert
    assertTrue(actualDataCarrier.produce("Data"));
  }

  /**
   * Test {@link DataCarrier#DataCarrier(String, String, int, int)}.
   * <ul>
   *   <li>When {@code Env Prefix}.</li>
   *   <li>Then return produce {@code Data}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DataCarrier#DataCarrier(String, String, int, int)}
   */
  @Test
  @DisplayName("Test new DataCarrier(String, String, int, int); when 'Env Prefix'; then return produce 'Data'")
  @Tag("MaintainedByDiffblue")
  void testNewDataCarrier_whenEnvPrefix_thenReturnProduceData() {
    // Arrange and Act
    DataCarrier<Object> actualDataCarrier = new DataCarrier<>("Name", "Env Prefix", 3, 3);

    // Assert
    assertTrue(actualDataCarrier.produce("Data"));
  }

  /**
   * Test {@link DataCarrier#DataCarrier(String, String, int, int, BufferStrategy)}.
   * <ul>
   *   <li>When {@code Env Prefix}.</li>
   *   <li>Then return produce {@code Data}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DataCarrier#DataCarrier(String, String, int, int, BufferStrategy)}
   */
  @Test
  @DisplayName("Test new DataCarrier(String, String, int, int, BufferStrategy); when 'Env Prefix'; then return produce 'Data'")
  @Tag("MaintainedByDiffblue")
  void testNewDataCarrier_whenEnvPrefix_thenReturnProduceData2() {
    // Arrange and Act
    DataCarrier<Object> actualDataCarrier = new DataCarrier<>("Name", "Env Prefix", 3, 3, BufferStrategy.BLOCKING);

    // Assert
    assertTrue(actualDataCarrier.produce("Data"));
  }

  /**
   * Test {@link DataCarrier#DataCarrier(String, String, int, int)}.
   * <ul>
   *   <li>When {@code Env Prefix}.</li>
   *   <li>Then return produce {@code Data}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DataCarrier#DataCarrier(String, String, int, int)}
   */
  @Test
  @DisplayName("Test new DataCarrier(String, String, int, int); when 'Env Prefix'; then return produce 'Data'")
  @Tag("MaintainedByDiffblue")
  void testNewDataCarrier_whenEnvPrefix_thenReturnProduceData3() {
    // Arrange and Act
    DataCarrier<Object> actualDataCarrier = new DataCarrier<>("Name", "Env Prefix", 3, 3);

    // Assert
    assertTrue(actualDataCarrier.produce("Data"));
  }

  /**
   * Test {@link DataCarrier#DataCarrier(String, String, int, int, BufferStrategy)}.
   * <ul>
   *   <li>When {@code Env Prefix}.</li>
   *   <li>Then return produce {@code Data}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DataCarrier#DataCarrier(String, String, int, int, BufferStrategy)}
   */
  @Test
  @DisplayName("Test new DataCarrier(String, String, int, int, BufferStrategy); when 'Env Prefix'; then return produce 'Data'")
  @Tag("MaintainedByDiffblue")
  void testNewDataCarrier_whenEnvPrefix_thenReturnProduceData4() {
    // Arrange and Act
    DataCarrier<Object> actualDataCarrier = new DataCarrier<>("Name", "Env Prefix", 3, 3, BufferStrategy.BLOCKING);

    // Assert
    assertTrue(actualDataCarrier.produce("Data"));
  }

  /**
   * Test {@link DataCarrier#DataCarrier(String, int, int)}.
   * <ul>
   *   <li>When {@code Name}.</li>
   *   <li>Then return produce {@code Data}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DataCarrier#DataCarrier(String, int, int)}
   */
  @Test
  @DisplayName("Test new DataCarrier(String, int, int); when 'Name'; then return produce 'Data'")
  @Tag("MaintainedByDiffblue")
  void testNewDataCarrier_whenName_thenReturnProduceData() {
    // Arrange and Act
    DataCarrier<Object> actualDataCarrier = new DataCarrier<>("Name", 3, 3);

    // Assert
    assertTrue(actualDataCarrier.produce("Data"));
  }

  /**
   * Test {@link DataCarrier#DataCarrier(String, int, int)}.
   * <ul>
   *   <li>When {@code Name}.</li>
   *   <li>Then return produce {@code Data}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DataCarrier#DataCarrier(String, int, int)}
   */
  @Test
  @DisplayName("Test new DataCarrier(String, int, int); when 'Name'; then return produce 'Data'")
  @Tag("MaintainedByDiffblue")
  void testNewDataCarrier_whenName_thenReturnProduceData2() {
    // Arrange and Act
    DataCarrier<Object> actualDataCarrier = new DataCarrier<>("Name", 3, 3);

    // Assert
    assertTrue(actualDataCarrier.produce("Data"));
  }

  /**
   * Test {@link DataCarrier#DataCarrier(int, int)}.
   * <ul>
   *   <li>When three.</li>
   *   <li>Then return produce {@code Data}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DataCarrier#DataCarrier(int, int)}
   */
  @Test
  @DisplayName("Test new DataCarrier(int, int); when three; then return produce 'Data'")
  @Tag("MaintainedByDiffblue")
  void testNewDataCarrier_whenThree_thenReturnProduceData() {
    // Arrange and Act
    DataCarrier<Object> actualDataCarrier = new DataCarrier<>(3, 3);

    // Assert
    assertTrue(actualDataCarrier.produce("Data"));
  }

  /**
   * Test {@link DataCarrier#DataCarrier(int, int)}.
   * <ul>
   *   <li>When three.</li>
   *   <li>Then return produce {@code Data}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DataCarrier#DataCarrier(int, int)}
   */
  @Test
  @DisplayName("Test new DataCarrier(int, int); when three; then return produce 'Data'")
  @Tag("MaintainedByDiffblue")
  void testNewDataCarrier_whenThree_thenReturnProduceData2() {
    // Arrange and Act
    DataCarrier<Object> actualDataCarrier = new DataCarrier<>(3, 3);

    // Assert
    assertTrue(actualDataCarrier.produce("Data"));
  }

  /**
   * Test {@link DataCarrier#produce(Object)}.
   * <ul>
   *   <li>Given {@link DataCarrier#DataCarrier(int, int)} with channelSize is three and bufferSize is three.</li>
   *   <li>When {@code Data}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DataCarrier#produce(Object)}
   */
  @Test
  @DisplayName("Test produce(Object); given DataCarrier(int, int) with channelSize is three and bufferSize is three; when 'Data'")
  @Tag("MaintainedByDiffblue")
  void testProduce_givenDataCarrierWithChannelSizeIsThreeAndBufferSizeIsThree_whenData() {
    // Arrange
    DataCarrier<Object> dataCarrier = new DataCarrier<>(3, 3);

    // Act and Assert
    assertTrue(dataCarrier.produce("Data"));
  }

  /**
   * Test {@link DataCarrier#consume(Class, int)} with {@code consumerClass}, {@code num}.
   * <p>
   * Method under test: {@link DataCarrier#consume(Class, int)}
   */
  @Test
  @DisplayName("Test consume(Class, int) with 'consumerClass', 'num'")
  @Tag("MaintainedByDiffblue")
  void testConsumeWithConsumerClassNum() {
    // Arrange
    DataCarrier<Object> dataCarrier = new DataCarrier<>(0, 3);
    Class<IConsumer> forNameResult = IConsumer.class;

    // Act and Assert
    assertSame(dataCarrier, dataCarrier.consume((Class<IConsumer<Object>>) (Class) forNameResult, 0));
  }

  /**
   * Test {@link DataCarrier#consume(Class, int)} with {@code consumerClass}, {@code num}.
   * <p>
   * Method under test: {@link DataCarrier#consume(Class, int)}
   */
  @Test
  @DisplayName("Test consume(Class, int) with 'consumerClass', 'num'")
  @Tag("MaintainedByDiffblue")
  void testConsumeWithConsumerClassNum2() {
    // Arrange
    DataCarrier<Object> dataCarrier = new DataCarrier<>(0, 3);
    Class<IConsumer> forNameResult = IConsumer.class;

    // Act and Assert
    assertSame(dataCarrier, dataCarrier.consume((Class<IConsumer<Object>>) (Class) forNameResult, 0));
  }

  /**
   * Test {@link DataCarrier#consume(Class, int, long, Properties)} with {@code consumerClass}, {@code num}, {@code consumeCycle}, {@code properties}.
   * <p>
   * Method under test: {@link DataCarrier#consume(Class, int, long, Properties)}
   */
  @Test
  @DisplayName("Test consume(Class, int, long, Properties) with 'consumerClass', 'num', 'consumeCycle', 'properties'")
  @Tag("MaintainedByDiffblue")
  void testConsumeWithConsumerClassNumConsumeCycleProperties() {
    // Arrange
    DataCarrier<Object> dataCarrier = new DataCarrier<>(0, 3);
    Class<IConsumer> forNameResult = IConsumer.class;

    // Act and Assert
    assertSame(dataCarrier,
        dataCarrier.consume((Class<IConsumer<Object>>) (Class) forNameResult, 0, 1L, new Properties()));
  }

  /**
   * Test {@link DataCarrier#consume(Class, int, long, Properties)} with {@code consumerClass}, {@code num}, {@code consumeCycle}, {@code properties}.
   * <p>
   * Method under test: {@link DataCarrier#consume(Class, int, long, Properties)}
   */
  @Test
  @DisplayName("Test consume(Class, int, long, Properties) with 'consumerClass', 'num', 'consumeCycle', 'properties'")
  @Tag("MaintainedByDiffblue")
  void testConsumeWithConsumerClassNumConsumeCycleProperties2() {
    // Arrange
    DataCarrier<Object> dataCarrier = new DataCarrier<>(0, 3);
    Class<IConsumer> forNameResult = IConsumer.class;

    // Act and Assert
    assertSame(dataCarrier,
        dataCarrier.consume((Class<IConsumer<Object>>) (Class) forNameResult, 0, 1L, new Properties()));
  }

  /**
   * Test {@link DataCarrier#consume(ConsumerPool, IConsumer)} with {@code consumerPool}, {@code consumer}.
   * <ul>
   *   <li>Given {@code Name}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DataCarrier#consume(ConsumerPool, IConsumer)}
   */
  @Test
  @DisplayName("Test consume(ConsumerPool, IConsumer) with 'consumerPool', 'consumer'; given 'Name'")
  @Tag("MaintainedByDiffblue")
  void testConsumeWithConsumerPoolConsumer_givenName() {
    // Arrange
    DataCarrier<Object> dataCarrier = new DataCarrier<>(3, 3);

    BulkConsumePool consumerPool = new BulkConsumePool("Name", 3, 1L);
    Channels channels = new Channels(3, 3, new ProducerThreadPartitioner(), BufferStrategy.BLOCKING);

    consumerPool.add("Name", channels, new SampleConsumer());

    // Act
    DataCarrier actualConsumeResult = dataCarrier.consume(consumerPool, mock(IConsumer.class));

    // Assert
    assertTrue(actualConsumeResult.produce("Data"));
    assertTrue(consumerPool.isRunning(null));
    assertSame(dataCarrier, actualConsumeResult);
  }

  /**
   * Test {@link DataCarrier#consume(ConsumerPool, IConsumer)} with {@code consumerPool}, {@code consumer}.
   * <ul>
   *   <li>Then return produce {@code Data}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DataCarrier#consume(ConsumerPool, IConsumer)}
   */
  @Test
  @DisplayName("Test consume(ConsumerPool, IConsumer) with 'consumerPool', 'consumer'; then return produce 'Data'")
  @Tag("MaintainedByDiffblue")
  void testConsumeWithConsumerPoolConsumer_thenReturnProduceData() {
    // Arrange
    DataCarrier<Object> dataCarrier = new DataCarrier<>(3, 3);
    BulkConsumePool consumerPool = new BulkConsumePool("Name", 3, 1L);

    // Act
    DataCarrier actualConsumeResult = dataCarrier.consume(consumerPool, mock(IConsumer.class));

    // Assert
    assertTrue(actualConsumeResult.produce("Data"));
    assertTrue(consumerPool.isRunning(null));
    assertSame(dataCarrier, actualConsumeResult);
  }

  /**
   * Test {@link DataCarrier#consume(ConsumerPool, IConsumer)} with {@code consumerPool}, {@code consumer}.
   * <ul>
   *   <li>Then return produce {@code Data}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DataCarrier#consume(ConsumerPool, IConsumer)}
   */
  @Test
  @DisplayName("Test consume(ConsumerPool, IConsumer) with 'consumerPool', 'consumer'; then return produce 'Data'")
  @Tag("MaintainedByDiffblue")
  void testConsumeWithConsumerPoolConsumer_thenReturnProduceData2() {
    // Arrange
    DataCarrier<Object> dataCarrier = new DataCarrier<>(3, 3);
    BulkConsumePool consumerPool = new BulkConsumePool("Name", 3, 1L);

    // Act
    DataCarrier actualConsumeResult = dataCarrier.consume(consumerPool, mock(IConsumer.class));

    // Assert
    assertTrue(actualConsumeResult.produce("Data"));
    assertTrue(consumerPool.isRunning(null));
    assertSame(dataCarrier, actualConsumeResult);
  }
}
