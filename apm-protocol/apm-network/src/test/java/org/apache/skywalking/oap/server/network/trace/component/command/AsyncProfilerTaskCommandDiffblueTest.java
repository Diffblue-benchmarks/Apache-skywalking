package org.apache.skywalking.oap.server.network.trace.component.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.google.protobuf.Descriptors;
import com.google.protobuf.Descriptors.Descriptor;
import com.google.protobuf.UnknownFieldSet;
import java.util.ArrayList;
import java.util.List;
import org.apache.skywalking.apm.network.common.v3.Command;
import org.apache.skywalking.apm.network.common.v3.Command.Builder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class AsyncProfilerTaskCommandDiffblueTest {
  /**
   * Test {@link AsyncProfilerTaskCommand#AsyncProfilerTaskCommand(String, String, int, String, long)}.
   * <p>
   * Method under test: {@link AsyncProfilerTaskCommand#AsyncProfilerTaskCommand(String, String, int, String, long)}
   */
  @Test
  @DisplayName("Test new AsyncProfilerTaskCommand(String, String, int, String, long)")
  @Tag("MaintainedByDiffblue")
  void testNewAsyncProfilerTaskCommand() {
    // Arrange and Act
    AsyncProfilerTaskCommand actualAsyncProfilerTaskCommand = new AsyncProfilerTaskCommand("42", "42", 1, "Exec Args",
        1L);

    // Assert
    Builder commandBuilderResult = actualAsyncProfilerTaskCommand.commandBuilder();
    assertEquals("", commandBuilderResult.getInitializationErrorString());
    assertEquals("42", actualAsyncProfilerTaskCommand.getTaskId());
    assertEquals("42", actualAsyncProfilerTaskCommand.getSerialNumber());
    assertEquals("Exec Args", actualAsyncProfilerTaskCommand.getExecArgs());
    assertEquals(1, commandBuilderResult.getArgsBuilderList().size());
    assertEquals(1, commandBuilderResult.getArgsList().size());
    assertEquals(1, commandBuilderResult.getArgsOrBuilderList().size());
    assertEquals(1, commandBuilderResult.getArgsCount());
    assertEquals(1, actualAsyncProfilerTaskCommand.getDuration());
    assertEquals(1L, actualAsyncProfilerTaskCommand.getCreateTime());
    assertEquals(2, commandBuilderResult.getAllFields().size());
    assertTrue(commandBuilderResult.findInitializationErrors().isEmpty());
    assertTrue(commandBuilderResult.isInitialized());
    assertEquals(AsyncProfilerTaskCommand.NAME, commandBuilderResult.getCommand());
    assertEquals(AsyncProfilerTaskCommand.NAME, actualAsyncProfilerTaskCommand.getCommand());
  }

  /**
   * Test {@link AsyncProfilerTaskCommand#AsyncProfilerTaskCommand(String, String, int, List, String, long)}.
   * <p>
   * Method under test: {@link AsyncProfilerTaskCommand#AsyncProfilerTaskCommand(String, String, int, List, String, long)}
   */
  @Test
  @DisplayName("Test new AsyncProfilerTaskCommand(String, String, int, List, String, long)")
  @Tag("MaintainedByDiffblue")
  void testNewAsyncProfilerTaskCommand2() {
    // Arrange
    ArrayList<String> events = new ArrayList<>();
    events.add(AsyncProfilerTaskCommand.NAME);

    // Act
    AsyncProfilerTaskCommand actualAsyncProfilerTaskCommand = new AsyncProfilerTaskCommand("42", "42", 1, events,
        "Exec Args", 1L);

    // Assert
    assertEquals("event=AsyncProfilerTaskQuery,Exec Args", actualAsyncProfilerTaskCommand.getExecArgs());
    Builder commandBuilderResult = actualAsyncProfilerTaskCommand.commandBuilder();
    assertEquals(1, commandBuilderResult.getArgsBuilderList().size());
    Descriptor descriptorForType = commandBuilderResult.getDescriptorForType();
    assertEquals(2, descriptorForType.getFields().size());
    assertEquals(2, descriptorForType.getFile().getMessageTypes().size());
  }

  /**
   * Test {@link AsyncProfilerTaskCommand#AsyncProfilerTaskCommand(String, String, int, List, String, long)}.
   * <p>
   * Method under test: {@link AsyncProfilerTaskCommand#AsyncProfilerTaskCommand(String, String, int, List, String, long)}
   */
  @Test
  @DisplayName("Test new AsyncProfilerTaskCommand(String, String, int, List, String, long)")
  @Tag("MaintainedByDiffblue")
  void testNewAsyncProfilerTaskCommand3() {
    // Arrange
    ArrayList<String> events = new ArrayList<>();
    events.add(ConfigurationDiscoveryCommand.SERIAL_NUMBER_CONST_NAME);
    events.add(AsyncProfilerTaskCommand.NAME);

    // Act
    AsyncProfilerTaskCommand actualAsyncProfilerTaskCommand = new AsyncProfilerTaskCommand("42", "42", 1, events,
        "Exec Args", 1L);

    // Assert
    assertEquals("event=SerialNumber,AsyncProfilerTaskQuery,Exec Args", actualAsyncProfilerTaskCommand.getExecArgs());
    Builder commandBuilderResult = actualAsyncProfilerTaskCommand.commandBuilder();
    assertEquals(1, commandBuilderResult.getArgsBuilderList().size());
    Descriptor descriptorForType = commandBuilderResult.getDescriptorForType();
    assertEquals(2, descriptorForType.getFields().size());
    assertEquals(2, descriptorForType.getFile().getMessageTypes().size());
  }

  /**
   * Test {@link AsyncProfilerTaskCommand#AsyncProfilerTaskCommand(String, String, int, List, String, long)}.
   * <ul>
   *   <li>When empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link AsyncProfilerTaskCommand#AsyncProfilerTaskCommand(String, String, int, List, String, long)}
   */
  @Test
  @DisplayName("Test new AsyncProfilerTaskCommand(String, String, int, List, String, long); when empty string")
  @Tag("MaintainedByDiffblue")
  void testNewAsyncProfilerTaskCommand_whenEmptyString() {
    // Arrange, Act and Assert
    Builder commandBuilderResult = (new AsyncProfilerTaskCommand("42", "42", 1, new ArrayList<>(), "", 1L))
        .commandBuilder();
    assertEquals(1, commandBuilderResult.getArgsOrBuilderList().size());
    UnknownFieldSet unknownFields = commandBuilderResult.getUnknownFields();
    assertSame(unknownFields, unknownFields.getDefaultInstanceForType());
    Command defaultInstanceForType = commandBuilderResult.getDefaultInstanceForType();
    assertSame(unknownFields, defaultInstanceForType.getUnknownFields());
    assertSame(defaultInstanceForType, defaultInstanceForType.getDefaultInstanceForType());
  }

  /**
   * Test {@link AsyncProfilerTaskCommand#AsyncProfilerTaskCommand(String, String, int, List, String, long)}.
   * <ul>
   *   <li>When {@code Exec Args}.</li>
   *   <li>Then return {@code ,Exec Args}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AsyncProfilerTaskCommand#AsyncProfilerTaskCommand(String, String, int, List, String, long)}
   */
  @Test
  @DisplayName("Test new AsyncProfilerTaskCommand(String, String, int, List, String, long); when 'Exec Args'; then return ',Exec Args'")
  @Tag("MaintainedByDiffblue")
  void testNewAsyncProfilerTaskCommand_whenExecArgs_thenReturnExecArgs() {
    // Arrange and Act
    AsyncProfilerTaskCommand actualAsyncProfilerTaskCommand = new AsyncProfilerTaskCommand("42", "42", 1,
        new ArrayList<>(), "Exec Args", 1L);

    // Assert
    assertEquals(",Exec Args", actualAsyncProfilerTaskCommand.getExecArgs());
    Builder commandBuilderResult = actualAsyncProfilerTaskCommand.commandBuilder();
    assertEquals(1, commandBuilderResult.getArgsOrBuilderList().size());
    UnknownFieldSet unknownFields = commandBuilderResult.getUnknownFields();
    assertSame(unknownFields, unknownFields.getDefaultInstanceForType());
    Command defaultInstanceForType = commandBuilderResult.getDefaultInstanceForType();
    assertSame(unknownFields, defaultInstanceForType.getUnknownFields());
    assertSame(defaultInstanceForType, defaultInstanceForType.getDefaultInstanceForType());
  }

  /**
   * Test {@link AsyncProfilerTaskCommand#AsyncProfilerTaskCommand(String, String, int, List, String, long)}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AsyncProfilerTaskCommand#AsyncProfilerTaskCommand(String, String, int, List, String, long)}
   */
  @Test
  @DisplayName("Test new AsyncProfilerTaskCommand(String, String, int, List, String, long); when 'null'")
  @Tag("MaintainedByDiffblue")
  void testNewAsyncProfilerTaskCommand_whenNull() {
    // Arrange, Act and Assert
    Builder commandBuilderResult = (new AsyncProfilerTaskCommand("42", "42", 1, new ArrayList<>(), null, 1L))
        .commandBuilder();
    assertEquals(1, commandBuilderResult.getArgsOrBuilderList().size());
    UnknownFieldSet unknownFields = commandBuilderResult.getUnknownFields();
    assertSame(unknownFields, unknownFields.getDefaultInstanceForType());
    Command defaultInstanceForType = commandBuilderResult.getDefaultInstanceForType();
    assertSame(unknownFields, defaultInstanceForType.getUnknownFields());
    assertSame(defaultInstanceForType, defaultInstanceForType.getDefaultInstanceForType());
  }

  /**
   * Test {@link AsyncProfilerTaskCommand#serialize()}.
   * <ul>
   *   <li>Then return InitializationErrorString is empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link AsyncProfilerTaskCommand#serialize()}
   */
  @Test
  @DisplayName("Test serialize(); then return InitializationErrorString is empty string")
  @Tag("MaintainedByDiffblue")
  void testSerialize_thenReturnInitializationErrorStringIsEmptyString() {
    // Arrange and Act
    Builder actualSerializeResult = (new AsyncProfilerTaskCommand("42", "42", 1, "Exec Args", 1L)).serialize();

    // Assert
    assertEquals("", actualSerializeResult.getInitializationErrorString());
    assertEquals(2, actualSerializeResult.getAllFields().size());
    assertEquals(5, actualSerializeResult.getArgsBuilderList().size());
    assertEquals(5, actualSerializeResult.getArgsList().size());
    assertEquals(5, actualSerializeResult.getArgsOrBuilderList().size());
    assertEquals(5, actualSerializeResult.getArgsCount());
    assertTrue(actualSerializeResult.findInitializationErrors().isEmpty());
    assertTrue(actualSerializeResult.isInitialized());
    assertEquals(AsyncProfilerTaskCommand.NAME, actualSerializeResult.getCommand());
  }
}
