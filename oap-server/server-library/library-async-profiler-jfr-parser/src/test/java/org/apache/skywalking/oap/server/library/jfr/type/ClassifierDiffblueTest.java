package org.apache.skywalking.oap.server.library.jfr.type;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.UnsupportedEncodingException;
import one.jfr.StackTrace;
import org.apache.skywalking.oap.server.library.jfr.parser.JFRToFrameTree;
import org.apache.skywalking.oap.server.library.jfr.type.Classifier.Category;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ClassifierDiffblueTest {
  /**
   * Test {@link Classifier#getCategory(StackTrace)}.
   * <p>
   * Method under test: {@link Classifier#getCategory(StackTrace)}
   */
  @Test
  @DisplayName("Test getCategory(StackTrace)")
  @Tag("MaintainedByDiffblue")
  void testGetCategory() throws UnsupportedEncodingException {
    // Arrange
    JFRToFrameTree jfrToFrameTree = new JFRToFrameTree(null, new Arguments());

    // Act and Assert
    assertEquals(Category.NATIVE, jfrToFrameTree
        .getCategory(new StackTrace(new long[]{}, "AXAXAXAX".getBytes("UTF-8"), new int[]{1, -1, 1, -1})));
  }

  /**
   * Test {@link Classifier#getCategory(StackTrace)}.
   * <p>
   * Method under test: {@link Classifier#getCategory(StackTrace)}
   */
  @Test
  @DisplayName("Test getCategory(StackTrace)")
  @Tag("MaintainedByDiffblue")
  void testGetCategory2() {
    // Arrange
    JFRToFrameTree jfrToFrameTree = new JFRToFrameTree(null, new Arguments());

    // Act and Assert
    assertEquals(Category.NATIVE, jfrToFrameTree.getCategory(new StackTrace(new long[]{},
        new byte[]{Frame.TYPE_INLINED, 'X', 'A', 'X', 'A', 'X', 'A', 'X'}, new int[]{1, -1, 1, -1})));
  }

  /**
   * Test {@link Classifier#getCategory(StackTrace)}.
   * <ul>
   *   <li>Then return {@code C1_COMP}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Classifier#getCategory(StackTrace)}
   */
  @Test
  @DisplayName("Test getCategory(StackTrace); then return 'C1_COMP'")
  @Tag("MaintainedByDiffblue")
  void testGetCategory_thenReturnC1Comp() {
    // Arrange
    JFRToFrameTree jfrToFrameTree = new JFRToFrameTree(null, new Arguments());

    // Act and Assert
    assertEquals(Category.C1_COMP, jfrToFrameTree.getCategory(new StackTrace(new long[]{},
        new byte[]{Frame.TYPE_C1_COMPILED, 'X', 'A', 'X', 'A', 'X', 'A', 'X'}, new int[]{1, -1, 1, -1})));
  }

  /**
   * Test {@link Classifier#getCategory(StackTrace)}.
   * <ul>
   *   <li>Then return {@code C2_COMP}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Classifier#getCategory(StackTrace)}
   */
  @Test
  @DisplayName("Test getCategory(StackTrace); then return 'C2_COMP'")
  @Tag("MaintainedByDiffblue")
  void testGetCategory_thenReturnC2Comp() {
    // Arrange
    JFRToFrameTree jfrToFrameTree = new JFRToFrameTree(null, new Arguments());

    // Act and Assert
    assertEquals(Category.C2_COMP, jfrToFrameTree.getCategory(new StackTrace(new long[]{},
        new byte[]{Frame.TYPE_JIT_COMPILED, 'X', 'A', 'X', 'A', 'X', 'A', 'X'}, new int[]{1, -1, 1, -1})));
  }

  /**
   * Test {@link Classifier#getCategory(StackTrace)}.
   * <ul>
   *   <li>Then return {@code INTERPRETER}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Classifier#getCategory(StackTrace)}
   */
  @Test
  @DisplayName("Test getCategory(StackTrace); then return 'INTERPRETER'")
  @Tag("MaintainedByDiffblue")
  void testGetCategory_thenReturnInterpreter() {
    // Arrange
    JFRToFrameTree jfrToFrameTree = new JFRToFrameTree(null, new Arguments());

    // Act and Assert
    assertEquals(Category.INTERPRETER, jfrToFrameTree.getCategory(new StackTrace(new long[]{},
        new byte[]{Frame.TYPE_INTERPRETED, 'X', 'A', 'X', 'A', 'X', 'A', 'X'}, new int[]{1, -1, 1, -1})));
  }
}
