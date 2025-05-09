package org.apache.skywalking.oap.server.library.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class StringUtilDiffblueTest {
  /**
   * Test {@link StringUtil#isEmpty(String)}.
   * <ul>
   *   <li>When empty string.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StringUtil#isEmpty(String)}
   */
  @Test
  @DisplayName("Test isEmpty(String); when empty string; then return 'true'")
  @Tag("MaintainedByDiffblue")
  void testIsEmpty_whenEmptyString_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue(StringUtil.isEmpty(""));
  }

  /**
   * Test {@link StringUtil#isEmpty(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StringUtil#isEmpty(String)}
   */
  @Test
  @DisplayName("Test isEmpty(String); when 'null'; then return 'true'")
  @Tag("MaintainedByDiffblue")
  void testIsEmpty_whenNull_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue(StringUtil.isEmpty(null));
  }

  /**
   * Test {@link StringUtil#isEmpty(String)}.
   * <ul>
   *   <li>When {@code Str}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StringUtil#isEmpty(String)}
   */
  @Test
  @DisplayName("Test isEmpty(String); when 'Str'; then return 'false'")
  @Tag("MaintainedByDiffblue")
  void testIsEmpty_whenStr_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(StringUtil.isEmpty("Str"));
  }

  /**
   * Test {@link StringUtil#isNotEmpty(String)}.
   * <ul>
   *   <li>When empty string.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StringUtil#isNotEmpty(String)}
   */
  @Test
  @DisplayName("Test isNotEmpty(String); when empty string; then return 'false'")
  @Tag("MaintainedByDiffblue")
  void testIsNotEmpty_whenEmptyString_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(StringUtil.isNotEmpty(""));
  }

  /**
   * Test {@link StringUtil#isNotEmpty(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StringUtil#isNotEmpty(String)}
   */
  @Test
  @DisplayName("Test isNotEmpty(String); when 'null'; then return 'false'")
  @Tag("MaintainedByDiffblue")
  void testIsNotEmpty_whenNull_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(StringUtil.isNotEmpty(null));
  }

  /**
   * Test {@link StringUtil#isNotEmpty(String)}.
   * <ul>
   *   <li>When {@code Str}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StringUtil#isNotEmpty(String)}
   */
  @Test
  @DisplayName("Test isNotEmpty(String); when 'Str'; then return 'true'")
  @Tag("MaintainedByDiffblue")
  void testIsNotEmpty_whenStr_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue(StringUtil.isNotEmpty("Str"));
  }

  /**
   * Test {@link StringUtil#isBlank(String)}.
   * <ul>
   *   <li>When empty string.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StringUtil#isBlank(String)}
   */
  @Test
  @DisplayName("Test isBlank(String); when empty string; then return 'true'")
  @Tag("MaintainedByDiffblue")
  void testIsBlank_whenEmptyString_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue(StringUtil.isBlank(""));
  }

  /**
   * Test {@link StringUtil#isBlank(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StringUtil#isBlank(String)}
   */
  @Test
  @DisplayName("Test isBlank(String); when 'null'; then return 'true'")
  @Tag("MaintainedByDiffblue")
  void testIsBlank_whenNull_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue(StringUtil.isBlank(null));
  }

  /**
   * Test {@link StringUtil#isBlank(String)}.
   * <ul>
   *   <li>When {@code Str}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StringUtil#isBlank(String)}
   */
  @Test
  @DisplayName("Test isBlank(String); when 'Str'; then return 'false'")
  @Tag("MaintainedByDiffblue")
  void testIsBlank_whenStr_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(StringUtil.isBlank("Str"));
  }

  /**
   * Test {@link StringUtil#isNotBlank(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StringUtil#isNotBlank(String)}
   */
  @Test
  @DisplayName("Test isNotBlank(String); when 'null'; then return 'false'")
  @Tag("MaintainedByDiffblue")
  void testIsNotBlank_whenNull_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(StringUtil.isNotBlank(null));
  }

  /**
   * Test {@link StringUtil#isNotBlank(String)}.
   * <ul>
   *   <li>When space.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StringUtil#isNotBlank(String)}
   */
  @Test
  @DisplayName("Test isNotBlank(String); when space; then return 'false'")
  @Tag("MaintainedByDiffblue")
  void testIsNotBlank_whenSpace_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(StringUtil.isNotBlank(" "));
  }

  /**
   * Test {@link StringUtil#isNotBlank(String)}.
   * <ul>
   *   <li>When {@code Str}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StringUtil#isNotBlank(String)}
   */
  @Test
  @DisplayName("Test isNotBlank(String); when 'Str'; then return 'true'")
  @Tag("MaintainedByDiffblue")
  void testIsNotBlank_whenStr_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue(StringUtil.isNotBlank("Str"));
  }

  /**
   * Test {@link StringUtil#join(char, String[])}.
   * <ul>
   *   <li>When {@code A}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StringUtil#join(char, String[])}
   */
  @Test
  @DisplayName("Test join(char, String[]); when 'A'; then return 'null'")
  @Tag("MaintainedByDiffblue")
  void testJoin_whenA_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(StringUtil.join('A'));
  }

  /**
   * Test {@link StringUtil#join(char, String[])}.
   * <ul>
   *   <li>When {@code null} and {@code 42}.</li>
   *   <li>Then return {@code A42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StringUtil#join(char, String[])}
   */
  @Test
  @DisplayName("Test join(char, String[]); when 'null' and '42'; then return 'A42'")
  @Tag("MaintainedByDiffblue")
  void testJoin_whenNullAnd42_thenReturnA42() {
    // Arrange, Act and Assert
    assertEquals("A42", StringUtil.join('A', null, "42"));
  }

  /**
   * Test {@link StringUtil#join(char, String[])}.
   * <ul>
   *   <li>When {@code Strings} and {@code 42}.</li>
   *   <li>Then return {@code StringsA42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StringUtil#join(char, String[])}
   */
  @Test
  @DisplayName("Test join(char, String[]); when 'Strings' and '42'; then return 'StringsA42'")
  @Tag("MaintainedByDiffblue")
  void testJoin_whenStringsAnd42_thenReturnStringsA42() {
    // Arrange, Act and Assert
    assertEquals("StringsA42", StringUtil.join('A', "Strings", "42"));
  }

  /**
   * Test {@link StringUtil#join(char, String[])}.
   * <ul>
   *   <li>When {@code Strings} and empty string.</li>
   *   <li>Then return {@code StringsA}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StringUtil#join(char, String[])}
   */
  @Test
  @DisplayName("Test join(char, String[]); when 'Strings' and empty string; then return 'StringsA'")
  @Tag("MaintainedByDiffblue")
  void testJoin_whenStringsAndEmptyString_thenReturnStringsA() {
    // Arrange, Act and Assert
    assertEquals("StringsA", StringUtil.join('A', "Strings", ""));
  }

  /**
   * Test {@link StringUtil#join(char, String[])}.
   * <ul>
   *   <li>When {@code Strings} and {@code null}.</li>
   *   <li>Then return {@code StringsA}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StringUtil#join(char, String[])}
   */
  @Test
  @DisplayName("Test join(char, String[]); when 'Strings' and 'null'; then return 'StringsA'")
  @Tag("MaintainedByDiffblue")
  void testJoin_whenStringsAndNull_thenReturnStringsA() {
    // Arrange, Act and Assert
    assertEquals("StringsA", StringUtil.join('A', "Strings", null));
  }

  /**
   * Test {@link StringUtil#join(char, String[])}.
   * <ul>
   *   <li>When {@code Strings}.</li>
   *   <li>Then return {@code Strings}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StringUtil#join(char, String[])}
   */
  @Test
  @DisplayName("Test join(char, String[]); when 'Strings'; then return 'Strings'")
  @Tag("MaintainedByDiffblue")
  void testJoin_whenStrings_thenReturnStrings() {
    // Arrange, Act and Assert
    assertEquals("Strings", StringUtil.join('A', "Strings"));
  }

  /**
   * Test {@link StringUtil#cut(String, int)}.
   * <ul>
   *   <li>When empty string.</li>
   *   <li>Then return empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link StringUtil#cut(String, int)}
   */
  @Test
  @DisplayName("Test cut(String, int); when empty string; then return empty string")
  @Tag("MaintainedByDiffblue")
  void testCut_whenEmptyString_thenReturnEmptyString() {
    // Arrange, Act and Assert
    assertEquals("", StringUtil.cut("", 1));
  }

  /**
   * Test {@link StringUtil#cut(String, int)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StringUtil#cut(String, int)}
   */
  @Test
  @DisplayName("Test cut(String, int); when 'null'; then return 'null'")
  @Tag("MaintainedByDiffblue")
  void testCut_whenNull_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(StringUtil.cut(null, 1));
  }

  /**
   * Test {@link StringUtil#cut(String, int)}.
   * <ul>
   *   <li>When {@code Str}.</li>
   *   <li>Then return {@code S}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StringUtil#cut(String, int)}
   */
  @Test
  @DisplayName("Test cut(String, int); when 'Str'; then return 'S'")
  @Tag("MaintainedByDiffblue")
  void testCut_whenStr_thenReturnS() {
    // Arrange, Act and Assert
    assertEquals("S", StringUtil.cut("Str", 1));
  }

  /**
   * Test {@link StringUtil#cut(String, int)}.
   * <ul>
   *   <li>When three.</li>
   *   <li>Then return {@code Str}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StringUtil#cut(String, int)}
   */
  @Test
  @DisplayName("Test cut(String, int); when three; then return 'Str'")
  @Tag("MaintainedByDiffblue")
  void testCut_whenThree_thenReturnStr() {
    // Arrange, Act and Assert
    assertEquals("Str", StringUtil.cut("Str", 3));
  }

  /**
   * Test {@link StringUtil#trim(String, char)}.
   * <ul>
   *   <li>When empty string.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StringUtil#trim(String, char)}
   */
  @Test
  @DisplayName("Test trim(String, char); when empty string; then return 'null'")
  @Tag("MaintainedByDiffblue")
  void testTrim_whenEmptyString_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(StringUtil.trim("", 'A'));
  }

  /**
   * Test {@link StringUtil#trim(String, char)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StringUtil#trim(String, char)}
   */
  @Test
  @DisplayName("Test trim(String, char); when 'null'; then return 'null'")
  @Tag("MaintainedByDiffblue")
  void testTrim_whenNull_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(StringUtil.trim(null, 'A'));
  }

  /**
   * Test {@link StringUtil#trim(String, char)}.
   * <ul>
   *   <li>When {@code r}.</li>
   *   <li>Then return {@code St}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StringUtil#trim(String, char)}
   */
  @Test
  @DisplayName("Test trim(String, char); when 'r'; then return 'St'")
  @Tag("MaintainedByDiffblue")
  void testTrim_whenR_thenReturnSt() {
    // Arrange, Act and Assert
    assertEquals("St", StringUtil.trim("Str", 'r'));
  }

  /**
   * Test {@link StringUtil#trim(String, char)}.
   * <ul>
   *   <li>When {@code S}.</li>
   *   <li>Then return {@code tr}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StringUtil#trim(String, char)}
   */
  @Test
  @DisplayName("Test trim(String, char); when 'S'; then return 'tr'")
  @Tag("MaintainedByDiffblue")
  void testTrim_whenS_thenReturnTr() {
    // Arrange, Act and Assert
    assertEquals("tr", StringUtil.trim("Str", 'S'));
  }

  /**
   * Test {@link StringUtil#trim(String, char)}.
   * <ul>
   *   <li>When {@code Str}.</li>
   *   <li>Then return {@code Str}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StringUtil#trim(String, char)}
   */
  @Test
  @DisplayName("Test trim(String, char); when 'Str'; then return 'Str'")
  @Tag("MaintainedByDiffblue")
  void testTrim_whenStr_thenReturnStr() {
    // Arrange, Act and Assert
    assertEquals("Str", StringUtil.trim("Str", 'A'));
  }
}
