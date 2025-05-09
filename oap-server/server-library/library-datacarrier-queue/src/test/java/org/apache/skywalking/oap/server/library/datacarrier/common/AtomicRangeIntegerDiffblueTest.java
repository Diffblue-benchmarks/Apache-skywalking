package org.apache.skywalking.oap.server.library.datacarrier.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class AtomicRangeIntegerDiffblueTest {
  /**
   * Test {@link AtomicRangeInteger#AtomicRangeInteger(int, int)}.
   * <ul>
   *   <li>When fifteen.</li>
   *   <li>Then return {@link AtomicRangeInteger#get()} is fifteen.</li>
   * </ul>
   * <p>
   * Method under test: {@link AtomicRangeInteger#AtomicRangeInteger(int, int)}
   */
  @Test
  @DisplayName("Test new AtomicRangeInteger(int, int); when fifteen; then return get() is fifteen")
  @Tag("MaintainedByDiffblue")
  void testNewAtomicRangeInteger_whenFifteen_thenReturnGetIsFifteen() {
    // Arrange and Act
    AtomicRangeInteger actualAtomicRangeInteger = new AtomicRangeInteger(15, 3);

    // Assert
    assertEquals(15, actualAtomicRangeInteger.get());
    assertEquals(2, actualAtomicRangeInteger.getAndIncrement());
  }

  /**
   * Test {@link AtomicRangeInteger#AtomicRangeInteger(int, int)}.
   * <ul>
   *   <li>When forty-two.</li>
   *   <li>Then return {@link AtomicRangeInteger#get()} is forty-two.</li>
   * </ul>
   * <p>
   * Method under test: {@link AtomicRangeInteger#AtomicRangeInteger(int, int)}
   */
  @Test
  @DisplayName("Test new AtomicRangeInteger(int, int); when forty-two; then return get() is forty-two")
  @Tag("MaintainedByDiffblue")
  void testNewAtomicRangeInteger_whenFortyTwo_thenReturnGetIsFortyTwo() {
    // Arrange and Act
    AtomicRangeInteger actualAtomicRangeInteger = new AtomicRangeInteger(42, 3);

    // Assert
    assertEquals(2, actualAtomicRangeInteger.getAndIncrement());
    assertEquals(42, actualAtomicRangeInteger.get());
  }

  /**
   * Test {@link AtomicRangeInteger#AtomicRangeInteger(int, int)}.
   * <ul>
   *   <li>When thirty-one.</li>
   *   <li>Then return {@link AtomicRangeInteger#get()} is thirty-one.</li>
   * </ul>
   * <p>
   * Method under test: {@link AtomicRangeInteger#AtomicRangeInteger(int, int)}
   */
  @Test
  @DisplayName("Test new AtomicRangeInteger(int, int); when thirty-one; then return get() is thirty-one")
  @Tag("MaintainedByDiffblue")
  void testNewAtomicRangeInteger_whenThirtyOne_thenReturnGetIsThirtyOne() {
    // Arrange and Act
    AtomicRangeInteger actualAtomicRangeInteger = new AtomicRangeInteger(31, 3);

    // Assert
    assertEquals(2, actualAtomicRangeInteger.getAndIncrement());
    assertEquals(31, actualAtomicRangeInteger.get());
  }

  /**
   * Test {@link AtomicRangeInteger#getAndIncrement()}.
   * <ul>
   *   <li>Then return two.</li>
   * </ul>
   * <p>
   * Method under test: {@link AtomicRangeInteger#getAndIncrement()}
   */
  @Test
  @DisplayName("Test getAndIncrement(); then return two")
  @Tag("MaintainedByDiffblue")
  void testGetAndIncrement_thenReturnTwo() {
    // Arrange
    AtomicRangeInteger atomicRangeInteger = new AtomicRangeInteger(42, 3);

    // Act and Assert
    assertEquals(2, atomicRangeInteger.getAndIncrement());
    assertEquals(42, atomicRangeInteger.get());
  }

  /**
   * Test {@link AtomicRangeInteger#get()}.
   * <p>
   * Method under test: {@link AtomicRangeInteger#get()}
   */
  @Test
  @DisplayName("Test get()")
  @Tag("MaintainedByDiffblue")
  void testGet() {
    // Arrange, Act and Assert
    assertEquals(1, (new AtomicRangeInteger(1, 3)).get());
  }

  /**
   * Test {@link AtomicRangeInteger#intValue()}.
   * <p>
   * Method under test: {@link AtomicRangeInteger#intValue()}
   */
  @Test
  @DisplayName("Test intValue()")
  @Tag("MaintainedByDiffblue")
  void testIntValue() {
    // Arrange, Act and Assert
    assertEquals(1, (new AtomicRangeInteger(1, 3)).intValue());
  }

  /**
   * Test {@link AtomicRangeInteger#longValue()}.
   * <p>
   * Method under test: {@link AtomicRangeInteger#longValue()}
   */
  @Test
  @DisplayName("Test longValue()")
  @Tag("MaintainedByDiffblue")
  void testLongValue() {
    // Arrange, Act and Assert
    assertEquals(1L, (new AtomicRangeInteger(1, 3)).longValue());
  }

  /**
   * Test {@link AtomicRangeInteger#floatValue()}.
   * <p>
   * Method under test: {@link AtomicRangeInteger#floatValue()}
   */
  @Test
  @DisplayName("Test floatValue()")
  @Tag("MaintainedByDiffblue")
  void testFloatValue() {
    // Arrange, Act and Assert
    assertEquals(1.0f, (new AtomicRangeInteger(1, 3)).floatValue());
  }

  /**
   * Test {@link AtomicRangeInteger#doubleValue()}.
   * <p>
   * Method under test: {@link AtomicRangeInteger#doubleValue()}
   */
  @Test
  @DisplayName("Test doubleValue()")
  @Tag("MaintainedByDiffblue")
  void testDoubleValue() {
    // Arrange, Act and Assert
    assertEquals(1.0d, (new AtomicRangeInteger(1, 3)).doubleValue());
  }
}
