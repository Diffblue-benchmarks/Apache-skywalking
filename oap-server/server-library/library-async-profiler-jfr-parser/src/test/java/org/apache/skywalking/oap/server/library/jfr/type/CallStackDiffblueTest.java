package org.apache.skywalking.oap.server.library.jfr.type;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class CallStackDiffblueTest {
  /**
   * Test {@link CallStack#push(String, byte)}.
   * <p>
   * Method under test: {@link CallStack#push(String, byte)}
   */
  @Test
  @DisplayName("Test push(String, byte)")
  @Tag("MaintainedByDiffblue")
  void testPush() {
    // Arrange
    CallStack callStack = new CallStack();

    // Act
    callStack.push("Name", (byte) 'A');

    // Assert
    String[] names = callStack.getNames();
    assertEquals("Name", names[0]);
    assertEquals(1, callStack.getSize());
    assertEquals(Short.SIZE, names.length);
    assertArrayEquals(new byte[]{'A', Frame.TYPE_INTERPRETED, Frame.TYPE_INTERPRETED, Frame.TYPE_INTERPRETED,
        Frame.TYPE_INTERPRETED, Frame.TYPE_INTERPRETED, Frame.TYPE_INTERPRETED, Frame.TYPE_INTERPRETED,
        Frame.TYPE_INTERPRETED, Frame.TYPE_INTERPRETED, Frame.TYPE_INTERPRETED, Frame.TYPE_INTERPRETED,
        Frame.TYPE_INTERPRETED, Frame.TYPE_INTERPRETED, Frame.TYPE_INTERPRETED, Frame.TYPE_INTERPRETED},
        callStack.getTypes());
  }

  /**
   * Test new {@link CallStack} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of {@link CallStack}
   */
  @Test
  @DisplayName("Test new CallStack (default constructor)")
  @Tag("MaintainedByDiffblue")
  void testNewCallStack() {
    // Arrange and Act
    CallStack actualCallStack = new CallStack();

    // Assert
    assertEquals(0, actualCallStack.getSize());
    assertArrayEquals(
        new byte[]{Frame.TYPE_INTERPRETED, Frame.TYPE_INTERPRETED, Frame.TYPE_INTERPRETED, Frame.TYPE_INTERPRETED,
            Frame.TYPE_INTERPRETED, Frame.TYPE_INTERPRETED, Frame.TYPE_INTERPRETED, Frame.TYPE_INTERPRETED,
            Frame.TYPE_INTERPRETED, Frame.TYPE_INTERPRETED, Frame.TYPE_INTERPRETED, Frame.TYPE_INTERPRETED,
            Frame.TYPE_INTERPRETED, Frame.TYPE_INTERPRETED, Frame.TYPE_INTERPRETED, Frame.TYPE_INTERPRETED},
        actualCallStack.getTypes());
    assertArrayEquals(
        new String[]{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
        actualCallStack.getNames());
  }
}
