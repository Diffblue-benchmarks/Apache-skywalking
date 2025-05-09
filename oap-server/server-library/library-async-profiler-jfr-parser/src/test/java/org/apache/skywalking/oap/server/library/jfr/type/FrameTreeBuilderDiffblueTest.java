package org.apache.skywalking.oap.server.library.jfr.type;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class FrameTreeBuilderDiffblueTest {
  /**
   * Test {@link FrameTreeBuilder#addSample(CallStack, long)}.
   * <ul>
   *   <li>When {@link CallStack} (default constructor).</li>
   *   <li>Then {@link FrameTreeBuilder} (default constructor) build Self is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link FrameTreeBuilder#addSample(CallStack, long)}
   */
  @Test
  @DisplayName("Test addSample(CallStack, long); when CallStack (default constructor); then FrameTreeBuilder (default constructor) build Self is one")
  @Tag("MaintainedByDiffblue")
  void testAddSample_whenCallStack_thenFrameTreeBuilderBuildSelfIsOne() {
    // Arrange
    FrameTreeBuilder frameTreeBuilder = new FrameTreeBuilder();

    // Act
    frameTreeBuilder.addSample(new CallStack(), 1L);

    // Assert
    FrameTree buildResult = frameTreeBuilder.build();
    assertEquals(1L, buildResult.getSelf());
    assertEquals(1L, buildResult.getTotal());
  }

  /**
   * Test {@link FrameTreeBuilder#build()}.
   * <p>
   * Method under test: {@link FrameTreeBuilder#build()}
   */
  @Test
  @DisplayName("Test build()")
  @Tag("MaintainedByDiffblue")
  void testBuild() {
    // Arrange and Act
    FrameTree actualBuildResult = (new FrameTreeBuilder()).build();

    // Assert
    assertEquals("all", actualBuildResult.getFrame());
    assertNull(actualBuildResult.getChildren());
    assertEquals(0L, actualBuildResult.getSelf());
    assertEquals(0L, actualBuildResult.getTotal());
  }

  /**
   * Test new {@link FrameTreeBuilder} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of {@link FrameTreeBuilder}
   */
  @Test
  @DisplayName("Test new FrameTreeBuilder (default constructor)")
  @Tag("MaintainedByDiffblue")
  void testNewFrameTreeBuilder() {
    // Arrange, Act and Assert
    FrameTree buildResult = (new FrameTreeBuilder()).build();
    assertEquals("all", buildResult.getFrame());
    assertNull(buildResult.getChildren());
    assertEquals(0L, buildResult.getSelf());
    assertEquals(0L, buildResult.getTotal());
  }
}
