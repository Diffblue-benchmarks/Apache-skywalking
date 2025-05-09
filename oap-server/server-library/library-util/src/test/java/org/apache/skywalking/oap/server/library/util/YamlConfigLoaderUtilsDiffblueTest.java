package org.apache.skywalking.oap.server.library.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Properties;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.yaml.snakeyaml.Yaml;

class YamlConfigLoaderUtilsDiffblueTest {
  /**
   * Test {@link YamlConfigLoaderUtils#replacePropertyAndLog(String, Object, Properties, Object, Yaml)}.
   * <ul>
   *   <li>Given {@code Load}.</li>
   *   <li>When {@link Yaml} {@link Yaml#load(String)} return {@code Load}.</li>
   *   <li>Then calls {@link Yaml#load(String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link YamlConfigLoaderUtils#replacePropertyAndLog(String, Object, Properties, Object, Yaml)}
   */
  @Test
  @DisplayName("Test replacePropertyAndLog(String, Object, Properties, Object, Yaml); given 'Load'; when Yaml load(String) return 'Load'; then calls load(String)")
  @Tag("MaintainedByDiffblue")
  void testReplacePropertyAndLog_givenLoad_whenYamlLoadReturnLoad_thenCallsLoad() {
    // Arrange
    Properties target = new Properties();
    Yaml yaml = mock(Yaml.class);
    when(yaml.load(Mockito.<String>any())).thenReturn("Load");

    // Act
    YamlConfigLoaderUtils.replacePropertyAndLog("Property Name", "Property Value", target, "Provider Name", yaml);

    // Assert
    verify(yaml).load(eq("Property Value"));
  }

  /**
   * Test {@link YamlConfigLoaderUtils#convertValueString(String, Yaml)}.
   * <p>
   * Method under test: {@link YamlConfigLoaderUtils#convertValueString(String, Yaml)}
   */
  @Test
  @DisplayName("Test convertValueString(String, Yaml)")
  @Tag("MaintainedByDiffblue")
  void testConvertValueString() {
    // Arrange, Act and Assert
    assertEquals("org.apache.skywalking.oap.server.library.util.YamlConfigLoaderUtils", YamlConfigLoaderUtils
        .convertValueString("org.apache.skywalking.oap.server.library.util.YamlConfigLoaderUtils", new Yaml()));
  }

  /**
   * Test {@link YamlConfigLoaderUtils#convertValueString(String, Yaml)}.
   * <ul>
   *   <li>When {@code 0b_00}.</li>
   *   <li>Then return intValue is zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link YamlConfigLoaderUtils#convertValueString(String, Yaml)}
   */
  @Test
  @DisplayName("Test convertValueString(String, Yaml); when '0b_00'; then return intValue is zero")
  @Tag("MaintainedByDiffblue")
  void testConvertValueString_when0b00_thenReturnIntValueIsZero() {
    // Arrange, Act and Assert
    assertEquals(0, ((Integer) YamlConfigLoaderUtils.convertValueString("0b_00", new Yaml())).intValue());
  }

  /**
   * Test {@link YamlConfigLoaderUtils#convertValueString(String, Yaml)}.
   * <ul>
   *   <li>When {@code 42}.</li>
   *   <li>Then return intValue is forty-two.</li>
   * </ul>
   * <p>
   * Method under test: {@link YamlConfigLoaderUtils#convertValueString(String, Yaml)}
   */
  @Test
  @DisplayName("Test convertValueString(String, Yaml); when '42'; then return intValue is forty-two")
  @Tag("MaintainedByDiffblue")
  void testConvertValueString_when42_thenReturnIntValueIsFortyTwo() {
    // Arrange, Act and Assert
    assertEquals(42, ((Integer) YamlConfigLoaderUtils.convertValueString("42", new Yaml())).intValue());
  }

  /**
   * Test {@link YamlConfigLoaderUtils#convertValueString(String, Yaml)}.
   * <ul>
   *   <li>When empty string.</li>
   *   <li>Then return empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link YamlConfigLoaderUtils#convertValueString(String, Yaml)}
   */
  @Test
  @DisplayName("Test convertValueString(String, Yaml); when empty string; then return empty string")
  @Tag("MaintainedByDiffblue")
  void testConvertValueString_whenEmptyString_thenReturnEmptyString() {
    // Arrange, Act and Assert
    assertEquals("", YamlConfigLoaderUtils.convertValueString("", new Yaml()));
  }

  /**
   * Test {@link YamlConfigLoaderUtils#convertValueString(String, Yaml)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link YamlConfigLoaderUtils#convertValueString(String, Yaml)}
   */
  @Test
  @DisplayName("Test convertValueString(String, Yaml); when 'null'; then return '42'")
  @Tag("MaintainedByDiffblue")
  void testConvertValueString_whenNull_thenReturn42() {
    // Arrange, Act and Assert
    assertEquals("42", YamlConfigLoaderUtils.convertValueString("42", null));
  }

  /**
   * Test {@link YamlConfigLoaderUtils#convertValueString(String, Yaml)}.
   * <ul>
   *   <li>When {@code _}.</li>
   *   <li>Then return {@code _}.</li>
   * </ul>
   * <p>
   * Method under test: {@link YamlConfigLoaderUtils#convertValueString(String, Yaml)}
   */
  @Test
  @DisplayName("Test convertValueString(String, Yaml); when '_'; then return '_'")
  @Tag("MaintainedByDiffblue")
  void testConvertValueString_whenUnderscore_thenReturnUnderscore() {
    // Arrange, Act and Assert
    assertEquals("_", YamlConfigLoaderUtils.convertValueString("_", new Yaml()));
  }

  /**
   * Test {@link YamlConfigLoaderUtils#convertValueString(String, Yaml)}.
   * <ul>
   *   <li>When {@code Value String}.</li>
   *   <li>Then return {@code Value String}.</li>
   * </ul>
   * <p>
   * Method under test: {@link YamlConfigLoaderUtils#convertValueString(String, Yaml)}
   */
  @Test
  @DisplayName("Test convertValueString(String, Yaml); when 'Value String'; then return 'Value String'")
  @Tag("MaintainedByDiffblue")
  void testConvertValueString_whenValueString_thenReturnValueString() {
    // Arrange, Act and Assert
    assertEquals("Value String", YamlConfigLoaderUtils.convertValueString("Value String", new Yaml()));
  }

  /**
   * Test {@link YamlConfigLoaderUtils#getDeclaredField(Class, String)}.
   * <ul>
   *   <li>Then throw {@link NoSuchFieldException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link YamlConfigLoaderUtils#getDeclaredField(Class, String)}
   */
  @Test
  @DisplayName("Test getDeclaredField(Class, String); then throw NoSuchFieldException")
  @Tag("MaintainedByDiffblue")
  void testGetDeclaredField_thenThrowNoSuchFieldException() throws NoSuchFieldException {
    // Arrange
    Class<YamlConfigLoaderUtils> destClass = YamlConfigLoaderUtils.class;

    // Act and Assert
    assertThrows(NoSuchFieldException.class, () -> YamlConfigLoaderUtils.getDeclaredField(destClass, "Field Name"));
  }

  /**
   * Test {@link YamlConfigLoaderUtils#getDeclaredField(Class, String)}.
   * <ul>
   *   <li>When {@code Object}.</li>
   *   <li>Then throw {@link NoSuchFieldException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link YamlConfigLoaderUtils#getDeclaredField(Class, String)}
   */
  @Test
  @DisplayName("Test getDeclaredField(Class, String); when 'java.lang.Object'; then throw NoSuchFieldException")
  @Tag("MaintainedByDiffblue")
  void testGetDeclaredField_whenJavaLangObject_thenThrowNoSuchFieldException() throws NoSuchFieldException {
    // Arrange
    Class<Object> destClass = Object.class;

    // Act and Assert
    assertThrows(NoSuchFieldException.class, () -> YamlConfigLoaderUtils.getDeclaredField(destClass, "Field Name"));
  }

  /**
   * Test {@link YamlConfigLoaderUtils#getDeclaredField(Class, String)}.
   * <ul>
   *   <li>When {@code log}.</li>
   *   <li>Then return Name is {@code log}.</li>
   * </ul>
   * <p>
   * Method under test: {@link YamlConfigLoaderUtils#getDeclaredField(Class, String)}
   */
  @Test
  @DisplayName("Test getDeclaredField(Class, String); when 'log'; then return Name is 'log'")
  @Tag("MaintainedByDiffblue")
  void testGetDeclaredField_whenLog_thenReturnNameIsLog() throws NoSuchFieldException {
    // Arrange
    Class<YamlConfigLoaderUtils> destClass = YamlConfigLoaderUtils.class;

    // Act
    Field actualDeclaredField = YamlConfigLoaderUtils.getDeclaredField(destClass, "log");

    // Assert
    assertEquals("log", actualDeclaredField.getName());
    assertEquals(
        "private static final org.slf4j.Logger org.apache.skywalking.oap.server.library.util.YamlConfigLoaderUtils"
            + ".log",
        actualDeclaredField.toGenericString());
    Annotation[] annotations = actualDeclaredField.getAnnotations();
    assertEquals(0, annotations.length);
    assertEquals(26, actualDeclaredField.getModifiers());
    assertFalse(actualDeclaredField.isAccessible());
    assertFalse(actualDeclaredField.isEnumConstant());
    assertFalse(actualDeclaredField.isSynthetic());
    Class<YamlConfigLoaderUtils> expectedDeclaringClass = YamlConfigLoaderUtils.class;
    assertEquals(expectedDeclaringClass, actualDeclaredField.getDeclaringClass());
    assertSame(annotations, actualDeclaredField.getDeclaredAnnotations());
    assertSame(actualDeclaredField.getGenericType(), actualDeclaredField.getType());
  }
}
