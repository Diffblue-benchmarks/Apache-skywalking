package org.apache.skywalking.oap.server.network.trace.component.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.apache.skywalking.apm.network.common.v3.Command;
import org.apache.skywalking.apm.network.common.v3.Command.Builder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ContinuousProfilingReportCommandDiffblueTest {
  /**
   * Test {@link ContinuousProfilingReportCommand#ContinuousProfilingReportCommand(String, String)}.
   * <p>
   * Method under test: {@link ContinuousProfilingReportCommand#ContinuousProfilingReportCommand(String, String)}
   */
  @Test
  @DisplayName("Test new ContinuousProfilingReportCommand(String, String)")
  @Tag("MaintainedByDiffblue")
  void testNewContinuousProfilingReportCommand() {
    // Arrange and Act
    ContinuousProfilingReportCommand actualContinuousProfilingReportCommand = new ContinuousProfilingReportCommand("42",
        "42");

    // Assert
    Builder commandBuilderResult = actualContinuousProfilingReportCommand.commandBuilder();
    assertEquals("", commandBuilderResult.getInitializationErrorString());
    assertEquals("42", actualContinuousProfilingReportCommand.getSerialNumber());
    assertEquals(1, commandBuilderResult.getArgsBuilderList().size());
    assertEquals(1, commandBuilderResult.getArgsList().size());
    assertEquals(1, commandBuilderResult.getArgsOrBuilderList().size());
    assertEquals(1, commandBuilderResult.getArgsCount());
    assertEquals(2, commandBuilderResult.getAllFields().size());
    assertTrue(commandBuilderResult.findInitializationErrors().isEmpty());
    assertTrue(commandBuilderResult.isInitialized());
    assertEquals(ContinuousProfilingReportCommand.NAME, commandBuilderResult.getCommand());
    assertEquals(ContinuousProfilingReportCommand.NAME, actualContinuousProfilingReportCommand.getCommand());
  }

  /**
   * Test {@link ContinuousProfilingReportCommand#serialize()}.
   * <p>
   * Method under test: {@link ContinuousProfilingReportCommand#serialize()}
   */
  @Test
  @DisplayName("Test serialize()")
  @Tag("MaintainedByDiffblue")
  void testSerialize() {
    // Arrange and Act
    Builder actualSerializeResult = (new ContinuousProfilingReportCommand("42", "42")).serialize();

    // Assert
    assertEquals("", actualSerializeResult.getInitializationErrorString());
    assertEquals(2, actualSerializeResult.getArgsBuilderList().size());
    assertEquals(2, actualSerializeResult.getArgsList().size());
    assertEquals(2, actualSerializeResult.getArgsOrBuilderList().size());
    assertEquals(2, actualSerializeResult.getAllFields().size());
    assertEquals(2, actualSerializeResult.getArgsCount());
    assertTrue(actualSerializeResult.findInitializationErrors().isEmpty());
    assertTrue(actualSerializeResult.isInitialized());
    assertEquals(ContinuousProfilingReportCommand.NAME, actualSerializeResult.getCommand());
  }
}
