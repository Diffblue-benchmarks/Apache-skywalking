package org.apache.skywalking.oap.server.library.jfr.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.apache.skywalking.oap.server.library.jfr.type.Frame;
import org.apache.skywalking.oap.server.library.jfr.type.FrameTree;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class JFRMergeBuilderDiffblueTest {
  /**
   * Test {@link JFRMergeBuilder#merge(FrameTree)} with {@code tree}.
   * <ul>
   *   <li>Given zero.</li>
   *   <li>Then calls {@link Frame#getTitleIndex()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JFRMergeBuilder#merge(FrameTree)}
   */
  @Test
  @DisplayName("Test merge(FrameTree) with 'tree'; given zero; then calls getTitleIndex()")
  @Tag("MaintainedByDiffblue")
  void testMergeWithTree_givenZero_thenCallsGetTitleIndex() {
    // Arrange
    JFRMergeBuilder jfrMergeBuilder = new JFRMergeBuilder();
    Frame frame = mock(Frame.class);
    when(frame.getTitleIndex()).thenReturn(0);

    // Act
    JFRMergeBuilder actualMergeResult = jfrMergeBuilder.merge(new FrameTree(frame, new String[]{"Key2frame"}));

    // Assert
    verify(frame).getTitleIndex();
    assertSame(jfrMergeBuilder, actualMergeResult);
  }

  /**
   * Test {@link JFRMergeBuilder#merge(FrameTree)} with {@code tree}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JFRMergeBuilder#merge(FrameTree)}
   */
  @Test
  @DisplayName("Test merge(FrameTree) with 'tree'; when 'null'")
  @Tag("MaintainedByDiffblue")
  void testMergeWithTree_whenNull() {
    // Arrange
    JFRMergeBuilder jfrMergeBuilder = new JFRMergeBuilder();

    // Act and Assert
    assertSame(jfrMergeBuilder, jfrMergeBuilder.merge((FrameTree) null));
  }

  /**
   * Test {@link JFRMergeBuilder#merge(List)} with {@code trees}.
   * <ul>
   *   <li>Given {@link Frame} {@link Frame#getTitleIndex()} return zero.</li>
   *   <li>Then calls {@link Frame#getTitleIndex()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JFRMergeBuilder#merge(List)}
   */
  @Test
  @DisplayName("Test merge(List) with 'trees'; given Frame getTitleIndex() return zero; then calls getTitleIndex()")
  @Tag("MaintainedByDiffblue")
  void testMergeWithTrees_givenFrameGetTitleIndexReturnZero_thenCallsGetTitleIndex() {
    // Arrange
    JFRMergeBuilder jfrMergeBuilder = new JFRMergeBuilder();
    Frame frame = mock(Frame.class);
    when(frame.getTitleIndex()).thenReturn(0);
    FrameTree frameTree = new FrameTree(frame, new String[]{"Key2frame"});

    ArrayList<FrameTree> trees = new ArrayList<>();
    trees.add(frameTree);

    // Act
    JFRMergeBuilder actualMergeResult = jfrMergeBuilder.merge(trees);

    // Assert
    verify(frame).getTitleIndex();
    assertSame(jfrMergeBuilder, actualMergeResult);
  }

  /**
   * Test {@link JFRMergeBuilder#merge(List)} with {@code trees}.
   * <ul>
   *   <li>Given {@code null}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JFRMergeBuilder#merge(List)}
   */
  @Test
  @DisplayName("Test merge(List) with 'trees'; given 'null'; when ArrayList() add 'null'")
  @Tag("MaintainedByDiffblue")
  void testMergeWithTrees_givenNull_whenArrayListAddNull() {
    // Arrange
    JFRMergeBuilder jfrMergeBuilder = new JFRMergeBuilder();

    ArrayList<FrameTree> trees = new ArrayList<>();
    trees.add(null);

    // Act and Assert
    assertSame(jfrMergeBuilder, jfrMergeBuilder.merge(trees));
  }

  /**
   * Test {@link JFRMergeBuilder#merge(List)} with {@code trees}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JFRMergeBuilder#merge(List)}
   */
  @Test
  @DisplayName("Test merge(List) with 'trees'; when ArrayList()")
  @Tag("MaintainedByDiffblue")
  void testMergeWithTrees_whenArrayList() {
    // Arrange
    JFRMergeBuilder jfrMergeBuilder = new JFRMergeBuilder();

    // Act and Assert
    assertSame(jfrMergeBuilder, jfrMergeBuilder.merge(new ArrayList<>()));
  }

  /**
   * Test {@link JFRMergeBuilder#merge(List)} with {@code trees}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JFRMergeBuilder#merge(List)}
   */
  @Test
  @DisplayName("Test merge(List) with 'trees'; when 'null'")
  @Tag("MaintainedByDiffblue")
  void testMergeWithTrees_whenNull() {
    // Arrange
    JFRMergeBuilder jfrMergeBuilder = new JFRMergeBuilder();

    // Act and Assert
    assertSame(jfrMergeBuilder, jfrMergeBuilder.merge((List<FrameTree>) null));
  }

  /**
   * Test {@link JFRMergeBuilder#merge0(Frame, FrameTree)}.
   * <ul>
   *   <li>Given one.</li>
   *   <li>When {@link Frame} {@link Frame#getSelf()} return one.</li>
   *   <li>Then calls {@link Frame#getSelf()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JFRMergeBuilder#merge0(Frame, FrameTree)}
   */
  @Test
  @DisplayName("Test merge0(Frame, FrameTree); given one; when Frame getSelf() return one; then calls getSelf()")
  @Tag("MaintainedByDiffblue")
  void testMerge0_givenOne_whenFrameGetSelfReturnOne_thenCallsGetSelf() {
    // Arrange
    JFRMergeBuilder jfrMergeBuilder = new JFRMergeBuilder();
    Frame frame = mock(Frame.class);
    when(frame.getSelf()).thenReturn(1L);
    when(frame.getTotal()).thenReturn(1L);
    doNothing().when(frame).setSelf(anyLong());
    doNothing().when(frame).setTotal(anyLong());
    Frame frame2 = mock(Frame.class);
    when(frame2.getTitleIndex()).thenReturn(0);

    // Act
    jfrMergeBuilder.merge0(frame, new FrameTree(frame2, new String[]{"Key2frame"}));

    // Assert
    verify(frame).getSelf();
    verify(frame2).getTitleIndex();
    verify(frame).getTotal();
    verify(frame).setSelf(eq(1L));
    verify(frame).setTotal(eq(1L));
  }

  /**
   * Test {@link JFRMergeBuilder#build()}.
   * <p>
   * Method under test: {@link JFRMergeBuilder#build()}
   */
  @Test
  @DisplayName("Test build()")
  @Tag("MaintainedByDiffblue")
  void testBuild() {
    // Arrange and Act
    FrameTree actualBuildResult = (new JFRMergeBuilder()).build();

    // Assert
    assertEquals("", actualBuildResult.getFrame());
    assertNull(actualBuildResult.getChildren());
    assertEquals(0L, actualBuildResult.getSelf());
    assertEquals(0L, actualBuildResult.getTotal());
  }

  /**
   * Test new {@link JFRMergeBuilder} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of {@link JFRMergeBuilder}
   */
  @Test
  @DisplayName("Test new JFRMergeBuilder (default constructor)")
  @Tag("MaintainedByDiffblue")
  void testNewJFRMergeBuilder() {
    // Arrange, Act and Assert
    FrameTree buildResult = (new JFRMergeBuilder()).build();
    assertEquals("", buildResult.getFrame());
    assertNull(buildResult.getChildren());
    assertEquals(0L, buildResult.getSelf());
    assertEquals(0L, buildResult.getTotal());
  }
}
