package org.apache.skywalking.oap.server.library.datacarrier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class EnvUtilDiffblueTest {
  /**
   * Test {@link EnvUtil#getInt(String, int)}.
   * <ul>
   *   <li>When {@code Env Name}.</li>
   *   <li>Then return forty-two.</li>
   * </ul>
   * <p>
   * Method under test: {@link EnvUtil#getInt(String, int)}
   */
  @Test
  @DisplayName("Test getInt(String, int); when 'Env Name'; then return forty-two")
  @Tag("MaintainedByDiffblue")
  void testGetInt_whenEnvName_thenReturnFortyTwo() {
    // Arrange, Act and Assert
    assertEquals(42, EnvUtil.getInt("Env Name", 42));
  }

  /**
   * Test {@link EnvUtil#getLong(String, long)}.
   * <ul>
   *   <li>When {@code Env Name}.</li>
   *   <li>Then return forty-two.</li>
   * </ul>
   * <p>
   * Method under test: {@link EnvUtil#getLong(String, long)}
   */
  @Test
  @DisplayName("Test getLong(String, long); when 'Env Name'; then return forty-two")
  @Tag("MaintainedByDiffblue")
  void testGetLong_whenEnvName_thenReturnFortyTwo() {
    // Arrange, Act and Assert
    assertEquals(42L, EnvUtil.getLong("Env Name", 42L));
  }
}
