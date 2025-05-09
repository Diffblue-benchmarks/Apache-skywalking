package org.apache.skywalking.oap.server.network.trace.component.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.apache.skywalking.apm.network.common.v3.Command;
import org.apache.skywalking.apm.network.common.v3.Command.Builder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class BaseCommandDiffblueTest {
  /**
   * Test {@link BaseCommand#commandBuilder()}.
   * <p>
   * Method under test: {@link BaseCommand#commandBuilder()}
   */
  @Test
  @DisplayName("Test commandBuilder()")
  @Tag("MaintainedByDiffblue")
  void testCommandBuilder() {
    // Arrange and Act
    Builder actualCommandBuilderResult = (new TraceIgnoreCommand("42")).commandBuilder();

    // Assert
    assertEquals("", actualCommandBuilderResult.getInitializationErrorString());
    assertEquals("TraceIgnore", actualCommandBuilderResult.getCommand());
    assertEquals(1, actualCommandBuilderResult.getArgsBuilderList().size());
    assertEquals(1, actualCommandBuilderResult.getArgsList().size());
    assertEquals(1, actualCommandBuilderResult.getArgsOrBuilderList().size());
    assertEquals(1, actualCommandBuilderResult.getArgsCount());
    assertEquals(2, actualCommandBuilderResult.getAllFields().size());
    assertTrue(actualCommandBuilderResult.findInitializationErrors().isEmpty());
    assertTrue(actualCommandBuilderResult.isInitialized());
  }

  /**
   * Test {@link BaseCommand#getCommand()}.
   * <p>
   * Method under test: {@link BaseCommand#getCommand()}
   */
  @Test
  @DisplayName("Test getCommand()")
  @Tag("MaintainedByDiffblue")
  void testGetCommand() {
    // Arrange, Act and Assert
    assertEquals("TraceIgnore", (new TraceIgnoreCommand("42")).getCommand());
  }

  /**
   * Test {@link BaseCommand#getSerialNumber()}.
   * <p>
   * Method under test: {@link BaseCommand#getSerialNumber()}
   */
  @Test
  @DisplayName("Test getSerialNumber()")
  @Tag("MaintainedByDiffblue")
  void testGetSerialNumber() {
    // Arrange, Act and Assert
    assertEquals("42", (new TraceIgnoreCommand("42")).getSerialNumber());
  }
}
