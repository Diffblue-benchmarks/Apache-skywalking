package org.apache.skywalking.oap.server.network.trace.component.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.apache.skywalking.apm.network.common.v3.Command;
import org.apache.skywalking.apm.network.common.v3.Command.Builder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ProfileTaskCommandDiffblueTest {
  /**
   * Test {@link ProfileTaskCommand#ProfileTaskCommand(String, String, String, int, int, int, int, long, long)}.
   * <ul>
   *   <li>Then return SerialNumber is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProfileTaskCommand#ProfileTaskCommand(String, String, String, int, int, int, int, long, long)}
   */
  @Test
  @DisplayName("Test new ProfileTaskCommand(String, String, String, int, int, int, int, long, long); then return SerialNumber is '42'")
  @Tag("MaintainedByDiffblue")
  void testNewProfileTaskCommand_thenReturnSerialNumberIs42() {
    // Arrange and Act
    ProfileTaskCommand actualProfileTaskCommand = new ProfileTaskCommand("42", "42",
        "https://config.us-east-2.amazonaws.com", 1, 1, 1, 3, 1L, 1L);

    // Assert
    assertEquals("42", actualProfileTaskCommand.getSerialNumber());
    assertEquals("42", actualProfileTaskCommand.getTaskId());
    assertEquals("https://config.us-east-2.amazonaws.com", actualProfileTaskCommand.getEndpointName());
    assertEquals(1, actualProfileTaskCommand.getDumpPeriod());
    assertEquals(1, actualProfileTaskCommand.getDuration());
    assertEquals(1, actualProfileTaskCommand.getMinDurationThreshold());
    assertEquals(1L, actualProfileTaskCommand.getCreateTime());
    assertEquals(1L, actualProfileTaskCommand.getStartTime());
    assertEquals(3, actualProfileTaskCommand.getMaxSamplingCount());
    assertEquals(ProfileTaskCommand.NAME, actualProfileTaskCommand.getCommand());
  }

  /**
   * Test {@link ProfileTaskCommand#serialize()}.
   * <ul>
   *   <li>Then return InitializationErrorString is empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProfileTaskCommand#serialize()}
   */
  @Test
  @DisplayName("Test serialize(); then return InitializationErrorString is empty string")
  @Tag("MaintainedByDiffblue")
  void testSerialize_thenReturnInitializationErrorStringIsEmptyString() {
    // Arrange and Act
    Builder actualSerializeResult = (new ProfileTaskCommand("42", "42", "https://config.us-east-2.amazonaws.com", 1, 1,
        1, 3, 1L, 1L)).serialize();

    // Assert
    assertEquals("", actualSerializeResult.getInitializationErrorString());
    assertEquals(2, actualSerializeResult.getAllFields().size());
    assertEquals(9, actualSerializeResult.getArgsBuilderList().size());
    assertEquals(9, actualSerializeResult.getArgsList().size());
    assertEquals(9, actualSerializeResult.getArgsOrBuilderList().size());
    assertEquals(9, actualSerializeResult.getArgsCount());
    assertTrue(actualSerializeResult.findInitializationErrors().isEmpty());
    assertTrue(actualSerializeResult.isInitialized());
    assertEquals(ProfileTaskCommand.NAME, actualSerializeResult.getCommand());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ProfileTaskCommand#getCreateTime()}
   *   <li>{@link ProfileTaskCommand#getDumpPeriod()}
   *   <li>{@link ProfileTaskCommand#getDuration()}
   *   <li>{@link ProfileTaskCommand#getEndpointName()}
   *   <li>{@link ProfileTaskCommand#getMaxSamplingCount()}
   *   <li>{@link ProfileTaskCommand#getMinDurationThreshold()}
   *   <li>{@link ProfileTaskCommand#getStartTime()}
   *   <li>{@link ProfileTaskCommand#getTaskId()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  void testGettersAndSetters() {
    // Arrange
    ProfileTaskCommand profileTaskCommand = new ProfileTaskCommand("42", "42", "https://config.us-east-2.amazonaws.com",
        1, 1, 1, 3, 1L, 1L);

    // Act
    long actualCreateTime = profileTaskCommand.getCreateTime();
    int actualDumpPeriod = profileTaskCommand.getDumpPeriod();
    int actualDuration = profileTaskCommand.getDuration();
    String actualEndpointName = profileTaskCommand.getEndpointName();
    int actualMaxSamplingCount = profileTaskCommand.getMaxSamplingCount();
    int actualMinDurationThreshold = profileTaskCommand.getMinDurationThreshold();
    long actualStartTime = profileTaskCommand.getStartTime();

    // Assert
    assertEquals("42", profileTaskCommand.getTaskId());
    assertEquals("https://config.us-east-2.amazonaws.com", actualEndpointName);
    assertEquals(1, actualDumpPeriod);
    assertEquals(1, actualDuration);
    assertEquals(1, actualMinDurationThreshold);
    assertEquals(1L, actualCreateTime);
    assertEquals(1L, actualStartTime);
    assertEquals(3, actualMaxSamplingCount);
  }
}
