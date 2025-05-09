package org.apache.skywalking.oap.server.library.jfr.type;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.HashMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class FrameTreeDiffblueTest {
  /**
   * Test {@link FrameTree#FrameTree(Frame, String[])}.
   * <ul>
   *   <li>Given zero.</li>
   *   <li>Then return Frame is {@code Key2frame}.</li>
   * </ul>
   * <p>
   * Method under test: {@link FrameTree#FrameTree(Frame, String[])}
   */
  @Test
  @DisplayName("Test new FrameTree(Frame, String[]); given zero; then return Frame is 'Key2frame'")
  @Tag("MaintainedByDiffblue")
  void testNewFrameTree_givenZero_thenReturnFrameIsKey2frame() {
    // Arrange
    Frame frame = mock(Frame.class);
    when(frame.getTitleIndex()).thenReturn(0);

    // Act
    FrameTree actualFrameTree = new FrameTree(frame, new String[]{"Key2frame"});

    // Assert
    verify(frame).getTitleIndex();
    assertEquals("Key2frame", actualFrameTree.getFrame());
    assertNull(actualFrameTree.getChildren());
    assertEquals(0L, actualFrameTree.getSelf());
    assertEquals(0L, actualFrameTree.getTotal());
  }

  /**
   * Test {@link FrameTree#buildTree(Frame, String[])}.
   * <ul>
   *   <li>Given {@code false}.</li>
   *   <li>When {@link Frame} {@link HashMap#isEmpty()} return {@code false}.</li>
   *   <li>Then return Children Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link FrameTree#buildTree(Frame, String[])}
   */
  @Test
  @DisplayName("Test buildTree(Frame, String[]); given 'false'; when Frame isEmpty() return 'false'; then return Children Empty")
  @Tag("MaintainedByDiffblue")
  void testBuildTree_givenFalse_whenFrameIsEmptyReturnFalse_thenReturnChildrenEmpty() {
    // Arrange
    Frame frame = mock(Frame.class);
    when(frame.isEmpty()).thenReturn(false);
    when(frame.size()).thenReturn(3);
    when(frame.getTitleIndex()).thenReturn(0);
    when(frame.values()).thenReturn(new ArrayList<>());

    // Act
    FrameTree actualBuildTreeResult = FrameTree.buildTree(frame, new String[]{"Key2frame"});

    // Assert
    verify(frame).isEmpty();
    verify(frame).size();
    verify(frame).values();
    verify(frame).getTitleIndex();
    assertEquals("Key2frame", actualBuildTreeResult.getFrame());
    assertEquals(0L, actualBuildTreeResult.getSelf());
    assertEquals(0L, actualBuildTreeResult.getTotal());
    assertTrue(actualBuildTreeResult.getChildren().isEmpty());
  }

  /**
   * Test {@link FrameTree#buildTree(Frame, String[])}.
   * <ul>
   *   <li>Given {@code true}.</li>
   *   <li>When {@link Frame} {@link HashMap#isEmpty()} return {@code true}.</li>
   *   <li>Then return Children is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link FrameTree#buildTree(Frame, String[])}
   */
  @Test
  @DisplayName("Test buildTree(Frame, String[]); given 'true'; when Frame isEmpty() return 'true'; then return Children is 'null'")
  @Tag("MaintainedByDiffblue")
  void testBuildTree_givenTrue_whenFrameIsEmptyReturnTrue_thenReturnChildrenIsNull() {
    // Arrange
    Frame frame = mock(Frame.class);
    when(frame.isEmpty()).thenReturn(true);
    when(frame.getTitleIndex()).thenReturn(0);

    // Act
    FrameTree actualBuildTreeResult = FrameTree.buildTree(frame, new String[]{"Key2frame"});

    // Assert
    verify(frame).isEmpty();
    verify(frame).getTitleIndex();
    assertEquals("Key2frame", actualBuildTreeResult.getFrame());
    assertNull(actualBuildTreeResult.getChildren());
    assertEquals(0L, actualBuildTreeResult.getSelf());
    assertEquals(0L, actualBuildTreeResult.getTotal());
  }

  /**
   * Test {@link FrameTree#buildTree(Frame, String[])}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link FrameTree#buildTree(Frame, String[])}
   */
  @Test
  @DisplayName("Test buildTree(Frame, String[]); when 'null'; then return 'null'")
  @Tag("MaintainedByDiffblue")
  void testBuildTree_whenNull_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(FrameTree.buildTree(null, new String[]{"Key2frame"}));
  }
}
