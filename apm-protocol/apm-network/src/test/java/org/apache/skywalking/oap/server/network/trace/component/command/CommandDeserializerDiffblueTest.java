package org.apache.skywalking.oap.server.network.trace.component.command;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.apache.skywalking.apm.network.common.v3.Command;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class CommandDeserializerDiffblueTest {
  /**
   * Test {@link CommandDeserializer#deserialize(Command)}.
   * <ul>
   *   <li>When DefaultInstance.</li>
   *   <li>Then throw {@link UnsupportedCommandException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CommandDeserializer#deserialize(Command)}
   */
  @Test
  @DisplayName("Test deserialize(Command); when DefaultInstance; then throw UnsupportedCommandException")
  @Tag("MaintainedByDiffblue")
  void testDeserialize_whenDefaultInstance_thenThrowUnsupportedCommandException() {
    // Arrange, Act and Assert
    assertThrows(UnsupportedCommandException.class,
        () -> CommandDeserializer.deserialize(Command.getDefaultInstance()));
  }
}
