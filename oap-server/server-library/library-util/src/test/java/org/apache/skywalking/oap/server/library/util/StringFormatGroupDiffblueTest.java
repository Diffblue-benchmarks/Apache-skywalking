package org.apache.skywalking.oap.server.library.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.apache.skywalking.oap.server.library.util.StringFormatGroup.FormatResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class StringFormatGroupDiffblueTest {
  /**
   * Test {@link StringFormatGroup#StringFormatGroup(int)}.
   * <ul>
   *   <li>When three.</li>
   *   <li>Then return format {@code String} Name is {@code String}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StringFormatGroup#StringFormatGroup(int)}
   */
  @Test
  @DisplayName("Test new StringFormatGroup(int); when three; then return format 'String' Name is 'String'")
  @Tag("MaintainedByDiffblue")
  void testNewStringFormatGroup_whenThree_thenReturnFormatStringNameIsString() {
    // Arrange, Act and Assert
    FormatResult formatResult = (new StringFormatGroup(3)).format("String");
    assertEquals("String", formatResult.getName());
    assertEquals("String", formatResult.getReplacedName());
    assertFalse(formatResult.isMatch());
  }

  /**
   * Test {@link StringFormatGroup#addRule(String, String)}.
   * <p>
   * Method under test: {@link StringFormatGroup#addRule(String, String)}
   */
  @Test
  @DisplayName("Test addRule(String, String)")
  @Tag("MaintainedByDiffblue")
  void testAddRule() {
    // Arrange
    StringFormatGroup stringFormatGroup = new StringFormatGroup(3);
    stringFormatGroup.addRule("org.apache.skywalking.oap.server.library.util.StringFormatGroup$PatternRule", "Name");
    stringFormatGroup.addRule("Name", ".*");

    // Act
    stringFormatGroup.addRule("Name", ".*");

    // Assert that nothing has changed
    FormatResult formatResult = stringFormatGroup.format("String");
    assertEquals("Name", formatResult.getReplacedName());
    assertTrue(formatResult.isMatch());
  }

  /**
   * Test {@link StringFormatGroup#addRule(String, String)}.
   * <ul>
   *   <li>Given {@link StringFormatGroup#StringFormatGroup(int)} with size is three.</li>
   * </ul>
   * <p>
   * Method under test: {@link StringFormatGroup#addRule(String, String)}
   */
  @Test
  @DisplayName("Test addRule(String, String); given StringFormatGroup(int) with size is three")
  @Tag("MaintainedByDiffblue")
  void testAddRule_givenStringFormatGroupWithSizeIsThree() {
    // Arrange
    StringFormatGroup stringFormatGroup = new StringFormatGroup(3);

    // Act
    stringFormatGroup.addRule("Name", ".*");

    // Assert
    FormatResult formatResult = stringFormatGroup.format("String");
    assertEquals("Name", formatResult.getReplacedName());
    assertTrue(formatResult.isMatch());
  }

  /**
   * Test {@link StringFormatGroup#addRule(String, String)}.
   * <ul>
   *   <li>Given {@link StringFormatGroup#StringFormatGroup(int)} with size is three addRule {@code Name} and {@code .*}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StringFormatGroup#addRule(String, String)}
   */
  @Test
  @DisplayName("Test addRule(String, String); given StringFormatGroup(int) with size is three addRule 'Name' and '.*'")
  @Tag("MaintainedByDiffblue")
  void testAddRule_givenStringFormatGroupWithSizeIsThreeAddRuleNameAndDotAsterisk() {
    // Arrange
    StringFormatGroup stringFormatGroup = new StringFormatGroup(3);
    stringFormatGroup.addRule("Name", ".*");

    // Act
    stringFormatGroup.addRule("Name", ".*");

    // Assert that nothing has changed
    FormatResult formatResult = stringFormatGroup.format("String");
    assertEquals("Name", formatResult.getReplacedName());
    assertTrue(formatResult.isMatch());
  }

  /**
   * Test {@link StringFormatGroup#format(String)}.
   * <ul>
   *   <li>Given {@link StringFormatGroup#StringFormatGroup(int)} with size is three addRule {@code Name} and {@code U}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StringFormatGroup#format(String)}
   */
  @Test
  @DisplayName("Test format(String); given StringFormatGroup(int) with size is three addRule 'Name' and 'U'")
  @Tag("MaintainedByDiffblue")
  void testFormat_givenStringFormatGroupWithSizeIsThreeAddRuleNameAndU() {
    // Arrange
    StringFormatGroup stringFormatGroup = new StringFormatGroup(3);
    stringFormatGroup.addRule("Name", "U");
    stringFormatGroup.addRule("Name", ".*");

    // Act
    FormatResult actualFormatResult = stringFormatGroup.format("String");

    // Assert
    assertEquals("String", actualFormatResult.getName());
    assertEquals("String", actualFormatResult.getReplacedName());
    assertFalse(actualFormatResult.isMatch());
  }

  /**
   * Test {@link StringFormatGroup#format(String)}.
   * <ul>
   *   <li>Given {@link StringFormatGroup#StringFormatGroup(int)} with size is three.</li>
   *   <li>Then return ReplacedName is {@code String}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StringFormatGroup#format(String)}
   */
  @Test
  @DisplayName("Test format(String); given StringFormatGroup(int) with size is three; then return ReplacedName is 'String'")
  @Tag("MaintainedByDiffblue")
  void testFormat_givenStringFormatGroupWithSizeIsThree_thenReturnReplacedNameIsString() {
    // Arrange and Act
    FormatResult actualFormatResult = (new StringFormatGroup(3)).format("String");

    // Assert
    assertEquals("String", actualFormatResult.getName());
    assertEquals("String", actualFormatResult.getReplacedName());
    assertFalse(actualFormatResult.isMatch());
  }

  /**
   * Test {@link StringFormatGroup#format(String)}.
   * <ul>
   *   <li>Then return ReplacedName is {@code Name}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StringFormatGroup#format(String)}
   */
  @Test
  @DisplayName("Test format(String); then return ReplacedName is 'Name'")
  @Tag("MaintainedByDiffblue")
  void testFormat_thenReturnReplacedNameIsName() {
    // Arrange
    StringFormatGroup stringFormatGroup = new StringFormatGroup(3);
    stringFormatGroup.addRule("Name", ".*");

    // Act
    FormatResult actualFormatResult = stringFormatGroup.format("String");

    // Assert
    assertEquals("Name", actualFormatResult.getReplacedName());
    assertEquals("String", actualFormatResult.getName());
    assertTrue(actualFormatResult.isMatch());
  }
}
