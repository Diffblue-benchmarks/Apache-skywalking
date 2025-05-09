package org.apache.skywalking.oap.server.network.trace.component.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.google.protobuf.Descriptors;
import com.google.protobuf.Descriptors.FileDescriptor;
import java.util.ArrayList;
import java.util.List;
import org.apache.skywalking.apm.network.common.v3.Command;
import org.apache.skywalking.apm.network.common.v3.Command.Builder;
import org.apache.skywalking.apm.network.common.v3.KeyStringValuePair;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ConfigurationDiscoveryCommandDiffblueTest {
  /**
   * Test {@link ConfigurationDiscoveryCommand#ConfigurationDiscoveryCommand(String, String, List)}.
   * <p>
   * Method under test: {@link ConfigurationDiscoveryCommand#ConfigurationDiscoveryCommand(String, String, List)}
   */
  @Test
  @DisplayName("Test new ConfigurationDiscoveryCommand(String, String, List)")
  @Tag("MaintainedByDiffblue")
  void testNewConfigurationDiscoveryCommand() {
    // Arrange and Act
    ConfigurationDiscoveryCommand actualConfigurationDiscoveryCommand = new ConfigurationDiscoveryCommand("42",
        "01234567-89AB-CDEF-FEDC-BA9876543210", new ArrayList<>());

    // Assert
    Builder commandBuilderResult = actualConfigurationDiscoveryCommand.commandBuilder();
    FileDescriptor file = commandBuilderResult.getDescriptorForType().getFile();
    assertEquals(1, file.getDependencies().size());
    assertEquals(1, commandBuilderResult.getArgsList().size());
    assertEquals(2, file.getMessageTypes().size());
    assertTrue(actualConfigurationDiscoveryCommand.getConfig().isEmpty());
  }

  /**
   * Test {@link ConfigurationDiscoveryCommand#ConfigurationDiscoveryCommand(String, String, List)}.
   * <ul>
   *   <li>Then return Config is {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ConfigurationDiscoveryCommand#ConfigurationDiscoveryCommand(String, String, List)}
   */
  @Test
  @DisplayName("Test new ConfigurationDiscoveryCommand(String, String, List); then return Config is ArrayList()")
  @Tag("MaintainedByDiffblue")
  void testNewConfigurationDiscoveryCommand_thenReturnConfigIsArrayList() {
    // Arrange
    ArrayList<KeyStringValuePair> config = new ArrayList<>();
    config.add(KeyStringValuePair.getDefaultInstance());

    // Act and Assert
    assertSame(config,
        (new ConfigurationDiscoveryCommand("42", "01234567-89AB-CDEF-FEDC-BA9876543210", config)).getConfig());
  }

  /**
   * Test {@link ConfigurationDiscoveryCommand#ConfigurationDiscoveryCommand(String, String, List)}.
   * <ul>
   *   <li>Then return Config is {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ConfigurationDiscoveryCommand#ConfigurationDiscoveryCommand(String, String, List)}
   */
  @Test
  @DisplayName("Test new ConfigurationDiscoveryCommand(String, String, List); then return Config is ArrayList()")
  @Tag("MaintainedByDiffblue")
  void testNewConfigurationDiscoveryCommand_thenReturnConfigIsArrayList2() {
    // Arrange
    ArrayList<KeyStringValuePair> config = new ArrayList<>();
    config.add(KeyStringValuePair.getDefaultInstance());
    config.add(KeyStringValuePair.getDefaultInstance());

    // Act and Assert
    assertSame(config,
        (new ConfigurationDiscoveryCommand("42", "01234567-89AB-CDEF-FEDC-BA9876543210", config)).getConfig());
  }

  /**
   * Test {@link ConfigurationDiscoveryCommand#serialize()}.
   * <ul>
   *   <li>Then return InitializationErrorString is empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link ConfigurationDiscoveryCommand#serialize()}
   */
  @Test
  @DisplayName("Test serialize(); then return InitializationErrorString is empty string")
  @Tag("MaintainedByDiffblue")
  void testSerialize_thenReturnInitializationErrorStringIsEmptyString() {
    // Arrange and Act
    Builder actualSerializeResult = (new ConfigurationDiscoveryCommand("42", "01234567-89AB-CDEF-FEDC-BA9876543210",
        new ArrayList<>())).serialize();

    // Assert
    assertEquals("", actualSerializeResult.getInitializationErrorString());
    assertEquals(2, actualSerializeResult.getArgsBuilderList().size());
    assertEquals(2, actualSerializeResult.getArgsList().size());
    assertEquals(2, actualSerializeResult.getArgsOrBuilderList().size());
    assertEquals(2, actualSerializeResult.getAllFields().size());
    assertEquals(2, actualSerializeResult.getArgsCount());
    assertTrue(actualSerializeResult.findInitializationErrors().isEmpty());
    assertTrue(actualSerializeResult.isInitialized());
    assertEquals(ConfigurationDiscoveryCommand.NAME, actualSerializeResult.getCommand());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ConfigurationDiscoveryCommand#toString()}
   *   <li>{@link ConfigurationDiscoveryCommand#getConfig()}
   *   <li>{@link ConfigurationDiscoveryCommand#getUuid()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  void testGettersAndSetters() {
    // Arrange
    ArrayList<KeyStringValuePair> config = new ArrayList<>();
    ConfigurationDiscoveryCommand configurationDiscoveryCommand = new ConfigurationDiscoveryCommand("42",
        "01234567-89AB-CDEF-FEDC-BA9876543210", config);

    // Act
    String actualToStringResult = configurationDiscoveryCommand.toString();
    List<KeyStringValuePair> actualConfig = configurationDiscoveryCommand.getConfig();

    // Assert
    assertEquals("01234567-89AB-CDEF-FEDC-BA9876543210", configurationDiscoveryCommand.getUuid());
    assertEquals("ConfigurationDiscoveryCommand{uuid='01234567-89AB-CDEF-FEDC-BA9876543210', config=[]}",
        actualToStringResult);
    assertTrue(actualConfig.isEmpty());
    assertSame(config, actualConfig);
  }
}
