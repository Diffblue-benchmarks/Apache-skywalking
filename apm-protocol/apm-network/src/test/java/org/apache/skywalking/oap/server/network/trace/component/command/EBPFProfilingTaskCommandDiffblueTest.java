package org.apache.skywalking.oap.server.network.trace.component.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.google.protobuf.ByteString;
import com.google.protobuf.ByteString.ByteIterator;
import com.google.protobuf.Descriptors;
import com.google.protobuf.Descriptors.Descriptor;
import com.google.protobuf.UnknownFieldSet;
import java.util.ArrayList;
import java.util.List;
import org.apache.skywalking.apm.network.common.v3.Command;
import org.apache.skywalking.apm.network.common.v3.Command.Builder;
import org.apache.skywalking.apm.network.common.v3.KeyStringValuePair;
import org.apache.skywalking.apm.network.common.v3.KeyStringValuePairOrBuilder;
import org.apache.skywalking.oap.server.network.trace.component.command.EBPFProfilingTaskCommand.FixedTrigger;
import org.apache.skywalking.oap.server.network.trace.component.command.EBPFProfilingTaskExtensionConfig.NetworkSamplingRule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class EBPFProfilingTaskCommandDiffblueTest {
  /**
   * Test {@link EBPFProfilingTaskCommand#EBPFProfilingTaskCommand(String, String, List, long, long, String, FixedTrigger, String, EBPFProfilingTaskExtensionConfig)}.
   * <ul>
   *   <li>Given {@link EBPFProfilingTaskCommand#NAME}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@link EBPFProfilingTaskCommand#NAME}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EBPFProfilingTaskCommand#EBPFProfilingTaskCommand(String, String, List, long, long, String, FixedTrigger, String, EBPFProfilingTaskExtensionConfig)}
   */
  @Test
  @DisplayName("Test new EBPFProfilingTaskCommand(String, String, List, long, long, String, FixedTrigger, String, EBPFProfilingTaskExtensionConfig); given NAME; when ArrayList() add NAME")
  @Tag("MaintainedByDiffblue")
  void testNewEBPFProfilingTaskCommand_givenName_whenArrayListAddName() {
    // Arrange
    ArrayList<String> processIdList = new ArrayList<>();
    processIdList.add(EBPFProfilingTaskCommand.NAME);
    FixedTrigger fixedTrigger = new FixedTrigger(1L);

    EBPFProfilingTaskExtensionConfig extensionConfig = new EBPFProfilingTaskExtensionConfig();
    ArrayList<NetworkSamplingRule> networkSamplings = new ArrayList<>();
    extensionConfig.setNetworkSamplings(networkSamplings);

    // Act and Assert
    Builder commandBuilderResult = (new EBPFProfilingTaskCommand("42", "42", processIdList, 1L, 1L, "Trigger Type",
        fixedTrigger, "Target Type", extensionConfig)).commandBuilder();
    assertEquals(1, commandBuilderResult.getArgsBuilderList().size());
    Descriptor descriptorForType = commandBuilderResult.getDescriptorForType();
    assertEquals(2, descriptorForType.getFields().size());
    assertEquals(2, descriptorForType.getFile().getMessageTypes().size());
    assertEquals(networkSamplings, descriptorForType.toProto().getReservedNameList());
  }

  /**
   * Test {@link EBPFProfilingTaskCommand#EBPFProfilingTaskCommand(String, String, List, long, long, String, FixedTrigger, String, EBPFProfilingTaskExtensionConfig)}.
   * <ul>
   *   <li>Given {@link ConfigurationDiscoveryCommand#SERIAL_NUMBER_CONST_NAME}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EBPFProfilingTaskCommand#EBPFProfilingTaskCommand(String, String, List, long, long, String, FixedTrigger, String, EBPFProfilingTaskExtensionConfig)}
   */
  @Test
  @DisplayName("Test new EBPFProfilingTaskCommand(String, String, List, long, long, String, FixedTrigger, String, EBPFProfilingTaskExtensionConfig); given SERIAL_NUMBER_CONST_NAME")
  @Tag("MaintainedByDiffblue")
  void testNewEBPFProfilingTaskCommand_givenSerial_number_const_name() {
    // Arrange
    ArrayList<String> processIdList = new ArrayList<>();
    processIdList.add(ConfigurationDiscoveryCommand.SERIAL_NUMBER_CONST_NAME);
    processIdList.add(EBPFProfilingTaskCommand.NAME);
    FixedTrigger fixedTrigger = new FixedTrigger(1L);

    EBPFProfilingTaskExtensionConfig extensionConfig = new EBPFProfilingTaskExtensionConfig();
    ArrayList<NetworkSamplingRule> networkSamplings = new ArrayList<>();
    extensionConfig.setNetworkSamplings(networkSamplings);

    // Act and Assert
    Builder commandBuilderResult = (new EBPFProfilingTaskCommand("42", "42", processIdList, 1L, 1L, "Trigger Type",
        fixedTrigger, "Target Type", extensionConfig)).commandBuilder();
    assertEquals(1, commandBuilderResult.getArgsBuilderList().size());
    Descriptor descriptorForType = commandBuilderResult.getDescriptorForType();
    assertEquals(2, descriptorForType.getFields().size());
    assertEquals(2, descriptorForType.getFile().getMessageTypes().size());
    assertEquals(networkSamplings, descriptorForType.toProto().getReservedNameList());
  }

  /**
   * Test {@link EBPFProfilingTaskCommand#EBPFProfilingTaskCommand(String, String, List, long, long, String, FixedTrigger, String, EBPFProfilingTaskExtensionConfig)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EBPFProfilingTaskCommand#EBPFProfilingTaskCommand(String, String, List, long, long, String, FixedTrigger, String, EBPFProfilingTaskExtensionConfig)}
   */
  @Test
  @DisplayName("Test new EBPFProfilingTaskCommand(String, String, List, long, long, String, FixedTrigger, String, EBPFProfilingTaskExtensionConfig); when ArrayList()")
  @Tag("MaintainedByDiffblue")
  void testNewEBPFProfilingTaskCommand_whenArrayList() {
    // Arrange
    ArrayList<String> processIdList = new ArrayList<>();
    FixedTrigger fixedTrigger = new FixedTrigger(1L);

    EBPFProfilingTaskExtensionConfig extensionConfig = new EBPFProfilingTaskExtensionConfig();
    extensionConfig.setNetworkSamplings(new ArrayList<>());

    // Act and Assert
    Builder commandBuilderResult = (new EBPFProfilingTaskCommand("42", "42", processIdList, 1L, 1L, "Trigger Type",
        fixedTrigger, "Target Type", extensionConfig)).commandBuilder();
    assertEquals(1, commandBuilderResult.getArgsBuilderList().size());
    Descriptor descriptorForType = commandBuilderResult.getDescriptorForType();
    assertEquals(2, descriptorForType.getFields().size());
    assertEquals(2, descriptorForType.getFile().getMessageTypes().size());
    assertEquals(processIdList, descriptorForType.toProto().getReservedNameList());
  }

  /**
   * Test {@link EBPFProfilingTaskCommand#serialize()}.
   * <p>
   * Method under test: {@link EBPFProfilingTaskCommand#serialize()}
   */
  @Test
  @DisplayName("Test serialize()")
  @Tag("MaintainedByDiffblue")
  void testSerialize() {
    // Arrange
    ArrayList<String> processIdList = new ArrayList<>();
    processIdList.add("ProcessId");
    processIdList.add("TaskId");
    FixedTrigger fixedTrigger = new FixedTrigger(1L);

    // Act
    Builder actualSerializeResult = (new EBPFProfilingTaskCommand("42", "42", processIdList, 1L, 1L, "Trigger Type",
        fixedTrigger, "Target Type", new EBPFProfilingTaskExtensionConfig())).serialize();

    // Assert
    List<KeyStringValuePair.Builder> argsBuilderList = actualSerializeResult.getArgsBuilderList();
    assertEquals(9, argsBuilderList.size());
    KeyStringValuePair.Builder getResult = argsBuilderList.get(2);
    ByteString valueBytes = getResult.getValueBytes();
    assertEquals("ProcessId,TaskId", valueBytes.toStringUtf8());
    List<KeyStringValuePair> argsList = actualSerializeResult.getArgsList();
    assertEquals(9, argsList.size());
    KeyStringValuePair getResult2 = argsList.get(2);
    assertEquals("ProcessId,TaskId", getResult2.getValue());
    assertEquals("ProcessId,TaskId", getResult.getValue());
    assertEquals(29, getResult2.getSerializedSize());
    assertFalse(valueBytes.isEmpty());
    ByteIterator iteratorResult = valueBytes.iterator();
    assertTrue(iteratorResult.hasNext());
    assertEquals(valueBytes, getResult2.getValueBytes());
    assertEquals('P', iteratorResult.next().byteValue());
    assertEquals('r', iteratorResult.next().byteValue());
  }

  /**
   * Test {@link EBPFProfilingTaskCommand#serialize()}.
   * <p>
   * Method under test: {@link EBPFProfilingTaskCommand#serialize()}
   */
  @Test
  @DisplayName("Test serialize()")
  @Tag("MaintainedByDiffblue")
  void testSerialize2() {
    // Arrange
    ArrayList<String> processIdList = new ArrayList<>();

    // Act
    Builder actualSerializeResult = (new EBPFProfilingTaskCommand("42", "42", processIdList, 1L, 1L, "Trigger Type",
        new FixedTrigger(1L), "Target Type", null)).serialize();

    // Assert
    assertEquals(9, actualSerializeResult.getArgsOrBuilderList().size());
    UnknownFieldSet unknownFields = actualSerializeResult.getUnknownFields();
    assertSame(unknownFields, unknownFields.getDefaultInstanceForType());
    Command defaultInstanceForType = actualSerializeResult.getDefaultInstanceForType();
    assertSame(unknownFields, defaultInstanceForType.getUnknownFields());
    assertSame(defaultInstanceForType, defaultInstanceForType.getDefaultInstanceForType());
  }

  /**
   * Test {@link EBPFProfilingTaskCommand#serialize()}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@code TaskId}.</li>
   *   <li>Then return ArgsList third Value is {@code TaskId}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EBPFProfilingTaskCommand#serialize()}
   */
  @Test
  @DisplayName("Test serialize(); given ArrayList() add 'TaskId'; then return ArgsList third Value is 'TaskId'")
  @Tag("MaintainedByDiffblue")
  void testSerialize_givenArrayListAddTaskId_thenReturnArgsListThirdValueIsTaskId() {
    // Arrange
    ArrayList<String> processIdList = new ArrayList<>();
    processIdList.add("TaskId");
    FixedTrigger fixedTrigger = new FixedTrigger(1L);

    // Act
    Builder actualSerializeResult = (new EBPFProfilingTaskCommand("42", "42", processIdList, 1L, 1L, "Trigger Type",
        fixedTrigger, "Target Type", new EBPFProfilingTaskExtensionConfig())).serialize();

    // Assert
    List<KeyStringValuePair> argsList = actualSerializeResult.getArgsList();
    assertEquals(9, argsList.size());
    KeyStringValuePair getResult = argsList.get(2);
    assertEquals("TaskId", getResult.getValue());
    List<KeyStringValuePair.Builder> argsBuilderList = actualSerializeResult.getArgsBuilderList();
    assertEquals(9, argsBuilderList.size());
    assertEquals("TaskId", argsBuilderList.get(2).getValue());
    assertEquals(19, getResult.getSerializedSize());
  }

  /**
   * Test {@link EBPFProfilingTaskCommand#serialize()}.
   * <ul>
   *   <li>Then return ArgsBuilderList size is eight.</li>
   * </ul>
   * <p>
   * Method under test: {@link EBPFProfilingTaskCommand#serialize()}
   */
  @Test
  @DisplayName("Test serialize(); then return ArgsBuilderList size is eight")
  @Tag("MaintainedByDiffblue")
  void testSerialize_thenReturnArgsBuilderListSizeIsEight() {
    // Arrange
    ArrayList<String> processIdList = new ArrayList<>();

    // Act
    Builder actualSerializeResult = (new EBPFProfilingTaskCommand("42", "42", processIdList, 1L, 1L, "Trigger Type",
        null, "Target Type", new EBPFProfilingTaskExtensionConfig())).serialize();

    // Assert
    List<KeyStringValuePair.Builder> argsBuilderList = actualSerializeResult.getArgsBuilderList();
    assertEquals(8, argsBuilderList.size());
    assertEquals(8, actualSerializeResult.getArgsList().size());
    List<? extends KeyStringValuePairOrBuilder> argsOrBuilderList = actualSerializeResult.getArgsOrBuilderList();
    assertEquals(8, argsOrBuilderList.size());
    assertEquals(8, actualSerializeResult.getArgsCount());
    UnknownFieldSet unknownFields = actualSerializeResult.getUnknownFields();
    assertSame(unknownFields, unknownFields.getDefaultInstanceForType());
    Command defaultInstanceForType = actualSerializeResult.getDefaultInstanceForType();
    assertSame(unknownFields, defaultInstanceForType.getUnknownFields());
    assertSame(argsBuilderList.get(0), argsOrBuilderList.get(0));
    assertSame(argsBuilderList.get(1), argsOrBuilderList.get(1));
    assertSame(argsBuilderList.get(2), argsOrBuilderList.get(2));
    assertSame(argsBuilderList.get(5), argsOrBuilderList.get(5));
    assertSame(argsBuilderList.get(6), argsOrBuilderList.get(6));
    assertSame(argsBuilderList.get(7), argsOrBuilderList.get(7));
    assertSame(defaultInstanceForType, defaultInstanceForType.getDefaultInstanceForType());
  }

  /**
   * Test {@link EBPFProfilingTaskCommand#serialize()}.
   * <ul>
   *   <li>Then return ArgsOrBuilderList size is nine.</li>
   * </ul>
   * <p>
   * Method under test: {@link EBPFProfilingTaskCommand#serialize()}
   */
  @Test
  @DisplayName("Test serialize(); then return ArgsOrBuilderList size is nine")
  @Tag("MaintainedByDiffblue")
  void testSerialize_thenReturnArgsOrBuilderListSizeIsNine() {
    // Arrange
    ArrayList<String> processIdList = new ArrayList<>();
    FixedTrigger fixedTrigger = new FixedTrigger(1L);

    // Act
    Builder actualSerializeResult = (new EBPFProfilingTaskCommand("42", "42", processIdList, 1L, 1L, "Trigger Type",
        fixedTrigger, "Target Type", new EBPFProfilingTaskExtensionConfig())).serialize();

    // Assert
    assertEquals(9, actualSerializeResult.getArgsOrBuilderList().size());
    UnknownFieldSet unknownFields = actualSerializeResult.getUnknownFields();
    assertSame(unknownFields, unknownFields.getDefaultInstanceForType());
    Command defaultInstanceForType = actualSerializeResult.getDefaultInstanceForType();
    assertSame(unknownFields, defaultInstanceForType.getUnknownFields());
    assertSame(defaultInstanceForType, defaultInstanceForType.getDefaultInstanceForType());
  }
}
