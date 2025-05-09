package org.apache.skywalking.library.kubernetes;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class KubernetesServicesDiffblueTest {
  /**
   * Test {@link KubernetesServices#list()}.
   * <p>
   * Method under test: {@link KubernetesServices#list()}
   */
  @Test
  @DisplayName("Test list()")
  @Tag("MaintainedByDiffblue")
  void testList() {
    // Arrange, Act and Assert
    assertTrue(KubernetesServices.INSTANCE.list().isEmpty());
  }
}
