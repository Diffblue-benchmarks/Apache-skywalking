package org.apache.skywalking.apm.network.logging.v3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.apache.skywalking.apm.network.logging.v3.LogDataBody.ContentCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class LogDataBodyDiffblueTest {
  /**
   * Test ContentCase {@link ContentCase#forNumber(int)}.
   * <ul>
   *   <li>When forty-two.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ContentCase#forNumber(int)}
   */
  @Test
  @DisplayName("Test ContentCase forNumber(int); when forty-two; then return 'null'")
  @Tag("MaintainedByDiffblue")
  void testContentCaseForNumber_whenFortyTwo_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(ContentCase.forNumber(42));
  }

  /**
   * Test ContentCase {@link ContentCase#forNumber(int)}.
   * <ul>
   *   <li>When four.</li>
   *   <li>Then return {@code YAML}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ContentCase#forNumber(int)}
   */
  @Test
  @DisplayName("Test ContentCase forNumber(int); when four; then return 'YAML'")
  @Tag("MaintainedByDiffblue")
  void testContentCaseForNumber_whenFour_thenReturnYaml() {
    // Arrange, Act and Assert
    assertEquals(ContentCase.YAML, ContentCase.forNumber(4));
  }

  /**
   * Test ContentCase {@link ContentCase#forNumber(int)}.
   * <ul>
   *   <li>When three.</li>
   *   <li>Then return {@code JSON}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ContentCase#forNumber(int)}
   */
  @Test
  @DisplayName("Test ContentCase forNumber(int); when three; then return 'JSON'")
  @Tag("MaintainedByDiffblue")
  void testContentCaseForNumber_whenThree_thenReturnJson() {
    // Arrange, Act and Assert
    assertEquals(ContentCase.JSON, ContentCase.forNumber(3));
  }

  /**
   * Test ContentCase {@link ContentCase#forNumber(int)}.
   * <ul>
   *   <li>When two.</li>
   *   <li>Then return {@code TEXT}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ContentCase#forNumber(int)}
   */
  @Test
  @DisplayName("Test ContentCase forNumber(int); when two; then return 'TEXT'")
  @Tag("MaintainedByDiffblue")
  void testContentCaseForNumber_whenTwo_thenReturnText() {
    // Arrange, Act and Assert
    assertEquals(ContentCase.TEXT, ContentCase.forNumber(2));
  }

  /**
   * Test ContentCase {@link ContentCase#forNumber(int)}.
   * <ul>
   *   <li>When zero.</li>
   *   <li>Then return {@code CONTENT_NOT_SET}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ContentCase#forNumber(int)}
   */
  @Test
  @DisplayName("Test ContentCase forNumber(int); when zero; then return 'CONTENT_NOT_SET'")
  @Tag("MaintainedByDiffblue")
  void testContentCaseForNumber_whenZero_thenReturnContentNotSet() {
    // Arrange, Act and Assert
    assertEquals(ContentCase.CONTENT_NOT_SET, ContentCase.forNumber(0));
  }

  /**
   * Test ContentCase {@link ContentCase#getNumber()}.
   * <p>
   * Method under test: {@link ContentCase#getNumber()}
   */
  @Test
  @DisplayName("Test ContentCase getNumber()")
  @Tag("MaintainedByDiffblue")
  void testContentCaseGetNumber() {
    // Arrange, Act and Assert
    assertEquals(2, ContentCase.valueOf("TEXT").getNumber());
  }

  /**
   * Test ContentCase {@link ContentCase#valueOf(int)} with {@code value}.
   * <ul>
   *   <li>When forty-two.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ContentCase#valueOf(int)}
   */
  @Test
  @DisplayName("Test ContentCase valueOf(int) with 'value'; when forty-two; then return 'null'")
  @Tag("MaintainedByDiffblue")
  void testContentCaseValueOfWithValue_whenFortyTwo_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(ContentCase.valueOf(42));
  }

  /**
   * Test ContentCase {@link ContentCase#valueOf(int)} with {@code value}.
   * <ul>
   *   <li>When four.</li>
   *   <li>Then return {@code YAML}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ContentCase#valueOf(int)}
   */
  @Test
  @DisplayName("Test ContentCase valueOf(int) with 'value'; when four; then return 'YAML'")
  @Tag("MaintainedByDiffblue")
  void testContentCaseValueOfWithValue_whenFour_thenReturnYaml() {
    // Arrange, Act and Assert
    assertEquals(ContentCase.YAML, ContentCase.valueOf(4));
  }

  /**
   * Test ContentCase {@link ContentCase#valueOf(int)} with {@code value}.
   * <ul>
   *   <li>When three.</li>
   *   <li>Then return {@code JSON}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ContentCase#valueOf(int)}
   */
  @Test
  @DisplayName("Test ContentCase valueOf(int) with 'value'; when three; then return 'JSON'")
  @Tag("MaintainedByDiffblue")
  void testContentCaseValueOfWithValue_whenThree_thenReturnJson() {
    // Arrange, Act and Assert
    assertEquals(ContentCase.JSON, ContentCase.valueOf(3));
  }

  /**
   * Test ContentCase {@link ContentCase#valueOf(int)} with {@code value}.
   * <ul>
   *   <li>When two.</li>
   *   <li>Then return {@code TEXT}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ContentCase#valueOf(int)}
   */
  @Test
  @DisplayName("Test ContentCase valueOf(int) with 'value'; when two; then return 'TEXT'")
  @Tag("MaintainedByDiffblue")
  void testContentCaseValueOfWithValue_whenTwo_thenReturnText() {
    // Arrange, Act and Assert
    assertEquals(ContentCase.TEXT, ContentCase.valueOf(2));
  }

  /**
   * Test ContentCase {@link ContentCase#valueOf(int)} with {@code value}.
   * <ul>
   *   <li>When zero.</li>
   *   <li>Then return {@code CONTENT_NOT_SET}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ContentCase#valueOf(int)}
   */
  @Test
  @DisplayName("Test ContentCase valueOf(int) with 'value'; when zero; then return 'CONTENT_NOT_SET'")
  @Tag("MaintainedByDiffblue")
  void testContentCaseValueOfWithValue_whenZero_thenReturnContentNotSet() {
    // Arrange, Act and Assert
    assertEquals(ContentCase.CONTENT_NOT_SET, ContentCase.valueOf(0));
  }
}
