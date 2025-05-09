package org.apache.skywalking.oap.server.library.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ConnectUtilsDiffblueTest {
  /**
   * Test {@link ConnectUtils#parse(String)}.
   * <ul>
   *   <li>When {@code :42}.</li>
   *   <li>Then return size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link ConnectUtils#parse(String)}
   */
  @Test
  @DisplayName("Test parse(String); when ':42'; then return size is one")
  @Tag("MaintainedByDiffblue")
  void testParse_when42_thenReturnSizeIsOne() throws ConnectStringParseException {
    // Arrange and Act
    List<Address> actualParseResult = ConnectUtils.parse(":42");

    // Assert
    assertEquals(1, actualParseResult.size());
    Address getResult = actualParseResult.get(0);
    assertEquals("", getResult.getHost());
    assertEquals(42, getResult.getPort());
  }

  /**
   * Test {@link ConnectUtils#parse(String)}.
   * <ul>
   *   <li>When {@code ,}.</li>
   *   <li>Then throw {@link ConnectStringParseException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ConnectUtils#parse(String)}
   */
  @Test
  @DisplayName("Test parse(String); when ','; then throw ConnectStringParseException")
  @Tag("MaintainedByDiffblue")
  void testParse_whenComma_thenThrowConnectStringParseException() throws ConnectStringParseException {
    // Arrange, Act and Assert
    assertThrows(ConnectStringParseException.class, () -> ConnectUtils.parse(","));
  }

  /**
   * Test {@link ConnectUtils#parse(String)}.
   * <ul>
   *   <li>When {@code Connect String}.</li>
   *   <li>Then throw {@link ConnectStringParseException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ConnectUtils#parse(String)}
   */
  @Test
  @DisplayName("Test parse(String); when 'Connect String'; then throw ConnectStringParseException")
  @Tag("MaintainedByDiffblue")
  void testParse_whenConnectString_thenThrowConnectStringParseException() throws ConnectStringParseException {
    // Arrange, Act and Assert
    assertThrows(ConnectStringParseException.class, () -> ConnectUtils.parse("Connect String"));
  }

  /**
   * Test {@link ConnectUtils#parse(String)}.
   * <ul>
   *   <li>When {@code :Invalid connect string pattern.}.</li>
   *   <li>Then throw {@link ConnectStringParseException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ConnectUtils#parse(String)}
   */
  @Test
  @DisplayName("Test parse(String); when ':Invalid connect string pattern.'; then throw ConnectStringParseException")
  @Tag("MaintainedByDiffblue")
  void testParse_whenInvalidConnectStringPattern_thenThrowConnectStringParseException()
      throws ConnectStringParseException {
    // Arrange, Act and Assert
    assertThrows(ConnectStringParseException.class, () -> ConnectUtils.parse(":Invalid connect string pattern."));
  }

  /**
   * Test {@link ConnectUtils#parse(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ConnectStringParseException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ConnectUtils#parse(String)}
   */
  @Test
  @DisplayName("Test parse(String); when 'null'; then throw ConnectStringParseException")
  @Tag("MaintainedByDiffblue")
  void testParse_whenNull_thenThrowConnectStringParseException() throws ConnectStringParseException {
    // Arrange, Act and Assert
    assertThrows(ConnectStringParseException.class, () -> ConnectUtils.parse(null));
  }
}
