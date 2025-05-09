package org.apache.skywalking.oap.server.library.jfr.parser;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.apache.skywalking.oap.server.library.jfr.type.Arguments;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class JFRToFrameTreeDiffblueTest {
  /**
   * Test {@link JFRToFrameTree#getFrameTreeMap()}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link JFRToFrameTree#getFrameTreeMap()}
   */
  @Test
  @DisplayName("Test getFrameTreeMap(); then return Empty")
  @Tag("MaintainedByDiffblue")
  void testGetFrameTreeMap_thenReturnEmpty() {
    // Arrange, Act and Assert
    assertTrue((new JFRToFrameTree(null, new Arguments())).getFrameTreeMap().isEmpty());
  }
}
