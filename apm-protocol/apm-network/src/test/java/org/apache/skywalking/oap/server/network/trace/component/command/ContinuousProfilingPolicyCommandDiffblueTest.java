package org.apache.skywalking.oap.server.network.trace.component.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import com.google.protobuf.Descriptors;
import com.google.protobuf.Descriptors.Descriptor;
import com.google.protobuf.UnknownFieldSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.skywalking.apm.network.common.v3.Command;
import org.apache.skywalking.apm.network.common.v3.Command.Builder;
import org.apache.skywalking.apm.network.common.v3.KeyStringValuePair;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ContinuousProfilingPolicyCommandDiffblueTest {
  /**
   * Test {@link ContinuousProfilingPolicyCommand#ContinuousProfilingPolicyCommand(String, List)}.
   * <p>
   * Method under test: {@link ContinuousProfilingPolicyCommand#ContinuousProfilingPolicyCommand(String, List)}
   */
  @Test
  @DisplayName("Test new ContinuousProfilingPolicyCommand(String, List)")
  @Tag("MaintainedByDiffblue")
  void testNewContinuousProfilingPolicyCommand() {
    // Arrange
    ArrayList<ContinuousProfilingPolicy> policies = new ArrayList<>();

    // Act and Assert
    Builder commandBuilderResult = (new ContinuousProfilingPolicyCommand("42", policies)).commandBuilder();
    assertEquals(1, commandBuilderResult.getArgsBuilderList().size());
    Descriptor descriptorForType = commandBuilderResult.getDescriptorForType();
    assertEquals(2, descriptorForType.getFields().size());
    assertEquals(2, descriptorForType.getFile().getMessageTypes().size());
    assertEquals(policies, descriptorForType.toProto().getReservedNameList());
  }

  /**
   * Test {@link ContinuousProfilingPolicyCommand#ContinuousProfilingPolicyCommand(String, List)}.
   * <p>
   * Method under test: {@link ContinuousProfilingPolicyCommand#ContinuousProfilingPolicyCommand(String, List)}
   */
  @Test
  @DisplayName("Test new ContinuousProfilingPolicyCommand(String, List)")
  @Tag("MaintainedByDiffblue")
  void testNewContinuousProfilingPolicyCommand2() {
    // Arrange
    ContinuousProfilingPolicy continuousProfilingPolicy = new ContinuousProfilingPolicy();
    continuousProfilingPolicy.setProfiling(new HashMap<>());
    continuousProfilingPolicy.setServiceName(ContinuousProfilingPolicyCommand.NAME);
    continuousProfilingPolicy.setUuid("01234567-89AB-CDEF-FEDC-BA9876543210");

    ArrayList<ContinuousProfilingPolicy> policies = new ArrayList<>();
    policies.add(continuousProfilingPolicy);

    // Act and Assert
    Builder commandBuilderResult = (new ContinuousProfilingPolicyCommand("42", policies)).commandBuilder();
    assertEquals(1, commandBuilderResult.getArgsBuilderList().size());
    Descriptor descriptorForType = commandBuilderResult.getDescriptorForType();
    assertEquals(2, descriptorForType.getFields().size());
    assertEquals(2, descriptorForType.getFile().getMessageTypes().size());
  }

  /**
   * Test {@link ContinuousProfilingPolicyCommand#ContinuousProfilingPolicyCommand(String, List)}.
   * <p>
   * Method under test: {@link ContinuousProfilingPolicyCommand#ContinuousProfilingPolicyCommand(String, List)}
   */
  @Test
  @DisplayName("Test new ContinuousProfilingPolicyCommand(String, List)")
  @Tag("MaintainedByDiffblue")
  void testNewContinuousProfilingPolicyCommand3() {
    // Arrange
    ContinuousProfilingPolicy continuousProfilingPolicy = new ContinuousProfilingPolicy();
    continuousProfilingPolicy.setProfiling(new HashMap<>());
    continuousProfilingPolicy.setServiceName(ContinuousProfilingPolicyCommand.NAME);
    continuousProfilingPolicy.setUuid("01234567-89AB-CDEF-FEDC-BA9876543210");

    ContinuousProfilingPolicy continuousProfilingPolicy2 = new ContinuousProfilingPolicy();
    continuousProfilingPolicy2.setProfiling(new HashMap<>());
    continuousProfilingPolicy2.setServiceName(ConfigurationDiscoveryCommand.SERIAL_NUMBER_CONST_NAME);
    continuousProfilingPolicy2.setUuid("1234");

    ArrayList<ContinuousProfilingPolicy> policies = new ArrayList<>();
    policies.add(continuousProfilingPolicy2);
    policies.add(continuousProfilingPolicy);

    // Act and Assert
    Builder commandBuilderResult = (new ContinuousProfilingPolicyCommand("42", policies)).commandBuilder();
    assertEquals(1, commandBuilderResult.getArgsBuilderList().size());
    Descriptor descriptorForType = commandBuilderResult.getDescriptorForType();
    assertEquals(2, descriptorForType.getFields().size());
    assertEquals(2, descriptorForType.getFile().getMessageTypes().size());
  }

  /**
   * Test {@link ContinuousProfilingPolicyCommand#serialize()}.
   * <p>
   * Method under test: {@link ContinuousProfilingPolicyCommand#serialize()}
   */
  @Test
  @DisplayName("Test serialize()")
  @Tag("MaintainedByDiffblue")
  void testSerialize() {
    // Arrange and Act
    Builder actualSerializeResult = (new ContinuousProfilingPolicyCommand("42", null)).serialize();

    // Assert
    assertEquals(2, actualSerializeResult.getArgsOrBuilderList().size());
    UnknownFieldSet unknownFields = actualSerializeResult.getUnknownFields();
    assertSame(unknownFields, unknownFields.getDefaultInstanceForType());
    Command defaultInstanceForType = actualSerializeResult.getDefaultInstanceForType();
    assertSame(unknownFields, defaultInstanceForType.getUnknownFields());
    assertSame(defaultInstanceForType, defaultInstanceForType.getDefaultInstanceForType());
  }

  /**
   * Test {@link ContinuousProfilingPolicyCommand#serialize()}.
   * <p>
   * Method under test: {@link ContinuousProfilingPolicyCommand#serialize()}
   */
  @Test
  @DisplayName("Test serialize()")
  @Tag("MaintainedByDiffblue")
  void testSerialize2() {
    // Arrange and Act
    Builder actualSerializeResult = (new ContinuousProfilingPolicyCommand("ServiceWithPolicyJSON", new ArrayList<>()))
        .serialize();

    // Assert
    assertEquals(2, actualSerializeResult.getArgsOrBuilderList().size());
    UnknownFieldSet unknownFields = actualSerializeResult.getUnknownFields();
    assertSame(unknownFields, unknownFields.getDefaultInstanceForType());
    Command defaultInstanceForType = actualSerializeResult.getDefaultInstanceForType();
    assertSame(unknownFields, defaultInstanceForType.getUnknownFields());
    assertSame(defaultInstanceForType, defaultInstanceForType.getDefaultInstanceForType());
  }

  /**
   * Test {@link ContinuousProfilingPolicyCommand#serialize()}.
   * <p>
   * Method under test: {@link ContinuousProfilingPolicyCommand#serialize()}
   */
  @Test
  @DisplayName("Test serialize()")
  @Tag("MaintainedByDiffblue")
  void testSerialize3() {
    // Arrange
    ContinuousProfilingPolicy continuousProfilingPolicy = new ContinuousProfilingPolicy();
    continuousProfilingPolicy.setProfiling(new HashMap<>());
    continuousProfilingPolicy.setServiceName("");
    continuousProfilingPolicy.setUuid("01234567-89AB-CDEF-FEDC-BA9876543210");

    ArrayList<ContinuousProfilingPolicy> policies = new ArrayList<>();
    policies.add(continuousProfilingPolicy);

    // Act
    Builder actualSerializeResult = (new ContinuousProfilingPolicyCommand("42", policies)).serialize();

    // Assert
    List<KeyStringValuePair.Builder> argsBuilderList = actualSerializeResult.getArgsBuilderList();
    assertEquals(2, argsBuilderList.size());
    KeyStringValuePair.Builder getResult = argsBuilderList.get(1);
    assertEquals("[{\"ServiceName\":\"\",\"UUID\":\"01234567-89AB-CDEF-FEDC-BA9876543210\",\"Profiling\":{}}]",
        getResult.getValueBytes().toStringUtf8());
    List<KeyStringValuePair> argsList = actualSerializeResult.getArgsList();
    assertEquals(2, argsList.size());
    KeyStringValuePair getResult2 = argsList.get(1);
    assertEquals("[{\"ServiceName\":\"\",\"UUID\":\"01234567-89AB-CDEF-FEDC-BA9876543210\",\"Profiling\":{}}]",
        getResult2.getValue());
    assertEquals("[{\"ServiceName\":\"\",\"UUID\":\"01234567-89AB-CDEF-FEDC-BA9876543210\",\"Profiling\":{}}]",
        getResult.getValue());
    assertEquals(106, getResult2.getSerializedSize());
  }

  /**
   * Test {@link ContinuousProfilingPolicyCommand#serialize()}.
   * <ul>
   *   <li>Then return ArgsList second SerializedSize is {@link Float#MAX_EXPONENT}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ContinuousProfilingPolicyCommand#serialize()}
   */
  @Test
  @DisplayName("Test serialize(); then return ArgsList second SerializedSize is MAX_EXPONENT")
  @Tag("MaintainedByDiffblue")
  void testSerialize_thenReturnArgsListSecondSerializedSizeIsMax_exponent() {
    // Arrange
    ContinuousProfilingPolicy continuousProfilingPolicy = new ContinuousProfilingPolicy();
    continuousProfilingPolicy.setProfiling(new HashMap<>());
    continuousProfilingPolicy.setServiceName("ServiceWithPolicyJSON");
    continuousProfilingPolicy.setUuid("01234567-89AB-CDEF-FEDC-BA9876543210");

    ArrayList<ContinuousProfilingPolicy> policies = new ArrayList<>();
    policies.add(continuousProfilingPolicy);

    // Act
    Builder actualSerializeResult = (new ContinuousProfilingPolicyCommand("42", policies)).serialize();

    // Assert
    List<KeyStringValuePair.Builder> argsBuilderList = actualSerializeResult.getArgsBuilderList();
    assertEquals(2, argsBuilderList.size());
    KeyStringValuePair.Builder getResult = argsBuilderList.get(1);
    assertEquals(
        "[{\"ServiceName\":\"ServiceWithPolicyJSON\",\"UUID\":\"01234567-89AB-CDEF-FEDC-BA9876543210\",\"Profiling"
            + "\":{}}]",
        getResult.getValueBytes().toStringUtf8());
    List<KeyStringValuePair> argsList = actualSerializeResult.getArgsList();
    assertEquals(2, argsList.size());
    KeyStringValuePair getResult2 = argsList.get(1);
    assertEquals(
        "[{\"ServiceName\":\"ServiceWithPolicyJSON\",\"UUID\":\"01234567-89AB-CDEF-FEDC-BA9876543210\",\"Profiling"
            + "\":{}}]",
        getResult2.getValue());
    assertEquals(
        "[{\"ServiceName\":\"ServiceWithPolicyJSON\",\"UUID\":\"01234567-89AB-CDEF-FEDC-BA9876543210\",\"Profiling"
            + "\":{}}]",
        getResult.getValue());
    assertEquals(Float.MAX_EXPONENT, getResult2.getSerializedSize());
  }

  /**
   * Test {@link ContinuousProfilingPolicyCommand#serialize()}.
   * <ul>
   *   <li>Then return ArgsList second SerializedSize is one hundred eighty-eight.</li>
   * </ul>
   * <p>
   * Method under test: {@link ContinuousProfilingPolicyCommand#serialize()}
   */
  @Test
  @DisplayName("Test serialize(); then return ArgsList second SerializedSize is one hundred eighty-eight")
  @Tag("MaintainedByDiffblue")
  void testSerialize_thenReturnArgsListSecondSerializedSizeIsOneHundredEightyEight() {
    // Arrange
    ContinuousProfilingPolicy continuousProfilingPolicy = new ContinuousProfilingPolicy();
    continuousProfilingPolicy.setProfiling(new HashMap<>());
    continuousProfilingPolicy.setServiceName("ServiceWithPolicyJSON");
    continuousProfilingPolicy.setUuid("01234567-89AB-CDEF-FEDC-BA9876543210");

    ContinuousProfilingPolicy continuousProfilingPolicy2 = new ContinuousProfilingPolicy();
    continuousProfilingPolicy2.setProfiling(new HashMap<>());
    continuousProfilingPolicy2.setServiceName("Service Name");
    continuousProfilingPolicy2.setUuid("1234");

    ArrayList<ContinuousProfilingPolicy> policies = new ArrayList<>();
    policies.add(continuousProfilingPolicy2);
    policies.add(continuousProfilingPolicy);

    // Act
    Builder actualSerializeResult = (new ContinuousProfilingPolicyCommand("42", policies)).serialize();

    // Assert
    List<KeyStringValuePair.Builder> argsBuilderList = actualSerializeResult.getArgsBuilderList();
    assertEquals(2, argsBuilderList.size());
    KeyStringValuePair.Builder getResult = argsBuilderList.get(1);
    assertEquals(
        "[{\"ServiceName\":\"Service Name\",\"UUID\":\"1234\",\"Profiling\":{}},{\"ServiceName\":\"ServiceWithPolicyJSON\","
            + "\"UUID\":\"01234567-89AB-CDEF-FEDC-BA9876543210\",\"Profiling\":{}}]",
        getResult.getValueBytes().toStringUtf8());
    List<KeyStringValuePair> argsList = actualSerializeResult.getArgsList();
    assertEquals(2, argsList.size());
    KeyStringValuePair getResult2 = argsList.get(1);
    assertEquals(
        "[{\"ServiceName\":\"Service Name\",\"UUID\":\"1234\",\"Profiling\":{}},{\"ServiceName\":\"ServiceWithPolicyJSON\","
            + "\"UUID\":\"01234567-89AB-CDEF-FEDC-BA9876543210\",\"Profiling\":{}}]",
        getResult2.getValue());
    assertEquals(
        "[{\"ServiceName\":\"Service Name\",\"UUID\":\"1234\",\"Profiling\":{}},{\"ServiceName\":\"ServiceWithPolicyJSON\","
            + "\"UUID\":\"01234567-89AB-CDEF-FEDC-BA9876543210\",\"Profiling\":{}}]",
        getResult.getValue());
    assertEquals(188, getResult2.getSerializedSize());
  }

  /**
   * Test {@link ContinuousProfilingPolicyCommand#serialize()}.
   * <ul>
   *   <li>Then return DescriptorForType Fields size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link ContinuousProfilingPolicyCommand#serialize()}
   */
  @Test
  @DisplayName("Test serialize(); then return DescriptorForType Fields size is two")
  @Tag("MaintainedByDiffblue")
  void testSerialize_thenReturnDescriptorForTypeFieldsSizeIsTwo() {
    // Arrange, Act and Assert
    assertEquals(2,
        (new ContinuousProfilingPolicyCommand("42", new ArrayList<>())).serialize()
            .getDescriptorForType()
            .getFields()
            .size());
  }
}
