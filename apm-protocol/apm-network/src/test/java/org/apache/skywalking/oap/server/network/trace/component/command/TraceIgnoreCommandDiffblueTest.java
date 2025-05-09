package org.apache.skywalking.oap.server.network.trace.component.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;
import org.apache.skywalking.apm.network.common.v3.Command;
import org.apache.skywalking.apm.network.common.v3.Command.Builder;
import org.apache.skywalking.apm.network.common.v3.KeyStringValuePair;
import org.apache.skywalking.apm.network.common.v3.KeyStringValuePairOrBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class TraceIgnoreCommandDiffblueTest {
  /**
   * Test {@link TraceIgnoreCommand#TraceIgnoreCommand(String)}.
   * <p>
   * Method under test: {@link TraceIgnoreCommand#TraceIgnoreCommand(String)}
   */
  @Test
  @DisplayName("Test new TraceIgnoreCommand(String)")
  @Tag("MaintainedByDiffblue")
  void testNewTraceIgnoreCommand() {
    // Arrange and Act
    TraceIgnoreCommand actualTraceIgnoreCommand = new TraceIgnoreCommand("42");

    // Assert
    Builder commandBuilderResult = actualTraceIgnoreCommand.commandBuilder();
    assertEquals("", commandBuilderResult.getInitializationErrorString());
    assertEquals("42", actualTraceIgnoreCommand.getSerialNumber());
    assertEquals("TraceIgnore", commandBuilderResult.getCommand());
    assertEquals("TraceIgnore", actualTraceIgnoreCommand.getCommand());
    assertEquals(1, commandBuilderResult.getArgsBuilderList().size());
    assertEquals(1, commandBuilderResult.getArgsList().size());
    assertEquals(1, commandBuilderResult.getArgsOrBuilderList().size());
    assertEquals(1, commandBuilderResult.getArgsCount());
    assertEquals(2, commandBuilderResult.getAllFields().size());
    assertTrue(commandBuilderResult.findInitializationErrors().isEmpty());
    assertTrue(commandBuilderResult.isInitialized());
  }

  /**
   * Test {@link TraceIgnoreCommand#serialize()}.
   * <p>
   * Method under test: {@link TraceIgnoreCommand#serialize()}
   */
  @Test
  @DisplayName("Test serialize()")
  @Tag("MaintainedByDiffblue")
  void testSerialize() {
    // Arrange and Act
    Builder actualSerializeResult = (new TraceIgnoreCommand("42")).serialize();

    // Assert
    assertEquals("", actualSerializeResult.getInitializationErrorString());
    assertEquals("TraceIgnore", actualSerializeResult.getCommand());
    assertEquals(1, actualSerializeResult.getArgsBuilderList().size());
    assertEquals(1, actualSerializeResult.getArgsList().size());
    assertEquals(1, actualSerializeResult.getArgsOrBuilderList().size());
    assertEquals(1, actualSerializeResult.getArgsCount());
    assertEquals(2, actualSerializeResult.getAllFields().size());
    assertTrue(actualSerializeResult.findInitializationErrors().isEmpty());
    assertTrue(actualSerializeResult.isInitialized());
  }

  /**
   * Test {@link TraceIgnoreCommand#addRule(String)}.
   * <p>
   * Method under test: {@link TraceIgnoreCommand#addRule(String)}
   */
  @Test
  @DisplayName("Test addRule(String)")
  @Tag("MaintainedByDiffblue")
  void testAddRule() {
    // Arrange
    TraceIgnoreCommand traceIgnoreCommand = new TraceIgnoreCommand("42");

    // Act
    traceIgnoreCommand.addRule("Path");

    // Assert
    Builder commandBuilderResult = traceIgnoreCommand.commandBuilder();
    List<KeyStringValuePair.Builder> argsBuilderList = commandBuilderResult.getArgsBuilderList();
    assertEquals(2, argsBuilderList.size());
    assertEquals(2, commandBuilderResult.getArgsList().size());
    List<? extends KeyStringValuePairOrBuilder> argsOrBuilderList = commandBuilderResult.getArgsOrBuilderList();
    assertEquals(2, argsOrBuilderList.size());
    assertEquals(2, commandBuilderResult.getArgsCount());
    assertSame(argsBuilderList.get(1), argsOrBuilderList.get(1));
  }
}
