package org.apache.skywalking.oap.server.library.jfr.type;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class JFREventTypeDiffblueTest {
  /**
   * Test {@link JFREventType#isLockSample(JFREventType)}.
   * <ul>
   *   <li>When {@code JAVA_MONITOR_ENTER}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JFREventType#isLockSample(JFREventType)}
   */
  @Test
  @DisplayName("Test isLockSample(JFREventType); when 'JAVA_MONITOR_ENTER'; then return 'true'")
  @Tag("MaintainedByDiffblue")
  void testIsLockSample_whenJavaMonitorEnter_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue(JFREventType.isLockSample(JFREventType.JAVA_MONITOR_ENTER));
  }

  /**
   * Test {@link JFREventType#isLockSample(JFREventType)}.
   * <ul>
   *   <li>When {@code LOCK}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JFREventType#isLockSample(JFREventType)}
   */
  @Test
  @DisplayName("Test isLockSample(JFREventType); when 'LOCK'; then return 'true'")
  @Tag("MaintainedByDiffblue")
  void testIsLockSample_whenLock_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue(JFREventType.isLockSample(JFREventType.LOCK));
  }

  /**
   * Test {@link JFREventType#isLockSample(JFREventType)}.
   * <ul>
   *   <li>When {@code THREAD_PARK}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JFREventType#isLockSample(JFREventType)}
   */
  @Test
  @DisplayName("Test isLockSample(JFREventType); when 'THREAD_PARK'; then return 'true'")
  @Tag("MaintainedByDiffblue")
  void testIsLockSample_whenThreadPark_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue(JFREventType.isLockSample(JFREventType.THREAD_PARK));
  }

  /**
   * Test {@link JFREventType#isLockSample(JFREventType)}.
   * <ul>
   *   <li>When {@code UNKNOWN}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JFREventType#isLockSample(JFREventType)}
   */
  @Test
  @DisplayName("Test isLockSample(JFREventType); when 'UNKNOWN'; then return 'false'")
  @Tag("MaintainedByDiffblue")
  void testIsLockSample_whenUnknown_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(JFREventType.isLockSample(JFREventType.UNKNOWN));
  }
}
